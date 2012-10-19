package se.vgregion.portal.vap.domain.autosuggest;

import java.util.ArrayList;
import java.util.List;

public class Suggestions {
    private String category;
    private List<Suggestion> suggestions = new ArrayList<Suggestion>();

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }


}
