package se.vgregion.portal.vap.service.repository;

import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;
import se.vgregion.portal.vap.domain.jpa.Bookmark;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link JpaBookmarkRepository}.
 *
 * @author Patrik Bergström
 */
public class JpaBookmarkRepositoryImpl extends DefaultJpaRepository<Bookmark, Long> implements JpaBookmarkRepository {
    @Override
    public Collection<Bookmark> findBookmarksByUserId(Long userId) {
        return findByQuery("select b from Bookmark b where b.folder.userId = ?1", new Object[]{userId});
    }

    @Override
    public List<Bookmark> findBookmarksByUserIdAndDocumentId(Long userId, String documentId) {
        List<Bookmark> bookmarks = findByQuery("select b from Bookmark b where b.folder.userId = ?1 and "
                + "b.documentId = ?2", new Object[]{userId, documentId});
        return bookmarks;
    }
}
