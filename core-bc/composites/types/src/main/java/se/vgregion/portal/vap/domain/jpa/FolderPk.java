package se.vgregion.portal.vap.domain.jpa;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Patrik Bergström
 */
@Embeddable
public class FolderPk implements Serializable {

    private Long userId;
    private String folderName;

    public FolderPk() {
    }

    public FolderPk(long userId, String folderName) {
        this.userId = userId;
        this.folderName = folderName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FolderPk folderPk = (FolderPk) o;

        if (folderName != null ? !folderName.equals(folderPk.folderName) : folderPk.folderName != null) return false;
        if (userId != null ? !userId.equals(folderPk.userId) : folderPk.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (folderName != null ? folderName.hashCode() : 0);
        return result;
    }
}
