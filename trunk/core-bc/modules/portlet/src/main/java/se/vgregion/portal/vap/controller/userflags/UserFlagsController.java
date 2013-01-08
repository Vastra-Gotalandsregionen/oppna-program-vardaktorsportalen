package se.vgregion.portal.vap.controller.userflags;

import java.io.IOException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.vgregion.portal.vap.controller.BaseController;
import se.vgregion.portal.vap.domain.jpa.Folder;
import se.vgregion.portal.vap.domain.searchresult.Document;
import se.vgregion.portal.vap.service.DocumentSearchServiceException;
import se.vgregion.portal.vap.service.FlagService;
import se.vgregion.portal.vap.service.UserEventsService;

import com.liferay.portal.model.User;

/**
 * User Flags controller class.
 *
 * @author Erik Andersson
 */
@Controller
@RequestMapping(value = "VIEW")
public class UserFlagsController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserFlagsController.class);

    private FlagService flagService;

    /**
     * Constructor.
     *
     * @param flagService the flagService
     */
    @Autowired
    public UserFlagsController(FlagService flagService, UserEventsService userEventsService) {
        //setFlagService(flagService);
        this.flagService = flagService;
        
        setUserEventsService(userEventsService);
    }

    /**
     * Loads the {@link Folder}s of the requesting user and returns the folders view.
     *
     * @param request the request
     * @return the view
     */
    @RenderMapping
    public String showUserFlags(RenderRequest request) {
    	
    	User user = getUser(request);
    	long userId = user.getUserId();
    	
        boolean isLoggedIn = isLoggedIn(user);

        if (isLoggedIn(user)) {
            List<Document> documents = null;
            try {
            	documents = flagService.findUserFlagDocuments(userId);
            	
            } catch (DocumentSearchServiceException e) {
                LOGGER.error(e.getMessage(), e);
                request.setAttribute("errorMessage", "Det gick inte att hämta flaggade dokument på grund av tekniskt"
                        + " fel.");
            }

            request.setAttribute("documents", documents);
        }
        
        request.setAttribute("isLoggedIn", isLoggedIn);
    	
        return "user_flags";
    }
    
    /**
     * This method logs the document the user clicked on and redirects to the document url.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException IOException
     */
    @ActionMapping(params = "action=removeUserFlag")
    public void removeUserFlag(ActionRequest request, ActionResponse response) throws IOException {
    	
        User user = getUser(request);

        String documentId = request.getParameter("documentId");
        
        flagService.toggleFlag(user.getUserId(), documentId);
    }
    
    /**
     * This method removes a user flag
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
