package se.vgregion.portal.vap.service.repository;

import se.vgregion.dao.domain.patterns.repository.Repository;
import se.vgregion.portal.vap.domain.jpa.Flag;
import se.vgregion.portal.vap.domain.jpa.FlagPk;

import java.util.List;
import java.util.Map;

/**
 * Repository interface for managing {@link Flag}s.
 *
 * @author Patrik Bergström
 */
public interface FlagRepository extends Repository<Flag, FlagPk> {

    /**
     * Find all flags for a given userId.
     *
     * @param userId the userId
     * @return a {@link List} with {@link Flag}s
     */
    List<Flag> findUserFlags(Long userId);
}
