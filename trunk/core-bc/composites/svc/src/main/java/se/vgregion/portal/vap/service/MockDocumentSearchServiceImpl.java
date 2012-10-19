package se.vgregion.portal.vap.service;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.springframework.cache.annotation.Cacheable;
import se.vgregion.portal.vap.domain.searchresult.*;
import se.vgregion.portal.vap.util.JsonUtil;

import java.io.IOException;
import java.util.*;

/**
 * @author Patrik Bergström
 */
public class MockDocumentSearchServiceImpl implements DocumentSearchService {

    private IndexWriter indexWriter;
    private IndexSearcher indexSearcher;
    private QueryParser queryParser;

    private Set<String> wordPile = new HashSet<String>();

    public MockDocumentSearchServiceImpl() {
        Directory d = new RAMDirectory();
        try {
            indexWriter = new IndexWriter(d, new IndexWriterConfig(Version.LUCENE_36,
                    new StandardAnalyzer(Version.LUCENE_36)));

            addDocument("Around the world in eighty days", aroundTheWorldInEightyDays);
            addDocument("Moby Dick", mobyDick);
            addDocument("Oliver Twist", oliverTwist);
            addDocument("Robinson Crusoe", robinsonCrusoe);

            indexWriter.close();

            IndexReader reader = IndexReader.open(d);
            indexSearcher = new IndexSearcher(reader);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        queryParser = new QueryParser(Version.LUCENE_36, "content", new StandardAnalyzer(Version.LUCENE_36));

        addAllWordsToBucket(aroundTheWorldInEightyDays);
        addAllWordsToBucket(mobyDick);
        addAllWordsToBucket(oliverTwist);
        addAllWordsToBucket(robinsonCrusoe);
    }

    private void addAllWordsToBucket(String source) {
        Scanner scanner = new Scanner(source);
        while (scanner.hasNext()) {
            wordPile.add(scanner.next());
        }
    }

    private void addDocument(String title, String content) throws IOException {
        org.apache.lucene.document.Document document = new org.apache.lucene.document.Document();
        document.add(new Field("title", title, Field.Store.YES, Field.Index.ANALYZED));
        document.add(new Field("content", content, Field.Store.YES, Field.Index.ANALYZED));
        indexWriter.addDocument(document);
    }

    @Override
    public String searchJsonReply(String query) throws DocumentSearchServiceException {
        SearchResult result = search(query, 0, 10);

        try {
            return JsonUtil.format(result, false);
        } catch (IOException e) {
            throw new DocumentSearchServiceException(e.getMessage(), e);
        }
    }

    @Override
    public String searchJsonReply(String query, int offset, int size) throws DocumentSearchServiceException {
        SearchResult result = search(query, offset, size);

        try {
            return JsonUtil.format(result, false);
        } catch (IOException e) {
            throw new DocumentSearchServiceException(e.getMessage(), e);
        }
    }

    @Override
    public String searchJsonReply(List<String> strings) throws DocumentSearchServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SearchResult search(String query) throws DocumentSearchServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SearchResult search(String queryString, int offset, int size) {

        String luceneQueryString = "content:" + queryString;

        List<Document> documents = new ArrayList<Document>();

        try {

            Query query = queryParser.parse(luceneQueryString);
            TopDocs search = indexSearcher.search(query, 100);
            ScoreDoc[] scoreDocs = search.scoreDocs;

            for (ScoreDoc scoreDoc : scoreDocs) {

                org.apache.lucene.document.Document doc = indexSearcher.doc(scoreDoc.doc);

                Document document = new Document();
                document.setId(scoreDoc.doc + "");
                document.setId_hash(scoreDoc.doc + "");
                document.setDescription(doc.get("content"));
                document.setTitle(doc.get("title"));
                documents.add(document);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numberOfHits = documents.size();

        int resultsStart = offset * size;
        int resultsEnd = resultsStart + size;
        if (resultsEnd > numberOfHits) {
            resultsEnd = numberOfHits;
        }

        List<Document> documentsToReturn = documents.subList(resultsStart, resultsEnd);

        SearchResult searchResult = new SearchResult();
        searchResult.setNumberOfHits(new Long(numberOfHits));

        Components components = new Components();
        components.setDocuments(documentsToReturn);
        searchResult.setComponents(components);

        Facets facets = new Facets();
        Entry entry = new Entry();

        if (queryString.contains("&facet=true")) {
            AppliedItem appliedItem = new AppliedItem();
            appliedItem.setCount(80);
            appliedItem.setDisplayName("TestFacett");
            appliedItem.setQuery(queryString.replaceAll("&facet=true", ""));
            entry.setAppliedItems(Arrays.asList(appliedItem));
        } else {
            SelectableItem selectableItem = new SelectableItem();
            selectableItem.setCount(80);
            selectableItem.setDisplayName("TestFacett");
            selectableItem.setQuery(queryString + "&facet=true");
            entry.setSelectableItems(Arrays.asList(selectableItem));
        }
        facets.setEntries(Arrays.asList(entry));
        components.setFacets(facets);

        return searchResult;
    }

    @Override
    public SearchResult search(List<String> md5Sums) {
        throw new UnsupportedOperationException(); // todo
    }

    @Override
    @Cacheable("autosuggest")
    public Collection<String> getAutoSuggestions(String term) {
        Set<String> suggestions = new HashSet<String>();

        for (String s : wordPile) {
            if (s.startsWith(term)) {
                suggestions.add(s);
            }
        }

        return suggestions;
    }

    String aroundTheWorldInEightyDays = "This is a book excerpt. IN WHICH PASSEPARTOUT IS CONVINCED THAT HE HAS AT LAST FOUND HIS IDEAL \n" +
            "‘Faith,’ muttered Passepartout, somewhat flurried, ‘I’ve seen people at Madame Tussaud’s as lively as my new master!’\n" +
            "Madame Tussaud’s ‘people,’ let it be said, are of wax, and are much visited in London; speech is all that is wanting to make them human. \n" +
            "During his brief interview with Mr. Fogg, Passepartout had been carefully observing him. He appeared to be a man about forty years of  age, with fine, handsome \n" +
            "features, and a tall, well-shaped figure; his hair and whiskers were light, his forehead compact and unwrinkled, his face rather pale, his teeth magnificent. His countenance possessed in the highest degree what physiognomists call ‘repose  in action,’ a quality of those who act rather than talk. Calm and phlegmatic, with a clear eye, Mr. Fogg seemed a perfect type of that English composure which Angelica Kauffmann has so skilfully \n" +
            "represented on canvas. Seen in the various phases of his daily life, he gave the idea of being perfectly wellbalanced, as exactly regulated as a Leroy chronometer. Phileas Fogg was, indeed, exactitude personified, and this was betrayed even in the expression of his very hands and feet; for in men, as well as in animals, the limbs themselves are expressive of the passions.";

    String mobyDick = "This is a book excerpt. Call me Ishmael. Some years ago--never mind how long precisely--having little or no money in" +
            " my purse, and nothing particular to interest me on shore, I thought I would sail about a little and see" +
            " the watery part of the world. It is a way I have of driving off the spleen and regulating the" +
            " circulation. Whenever I find myself growing grim about the mouth; whenever it is a damp, drizzly" +
            " November in my soul; whenever I find myself involuntarily pausing before coffin warehouses, and" +
            " bringing up the rear of every funeral I meet; and especially whenever my hypos get such an upper hand" +
            " of me, that it requires a strong moral principle to prevent me from deliberately stepping into the" +
            " street, and methodically knocking people's hats off--then, I account it high time to get to sea as" +
            " soon as I can. This is my substitute for pistol and ball. With a philosophical flourish Cato throws" +
            " himself upon his sword; I quietly take to the ship. There is nothing surprising in this. If they but" +
            " knew it, almost all men in their degree, some time or other, cherish very nearly the same feelings" +
            " towards the ocean with me.";

    String oliverTwist = "This is a book excerpt. Among other public buildings in a certain town, which for many reasons it will be prudent" +
            " to refrain from mentioning, and to which I will assign no fictitious name, there is one anciently" +
            " common to most towns, great or small: to wit, a workhouse; and in this workhouse was born; on a day" +
            " and date which I need not trouble myself to repeat, inasmuch as it can be of no possible consequence" +
            " to the reader, in this stage of the business at all events; the item of mortality whose name is" +
            " prefixed to the head of this chapter.";

    String robinsonCrusoe = "This is a book excerpt. I WAS born in the year 1632, in the city of York, of a good family, though not of that" +
            " country, my father being a foreigner of Bremen, who settled first at Hull. He got a good estate by" +
            " merchandise, and leaving off his trade, lived afterwards at York, from whence he had married my mother," +
            " whose relations were named Robinson, a very good family in that country, and from whom I was called" +
            " Robinson Kreutznaer; but, by the usual corruption of words in England, we are now called - nay we call" +
            " ourselves and write our name - Crusoe; and so my companions always called me.";
}
