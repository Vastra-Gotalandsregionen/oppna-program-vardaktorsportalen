package se.vgregion.portal.vap.service;

import se.vgregion.portal.vap.domain.searchresult.SearchResult;

import java.util.Collection;
import java.util.List;

/**
 * Service interface for making searches of documents and return them either as JSON {@link String}s or as
 * {@link SearchResult}.
 *
 * @author Patrik Bergström
 */
public interface DocumentSearchService {

    /**
     * Search by a query, e.g. "hits=10&offset=0&q=sjukdom+f%C3%B6rkylning" (but that of course depends on the
     * implementation) and return the result as a JSON {@link String}.
     *
     * @param query the query
     * @return the result as a JSON {@link String}
     * @throws DocumentSearchServiceException DocumentSearchServiceException
     */
    String searchJsonReply(String query) throws DocumentSearchServiceException;

    /**
     * Search by searchTerm, offset and size and return the result as a JSON {@link String}.
     *
     * @param searchTerm the searchTerm
     * @param offset     the offset
     * @param size       the size
     * @return the result as a JSON {@link String}
     * @throws DocumentSearchServiceException DocumentSearchServiceException
     */
    String searchJsonReply(String searchTerm, int offset, int size) throws DocumentSearchServiceException;

    /**
     * Search by a list a md5Sums so find specific documents.
     *
     * @param md5Sums the md5Sums
     * @return the result as a JSON {@link String}
     * @throws DocumentSearchServiceException DocumentSearchServiceException
     */
    String searchJsonReply(List<String> md5Sums) throws DocumentSearchServiceException;

    /**
     * Like {@link DocumentSearchService#searchJsonReply(java.lang.String)} but with {@link SearchResult} as the return
     * type.
     *
     * @param query the query
     * @return the result as a {@link SearchResult} object
     * @throws DocumentSearchServiceException DocumentSearchServiceException
     */
    SearchResult search(String query) throws DocumentSearchServiceException;

    /**
     * Like {@link DocumentSearchService#searchJsonReply(java.lang.String, int, int)} but with {@link SearchResult}
     * as the return type.
     *
     * @param searchTerm the searchTerm
     * @param offset     the offset
     * @param size       the size
     * @return the result as a {@link SearchResult} object
     * @throws DocumentSearchServiceException DocumentSearchServiceException
     */
    SearchResult search(String searchTerm, int offset, int size) throws DocumentSearchServiceException;

    /**
     * Like {@link DocumentSearchService#searchJsonReply(java.util.List)} but with {@link SearchResult} as the return
     * type.
     *
     * @param md5Sums the md5Sums
     * @return the result as a {@link SearchResult} object
     * @throws DocumentSearchServiceException DocumentSearchServiceException
     */
    SearchResult search(List<String> md5Sums) throws DocumentSearchServiceException;

    /**
     * Get auto-suggestions by a searchTerm.
     *
     * @param searchTerm the searchTerm
     * @return the result as a collection of {@link String}s
     */
    Collection<String> getAutoSuggestions(String searchTerm);

    void sendStatisticsRequest(String encodedSearchTerm, String jsonResponse, String id, String screenName,
                               String facetSource);
}
