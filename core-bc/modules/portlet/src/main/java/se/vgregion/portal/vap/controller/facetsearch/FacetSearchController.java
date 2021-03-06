package se.vgregion.portal.vap.controller.facetsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.vgregion.portal.vap.controller.BaseController;
import se.vgregion.portal.vap.domain.searchresult.*;
import se.vgregion.portal.vap.service.DocumentSearchService;
import se.vgregion.portal.vap.service.DocumentSearchServiceException;
import se.vgregion.portal.vap.util.JsonUtil;

import javax.portlet.*;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Controller for the facet search portlet.
 *
 * @author Patrik Bergström
 */
@Controller
@RequestMapping(value = "VIEW")
public class FacetSearchController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacetSearchController.class);

    /**
     * Constructor.
     *
     * @param documentSearchService the {@link DocumentSearchService}
     */
    @Autowired
    public FacetSearchController(DocumentSearchService documentSearchService) {
        setDocumentSearchService(documentSearchService);
    }

    /**
     * The default render method. If the searchResultJson parameter is set as a public render parameter it will be
     * picked up, parsed, and set on the request.
     *
     * @param request  the request
     * @param response the response
     * @return the view
     */
    @RenderMapping
    public String showFacetSearch(RenderRequest request, RenderResponse response) {
        String searchResultJson = request.getParameter("searchResultJson");

        LOGGER.info("searchResultJson: " + searchResultJson);

        try {
            if (searchResultJson != null) {
                SearchResult parsed = JsonUtil.parse(searchResultJson);
                request.setAttribute("searchResult", parsed);

                request.setAttribute("anyAppliedItems", anyAppliedItems(parsed));
                request.setAttribute("anySelectableItems", anySelectableItems(parsed));

                request.setAttribute("searchTerm", request.getParameter("searchTerm"));
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return "facet-search";
    }

    /**
     * The action method which is executed when a facet search is performed.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException IOException
     */
    @ActionMapping(params = "action=facetSearch")
    public void search(ActionRequest request, ActionResponse response) throws IOException {
        String searchQuery = request.getParameter("searchQuery");

        if (searchQuery != null) {
            response.setRenderParameter("searchQuery", URLEncoder.encode(searchQuery, "UTF-8"));
            response.setRenderParameter("isPaginatorCall", "false");
            String searchTerm = request.getParameter("searchTerm");
            request.setAttribute("searchTerm", searchTerm);
            try {
                String result = getDocumentSearchService().searchJsonReply(searchQuery);

                sendStatisticsRequest(request, URLEncoder.encode(searchTerm, "UTF-8"), result, null);

                response.setEvent(new QName("http://liferay.com/events", "vap.searchResultJson"), result);
            } catch (DocumentSearchServiceException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Method executed when the "{http://liferay.com/events}vap.searchResultJson" event is published. It sets the
     * searchResultJson parameter.
     *
     * @param request  the request
     * @param response the response
     */
    @EventMapping("{http://liferay.com/events}vap.searchResultJson")
    public void setSearchResultJson(EventRequest request, EventResponse response) {
        String value = (String) request.getEvent().getValue();
        response.setRenderParameter("searchResultJson", value);
    }

    // Loop through to see if there are any selectableItems in any entry.
    private boolean anySelectableItems(SearchResult searchResult) {
        // Null checks
        if (searchResult == null
                || searchResult.getComponents() == null
                || searchResult.getComponents().getFacets() == null
                || searchResult.getComponents().getFacets().getEntries() == null) {
            return false;
        }
        for (Entry entry : searchResult.getComponents().getFacets().getEntries()) {
            List<SelectableItem> selectableItems = entry.getSelectableItems();
            if (selectableItems != null && selectableItems.size() > 0) {
                for (SelectableItem selectableItem : selectableItems) {
                    if (selectableItem.getCount() > 0) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // Loop through to see if there are any appliedItems in any entry.
    private boolean anyAppliedItems(SearchResult searchResult) {
        // Null checks
        if (searchResult == null
                || searchResult.getComponents() == null
                || searchResult.getComponents().getFacets() == null
                || searchResult.getComponents().getFacets().getEntries() == null) {
            return false;
        }
        for (Entry entry : searchResult.getComponents().getFacets().getEntries()) {
            List<AppliedItem> appliedItems = entry.getAppliedItems();
            if (appliedItems != null && appliedItems.size() > 0) {
                return true;
            }
        }

        return false;
    }

}
