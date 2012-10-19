package se.vgregion.portal.vap.service;

import se.vgregion.portal.vap.domain.jpa.Folder;

import java.util.Collection;

/**
 * @author Patrik Bergström
 */
public interface FolderService {
    void persist(Folder folder);

    Folder merge(Folder folder);

    Collection<Folder> findAll();

    void remove(Long id);

    Collection<Folder> findByUserId(Long userId);
}
