package se.vgregion.portal.vap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.vgregion.portal.vap.domain.jpa.UserEvents;
import se.vgregion.portal.vap.domain.searchresult.Document;
import se.vgregion.portal.vap.domain.searchresult.SearchResult;
import se.vgregion.portal.vap.service.repository.UserEventsRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Bergström
 */
@Service
public class UserEventsServiceImpl implements UserEventsService {

    static final int RECENT_DOCUMENTS_CAPACITY = 5;

    private UserEventsRepository userEventsRepository;
    private DocumentSearchService documentSearchService;

    @Autowired
    public UserEventsServiceImpl(UserEventsRepository userEventsRepository,
                                 DocumentSearchService documentSearchService) {
        this.userEventsRepository = userEventsRepository;
        this.documentSearchService = documentSearchService;
    }

    @Override
    @Transactional
    public void addDocumentEvent(long userId, String documentId) {
        UserEvents events = userEventsRepository.find(userId);

        if (events == null) {
            events = new UserEvents(userId);
            events.getRecentDocumentIds().add(documentId);
            userEventsRepository.persist(events);
        } else {

            List<String> recentDocumentIds = events.getRecentDocumentIds();

            if (recentDocumentIds.contains(documentId)) {
                // Already in the list. Remove from current list position and add first.
                recentDocumentIds.remove(documentId);
            } else {
                // Now we need to ensure we don't exceed the capacity.
                // Remove the last entry if we reach the capacity limit.
                if (recentDocumentIds.size() >= RECENT_DOCUMENTS_CAPACITY) {
                    recentDocumentIds.remove(recentDocumentIds.size() - 1); // Remove last entry
                }
            }

            recentDocumentIds.add(0, documentId);
            userEventsRepository.merge(events);
        }
    }

    @Override
    public UserEvents findByUserId(long userId) {
        return userEventsRepository.find(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Document> findRecentDocuments(long userId) throws DocumentSearchServiceException {
        UserEvents events = findByUserId(userId);

        if (events == null) {
            return new ArrayList<Document>();
        }

        List<String> recentDocumentIds = events.getRecentDocumentIds();

        SearchResult search = documentSearchService.search(recentDocumentIds);

        List<Document> documents = search.getComponents().getDocuments();

        return documents;
    }
}
