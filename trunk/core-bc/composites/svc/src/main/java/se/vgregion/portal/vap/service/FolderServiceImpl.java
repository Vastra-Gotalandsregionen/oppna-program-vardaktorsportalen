package se.vgregion.portal.vap.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.vgregion.portal.vap.domain.jpa.Folder;
import se.vgregion.portal.vap.service.repository.FolderRepository;

import java.util.Collection;

/**
 * @author Patrik Bergström
 */
public class FolderServiceImpl implements FolderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderServiceImpl.class);

    private FolderRepository folderRepository;

    @Autowired
    public FolderServiceImpl(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    @Override
    public void persist(Folder folder) {
        folderRepository.persist(folder);
    }

    @Override
    public Folder merge(Folder folder) {
        return folderRepository.merge(folder);
    }

    @Override
    public Collection<Folder> findAll() {
        return folderRepository.findAll();
    }

    @Override
    public void remove(Long id) {
        folderRepository.remove(id);
    }

    @Override
    public Collection<Folder> findByUserId(Long userId) {
        return folderRepository.findByUserId(userId);
    }

}
