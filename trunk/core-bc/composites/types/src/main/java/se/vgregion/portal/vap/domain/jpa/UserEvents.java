package se.vgregion.portal.vap.domain.jpa;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Bergström
 */
@Entity
@Table(name = "vgr_vap_user_events")
public class UserEvents extends AbstractEntity<Long> implements Serializable {

    @Id
    private Long userId;

    @ElementCollection
    @CollectionTable(name = "vgr_vap_user_events_recent_document_ids")
    private List<String> recentDocumentIds = new ArrayList<String>();

    public UserEvents() {
    }

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
