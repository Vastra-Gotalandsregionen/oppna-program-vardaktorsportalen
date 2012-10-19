package se.vgregion.portal.vap.service.repository;

import se.vgregion.dao.domain.patterns.repository.Repository;
import se.vgregion.portal.vap.domain.jpa.Flag;
import se.vgregion.portal.vap.domain.jpa.FlagPk;

import java.util.Map;

/**
 * @author Patrik Bergström
 */
public interface FlagRepository extends Repository<Flag, FlagPk> {
    Map<String, Flag> findUserFlags(Long userId);
}
