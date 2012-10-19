package se.vgregion.portal.vap.domain.searchresult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Patrik Bergström
 */
public class AutoSuggestResult {

    private List<Map<String, String>> result = new ArrayList<Map<String, String>>();

    public void addKeyValuePair(String key, String value) {
        Map<String, String> keyValuePair = new HashMap<String, String>();
        keyValuePair.put(key, value);
        result.add(keyValuePair);
    }

    public List<Map<String, String>> getResult() {
        return result;
    }
}
