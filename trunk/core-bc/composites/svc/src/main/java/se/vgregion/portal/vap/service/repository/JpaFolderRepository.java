package se.vgregion.portal.vap.service.repository;

import se.vgregion.dao.domain.patterns.repository.db.jpa.JpaRepository;
import se.vgregion.portal.vap.domain.jpa.Folder;

/**
 * @author Patrik Bergström
 */
public interface JpaFolderRepository extends JpaRepository<Folder, Long, Long>, FolderRepository {
}
