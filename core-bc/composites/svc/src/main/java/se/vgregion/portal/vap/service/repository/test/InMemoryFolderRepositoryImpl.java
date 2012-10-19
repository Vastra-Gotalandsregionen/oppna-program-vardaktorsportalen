package se.vgregion.portal.vap.service.repository.test;

import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;
import se.vgregion.dao.domain.patterns.repository.inmemory.InMemoryRepository;
import se.vgregion.portal.vap.domain.jpa.Folder;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Patrik Bergström
 */
@Repository
public class InMemoryFolderRepositoryImpl extends InMemoryRepository<Folder, Long> implements InMemoryFolderRepository {

    private static long counter = 0L;

    @Override
    public Collection<Folder> findByUserId(Long userId) {
        Collection<Folder> result = new HashSet<Folder>();
        Collection<Folder> all = findAll();
        for (Folder folder : all) {
            if (folder.getUserId().equals(userId)) {
                result.add(folder);
            }
        }

        return result;
    }

    @Override
    public Folder findByUserIdAndFolderName(Long userId, String folderName) {
        Collection<Folder> all = findAll();
        for (Folder folder : all) {
            if (folder.getUserId().equals(userId) && folder.getFolderName().equals(folderName)) {
                return folder;
            }
        }
        return null;
    }

    @Override
    public List<Folder> findFolderByDocumentAndUser(Long userId, String documentId) {
        Collection<Folder> all = findAll();
        List<Folder> result = new ArrayList<Folder>();
        for (Folder folder : all) {
            if (folder.getUserId().equals(userId) && folder.getBookmarks().contains(documentId)) {
                result.add(folder);
            }
        }
        return result;
    }

    @Override
    public Folder persist(Folder object) {
        if (object.getId() != null) {
            throw new RuntimeException("The entity is already persisted");
        }
        try {
            Field id = object.getClass().getDeclaredField("id");
            id.setAccessible(true);
            ReflectionUtils.setField(id, object, counter++);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return super.persist(object);
    }
}
