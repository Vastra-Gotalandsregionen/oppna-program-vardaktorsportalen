package se.vgregion.portal.vap.util;

import org.codehaus.jackson.PrettyPrinter;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.util.DefaultPrettyPrinter;
import se.vgregion.portal.vap.domain.searchresult.SearchResult;
import se.vgregion.portal.vap.domain.autosuggest.AutoSuggestSolrResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Utility class for parsing and formatting JSON.
 *
 * @author Patrik Bergström
 */
public final class JsonUtil {

    private JsonUtil() {
    }

    /**
     * Format {@link SearchResult} into a JSON string.
     *
     * @param result      {@link SearchResult}
     * @param prettyPrint whether to print with line breaks and indentation
     * @return the JSON string
     * @throws IOException IOException
     */
    public static String format(SearchResult result, boolean prettyPrint) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        ObjectWriter writer;
        if (prettyPrint) {
            PrettyPrinter pp = new DefaultPrettyPrinter();
            writer = mapper.writer(pp);
        } else {
            writer = mapper.writer();
        }

        // Format
        StringWriter sw = new StringWriter();
        writer.writeValue(sw, result);

        return sw.toString();
    }

    /**
     * Parse a JSON string.
     *
     * @param input the JSON string
     * @return the {@link SearchResult}
     * @throws IOException IOException
     */
    public static SearchResult parse(String input) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        SearchResult searchResult = mapper.readValue(input, SearchResult.class);

        return searchResult;
    }

    /**
     * Parse an {@link InputStream} containing JSON data corresponding to {@link SearchResult}.
     *
     * @param input the {@link InputStream}
     * @return the {@link SearchResult}
     * @throws IOException IOException
     */
    public static SearchResult parseSearchResult(InputStream input) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        SearchResult searchResult = mapper.readValue(input, SearchResult.class);

        return searchResult;
    }

    /**
     * Parse an {@link InputStream} containing JSON data corresponding to {@link AutoSuggestSolrResult}.
     *
     * @param input the {@link InputStream}
     * @return the {@link AutoSuggestSolrResult}
     * @throws IOException IOException
     */
    public static AutoSuggestSolrResult parseAutoSuggestResult(InputStream input) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        AutoSuggestSolrResult autoSuggestSolrResult = mapper.readValue(input, AutoSuggestSolrResult.class);

        return autoSuggestSolrResult;
    }
}
