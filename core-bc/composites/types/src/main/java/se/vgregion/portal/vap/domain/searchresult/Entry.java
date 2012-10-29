package se.vgregion.portal.vap.domain.searchresult;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain class used for parsing JSON.
 *
 * @author Patrik Bergström
 */
public class Entry {

    private List<SelectableItem> selectableItems = new ArrayList<SelectableItem>();
    private List<AppliedItem> appliedItems = new ArrayList<AppliedItem>();
    private String type;
    private String displayName;
    
    public List<SelectableItem> getSelectableItems() {
        return selectableItems;
    }

    public void setSelectableItems(List<SelectableItem> selectableItems) {
        this.selectableItems = selectableItems;
    }

    public List<AppliedItem> getAppliedItems() {
        return appliedItems;
    }

    public void setAppliedItems(List<AppliedItem> appliedItems) {
        this.appliedItems = appliedItems;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
