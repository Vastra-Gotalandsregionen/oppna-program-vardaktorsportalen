package se.vgregion.portal.vap.domain.searchresult;

public class SearchTimes {
    private Long totalTimeInMillis;
    private Long searchEngineTimeInMillis;

    public Long getTotalTimeInMillis() {
        return totalTimeInMillis;
    }

    public void setTotalTimeInMillis(Long totalTimeInMillis) {
        this.totalTimeInMillis = totalTimeInMillis;
    }

    public Long getSearchEngineTimeInMillis() {
        return searchEngineTimeInMillis;
    }

    public void setSearchEngineTimeInMillis(Long searchEngineTimeInMillis) {
        this.searchEngineTimeInMillis = searchEngineTimeInMillis;
    }
}
