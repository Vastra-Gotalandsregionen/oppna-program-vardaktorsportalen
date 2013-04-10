package se.vgregion.portal.vap.service;

import se.vgregion.portal.vap.domain.jpa.Flag;
import se.vgregion.portal.vap.domain.jpa.FlagPk;
import se.vgregion.portal.vap.domain.searchresult.Document;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Service for managing {@link Flag}s.
 *
 * @author Patrik Bergström
 */
public interface FlagService {

    /**
     * Persist a {@link Flag}.
     *
     * @param flag the {@link Flag}
     */
    void persist(Flag flag);

    /**
     * Merge a {@link Flag}.
     *
     * @param flag the {@link Flag}
     * @return the merged instance
     */
    Flag merge(Flag flag);

    /**
     * Find all {@link Flag}s.
     *
     * @return a {@link Collection} of all {@link Flag}s
     */
    Collection<Flag> findAll();

    /**
     * Remove a {@link Flag}.
     *
     * @param id the id
     */
    void remove(FlagPk id);

    /**
     * Toggle a {@link Flag} for a user and a document, meaning persisting if it doesn't already exist or removing if it
     * does exist.
     *
     * @param userId     the userId
     * @param documentId the documentId
     */
    void toggleFlag(Long userId, String documentId);
    
    /**
     * Find all {@link Document}s that are flagged  for a given user.
     *
     * @param userId the userId
     * @return a {@link List} with {@link Document}s.
     */
    List<Document> findUserFlagDocuments(Long userId) throws DocumentSearchServiceException;
    
    
    /**
     * Find all {@link Flag}s  for a given user.
     *
     * @param userId the userId
     * @return a {@link List} with {@link String}s of documentIds.
     */
    List<String> findUserFlagDocumentIds(Long userId);
    

    /**
     * Find all {@link Flag}s for a given user.
     *
     * @param userId the userId
     * @return a {@link List} with {@link Flag}s.
     */
    List<Flag> findUserFlags(Long userId);
    
    /**
     * Find all {@link Flag}s for a given user.
     *
     * @param userId the userId
     * @return a {@link Map} where documentIds are mapped to {@link Flag}s.
     */
    Map<String, Flag> findUserFlagsMap(Long userId);
    
}
