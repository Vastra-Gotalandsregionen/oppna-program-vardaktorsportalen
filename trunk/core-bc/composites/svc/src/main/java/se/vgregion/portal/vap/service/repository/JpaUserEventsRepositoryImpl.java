package se.vgregion.portal.vap.service.repository;

import org.springframework.stereotype.Repository;
import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;
import se.vgregion.portal.vap.domain.jpa.UserEvents;

/**
 * @author Patrik Bergström
 */
@Repository
public class JpaUserEventsRepositoryImpl extends DefaultJpaRepository<UserEvents, Long>
        implements JpaUserEventsRepository {

}
