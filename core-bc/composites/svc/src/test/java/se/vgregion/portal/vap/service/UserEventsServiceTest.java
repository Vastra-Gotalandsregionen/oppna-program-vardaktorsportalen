package se.vgregion.portal.vap.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.portal.vap.domain.jpa.UserEvents;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * @author Patrik Bergström
 */
@ContextConfiguration({
        "classpath:spring/datasource-config.xml",
        "classpath:spring/jpa-connector-direct.xml",
        "classpath:jpa-vap-service-context.xml",
        "classpath:search-service-context-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserEventsServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserEventsService userEventsService;

    @Before
    public void setup() {
        executeSqlScript("classpath:dbsetup/drop-test-data.sql", false);
    }

    @Test
    public void testAddDocumentEvent_StopsAtCapacity() {
        int capacity = UserEventsServiceImpl.RECENT_DOCUMENTS_CAPACITY;

        for (int i = 0; i < capacity; i++) {
            userEventsService.addDocumentEvent(12345L, UUID.randomUUID().toString());
        }

        UserEvents found = userEventsService.findByUserId(12345L);

        assertEquals(capacity, found.getRecentDocumentIds().size());
    }

    @Test
    public void testAddDocumentEvent_RepositionsExistingEntry() {

        userEventsService.addDocumentEvent(12345L, "1");
        userEventsService.addDocumentEvent(12345L, "2");
        userEventsService.addDocumentEvent(12345L, "3");
        userEventsService.addDocumentEvent(12345L, "4");
        userEventsService.addDocumentEvent(12345L, "5");
        userEventsService.addDocumentEvent(12345L, "3");

        UserEvents found = userEventsService.findByUserId(12345L);

        List<String> recentDocumentIds = found.getRecentDocumentIds();

        assertEquals("3", recentDocumentIds.get(0));
        assertEquals("5", recentDocumentIds.get(1));
        assertEquals("4", recentDocumentIds.get(2));
        assertEquals("2", recentDocumentIds.get(3));
        assertEquals("1", recentDocumentIds.get(4));

        assertEquals(5, recentDocumentIds.size());
    }
}
