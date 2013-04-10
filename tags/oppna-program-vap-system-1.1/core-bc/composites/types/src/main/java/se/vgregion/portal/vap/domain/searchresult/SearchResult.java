package se.vgregion.portal.vap.domain.searchresult;

public class SearchResult {
    private Long numberOfHits;
    private Components components;

    public Long getNumberOfHits() {
        return numberOfHits;
    }

    public void setNumberOfHits(Long numberOfHits) {
        this.numberOfHits = numberOfHits;
    }

    public Components getComponents() {
        return components;
    }

    public void setComponents(Components components) {
        this.components = components;
    }
}