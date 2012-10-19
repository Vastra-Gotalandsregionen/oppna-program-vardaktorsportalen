package se.vgregion.portal.vap.service.repository;

import se.vgregion.dao.domain.patterns.repository.db.jpa.JpaRepository;
import se.vgregion.portal.vap.domain.jpa.Bookmark;

/**
 * @author Patrik Bergström
 */
public interface JpaBookmarkRepository extends JpaRepository<Bookmark, Long, Long>, BookmarkRepository {

}
