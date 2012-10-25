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


    String searchJsonReply(List<String> md5Sums) throws DocumentSearchServiceException;

    SearchResult search(String query) throws DocumentSearchServiceException;

    SearchResult search(String searchTerm, int offset, int size) throws DocumentSearchServiceException;

    SearchResult search(List<String> md5Sums) throws DocumentSearchServiceException;

    Collection<String> getAutoSuggestions(String searchTerm);
}
