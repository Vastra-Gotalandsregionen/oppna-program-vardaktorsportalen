package se.vgregion.portal.vap.domain.jpa;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Patrik Bergström
 */
@Entity
@Table(name = "vgr_vap_bookmark",
        uniqueConstraints = @UniqueConstraint(columnNames = {"folder_id", "documentid"}))
public class Bookmark extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Folder folder;

    private String documentId;

    private String documentTitle;

    public Bookmark() {
    }

    public Bookmark(Folder folder, String documentId) {
        this.folder = folder;
        this.documentId = documentId;
    }

    public Folder getFolder() {
        return folder;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    @Override
    public Long getId() {
        return id;
    }
}
