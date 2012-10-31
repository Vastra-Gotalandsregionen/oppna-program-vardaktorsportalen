package se.vgregion.portal.vap.domain.jpa;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * JPA entity class representing a flag (a user can flag a document).
 *
 * @author Patrik Bergström
 */
@Entity
@Table(name = "vgr_vap_flag")
public class Flag extends AbstractEntity<FlagPk> implements Serializable {

    private static final long serialVersionUID = 1079947011450319238L;

    @Id
    private FlagPk id;

    /**
     * Constructor.
     */
    public Flag() {
    }

    /**
     * Constructor.
     * @param id the id
     */
    public Flag(FlagPk id) {
        this.id = id;
    }

    @Override
    public FlagPk getId() {
        return id;
    }

    public void setId(FlagPk id) {
        this.id = id;
    }
}
