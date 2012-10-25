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

import javax.portlet.PortletRequest;

/**
 * A base controller containing common methods.
 *
 * @author Patrik Bergström
 */
public abstract class BaseController {

    private DocumentSearchService documentSearchService;
    private FlagService flagService;
    private BookmarkService bookmarkService;
    private UserEventsService userEventsService;

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
//        try {
//            String viewDocumentFriendlyURL = request.getPreferences().getValue("viewDocumentFriendlyURL", null);
            //String viewDocumentPortletName = request.getPreferences().getValue("viewDocumentPortletName", null);

//            ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
//            long groupId = themeDisplay.getScopeGroupId();
//            boolean isPrivateLayout = themeDisplay.getLayout().isPrivateLayout();
//            Layout searchLayout = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, isPrivateLayout,
//                    viewDocumentFriendlyURL);
//            long plid = searchLayout.getPlid();

//            request.setAttribute("viewDocumentPortletName", viewDocumentPortletName);
//            request.setAttribute("viewDocumentPlid", plid);
            /*} catch (PortalException e) {
                throw new RuntimeException(e);
            } catch (SystemException e) {
                throw new RuntimeException(e);
            }*/
    }

    protected DocumentSearchService getDocumentSearchService() {
        return documentSearchService;
    }

    protected void setDocumentSearchService(DocumentSearchService documentSearchService) {
        this.documentSearchService = documentSearchService;
    }

    protected FlagService getFlagService() {
        return flagService;
    }

    protected void setFlagService(FlagService flagService) {
        this.flagService = flagService;
    }

    protected BookmarkService getBookmarkService() {
        return bookmarkService;
    }

    protected void setBookmarkService(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    protected UserEventsService getUserEventsService() {
        return userEventsService;
    }

    protected void setUserEventsService(UserEventsService userEventsService) {
        this.userEventsService = userEventsService;
    }
}
