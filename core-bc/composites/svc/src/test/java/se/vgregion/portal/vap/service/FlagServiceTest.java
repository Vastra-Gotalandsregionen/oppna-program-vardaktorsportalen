package se.vgregion.portal.vap.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.portal.vap.domain.jpa.Flag;
import se.vgregion.portal.vap.domain.jpa.FlagPk;
import se.vgregion.portal.vap.service.repository.FlagRepository;

import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Patrik Bergström
 */
@ContextConfiguration({
        "classpath:spring/datasource-config.xml",
        "classpath:spring/jpa-connector-direct.xml",
        "classpath:jpa-vap-service-context.xml",
        "classpath:search-service-context-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class FlagServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private FlagService flagService;
    @Autowired
    private FlagRepository flagRepository;

    @Before
    public void setup() {
        executeSqlScript("classpath:dbsetup/drop-test-data.sql", false);
    }

    @Test
    public void testFindUserFlagsMap() {
        FlagPk id1 = new FlagPk(12345L, "d1");
        Flag flag1 = new Flag(id1);
        flagService.persist(flag1);

        FlagPk id2 = new FlagPk(12345L, "d2");
        Flag flag2 = new Flag(id2);
        flagService.persist(flag2);

        List<Flag> userFlags = flagService.findUserFlags(12345L);

        assertEquals(2, userFlags.size());
    }

    @Test
    public void testCreate() {
        FlagPk id = new FlagPk(12345L, "d1");
        Flag flag1 = new Flag(id);

        flagService.persist(flag1);

        Collection<Flag> all = flagService.findAll();

        assertEquals(1, all.size());
    }

    @Test(expected = PersistenceException.class)
    public void testDuplicatePks() {
        FlagPk id1 = new FlagPk(12345L, "d1");
        Flag flag1 = new Flag(id1);

        flagService.persist(flag1);

        FlagPk id2 = new FlagPk(12345L, "d1");
        Flag flag2 = new Flag(id2);

        flagService.persist(flag2);
    }

    @Test
    public void removeFlag() {
        FlagPk id1 = new FlagPk(1L, "d1");
        Flag flag1 = new Flag(id1);
        flagService.persist(flag1);

        FlagPk id2 = new FlagPk(2L, "d1");
        Flag flag2 = new Flag(id2);
        flagService.persist(flag2);

        Collection<Flag> all = flagService.findAll();
        assertEquals(2, all.size());

        flagService.remove(id1);

        all = flagService.findAll();
        assertEquals(1, all.size());

        flagService.remove(id2);

        all = flagService.findAll();
        assertEquals(0, all.size());
    }

    @Test
    public void testToggleFlag() {
        FlagPk id1 = new FlagPk(1L, "d1");

        flagService.toggleFlag(id1.getUserId(), id1.getDocumentId());

        assertEquals(1, flagService.findUserFlags(id1.getUserId()).size());
        flagService.toggleFlag(id1.getUserId(), id1.getDocumentId());
        assertEquals(0, flagService.findUserFlags(id1.getUserId()).size());
        flagService.toggleFlag(id1.getUserId(), id1.getDocumentId());
        assertEquals(1, flagService.findUserFlags(id1.getUserId()).size());
        flagService.toggleFlag(id1.getUserId(), id1.getDocumentId());
        assertEquals(0, flagService.findUserFlags(id1.getUserId()).size());
    }
}
