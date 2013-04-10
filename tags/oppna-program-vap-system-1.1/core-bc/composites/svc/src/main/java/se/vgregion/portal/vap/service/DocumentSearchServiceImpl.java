package se.vgregion.portal.vap.service;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.vgregion.portal.vap.domain.autosuggest.AutoSuggestSolrResult;
import se.vgregion.portal.vap.domain.autosuggest.Suggestion;
import se.vgregion.portal.vap.domain.autosuggest.Suggestions;
import se.vgregion.portal.vap.domain.searchresult.Document;
import se.vgregion.portal.vap.domain.searchresult.SearchResult;
import se.vgregion.portal.vap.util.JsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Implementation of {@link DocumentSearchService}. The implementation queries a webservice on specific url:s.
 *
 * @author Patrik Bergström
 */
@Service
public class DocumentSearchServiceImpl implements DocumentSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentSearchServiceImpl.class);
    private String queryUrlBase;
    private HttpClient httpClientSearch;
    private HttpClient httpClientAutoSuggestion;
    private String queryStringStructure = "?q=%s&offset=%s&hits=%s";
    private Executor executor = Executors.newSingleThreadExecutor();

    @Value("${autoSuggestUrl}")
    private String autoSuggestUrl;

    @Value("${singleDocumentQueryUrl}")
    private String singleDocumentQueryUrl;

    @Value("${statisticsUrl}")
    private String statisticsUrl;

    /**
     * Constructor.
     *
     * @param queryUrlBase the base for the query url
     */
    public DocumentSearchServiceImpl(String queryUrlBase) {
        this.queryUrlBase = queryUrlBase;

        initHttpClient();
    }

    private void initHttpClient() {
        ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager();
        final int fifty = 50;
        connManager.setDefaultMaxPerRoute(fifty);
        connManager.setMaxTotal(fifty);
        httpClientSearch = new DefaultHttpClient(connManager);
        ThreadSafeClientConnManager connManagerAutoSuggestion = new ThreadSafeClientConnManager();
        connManagerAutoSuggestion.setDefaultMaxPerRoute(fifty);
        connManagerAutoSuggestion.setMaxTotal(fifty);
        httpClientAutoSuggestion = new DefaultHttpClient(connManagerAutoSuggestion);
    }

    @Override
    public String searchJsonReply(String query) throws DocumentSearchServiceException {
        try {
            String url = queryUrlBase + "?" + query;
            InputStream content = executeGetRequest(url, httpClientSearch);

            return IOUtils.toString(content);
        } catch (IOException e) {
            throw new DocumentSearchServiceException("Search failed.", e);
        }
    }

    @Override
    public String searchJsonReply(String query, int offset, int size) throws DocumentSearchServiceException {
        try {
            String queryEncoded = URLEncoder.encode(query, "UTF-8");
            String url = getCompleteUrl(queryEncoded, offset, size);
            InputStream content = executeGetRequest(url, httpClientSearch);

            return IOUtils.toString(content);
        } catch (IOException e) {
            throw new DocumentSearchServiceException("Search failed.", e);
        }
    }

    @Override
    public String searchJsonReply(List<String> md5Sums) throws DocumentSearchServiceException {

        String url = String.format(singleDocumentQueryUrl, makeCommaSeparatedString(md5Sums));
        try {
            InputStream inputStream = executeGetRequest(url, httpClientSearch);

            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new DocumentSearchServiceException(e.getMessage(), e);
        }
    }

    @Override
    public SearchResult search(String query) throws DocumentSearchServiceException {
        try {
            String url = queryUrlBase + "?" + query;
            InputStream content = executeGetRequest(url, httpClientSearch);
            SearchResult result = JsonUtil.parseSearchResult(content);
            return result;
        } catch (IOException e) {
            throw new DocumentSearchServiceException(e.getMessage(), e);
        }
    }

    @Override
    public SearchResult search(String query, int offset, int size) throws DocumentSearchServiceException {

        try {
            String queryEncoded = URLEncoder.encode(query, "UTF-8");
            String url = getCompleteUrl(queryEncoded, offset, size);
            InputStream content = executeGetRequest(url, httpClientSearch);

            SearchResult result = JsonUtil.parseSearchResult(content);

            return result;
        } catch (IOException e) {
            throw new DocumentSearchServiceException("Search failed.", e);
        }
    }

    @Override
    public SearchResult search(final List<String> md5Sums) throws DocumentSearchServiceException {

        String commaSeparatedString = makeCommaSeparatedString(md5Sums);

        String url = String.format(singleDocumentQueryUrl, commaSeparatedString);

        try {
            InputStream inputStream = executeGetRequest(url, httpClientSearch);

            SearchResult result = JsonUtil.parseSearchResult(inputStream);

            // Sort according to the order of the original list
            if (result != null && result.getComponents() != null && result.getComponents().getDocuments() != null) {
                Collections.sort(result.getComponents().getDocuments(), new DocumentComparator(md5Sums));
            }

            return result;
        } catch (IOException e) {
            throw new DocumentSearchServiceException(e.getMessage(), e);
        }
    }

    private String makeCommaSeparatedString(List<String> md5Sums) {
    	
        StringBuilder commaSeparatedString = new StringBuilder();
        for (String md5Sum : md5Sums) {
            commaSeparatedString.append(md5Sum + ",");
        }

        if(commaSeparatedString.length() > 0) {
        	commaSeparatedString.deleteCharAt(commaSeparatedString.length() - 1);	
        }

        return commaSeparatedString.toString();
    }

    @Override
    @Cacheable(value = "autosuggest")
    public Collection<String> getAutoSuggestions(String query) {
        Collection<String> toBeReturned = new ArrayList<String>();

        try {
            String queryEncoded = URLEncoder.encode(query, "UTF-8");

            String url = String.format(autoSuggestUrl, queryEncoded);

            InputStream inputStream = executeGetRequest(url, httpClientAutoSuggestion);

            AutoSuggestSolrResult result = JsonUtil.parseAutoSuggestResult(inputStream);

            List<Suggestions> suggestions = result.getSuggestions();

            for (Suggestions suggestion : suggestions) {
                List<Suggestion> innerSuggestions = suggestion.getSuggestions();
                for (Suggestion s : innerSuggestions) {
                    String value = s.getValue();
                    toBeReturned.add(value);
                }
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (DocumentSearchServiceException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return toBeReturned;
    }

    @Override
    public void sendStatisticsRequest(final String encodedSearchTerm, final String jsonResult, final String sessionId,
                                      final String screenName, final String facetSource) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer hits = extractHits(jsonResult);

                    String pageUrl = "q=" + encodedSearchTerm
                            + "&filter=scope:VGRegionvardaktorsportalen&scoped=true";
                    if (facetSource != null) {
                        pageUrl = pageUrl + "&facet_source=" + facetSource;
                    }
                    String pageurlEncoded = URLEncoder.encode(pageUrl, "UTF-8");
                    String url = String.format(statisticsUrl, hits, screenName, sessionId, pageurlEncoded);

                    executeGetRequest(url, httpClientSearch);
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                } catch (DocumentSearchServiceException e) {
                    LOGGER.error(e.getMessage(), e);
                } catch (RuntimeException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        });
    }

    Integer extractHits(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode hits = mapper.reader().readTree(json).get("numberOfHits");
            int intValue = hits.getIntValue();
            return intValue;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }

    private InputStream executeGetRequest(String url, HttpClient httpClient) throws IOException,
            DocumentSearchServiceException {
        HttpGet httpGet = new HttpGet(url);

        // Set params
        final HttpParams params = httpClient.getParams();
        final int fourThousand = 4000;
        HttpConnectionParams.setConnectionTimeout(params, fourThousand);
        HttpConnectionParams.setSoTimeout(params, fourThousand);

        HttpResponse response = httpClient.execute(httpGet);

        final int statusCode = response.getStatusLine().getStatusCode();
        final int ok = 200;
        final int noContent = 204;
        if (statusCode != ok) {
            throw new DocumentSearchServiceException("Request failed with response code = " + statusCode);
        } else if (statusCode == noContent) {
            return null;
        }

        return response.getEntity().getContent();
    }

    private String getCompleteUrl(String query, int offset, int hits) {
        return queryUrlBase + String.format(queryStringStructure, query, offset, hits);
    }

    public void setHttpClientSearch(HttpClient httpClientSearch) {
        this.httpClientSearch = httpClientSearch;
    }

    public void setHttpClientAutoSuggestion(HttpClient httpClientAutoSuggestion) {
        this.httpClientAutoSuggestion = httpClientAutoSuggestion;
    }

    private static class DocumentComparator implements Comparator<Document>, Serializable {
        private static final long serialVersionUID = 276875403019707216L;

        private final List<String> md5Sums;

        public DocumentComparator(List<String> md5Sums) {
            this.md5Sums = md5Sums;
        }

        @Override
        public int compare(Document o1, Document o2) {
            return md5Sums.indexOf(o1.getId_hash()) < md5Sums.indexOf(o2.getId_hash()) ? -1 : 1;
        }
    }
}
