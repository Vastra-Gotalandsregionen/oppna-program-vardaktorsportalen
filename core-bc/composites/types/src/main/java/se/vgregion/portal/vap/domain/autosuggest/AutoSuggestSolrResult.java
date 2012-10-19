package se.vgregion.portal.vap.domain.autosuggest;

import java.util.ArrayList;
import java.util.List;

public class AutoSuggestSolrResult {
    private String query;
    private Integer limit;
    private List<Suggestions> suggestions = new ArrayList<Suggestions>();

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<Suggestions> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestions> suggestions) {
        this.suggestions = suggestions;
    }
}