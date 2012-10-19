package se.vgregion.portal.vap.domain.jpa;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Patrik Bergström
 */
@Entity
@Table(name = "vgr_vap_flag")
public class Flag extends AbstractEntity<FlagPk> implements Serializable {

    @Id
    private FlagPk id;

    public Flag() {
    }

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
