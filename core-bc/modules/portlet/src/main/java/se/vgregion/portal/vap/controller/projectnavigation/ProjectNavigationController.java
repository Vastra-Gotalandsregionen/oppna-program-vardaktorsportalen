package se.vgregion.portal.vap.controller.projectnavigation;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.vgregion.portal.vap.controller.BaseController;
import se.vgregion.portal.vap.service.DocumentSearchServiceException;
import se.vgregion.portal.vap.util.project.ProjectUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.List;

/**
 * Controller class for project navigation.
 *
 * @author Erik Andersson
 */
@Controller
@RequestMapping(value = "VIEW")
public class ProjectNavigationController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectNavigationController.class);

    /**
     * Constructor.
     */
    public ProjectNavigationController() {
    }

    /**
     * Loads {@link JournalArticle}s, sets them as a request attribute and returns the view.
     *
     * @param request  the request
     * @param response the response
     * @return the view
     * @throws DocumentSearchServiceException DocumentSearchServiceException
     */
    @RenderMapping
    public String showProjectNavigation(RenderRequest request, RenderResponse response)
            throws DocumentSearchServiceException {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

        long groupId = themeDisplay.getScopeGroupId();

        try {
            List<JournalArticle> projectJournalArticles = ProjectUtil.getAllProjectArticles(groupId);

            request.setAttribute("projectArticles", projectJournalArticles);
        } catch (PortalException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (SystemException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return "project-navigation";
    }


}
