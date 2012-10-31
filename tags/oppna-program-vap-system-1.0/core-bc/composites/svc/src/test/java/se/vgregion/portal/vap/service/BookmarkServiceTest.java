package se.vgregion.portal.vap.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.portal.vap.domain.jpa.Bookmark;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author Patrik Bergström
 */
@ContextConfiguration({
        "classpath:spring/datasource-config.xml",
        "classpath:spring/jpa-connector-direct.xml",
        "classpath:jpa-vap-service-context.xml",
        "classpath:search-service-context-test.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class BookmarkServiceTest {

    @Autowired
    private BookmarkService bookmarkService;

    private int i = 0;

    @Before
    public void setup() {
        bookmarkService.removeAll();

        bookmarkService.addBookmark(1L, "d1", "f1");i++;
        bookmarkService.addBookmark(1L, "d2", "f1");i++;
        bookmarkService.addBookmark(1L, "d3", "f2");i++;
        bookmarkService.addBookmark(2L, "d1", "f1");i++;
        bookmarkService.addBookmark(2L, "d2", "f2");i++;
        bookmarkService.addBookmark(2L, "d3", "f2");i++;
        bookmarkService.addBookmark(2L, "d4", "f2");i++;
    }

    @Test
    public void testFindBookmarksByUserId() throws Exception {
        Collection<Bookmark> bookmarks = bookmarkService.findBookmarksByUserId(1L);

        assertEquals(3, bookmarks.size());
    }

    @Test
    public void testAddBookmark() throws Exception {
        assertEquals(i, bookmarkService.findAll().size()); // We already added in the setup method so just verify here
    }

}
