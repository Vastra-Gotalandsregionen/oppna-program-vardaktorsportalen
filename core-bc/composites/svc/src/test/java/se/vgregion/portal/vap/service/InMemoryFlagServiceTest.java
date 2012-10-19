package se.vgregion.portal.vap.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.portal.vap.domain.jpa.Flag;
import se.vgregion.portal.vap.domain.jpa.FlagPk;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author Patrik Bergström
 */
@ContextConfiguration({
        "classpath:in-memory-vap-service-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class InMemoryFlagServiceTest {

    @Autowired
    private FlagService flagService;

    @Before
    public void setup() {
        Collection<Flag> all = flagService.findAll();
        for (Flag flag : all) {
            flagService.remove(flag.getId());
        }
    }

    @Test
    public void testCreate() {
        FlagPk id = new FlagPk(12345L, "d1");
        Flag flag1 = new Flag(id);

        flagService.persist(flag1);

        Collection<Flag> all = flagService.findAll();

        assertEquals(1, all.size());
    }

    @Test
    public void testDuplicatePks() {
        FlagPk id1 = new FlagPk(12345L, "d1");
        Flag flag1 = new Flag(id1);

        flagService.persist(flag1);

        FlagPk id2 = new FlagPk(12345L, "d1");
        Flag flag2 = new Flag(id2);

        flagService.persist(flag2);

        Collection<Flag> all = flagService.findAll();

        assertEquals(1, all.size()); //Still just one since they are duplicate
    }

    @Test
    public void removeFlag() {
        FlagPk id1 = new FlagPk(1L, "d1");
        Flag flag1 = new Flag(id1);
        flagService.persist(flag1);

        FlagPk id2 = new FlagPk(2L, "d2");
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
}
