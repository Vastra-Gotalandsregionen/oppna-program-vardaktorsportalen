package se.vgregion.portal.vap.service.repository;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;
import se.vgregion.portal.vap.domain.jpa.Flag;
import se.vgregion.portal.vap.domain.jpa.FlagPk;

/**
 * Implementation of {@link JpaFlagRepository}s.
 *
 * @author Patrik Bergström
 */
@Repository
public class JpaFlagRepositoryImpl extends DefaultJpaRepository<Flag, FlagPk> implements JpaFlagRepository {
    @Override
    public List<Flag> findUserFlags(Long userId) {
        Query query = entityManager.createQuery("select f from Flag f where f.id.userId = :userId");
        query.setParameter("userId", userId);

        @SuppressWarnings("unchecked")
		List<Flag> resultList = query.getResultList();

        return resultList;
    }
}
