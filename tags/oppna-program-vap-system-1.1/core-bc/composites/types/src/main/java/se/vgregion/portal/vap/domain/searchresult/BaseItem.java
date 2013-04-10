package se.vgregion.portal.vap.domain.searchresult;

/**
 * Domain class used with parsing JSON.
 *
 * @author Patrik Bergström
 */
public class BaseItem {

    private Integer count;
    private String query;
    private Boolean selected;
    private String displayName;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
