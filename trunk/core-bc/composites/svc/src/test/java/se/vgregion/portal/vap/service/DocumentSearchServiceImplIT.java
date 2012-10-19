package se.vgregion.portal.vap.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.portal.vap.domain.searchresult.Document;
import se.vgregion.portal.vap.domain.searchresult.SearchResult;
import se.vgregion.portal.vap.util.JsonUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Patrik Bergström
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:search-service-context.xml", "classpath*:test-properties.xml"})
public class DocumentSearchServiceImplIT {

    @Autowired
    private DocumentSearchService documentSearchService;

    @Before
    public void assertBefore() {
        // This is a proxy and this is a simple (but ugly) way of finding the underlying target class
        if (!(documentSearchService.toString().contains("DocumentSearchServiceImpl"))) {
            throw new RuntimeException("These tests require a specific implementation.");
        }
    }

    @Test
    public void testSearch() throws Exception, DocumentSearchServiceException {
        SearchResult searchResult = documentSearchService.search("sjukdom förkylning", 0, 10);

        assertTrue(searchResult.getNumberOfHits() > 80);
    }

    @Test
    public void testSearchJsonString() throws DocumentSearchServiceException, IOException {
        String result = documentSearchService.searchJsonReply("hits=10&offset=0&q=sjukdom+f%C3%B6rkylning");

        System.out.println(result);

        SearchResult searchResult = JsonUtil.parse(result);

        // Success if no exception
    }

    @Test
    public void testAutosuggestion() {
        long start = System.currentTimeMillis();
        Collection<String> suggestions = documentSearchService.getAutoSuggestions("sj"); // Warm-up
        double firstRequestTime = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        suggestions = documentSearchService.getAutoSuggestions("för");
        double secondRequestTime = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        suggestions = documentSearchService.getAutoSuggestions("för");
        double thirdRequestTime = System.currentTimeMillis() - start;

        System.out.println(thirdRequestTime / secondRequestTime);

        // Without cache they take approximately the same time and with cache we say that it should take at most five
        // percent of the original time.
        assertTrue("The caching does not seem to work.", thirdRequestTime / secondRequestTime < 0.05);
    }

    @Test
    public void testAutoSuggestionSearchResult() throws IOException {

        Collection<String> autoSuggestions = documentSearchService.getAutoSuggestions("ba");

        assertTrue(autoSuggestions.size() > 5);
    }

    @Test
    public void testSearchSingleDocument() throws DocumentSearchServiceException {
        SearchResult searchResult = documentSearchService.search(Arrays.asList("026fec0720052869c491720555e215a8",
                "bdef57a2506eb0de9640777b103c4d64"));

        List<Document> documents = searchResult.getComponents().getDocuments();

        assertEquals(2, documents.size());
        assertEquals("Socialstyrelsen", documents.get(0).getAuthor());
    }

}