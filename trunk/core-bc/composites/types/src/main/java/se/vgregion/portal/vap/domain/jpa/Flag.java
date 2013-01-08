package se.vgregion.portal.vap.domain.jpa;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;

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
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
