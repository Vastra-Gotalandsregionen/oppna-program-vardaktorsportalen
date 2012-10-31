package se.vgregion.portal.vap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import se.vgregion.portal.vap.domain.jpa.Bookmark;
import se.vgregion.portal.vap.domain.jpa.Folder;
import se.vgregion.portal.vap.service.repository.BookmarkRepository;
import se.vgregion.portal.vap.service.repository.FolderRepository;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link BookmarkService}. It uses underlying repositories ({@link BookmarkRepository} and
 * {@link FolderRepository}) to store and fetch the entities.
 *
 * @author Patrik Bergström
 */
public class BookmarkServiceImpl implements BookmarkService {

    private BookmarkRepository bookmarkRepository;
    private FolderRepository folderRepository;

    /**
     * Constructor.
     *
     * @param bookmarkRepository bookmarkRepository
     * @param folderRepository folderRepository
     */
    @Autowired
    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository, FolderRepository folderRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.folderRepository = folderRepository;
    }

    @Override
    public Collection<Bookmark> findBookmarksByUserId(Long userId) {
        return bookmarkRepository.findBookmarksByUserId(userId);
    }

    @Override
    @Transactional
    public void addBookmark(Long userId, String documentId, String folderName) {
        Folder folder = folderRepository.findByUserIdAndFolderName(userId, folderName);

        if (folder == null) {
            // Create it
            folder = new Folder(folderName, userId);
            folderRepository.persist(folder);
        }

        // Remove old bookmarks for the same document and user
        List<Bookmark> old = bookmarkRepository.findBookmarksByUserIdAndDocumentId(userId, documentId);
        for (Bookmark b : old) {
            if (b.getFolder().equals(folder)) {
                // The document is already bookmarked in the current folder. Do nothing.
                return;
            }
            bookmarkRepository.remove(b);
        }

        Bookmark bookmark = new Bookmark(folder, documentId);
        bookmarkRepository.merge(bookmark);

    }

    @Override
    public Collection<Bookmark> findAll() {
        return bookmarkRepository.findAll();
    }

    @Override
    @Transactional
    public void remove(Bookmark bookmark) {
        Folder folder = bookmark.getFolder();
        folder.getBookmarks().remove(bookmark);
        folderRepository.merge(folder);
        bookmarkRepository.remove(bookmark);
    }

    @Override
    @Transactional
    public void removeAll() {
        Collection<Bookmark> all = bookmarkRepository.findAll();
        for (Bookmark bookmark : all) {
            bookmarkRepository.remove(bookmark);
        }
    }

}
