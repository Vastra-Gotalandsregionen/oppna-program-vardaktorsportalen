package se.vgregion.portal.vap.service;

import se.vgregion.portal.vap.domain.searchresult.SearchResult;

import java.util.Collection;
import java.util.List;

/**
 * @author Patrik Bergström
 */
public interface DocumentSearchService {
    String searchJsonReply(String query) throws DocumentSearchServiceException;
    String searchJsonReply(String searchTerm, int offset, int size) throws DocumentSearchServiceException;
    String searchJsonReply(List<String> strings) throws DocumentSearchServiceException;
    SearchResult search(String query) throws DocumentSearchServiceException;
    SearchResult search(String searchTerm, int offset, int size) throws DocumentSearchServiceException;
    SearchResult search(List<String> md5Sums) throws DocumentSearchServiceException;

    Collection<String> getAutoSuggestions(String searchTerm);
}
