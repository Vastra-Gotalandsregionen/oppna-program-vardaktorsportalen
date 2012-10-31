package se.vgregion.portal.vap.service.repository;

import se.vgregion.dao.domain.patterns.repository.Repository;
import se.vgregion.portal.vap.domain.jpa.Folder;

import java.util.Collection;
import java.util.List;

/**
 * Repository for {@link Folder}s.
 *
 * @author Patrik Bergström
 */
public interface FolderRepository extends Repository<Folder, Long> {
    /**
     * Find all {@link Folder}s for a user.
     *
     * @param userId the userId
     * @return a {@link Collection} of a user's all {@link Folder}s
     */
    Collection<Folder> findByUserId(Long userId);

    /**
     * Find a {@link Folder} by userId and folderName.
     *
     * @param userId     the userId
     * @param folderName the folderName
     * @return the found {@link Folder} or <code>null</code> if none found.
     */
    Folder findByUserIdAndFolderName(Long userId, String folderName);

    /**
     * Find all {@link Folder}s with a given userId and documentId (a user can bookmark a document in several
     * {@link Folder}s).
     *
     * @param userId     the userId
     * @param documentId the documentId
     * @return a {@link List} of the {@link Folder}s.
     */
    List<Folder> findFolderByDocumentAndUser(Long userId, String documentId);
}
