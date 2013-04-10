package se.vgregion.portal.vap.service.repository;

import se.vgregion.dao.domain.patterns.repository.Repository;
import se.vgregion.portal.vap.domain.jpa.UserEvents;

/**
 * Repository interface for managing {@link UserEvents}.
 *
 * @author Patrik Bergström
 */
public interface UserEventsRepository extends Repository<UserEvents, Long> {

}
