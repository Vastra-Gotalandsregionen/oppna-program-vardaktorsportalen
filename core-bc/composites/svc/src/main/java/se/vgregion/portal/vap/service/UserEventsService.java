package se.vgregion.portal.vap.service;

import se.vgregion.portal.vap.domain.jpa.UserEvents;
import se.vgregion.portal.vap.domain.searchresult.Document;

import java.util.List;

/**
 * @author Patrik Bergström
 */
public interface UserEventsService {
    void addDocumentEvent(long userId, String documentId);

    UserEvents findByUserId(long userId);

    List<Document> findRecentDocuments(long userId) throws DocumentSearchServiceException;
}
