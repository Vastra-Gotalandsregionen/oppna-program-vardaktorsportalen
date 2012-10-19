package se.vgregion.portal.vap.service.repository.test;

import org.springframework.stereotype.Repository;
import se.vgregion.dao.domain.patterns.repository.inmemory.InMemoryRepository;
import se.vgregion.portal.vap.domain.jpa.Flag;
import se.vgregion.portal.vap.domain.jpa.FlagPk;

import java.util.Map;

/**
 * @author Patrik Bergström
 */
@Repository
public class InMemoryFlagRepositoryImpl extends InMemoryRepository<Flag, FlagPk> implements InMemoryFlagRepository {

    @Override
    public Map<String, Flag> findUserFlags(Long userId) {
        throw new UnsupportedOperationException();
    }
}
