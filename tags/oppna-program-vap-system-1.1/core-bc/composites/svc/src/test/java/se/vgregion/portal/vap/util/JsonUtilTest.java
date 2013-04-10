package se.vgregion.portal.vap.util;

import org.junit.Test;
import se.vgregion.portal.vap.domain.searchresult.*;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author Patrik Bergström
 */
public class JsonUtilTest {

    @Test
    public void testJsonFormattingAndParseBack() throws IOException {
        SearchResult result = setupObject();

        // Format
        String jsonResult = JsonUtil.format(result, false);

        // Parse back
        SearchResult searchResult = JsonUtil.parse(jsonResult);

        // Verify
        assertEquals((Long) 561L, searchResult.getNumberOfHits());
        assertEquals(2, searchResult.getComponents().getDocuments().size());
    }

    @Test
    public void testParseJson() throws IOException {
        SearchResult searchResult = JsonUtil.parse(searchResultString);

        // Take some sample tests
        assertEquals((Long) 5949L, searchResult.getNumberOfHits());
        assertEquals(10, searchResult.getComponents().getDocuments().size());
        assertEquals("1", searchResult.getComponents().getPagination().getFirstPage().getDisplayName());
        assertEquals((Long) 46L, searchResult.getComponents().getSearchTimes().getTotalTimeInMillis());
    }

    private SearchResult setupObject() {
        SearchResult result = new SearchResult();
        result.setNumberOfHits(561L);

        Components components = new Components();
        Document d1 = new Document();
        d1.setTitle("aTitle");
        d1.setDescription("Detta dokument handlar om...");
        d1.setId("aslökfjwa4h328927y98ytaw89eyf");
        d1.setId_hash("aslökfjwa4h328927y98ytaw89eyf");
        d1.setUrl("http://example.com");
        Document d2 = new Document();
        d2.setTitle("anotherTitle");
        d2.setDescription("Detta dokument handlar om...");
        d2.setId("aslökfjwa4h328927y98ytaw89eyf");
        d2.setUrl("http://google.com");
        components.setDocuments(Arrays.asList(d1, d2));

        Pagination pagination = new Pagination();

        Page firstPage = new Page();
        firstPage.setApplied(true);
        firstPage.setDisplayName("1");
        firstPage.setQuery("hits=10&offset=0&q=sjukdom");

        Page aPage = new Page();
        aPage.setApplied(true);
        aPage.setDisplayName("1");
        aPage.setQuery("hits=10&offset=0&q=sjukdom");

        Page anotherPage = new Page();
        anotherPage.setApplied(true);
        anotherPage.setDisplayName("1");
        anotherPage.setQuery("hits=10&offset=0&q=sjukdom");

        pagination.setFirstPage(firstPage);
        pagination.setPages(Arrays.asList(aPage, anotherPage));

        components.setPagination(pagination);

        result.setComponents(components);
        return result;
    }

    private String searchResultString = "{\n" +
            "   \"components\" : {\n" +
            "      \"documents\" : [\n" +
            "         {\n" +
            "            \"source\" : \"Dokumentlager\",\n" +
            "            \"revisiondate\" : \"2012-02-03 09:14:28\",\n" +
            "            \"description\" : \" detta ingår en noggrann förplanering av graviditeten vid känd <em>diabetes</em>, screening för tidig upptäckt av\",\n" +
            "            \"id_hash\" : \"2aef76ce3a018d8fb7f02121b22676be\",\n" +
            "            \"format\" : [\n" +
            "               \"p-facet.format.pdf\"\n" +
            "            ],\n" +
            "            \"url\" : \"http://alfresco.vgregion.se/alfresco/service/vgr/storage/node/content/3130?a=false&guest=true\",\n" +
            "            \"id\" : \"tag:alfresco.vgregion.se,2011-06-30:2d056e3b-6e9a-4a25-ab63-376471684637\",\n" +
            "            \"title\" : \"<em>Diabetes</em> och graviditet\",\n" +
            "            \"dc_date_validto_dt\" : \"2014-01-18 00:00:00\",\n" +
            "            \"dc_publisher_forunit\" : [\n" +
            "               \"kiv\"\n" +
            "            ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"source\" : \"Dokumentlager\",\n" +
            "            \"revisiondate\" : \"2012-02-03 09:14:27\",\n" +
            "            \"description\" : \"Diabetesvården av äldre bör fokusera på välbefinnande, en god livskvalitet och inriktas på fullgod nutrition, säkerhet, förhindra uppkomsten av fotsår. Individuell vårdplan är nyckeln till god och säk\",\n" +
            "            \"id_hash\" : \"1e03603686980ae17f0c191c92ac0c7a\",\n" +
            "            \"format\" : [\n" +
            "               \"p-facet.format.pdf\"\n" +
            "            ],\n" +
            "            \"url\" : \"http://alfresco.vgregion.se/alfresco/service/vgr/storage/node/content/3138?a=false&guest=true\",\n" +
            "            \"id\" : \"tag:alfresco.vgregion.se,2011-06-30:0fd9515d-b05f-4676-b3bd-e53ecb704663\",\n" +
            "            \"title\" : \"<em>Diabetes</em> och äldre\",\n" +
            "            \"dc_date_validto_dt\" : \"2014-01-18 00:00:00\",\n" +
            "            \"dc_publisher_forunit\" : [\n" +
            "               \"kiv\"\n" +
            "            ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"source\" : \"Dokumentlager\",\n" +
            "            \"revisiondate\" : \"2012-03-01 17:58:00\",\n" +
            "            \"description\" : \"Kartläggning av patientens behov, diabetesteamets roll och hur enskilda teammedlemmar nås, ansvarsfördelning samt mål med behandlingen är viktiga delar i denna vårdöverenskommelse. Kan användas som et\",\n" +
            "            \"id_hash\" : \"1aff98cacdc390015205a6d33faaafe3\",\n" +
            "            \"format\" : [\n" +
            "               \"p-facet.format.pdf\"\n" +
            "            ],\n" +
            "            \"url\" : \"http://alfresco.vgregion.se/alfresco/service/vgr/storage/node/content/3524?a=false&guest=true\",\n" +
            "            \"id\" : \"tag:alfresco.vgregion.se,2011-06-30:207088c5-3875-456d-a0a9-6d3d38103523\",\n" +
            "            \"title\" : \"<em>Diabetes</em> - individuell vårdöverenskommelse\",\n" +
            "            \"dc_date_validto_dt\" : \"2014-02-20 00:00:00\",\n" +
            "            \"dc_publisher_forunit\" : [\n" +
            "               \"kiv\"\n" +
            "            ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"source\" : \"Dokumentlager\",\n" +
            "            \"revisiondate\" : \"2012-03-06 08:53:00\",\n" +
            "            \"description\" : \"Vikten av tidigt insatt och intensiv behandling av samtliga riskfaktorer vid <em>diabetes</em> typ 2\",\n" +
            "            \"id_hash\" : \"6e4a426e92061f9484e975649c359c5b\",\n" +
            "            \"format\" : [\n" +
            "               \"p-facet.format.pdf\"\n" +
            "            ],\n" +
            "            \"url\" : \"http://alfresco.vgregion.se/alfresco/service/vgr/storage/node/content/3601?a=false&guest=true\",\n" +
            "            \"id\" : \"tag:alfresco.vgregion.se,2011-06-30:213f1a62-91f9-4c07-88d8-cf79a268483a\",\n" +
            "            \"title\" : \"<em>Diabetes</em>-typ 2\",\n" +
            "            \"dc_date_validto_dt\" : \"2014-03-06 00:00:00\",\n" +
            "            \"dc_publisher_forunit\" : [\n" +
            "               \"kiv\"\n" +
            "            ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"source\" : \"1177\",\n" +
            "            \"format\" : [\n" +
            "               \"p-facet.format.webpage\"\n" +
            "            ],\n" +
            "            \"url\" : \"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Diabetes-typ-1/\",\n" +
            "            \"revisiondate\" : \"2012-06-27 02:00:00\",\n" +
            "            \"title\" : \"<em>Diabetes</em> typ 1\",\n" +
            "            \"id\" : \"d311a4343867ab5bcb7597f1bcd0a26d\",\n" +
            "            \"id_hash\" : \"d311a4343867ab5bcb7597f1bcd0a26d\"\n" +
            "         },\n" +
            "         {\n" +
            "            \"source\" : \"Dokumentlager\",\n" +
            "            \"revisiondate\" : \"2012-03-06 09:15:00\",\n" +
            "            \"description\" : \" ifrågasatts för patienter med <em>diabetes</em> typ 2 som inte behandlas med insulin.\",\n" +
            "            \"id_hash\" : \"deb311f2c9cd753cde34b7257b3d0907\",\n" +
            "            \"format\" : [\n" +
            "               \"p-facet.format.pdf\"\n" +
            "            ],\n" +
            "            \"url\" : \"http://alfresco.vgregion.se/alfresco/service/vgr/storage/node/content/3603?a=false&guest=true\",\n" +
            "            \"id\" : \"tag:alfresco.vgregion.se,2011-06-30:b2e5d684-00c7-4628-8932-f8c1d0dc7733\",\n" +
            "            \"title\" : \"Egenmätning av blodglukos vid <em>diabetes</em>\",\n" +
            "            \"dc_date_validto_dt\" : \"2014-03-06 00:00:00\",\n" +
            "            \"dc_publisher_forunit\" : [\n" +
            "               \"kiv\"\n" +
            "            ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"source\" : \"Dokumentlager\",\n" +
            "            \"revisiondate\" : \"2012-02-03 09:14:28\",\n" +
            "            \"description\" : \"<em>Diabetes</em> medför en ökad risk för att utveckla fotkomplikationer. Fotkomplikationer omfattar sår\",\n" +
            "            \"id_hash\" : \"0393d3601a42828ed30a241d73d2fef2\",\n" +
            "            \"format\" : [\n" +
            "               \"p-facet.format.pdf\"\n" +
            "            ],\n" +
            "            \"url\" : \"http://alfresco.vgregion.se/alfresco/service/vgr/storage/node/content/3132?a=false&guest=true\",\n" +
            "            \"id\" : \"tag:alfresco.vgregion.se,2011-06-30:d2701db9-312c-4f1f-81c2-655e8c5fa2df\",\n" +
            "            \"title\" : \"Diabetesfoten\",\n" +
            "            \"dc_date_validto_dt\" : \"2014-01-18 00:00:00\",\n" +
            "            \"dc_publisher_forunit\" : [\n" +
            "               \"kiv\"\n" +
            "            ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"source\" : \"1177\",\n" +
            "            \"revisiondate\" : \"2012-06-27 02:00:00\",\n" +
            "            \"description\" : \"Insulin är ett hormon som tillverkas i bukspottkörteln. Det behövs för att socker, glukos, som finns i blodet ska kunna tas upp av kroppens celler. Om man\",\n" +
            "            \"id_hash\" : \"fa28bc9d113a4a288f997cca6388b3d5\",\n" +
            "            \"format\" : [\n" +
            "               \"p-facet.format.webpage\"\n" +
            "            ],\n" +
            "            \"url\" : \"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Rad-om-lakemedel/Lakemedel-vid-typ-2-diabetes-/\",\n" +
            "            \"id\" : \"fa28bc9d113a4a288f997cca6388b3d5\",\n" +
            "            \"title\" : \"Läkemedel vid typ 2-<em>diabetes</em>\"\n" +
            "         },\n" +
            "         {\n" +
            "            \"source\" : \"1177\",\n" +
            "            \"revisiondate\" : \"2012-06-27 02:00:00\",\n" +
            "            \"description\" : \"Det finns flera olika typer av <em>diabetes</em>. Typ 2-<em>diabetes</em> är vanligast och kommer oftast i vuxen\",\n" +
            "            \"id_hash\" : \"833417b0e3a2bc5c5652aac57a47549e\",\n" +
            "            \"format\" : [\n" +
            "               \"p-facet.format.webpage\"\n" +
            "            ],\n" +
            "            \"url\" : \"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Diabetes-typ-2/\",\n" +
            "            \"id\" : \"833417b0e3a2bc5c5652aac57a47549e\",\n" +
            "            \"title\" : \"<em>Diabetes</em> typ 2\"\n" +
            "         },\n" +
            "         {\n" +
            "            \"source\" : \"1177\",\n" +
            "            \"format\" : [\n" +
            "               \"p-facet.format.webpage\"\n" +
            "            ],\n" +
            "            \"url\" : \"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Mer-om/Boktips-till-barn-och-ungdomar/\",\n" +
            "            \"revisiondate\" : \"2012-06-27 02:00:00\",\n" +
            "            \"title\" : \"Boktips till barn och ungdomar\",\n" +
            "            \"id\" : \"f116eff6f3b12aae232e5d9de83bbfa3\",\n" +
            "            \"id_hash\" : \"f116eff6f3b12aae232e5d9de83bbfa3\"\n" +
            "         }\n" +
            "      ],\n" +
            "      \"pagination\" : {\n" +
            "         \"previous\" : null,\n" +
            "         \"next\" : {\n" +
            "            \"query\" : \"hits=10&offset=10&q=diabetes\",\n" +
            "            \"applied\" : false,\n" +
            "            \"displayName\" : \"2\"\n" +
            "         },\n" +
            "         \"firstPage\" : {\n" +
            "            \"query\" : \"hits=10&offset=0&q=diabetes\",\n" +
            "            \"applied\" : false,\n" +
            "            \"displayName\" : \"1\"\n" +
            "         },\n" +
            "         \"hitsReturned\" : 10,\n" +
            "         \"pages\" : [\n" +
            "            {\n" +
            "               \"query\" : \"hits=10&offset=0&q=diabetes\",\n" +
            "               \"applied\" : true,\n" +
            "               \"displayName\" : \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"query\" : \"hits=10&offset=10&q=diabetes\",\n" +
            "               \"applied\" : false,\n" +
            "               \"displayName\" : \"2\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"query\" : \"hits=10&offset=20&q=diabetes\",\n" +
            "               \"applied\" : false,\n" +
            "               \"displayName\" : \"3\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"query\" : \"hits=10&offset=30&q=diabetes\",\n" +
            "               \"applied\" : false,\n" +
            "               \"displayName\" : \"4\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"query\" : \"hits=10&offset=40&q=diabetes\",\n" +
            "               \"applied\" : false,\n" +
            "               \"displayName\" : \"5\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"query\" : \"hits=10&offset=50&q=diabetes\",\n" +
            "               \"applied\" : false,\n" +
            "               \"displayName\" : \"6\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"query\" : \"hits=10&offset=60&q=diabetes\",\n" +
            "               \"applied\" : false,\n" +
            "               \"displayName\" : \"7\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"query\" : \"hits=10&offset=70&q=diabetes\",\n" +
            "               \"applied\" : false,\n" +
            "               \"displayName\" : \"8\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"query\" : \"hits=10&offset=80&q=diabetes\",\n" +
            "               \"applied\" : false,\n" +
            "               \"displayName\" : \"9\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"query\" : \"hits=10&offset=90&q=diabetes\",\n" +
            "               \"applied\" : false,\n" +
            "               \"displayName\" : \"10\"\n" +
            "            }\n" +
            "         ],\n" +
            "         \"offset\" : 0\n" +
            "      },\n" +
            "      \"searchTimes\" : {\n" +
            "         \"searchEngineTimeInMillis\" : 15,\n" +
            "         \"totalTimeInMillis\" : 46\n" +
            "      },\n" +
            "      \"facets\" : {\n" +
            "         \"entries\" : [\n" +
            "            {\n" +
            "               \"selectableItems\" : [\n" +
            "                  {\n" +
            "                     \"count\" : 23,\n" +
            "                     \"query\" : \"hits=10&q=diabetes&scope_facet=regional\",\n" +
            "                     \"selected\" : false,\n" +
            "                     \"displayName\" : \"Regional\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"count\" : 5926,\n" +
            "                     \"query\" : \"hits=10&q=diabetes&scope_facet=nationell\",\n" +
            "                     \"selected\" : false,\n" +
            "                     \"displayName\" : \"Nationell\"\n" +
            "                  }\n" +
            "               ],\n" +
            "               \"appliedItems\" : [],\n" +
            "               \"type\" : \"standardFacet\",\n" +
            "               \"displayName\" : \"scope\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"selectableItems\" : [\n" +
            "                  {\n" +
            "                     \"count\" : 23,\n" +
            "                     \"query\" : \"hits=10&q=diabetes&source_facet=Dokumentlager\",\n" +
            "                     \"selected\" : false,\n" +
            "                     \"displayName\" : \"Regional\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"count\" : 1193,\n" +
            "                     \"query\" : \"hits=10&q=diabetes&source_facet=Socialstyrelsen\",\n" +
            "                     \"selected\" : false,\n" +
            "                     \"displayName\" : \"Socialstyrelsen\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"count\" : 140,\n" +
            "                     \"query\" : \"hits=10&q=diabetes&source_facet=SBU\",\n" +
            "                     \"selected\" : false,\n" +
            "                     \"displayName\" : \"SBU\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"count\" : 277,\n" +
            "                     \"query\" : \"hits=10&q=diabetes&source_facet=1177\",\n" +
            "                     \"selected\" : false,\n" +
            "                     \"displayName\" : \"1177\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"count\" : 3815,\n" +
            "                     \"query\" : \"hits=10&q=diabetes&source_facet=L%C3%A4kemedelsverket\",\n" +
            "                     \"selected\" : false,\n" +
            "                     \"displayName\" : \"Läkemedelsverket\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"count\" : 501,\n" +
            "                     \"query\" : \"hits=10&q=diabetes&source_facet=TLV\",\n" +
            "                     \"selected\" : false,\n" +
            "                     \"displayName\" : \"TLV\"\n" +
            "                  }\n" +
            "               ],\n" +
            "               \"appliedItems\" : [],\n" +
            "               \"type\" : \"standardFacet\",\n" +
            "               \"displayName\" : \"source\"\n" +
            "            }\n" +
            "         ]\n" +
            "      }\n" +
            "   },\n" +
            "   \"numberOfHits\" : 5949\n" +
            "}";
}