package se.vgregion.portal.vap.domain.jpa;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity class representing a user event which is used to retrieve "latest documents".
 *
 * @author Patrik Bergström
 */
@Entity
@Table(name = "vgr_vap_user_events")
public class UserEvents extends AbstractEntity<Long> implements Serializable {

    private static final long serialVersionUID = -8510690241650333039L;

    @Id
    private Long userId;

    @ElementCollection
    @CollectionTable(name = "vgr_vap_user_events_recent_document_ids")
    private List<String> recentDocumentIds = new ArrayList<String>();

    /**
     * Constructor.
     */
    public UserEvents() {
    }

    /**
     * Constructor.
     *
     * @param userId the userId
     */
    public UserEvents(Long userId) {
        this.userId = userId;
    }

    @Override
    public Long getId() {
        return userId;
    }

    public List<String> getRecentDocumentIds() {
        return recentDocumentIds;
    }
}
