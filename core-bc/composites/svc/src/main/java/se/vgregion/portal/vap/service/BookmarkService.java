package se.vgregion.portal.vap.service;

import se.vgregion.portal.vap.domain.jpa.Bookmark;

import java.util.Collection;

/**
 * @author Patrik Bergström
 */
public interface BookmarkService {

    Collection<Bookmark> findBookmarksByUserId(Long userId);

    void addBookmark(Long userId, String documentId, String folderName);

    Collection<Bookmark> findAll();

    void remove(Bookmark bookmark);

    void removeAll();
}
