package se.vgregion.portal.vap.service;

import se.vgregion.portal.vap.domain.jpa.Folder;

import java.util.Collection;

/**
 * Service for managing {@link Folder}s.
 *
 * @author Patrik Bergström
 */
public interface FolderService {

    /**
     * Persist a {@link Folder}.
     *
     * @param folder the folder
     */
    void persist(Folder folder);

    /**
     * Merge a {@link Folder}.
     *
     * @param folder the {@link Folder}
     * @return the merged instance
     */
    Folder merge(Folder folder);

    /**
     * Find all {@link Folder}s.
     *
     * @return a {@link Collection} of all {@link Folder}s
     */
    Collection<Folder> findAll();

    /**
     * Remove a {@link Folder}.
     *
     * @param id the id of the {@link Folder}
     */
    void remove(Long id);

    /**
     * Find all {@link Folder}s by a userId.
     *
     * @param userId the userId
     * @return a {@link Collection} of {@link Folder}s
     */
    Collection<Folder> findByUserId(Long userId);
}
