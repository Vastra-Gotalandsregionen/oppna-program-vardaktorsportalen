package se.vgregion.portal.vap.service;

import se.vgregion.portal.vap.domain.jpa.UserEvents;
import se.vgregion.portal.vap.domain.searchresult.Document;

import java.util.List;

/**
 * Service interface for managing {@link UserEvents}.
 *
 * @author Patrik Bergström
 */
public interface UserEventsService {

    /**
     * Add a document so it will be the "latest" document.
     *
     * @param userId     the userId
     * @param documentId the documentId
     */
    void addDocumentEvent(long userId, String documentId);

    /**
     * Find {@link UserEvents} by userId.
     *
     * @param userId the userId
     * @return the found {@link UserEvents} or <code>null</code> if none found
     */
    UserEvents findByUserId(long userId);

    /**
     * Find the most recent {@link Document}s for a user.
     *
     * @param userId the usesrId
     * @return a {@link List} of {@link Document}s
     * @throws DocumentSearchServiceException DocumentSearchServiceException
     */
    List<Document> findRecentDocuments(long userId) throws DocumentSearchServiceException;
}
