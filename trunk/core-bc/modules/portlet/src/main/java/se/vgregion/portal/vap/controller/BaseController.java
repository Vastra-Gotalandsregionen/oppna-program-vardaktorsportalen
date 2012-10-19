package se.vgregion.portal.vap.controller;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import se.vgregion.portal.vap.service.BookmarkService;
import se.vgregion.portal.vap.service.DocumentSearchService;
import se.vgregion.portal.vap.service.FlagService;
import se.vgregion.portal.vap.service.UserEventsService;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

/**
 * @author Patrik Bergström
 */
public abstract class BaseController {

    protected DocumentSearchService documentSearchService;
    protected FlagService flagService;
    protected BookmarkService bookmarkService;
    protected UserEventsService userEventsService;

    protected void logDocumentEvent(PortletRequest request) {
        User user = getUser(request);

        if (isLoggedIn(user)) {
            String documentId = request.getParameter("documentId");
            userEventsService.addDocumentEvent(user.getUserId(), documentId);
        }
    }

    protected boolean isLoggedIn(User user) {
        return user != null && !user.isDefaultUser();
    }

    protected User getUser(PortletRequest request) {
        return ((ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY)).getUser();
    }

    protected void addViewDocumentAttributes(PortletRequest request) {
        try {
            String viewDocumentFriendlyURL = request.getPreferences().getValue("viewDocumentFriendlyURL", null);
            String viewDocumentPortletName = request.getPreferences().getValue("viewDocumentPortletName", null);

            ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
            long groupId = themeDisplay.getScopeGroupId();
            boolean isPrivateLayout = themeDisplay.getLayout().isPrivateLayout();
            Layout searchLayout = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, isPrivateLayout, viewDocumentFriendlyURL);
            long plid = searchLayout.getPlid();

            request.setAttribute("viewDocumentPortletName", viewDocumentPortletName);
            request.setAttribute("viewDocumentPlid", plid);
        } catch (PortalException e) {
            throw new RuntimeException(e);
        } catch (SystemException e) {
            throw new RuntimeException(e);
        }
    }



}
