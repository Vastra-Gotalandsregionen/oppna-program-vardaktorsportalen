package se.vgregion.portal.vap.controller.latestdocuments;

import com.liferay.portal.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.vgregion.portal.vap.controller.BaseController;
import se.vgregion.portal.vap.domain.searchresult.Document;
import se.vgregion.portal.vap.service.DocumentSearchServiceException;
import se.vgregion.portal.vap.service.UserEventsService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller for latest documents.
 *
 * @author Patrik Bergström
 */
@Controller
@RequestMapping(value = "VIEW")
public class LatestDocumentsController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LatestDocumentsController.class);

    /**
     * Constructor.
     *
     * @param userEventsService the {@link UserEventsService}
     */
    @Autowired
    public LatestDocumentsController(UserEventsService userEventsService) {
        setUserEventsService(userEventsService);
    }

    /**
     * Find the recently viewed documents, set them as a request attribute and show the view.
     *
     * @param request  the request
     * @param response the response
     * @return the view
     */
    @RenderMapping
    public String showLatestDocuments(RenderRequest request, RenderResponse response) {

        User user = getUser(request);
        boolean isLoggedIn = isLoggedIn(user);

        if (isLoggedIn(user)) {
            List<Document> documents = null;
            try {
                documents = getUserEventsService().findRecentDocuments(user.getUserId());
            } catch (DocumentSearchServiceException e) {
                LOGGER.error(e.getMessage(), e);
                request.setAttribute("errorMessage", "Det gick inte att hämta senaste dokument på grund av tekniskt"
                        + " fel.");
            }

            request.setAttribute("documents", documents);
        }

        request.setAttribute("isLoggedIn", isLoggedIn);

        return "latest-documents";
    }

    /**
     * This method logs the document the user clicked on and redirects to the document url.
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

}
