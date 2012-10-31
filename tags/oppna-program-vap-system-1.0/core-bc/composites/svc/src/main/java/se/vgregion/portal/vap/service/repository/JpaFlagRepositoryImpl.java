package se.vgregion.portal.vap.service.repository;

import org.springframework.stereotype.Repository;
import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;
import se.vgregion.portal.vap.domain.jpa.Flag;
import se.vgregion.portal.vap.domain.jpa.FlagPk;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link JpaFlagRepository}s.
 *
 * @author Patrik Bergström
 */
@Repository
public class JpaFlagRepositoryImpl extends DefaultJpaRepository<Flag, FlagPk> implements JpaFlagRepository {
    @Override
    public Map<String, Flag> findUserFlags(Long userId) {
        Query query = entityManager.createQuery("select f.id.documentId, f from Flag f where f.id.userId = :userId");
        query.setParameter("userId", userId);

        List resultList = query.getResultList();
        Map<String, Flag> documentIdFlagMap = new HashMap<String, Flag>();
        for (Object o : resultList) {
            Object[] pair = (Object[]) o;
            documentIdFlagMap.put((String) pair[0], (Flag) pair[1]);
        }

        return documentIdFlagMap;
    }
}
