package se.vgregion.portal.vap.domain.jpa;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * A JPA {@link Embeddable} primary key class.
 *
 * @author Patrik Bergström
 */
@Embeddable
public class FlagPk implements Serializable {

    private static final long serialVersionUID = -2761745128149288419L;

    private Long userId;
    private String documentId;

    /**
     * Constructor.
     */
    public FlagPk() {
    }

    /**
     * Constructor.
     *
     * @param userId     the userId
     * @param documentId the documentId
     */
    public FlagPk(Long userId, String documentId) {
        this.userId = userId;
        this.documentId = documentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FlagPk flagPk = (FlagPk) o;

        if (documentId != null ? !documentId.equals(flagPk.documentId) : flagPk.documentId != null) {
            return false;
        }
        if (userId != null ? !userId.equals(flagPk.userId) : flagPk.userId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        final int prime = 31;
        result = prime * result + (documentId != null ? documentId.hashCode() : 0);
        return result;
    }
}
