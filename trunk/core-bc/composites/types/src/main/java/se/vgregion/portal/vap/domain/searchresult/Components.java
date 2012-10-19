package se.vgregion.portal.vap.domain.searchresult;

import java.util.List;

public class Components {
    private List<Document> documents;
    private Facets facets = new Facets();
    private SearchTimes searchTimes = new SearchTimes();
    private Pagination pagination;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Facets getFacets() {
        return facets;
    }

    public void setFacets(Facets facets) {
        this.facets = facets;
    }

    public SearchTimes getSearchTimes() {
        return searchTimes;
    }

    public void setSearchTimes(SearchTimes searchTimes) {
        this.searchTimes = searchTimes;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
