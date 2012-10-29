package se.vgregion.portal.vap.service.repository;

import se.vgregion.dao.domain.patterns.repository.db.jpa.JpaRepository;
import se.vgregion.portal.vap.domain.jpa.Flag;
import se.vgregion.portal.vap.domain.jpa.FlagPk;

/**
 * JPA repository interface for managing {@link Flag}s.
 *
 * @author Patrik Bergström
 */
public interface JpaFlagRepository extends JpaRepository<Flag, FlagPk, FlagPk>, FlagRepository {
}
