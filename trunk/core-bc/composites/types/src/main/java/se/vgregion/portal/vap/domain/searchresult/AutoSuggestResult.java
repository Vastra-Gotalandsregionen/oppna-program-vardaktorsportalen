package se.vgregion.portal.vap.domain.searchresult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Domain class containing an ordered list of key-value mappings.
 *
 * @author Patrik Bergström
 */
public class AutoSuggestResult {

    private List<Map<String, String>> result = new ArrayList<Map<String, String>>();

    /**
     * Add a key-value mapping last in the list.
     *
     * @param key   the key
     * @param value the value
     */
    public void addKeyValuePair(String key, String value) {
        Map<String, String> keyValuePair = new HashMap<String, String>();
        keyValuePair.put(key, value);
        result.add(keyValuePair);
    }

    public List<Map<String, String>> getResult() {
        return result;
    }
}
