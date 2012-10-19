package se.vgregion.portal.vap.controller.bookmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.vgregion.portal.vap.controller.BaseController;
import se.vgregion.portal.vap.domain.jpa.Folder;
import se.vgregion.portal.vap.service.FolderService;

import javax.portlet.RenderRequest;
import java.util.Collection;

/**
 * @author Patrik Bergström
 */
@Controller
@RequestMapping(value = "VIEW")
public class BookmarkController extends BaseController {

    private FolderService folderService;

    @Autowired
    public BookmarkController(FolderService folderService) {
        this.folderService = folderService;
    }

    @RenderMapping
    public String showSearch(RenderRequest request) {
        Collection<Folder> folders = folderService.findByUserId(getUser(request).getUserId());

        request.setAttribute("folders", folders);

        return "folders";
    }

}
