package se.vgregion.portal.vap.controller.searchresult;

import com.liferay.portal.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import se.vgregion.portal.vap.controller.BaseController;
import se.vgregion.portal.vap.domain.searchresult.SearchResult;
import se.vgregion.portal.vap.domain.jpa.Bookmark;
import se.vgregion.portal.vap.domain.jpa.Flag;
import se.vgregion.portal.vap.service.*;
import se.vgregion.portal.vap.util.JsonUtil;

import javax.portlet.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the search result.
 *
 * @author Patrik Bergström
 */
@Controller
@RequestMapping(value = "VIEW")
public class SearchResultController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchResultController.class);
    private static final int SEARCH_SIZE_DEFAULT = 10;

    /**
     * Constructor.
     *
     * @param documentSearchService the {@link DocumentSearchService}
     * @param flagService           the {@link FlagService}
     * @param bookmarkService       the {@link BookmarkService}
     * @param userEventsService     the {@link UserEventsService}
     */
    @Autowired
    public SearchResultController(DocumentSearchService documentSearchService,
                                  FlagService flagService,
                                  BookmarkService bookmarkService,
                                  UserEventsService userEventsService) {
        setDocumentSearchService(documentSearchService);
        setFlagService(flagService);
        setBookmarkService(bookmarkService);
        setUserEventsService(userEventsService);
    }

    /**
     * Shows the search-result view.
     *
     * @param request  the request
     * @param response the response
     * @return the view
     */
    @RenderMapping()
    public String showSearchResult(RenderRequest request, RenderResponse response) {
    	User user = getUser(request);
    	request.setAttribute("isLoggedIn", isLoggedIn(user));
    	
        return "search-result";
    }

    /**
     * Paginate the search result.
     *
     * @param request  the request
     * @param response the response
     * @return the search-result view
     */
    @RenderMapping(params = "isPaginatorCall=true")
    public String paginate(RenderRequest request, RenderResponse response) {

        RequestUri requestUri = null;

        final User user = getUser(request);

        if (isLoggedIn(user)) {
            populateRequestWithBookmarks(request, user);
            populateRequestWithFlags(request, user);
        }
        
        request.setAttribute("isLoggedIn", isLoggedIn(user));

        String searchQuery = request.getParameter("searchQuery");
        if (searchQuery == null) {
            return "search-result";
        }

        try {
            searchQuery = URLDecoder.decode(searchQuery, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }

        requestUri = new RequestUri(searchQuery);

        SizeOffsetAndPageComponentOffset sizeOffsetAndPageComponentOffset = handlePaginatorParameters(request);

        int searchOffset = sizeOffsetAndPageComponentOffset.getSearchOffset();
        int deltaSize = sizeOffsetAndPageComponentOffset.getDeltaSize();
        int offset = sizeOffsetAndPageComponentOffset.getOffset();

        request.setAttribute("searchOffset", searchOffset);
        request.setAttribute("searchDelta", deltaSize);

        LOGGER.debug(("FLAG - SearchResultController - searchOffset is: " + searchOffset + ", offset is: " + offset
                + ", deltaSize is: " + deltaSize));

        SearchResult searchResult;
        try {
            requestUri.setParameter("offset", offset + "");
            requestUri.setParameter("hits", deltaSize + "");

            searchResult = getDocumentSearchService().search(requestUri.toString());

            request.setAttribute("searchResult", searchResult);
            request.setAttribute("searchQuery", URLEncoder.encode(requestUri.toString(), "UTF-8"));
            request.setAttribute("searchTerm", request.getParameter("searchTerm"));
        } catch (DocumentSearchServiceException e) {
            LOGGER.error(e.getMessage(), e);
            request.setAttribute("errorMessage", "Sökningen misslyckades. Var vänlig försök senare.");
            return "search-result";
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            return "search-result";
        }


        return "search-result";
    }

    /**
     * Show the search result when a search is already made, without re-searching.
     *
     * @param request  the request
     * @param response the response
     * @return the search-result view
     */
    @RenderMapping(params = "searchResultJson")
    public String showSearchResultWithGivenSearchResult(RenderRequest request, RenderResponse response) {
        final User user = getUser(request);

        boolean loggedIn = isLoggedIn(user);
        if (loggedIn) {
            populateRequestWithBookmarks(request, user);
            populateRequestWithFlags(request, user);
        }

        request.setAttribute("isLoggedIn", loggedIn);

        String searchResultJson = request.getParameter("searchResultJson");
        String searchTerm = request.getParameter("searchTerm");
        String searchQuery = request.getParameter("searchQuery");

        try {
            SearchResult parsed = JsonUtil.parse(searchResultJson);
            request.setAttribute("searchResult", parsed);

            SizeOffsetAndPageComponentOffset paginatorValues = handlePaginatorParameters(request);
            int searchOffset = paginatorValues.getSearchOffset();
            int deltaSize = paginatorValues.getDeltaSize();

            request.setAttribute("searchOffset", searchOffset);
            request.setAttribute("searchDelta", deltaSize);
            request.setAttribute("searchTerm", searchTerm);
            request.setAttribute("searchQuery", searchQuery);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return "search-result";
    }

    /**
     * Log the document click-event so it will be added to "latest documents" for a user.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException IOException
     */
    @ActionMapping(params = "action=interceptDocumentSourceClick")
    public void interceptDocumentSourceClick(ActionRequest request, ActionResponse response) throws IOException {

        logDocumentEvent(request);

        String targetUrl = request.getParameter("targetUrl");
        response.sendRedirect(targetUrl);
    }

    /**
     * Methods that consumes "{http://liferay.com/events}vap.searchResultJson" events and sets the searchResultJson
     * renderParameter.
     *
     * @param request  request
     * @param response response
     */
    @EventMapping("{http://liferay.com/events}vap.searchResultJson")
    public void setSearchResult(EventRequest request, EventResponse response) {
        String value = (String) request.getEvent().getValue();
        response.setRenderParameter("searchResultJson", value);
    }

    /**
     * Add a {@link Bookmark}.
     *
     * @param request the request
     */
    @ResourceMapping("bookmarkEntry")
    public void bookmarkEntry(ResourceRequest request) {
        User user = getUser(request);

        String documentId = request.getParameter("documentId");
        String folderName = request.getParameter("folderName");
        
        getBookmarkService().addBookmark(user.getUserId(), documentId, folderName);
    }

    /**
     * Toggle a flag.
     *
     * @param request the request
     */
    @ResourceMapping("toggleFlag")
    public void toggleFlag(ResourceRequest request) {
        User user = getUser(request);

        String documentId = request.getParameter("documentId");
        
        getFlagService().toggleFlag(user.getUserId(), documentId);
    }

    private SizeOffsetAndPageComponentOffset handlePaginatorParameters(PortletRequest request) {
        return new SizeOffsetAndPageComponentOffset(request).invoke();
    }

    private void populateRequestWithBookmarks(PortletRequest request, User user) {
        // todo the bookmarks may be better to load by a common portlet and set via public render parameter json
        // serialized
        Collection<Bookmark> bookmarks = getBookmarkService().findBookmarksByUserId(user.getUserId());

        // Map documentId to bookmark
        Map<String, Bookmark> documentIdBookmark = new HashMap<String, Bookmark>();
        for (Bookmark bookmark : bookmarks) {
            documentIdBookmark.put(bookmark.getDocumentId(), bookmark);
        }
        request.setAttribute("bookmarks", documentIdBookmark);
    }
    
    private void populateRequestWithFlags(PortletRequest request, User user) {
        // todo the flags may be better to load by a common portlet and set via public render parameter json
        // serialized
    	
    	Map<String, Flag> flags = getFlagService().findUserFlagsMap(user.getUserId());
    	
        request.setAttribute("flags", flags);
    }
    

    private static class SizeOffsetAndPageComponentOffset {
        private PortletRequest request;
        private int deltaSize;
        private int offset;
        // searchOffset is the value that the paginator component uses and it corresponds to the paginator page number
        private int searchOffset;

        public SizeOffsetAndPageComponentOffset(PortletRequest request) {
            this.request = request;
        }

        public int getDeltaSize() {
            return deltaSize;
        }

        public int getOffset() {
            return offset;
        }

        public int getSearchOffset() {
            return searchOffset;
        }

        public SizeOffsetAndPageComponentOffset invoke() {
            boolean isPaginatorCall = false;
            String isPaginatorCallString = request.getParameter("isPaginatorCall");

            if ("true".equals(isPaginatorCallString)) {
                isPaginatorCall = true;
            }

            LOGGER.debug("FLAG - SearchResultController - isPaginatorCall: " + isPaginatorCall);

            String searchDelta = request.getParameter("searchDelta");
            deltaSize = SEARCH_SIZE_DEFAULT;
            if (searchDelta != null) {
                deltaSize = Integer.valueOf(searchDelta);
            }

            offset = 0;
            searchOffset = 1;
            if (isPaginatorCall) {
                String searchOffsetString = request.getParameter("searchOffset");

                if (searchOffsetString != null) {
                    searchOffset = Integer.valueOf(searchOffsetString);
                }
                if (searchOffset > 0) {
                    offset = (searchOffset - 1) * deltaSize;
                }
            }
            return this;
        }
    }

    private static class RequestUri {

        private final Map<String, String> keyValuePairs = new HashMap<String, String>();

        public RequestUri(String searchQuery) {
            if (searchQuery != null) {
                String[] split = searchQuery.split("&");
                for (String s : split) {
                    String[] keyValue = s.split("=");
                    String value = keyValue.length > 1 ? keyValue[1] : "";
                    keyValuePairs.put(keyValue[0], value);
                }
            }
        }

        public void setParameter(String key, String value) {
            keyValuePairs.put(key, value);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : keyValuePairs.entrySet()) {
                sb.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
            sb.deleteCharAt(sb.lastIndexOf("&"));
            return sb.toString();
        }
    }
}
