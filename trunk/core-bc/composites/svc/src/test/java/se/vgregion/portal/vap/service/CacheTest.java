package se.vgregion.portal.vap.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author Patrik Bergström
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:mock-search-service-context.xml", "classpath*:test-properties.xml"})
public class CacheTest {

    @Autowired
    private DocumentSearchService documentSearchService;
    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testCacheBehavior() throws InterruptedException {

        // The cache is configured to hold maximum 10 elements in memory

        Cache autosuggest = cacheManager.getCache("autosuggest");

        Collection<String> suggestions = documentSearchService.getAutoSuggestions("1");
        suggestions = documentSearchService.getAutoSuggestions("2");
        suggestions = documentSearchService.getAutoSuggestions("3");
        suggestions = documentSearchService.getAutoSuggestions("4");
        suggestions = documentSearchService.getAutoSuggestions("5");
        suggestions = documentSearchService.getAutoSuggestions("6");
        suggestions = documentSearchService.getAutoSuggestions("7");
        suggestions = documentSearchService.getAutoSuggestions("8");
        suggestions = documentSearchService.getAutoSuggestions("9");
        suggestions = documentSearchService.getAutoSuggestions("10");
        suggestions = documentSearchService.getAutoSuggestions("11");

        // We first added one entry to the cache and then ten more which means that only the last ten should still be
        // in the cache.
        assertEquals(10, autosuggest.getStatistics().getObjectCount());

        // The first entry should not result in a hit
        documentSearchService.getAutoSuggestions("1");

        // So far no hits
        assertEquals(0, autosuggest.getStatistics().getCacheHits());

        // But now try a stored entry
        documentSearchService.getAutoSuggestions("11");

        // Now we have one hit
        assertEquals(1, autosuggest.getStatistics().getCacheHits());

        // Try after sleep (the cache entries should have expired)
        Thread.sleep(1100);
        documentSearchService.getAutoSuggestions("11");

        // No more hit
        assertEquals(1, autosuggest.getStatistics().getCacheHits());

    }

}
