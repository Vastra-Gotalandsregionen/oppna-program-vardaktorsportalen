package se.vgregion.portal.vap.domain.searchresult;

import java.util.ArrayList;
import java.util.List;

public class Facets {
    private List<Entry> entries = new ArrayList<Entry>();

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
