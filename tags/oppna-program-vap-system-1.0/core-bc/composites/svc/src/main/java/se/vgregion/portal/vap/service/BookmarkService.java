package se.vgregion.portal.vap.service;

import se.vgregion.portal.vap.domain.jpa.Bookmark;

import java.util.Collection;

/**
 * Service for managing {@link Bookmark}s.
 *
 * @author Patrik Bergström
 */
public interface BookmarkService {

    /**
     * Find all {@link Bookmark}s for a given user.
     *
     * @param userId the user's userId
     * @return all the user's {@link Bookmark}s
     */
    Collection<Bookmark> findBookmarksByUserId(Long userId);

    /**
     * Add (persist) a {@link Bookmark}. A {@link Bookmark} is made up of a userId, documentId and folderName. If the
     * folder does not exist it should be created.
     *
     * @param userId the userId
     * @param documentId the documentId
     * @param folderName the folderName
     */
    void addBookmark(Long userId, String documentId, String folderName);

    /**
     * Find all {@link Bookmark}s for all users and all folders.
     *
     * @return all {@link Bookmark}s
     */
    Collection<Bookmark> findAll();

    /**
     * Remove a {@link Bookmark}.
     *
     * @param bookmark the {@link Bookmark}
     */
    void remove(Bookmark bookmark);

    /**
     * Remove all {@link Bookmark}s.
     */
    void removeAll();
}
