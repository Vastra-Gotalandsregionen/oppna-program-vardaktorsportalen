package se.vgregion.portal.vap.service.repository;

import se.vgregion.dao.domain.patterns.repository.Repository;
import se.vgregion.portal.vap.domain.jpa.Folder;

import java.util.Collection;
import java.util.List;

/**
 * @author Patrik Bergström
 */
public interface FolderRepository extends Repository<Folder, Long> {
    Collection<Folder> findByUserId(Long userId);

    Folder findByUserIdAndFolderName(Long userId, String folderName);

    List<Folder> findFolderByDocumentAndUser(Long userId, String documentId);
}
