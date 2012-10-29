package se.vgregion.portal.vap.domain.jpa;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * JPA entity class representing a folder (a place where a user can place {@link Bookmark}s).
 *
 * @author Patrik Bergström
 */
@Entity
@Table(name = "vgr_vap_folder", uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "folderName"}))
public class Folder extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String folderName;

    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "folder")
    private Set<Bookmark> bookmarks = new HashSet<Bookmark>();

    /**
     * Constructor.
     */
    public Folder() {
    }

    /**
     * Constructor.
     *
     * @param folderName the folderName
     * @param userId     the userId
     */
    public Folder(String folderName, Long userId) {
        this.folderName = folderName;
        this.userId = userId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(Set<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    @Override
    public Long getId() {
        return id;
    }
}
