package se.vgregion.portal.vap.service;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.vgregion.portal.vap.domain.searchresult.Document;
import se.vgregion.portal.vap.domain.searchresult.SearchResult;
import se.vgregion.portal.vap.domain.autosuggest.AutoSuggestSolrResult;
import se.vgregion.portal.vap.domain.autosuggest.Suggestion;
import se.vgregion.portal.vap.domain.autosuggest.Suggestions;
import se.vgregion.portal.vap.util.JsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author Patrik Bergström
 */
@Service
public class DocumentSearchServiceImpl implements DocumentSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentSearchServiceImpl.class);

    private String queryUrlBase;
    private HttpClient httpClient;
    private HttpClient httpClientAutoSuggestion;
    private String queryStringStructure = "?q=%s&offset=%s&hits=%s";

    @Value("${autoSuggestUrl}")
    private String autoSuggestUrl;// = "http://gbg.findwise.com:9092/fwqc/complete.do?format=json&limit=10&q=%s";

    @Value("${singleDocumentQueryUrl}")
    private String singleDocumentQueryUrl;

    public DocumentSearchServiceImpl(String queryUrlBase) {
        this.queryUrlBase = queryUrlBase;

        initHttpClient();
    }

    private void initHttpClient() {
        ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager();
        connManager.setDefaultMaxPerRoute(50);
        connManager.setMaxTotal(50);
        httpClient = new DefaultHttpClient(connManager);
        ThreadSafeClientConnManager connManagerAutoSuggestion = new ThreadSafeClientConnManager();
        connManagerAutoSuggestion.setDefaultMaxPerRoute(50);
        connManagerAutoSuggestion.setMaxTotal(50);
        httpClientAutoSuggestion = new DefaultHttpClient(connManagerAutoSuggestion);
    }

    @Override
    public String searchJsonReply(String query) throws DocumentSearchServiceException {
        try {
            String url = queryUrlBase + "?" + query;
            InputStream content = executeGetRequest(url, httpClient);

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
            InputStream content = executeGetRequest(url, httpClient);

            return IOUtils.toString(content);
        } catch (IOException e) {
            throw new DocumentSearchServiceException("Search failed.", e);
        }
    }

    @Override
    public String searchJsonReply(List<String> md5Sums) throws DocumentSearchServiceException {

        String url = String.format(singleDocumentQueryUrl, makeCommaSeparatedString(md5Sums));
        try {
            InputStream inputStream = executeGetRequest(url, httpClient);

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
            InputStream content = executeGetRequest(url, httpClient);
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
            InputStream content = executeGetRequest(url, httpClient);

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
            InputStream inputStream = executeGetRequest(url, httpClient);

            SearchResult result = JsonUtil.parseSearchResult(inputStream);

            // Sort according to the order of the original list
            if (result != null && result.getComponents() != null && result.getComponents().getDocuments() != null) {
                Collections.sort(result.getComponents().getDocuments(), new Comparator<Document>() {
                    @Override
                    public int compare(Document o1, Document o2) {
                        return md5Sums.indexOf(o1.getId_hash()) < md5Sums.indexOf(o2.getId_hash()) ? -1 : 1;
                    }
                });
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
        commaSeparatedString.deleteCharAt(commaSeparatedString.length() - 1);

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

    private InputStream executeGetRequest(String url, HttpClient httpClient) throws IOException, DocumentSearchServiceException {
        HttpGet httpGet = new HttpGet(url);

        // Set params
        final HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 2000);
        HttpConnectionParams.setSoTimeout(params, 2000);

        HttpResponse response = httpClient.execute(httpGet);

        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new DocumentSearchServiceException("Search failed with response code = " + statusCode);
        }

        return response.getEntity().getContent();
    }

    private String getCompleteUrl(String query, int offset, int hits) {
        return queryUrlBase + String.format(queryStringStructure, query, offset, hits);
    }
}
