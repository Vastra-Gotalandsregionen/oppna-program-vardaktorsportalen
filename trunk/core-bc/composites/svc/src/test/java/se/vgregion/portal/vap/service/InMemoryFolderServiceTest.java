package se.vgregion.portal.vap.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.portal.vap.domain.jpa.Bookmark;
import se.vgregion.portal.vap.domain.jpa.Folder;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Folder service which uses an underlying in-memory-repository.
 *
 * @author Patrik Bergström
 */
@ContextConfiguration({
        "classpath:in-memory-vap-service-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class InMemoryFolderServiceTest {

    @Autowired FolderService folderService;

    @Before
    public void setup() {
        Collection<Folder> all = folderService.findAll();
        for (Folder folder : all) {
            folderService.remove(folder.getId());
        }
    }

    @Test
    public void testPersist() throws Exception {
        Folder folder = new Folder("testFolder", 2342345L);
        folderService.persist(folder);

        assertEquals(1, folderService.findAll().size());
        assertNotNull(folderService.findAll().iterator().next().getId());
    }

    @Test
    public void testMerge() throws Exception {
        Folder folder = new Folder("testFolder", 2342345L);
        folderService.persist(folder);

        folder.setBookmarks(new HashSet<Bookmark>(Arrays.asList(new Bookmark(folder, "d1"), new Bookmark(folder, "d2"),
                new Bookmark(folder, "d3"))));
        folderService.merge(folder);

        assertEquals(1, folderService.findAll().size());
        assertEquals(3, folderService.findAll().iterator().next().getBookmarks().size());
    }

    @Test
    public void testFindByUserId() throws Exception {
        Folder folder1 = new Folder("testFolder1", 1L);
        folderService.persist(folder1);

        Folder folder2 = new Folder("testFolder2", 1L);
        folderService.persist(folder2);

        Folder folder3 = new Folder("testFolder1", 2L);
        folderService.persist(folder3);

        Collection<Folder> foldersFirstUser = folderService.findByUserId(1L);
        Collection<Folder> foldersSecondUser = folderService.findByUserId(2L);

        assertEquals(2, foldersFirstUser.size());
        assertEquals(1, foldersSecondUser.size());

    }
}
