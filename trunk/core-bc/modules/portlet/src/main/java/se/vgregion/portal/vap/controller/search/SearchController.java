package se.vgregion.portal.vap.controller.search;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Collection;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import se.vgregion.portal.vap.controller.BaseController;
import se.vgregion.portal.vap.domain.searchresult.AutoSuggestResult;
import se.vgregion.portal.vap.service.DocumentSearchService;
import se.vgregion.portal.vap.service.DocumentSearchServiceException;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;

/**
 * Controller for searches.
 *
 * @author Patrik Bergström
 */
@Controller
@RequestMapping(value = "VIEW")
public class SearchController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    /**
     * Constructor.
     *
     * @param documentSearchService the {@link DocumentSearchService}
     */
    @Autowired
    public SearchController(DocumentSearchService documentSearchService) {
        setDocumentSearchService(documentSearchService);
    }

    /**
     * Show the search view.
     *
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @return the view
     */
    @RenderMapping
    public String showSearch(RenderRequest request, RenderResponse response, Model model) {

        User user = getUser(request);
        boolean isLoggedIn = isLoggedIn(user);

        String searchLayoutFriendlyURL = request.getPreferences().getValue("searchLayoutFriendlyURL", null);
        boolean isStartPageSearch = (searchLayoutFriendlyURL != null);

        model.addAttribute("isStartPageSearch", isStartPageSearch);
        model.addAttribute("isLoggedIn", isLoggedIn);

        return "search";
    }

    /**
     * Redirect to an action url along with the searchTerm parameter among others.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException IOException
     */
    @ActionMapping(params = "action=search")
    public void search(ActionRequest request, ActionResponse response) throws IOException {

        String searchLayoutFriendlyURL = request.getPreferences().getValue("searchLayoutFriendlyURL", null);

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        String searchTerm = ParamUtil.getString(request, "searchTerm", "");

        if (searchLayoutFriendlyURL != null) {

            long groupId = themeDisplay.getScopeGroupId();
            boolean isPrivateLayout = themeDisplay.getLayout().isPrivateLayout();
            try {
                Layout searchLayout = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, isPrivateLayout,
                        searchLayoutFriendlyURL);
                long plid = searchLayout.getPlid();

                String portletId = "search_WAR_vapportlet";

                HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);

                PortletURL searchURL = PortletURLFactoryUtil.create(httpRequest, portletId, plid,
                        ActionRequest.ACTION_PHASE);
                searchURL.setWindowState(WindowState.NORMAL);
                searchURL.setParameter("action", "search");
                searchURL.setParameter("searchTerm", searchTerm);

                response.sendRedirect(searchURL.toString());
            } catch (PortalException e) {
                LOGGER.error(e.getMessage(), e);
            } catch (SystemException e) {
                LOGGER.error(e.getMessage(), e);
            } catch (WindowStateException e) {
                LOGGER.error(e.getMessage(), e);

            }
            return;
        } else {
            response.setRenderParameter("searchTerm", searchTerm);

            String encodedSearchTerm = URLEncoder.encode(searchTerm, "UTF-8");
            String searchQuery = "q=" + encodedSearchTerm + "&hits=10&offset=0";
            response.setRenderParameter("searchQuery", URLEncoder.encode(searchQuery, "UTF-8"));

            response.setRenderParameter("isPaginatorCall", "false");
            try {
                String result = getDocumentSearchService().searchJsonReply(searchQuery);

                sendStatisticsRequest(request, encodedSearchTerm, result, null);

                response.setEvent(new QName("http://liferay.com/events", "vap.searchResultJson"), result);
            } catch (DocumentSearchServiceException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Query for augoSuggestions. The result is written to the portlet outputstream.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException IOException
     */
    @ResourceMapping("autosuggest")
    public void autoSuggest(ResourceRequest request, ResourceResponse response) throws IOException {
        String searchTerm = request.getParameter("autoCompleteSearchTerm");

        Collection<String> suggestions = getDocumentSearchService().getAutoSuggestions(searchTerm.toLowerCase());

        ObjectMapper mapper = new ObjectMapper();

        AutoSuggestResult autoSuggestResult = new AutoSuggestResult();
        for (String suggestion : suggestions) {
            autoSuggestResult.addKeyValuePair("suggestion", suggestion);
        }

        response.setContentType("application/json");

        Writer writer = null;
        try {
            writer = new OutputStreamWriter(response.getPortletOutputStream(), "UTF-8");
            mapper.writeValue(writer, autoSuggestResult);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


}
