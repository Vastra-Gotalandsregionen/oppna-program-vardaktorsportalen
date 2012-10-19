package se.vgregion.portal.vap.service.repository;

import se.vgregion.dao.domain.patterns.repository.Repository;
import se.vgregion.portal.vap.domain.jpa.Bookmark;

import java.util.Collection;
import java.util.List;

/**
 * @author Patrik Bergström
 */
public interface BookmarkRepository extends Repository<Bookmark, Long> {

    Collection<Bookmark> findBookmarksByUserId(Long userId);

    List<Bookmark> findBookmarksByUserIdAndDocumentId(Long userId, String documentId);
}
