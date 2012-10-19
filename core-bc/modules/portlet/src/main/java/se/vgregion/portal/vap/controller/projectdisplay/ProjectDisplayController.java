package se.vgregion.portal.vap.controller.projectdisplay;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.vgregion.portal.vap.controller.BaseController;
import se.vgregion.portal.vap.service.UserEventsService;
import se.vgregion.portal.vap.util.project.ProjectUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Erik Andersson
 */
@Controller
@RequestMapping(value = "VIEW")
public class ProjectDisplayController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectDisplayController.class);

    @Autowired
    public ProjectDisplayController(UserEventsService userEventsService) {
        super.userEventsService = userEventsService;
    }

    @RenderMapping
    public String showProjectNavigation(RenderRequest request, RenderResponse response) throws PortalException, SystemException {

    	ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    	long groupId = themeDisplay.getScopeGroupId();
    	
    	String projectArticleUrlTitle = ParamUtil.getString(request, "projectArticleUrlTitle", "");
    	
    	JournalArticle article = null;
    	
    	if(!projectArticleUrlTitle.equals("")) {
    		article = ProjectUtil.getProjectArticleByUrlTitle(groupId, projectArticleUrlTitle);
    	}
    	else {
    		article = ProjectUtil.getLatestProjectArticle(groupId);
    	}
    	
    	if(article != null) {
    		request.setAttribute("article", article);
    		System.out.println("ProjectDisplayController - showProjectNavigation - article is NOT null");
    	}
    	else {
    		System.out.println("ProjectDisplayController - showProjectNavigation - article IS null");
    	}
    	
        return "project-display";
    }

}
