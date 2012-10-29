package se.vgregion.portal.vap.service.repository;

import org.springframework.stereotype.Repository;
import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;
import se.vgregion.portal.vap.domain.jpa.Folder;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link JpaFlagRepository}s.
 *
 * @author Patrik Bergström
 */
@Repository
public class JpaFolderRepositoryImpl extends DefaultJpaRepository<Folder, Long> implements JpaFolderRepository {
    @Override
    public Collection<Folder> findByUserId(Long userId) {
        Collection<Folder> folders = findByAttribute("userId", userId);
        return folders;
    }

    @Override
    public Folder findByUserIdAndFolderName(Long userId, String folderName) {
        List<Folder> folders = findByQuery("select f from Folder f where f.userId = ?1 and f.folderName like ?2",
                new Object[]{userId, folderName});
        if (folders != null && folders.size() > 0) {
            return folders.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Folder> findFolderByDocumentAndUser(Long userId, String documentId) {
        List<Folder> result = findByQuery("select f from Folder f where f.userId = ?1 and f.bookmarkedDocumentIds = ?2",
                new Object[]{userId, documentId});
        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }
}
