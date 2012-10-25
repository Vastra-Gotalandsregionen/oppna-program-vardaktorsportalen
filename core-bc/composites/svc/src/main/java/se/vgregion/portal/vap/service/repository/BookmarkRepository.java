package se.vgregion.portal.vap.service.repository;

import se.vgregion.dao.domain.patterns.repository.Repository;
import se.vgregion.portal.vap.domain.jpa.Bookmark;

import java.util.Collection;
import java.util.List;

/**
 * The base interface for managing {@link Bookmark}s.
 *
 * @author Patrik Bergström
 */
public interface BookmarkRepository extends Repository<Bookmark, Long> {

    /**
     * Find all bookmarks for a given user.
     *
     * @param userId the user's userId
     * @return a collection of {@link Bookmark}s
     */
    Collection<Bookmark> findBookmarksByUserId(Long userId);

    /**
     * Find all bookmarks for a given user and documentId. A bookmark is associated with a
     * {@link se.vgregion.portal.vap.domain.jpa.Folder} and a user can thus have several bookmarks for with a given
     * documenetId.
     *
     * @param userId the user's userId
     * @param documentId the documenetId
     * @return a collection of {@link Bookmark}s
     */
    List<Bookmark> findBookmarksByUserIdAndDocumentId(Long userId, String documentId);
}
