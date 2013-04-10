package se.vgregion.portal.vap.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.BasicHttpParams;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URLEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Patrik Bergström
 */
public class DocumentSearchServiceImplTest {

    private String statisticsUrl = "http://hitta.vgregion.se/statistics-service/searchevent?hits=%s&user=%s&session=%s&pageurl=%s";

    @Test
    public void testSendStatisticsRequest() throws Exception {

        // Given
        String json = "{\"numberOfHits\":8840,\"components\":{\"documents\":[{\"dc_date_validto_dt\":\"2014-01-25 00:00:00\",\"description\":\"Regional medicinsk riktlinje för omvårdnad vid hematologiska maligniteter exempelvis lymfom, leukemi och myelom.\",\"format\":[\"p-facet.format.pdf\"],\"id\":\"tag:alfresco.vgregion.se,2011-06-30:1451ccd8-e39b-4188-9782-6a0ae1f9d27b\",\"id_hash\":\"5868f39b3ab26a8d4c2ed69cd3fea405\",\"revisiondate\":\"2013-03-04 15:03:00\",\"source\":\"Dokumentlager\",\"title\":\"Omvårdnad vid hemotologiska maligniteter\",\"url\":\"http://alfresco.vgregion.se/alfresco/service/vgr/storage/node/content/3284?a=false&guest=true\"},{\"description\":\"Kikhosta är en barnsjukdom som de flesta barn numera är vaccinerade emot.\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_8d8d2162fc893aa7773dc6e5cf06674d\",\"id_hash\":\"1177_8d8d2162fc893aa7773dc6e5cf06674d\",\"revisiondate\":\"2012-09-18 02:00:00\",\"source\":\"1177\",\"title\":\"Kikhosta\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Kikhosta/\"},{\"description\":\"Luftrörskatarr Sammanfattning Allmänt Luftrörskatarr är en mycket vanlig sjukdom som oftast orsakas av virusinfektioner. Det leder till att slemhinnorna i luftrören svullnar och bildar mer slem än vanligt. För det mesta går sjukdomen över av sig själv inom tre till fyra veckor. Luftrörskatarr kan ibland leda till lunginflammation. Men det är ovanligt. Symtom Luftrörskatarr börjar ofta som en förky\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_b7b62abcbcaf44a00fc9abb36369721b\",\"id_hash\":\"1177_b7b62abcbcaf44a00fc9abb36369721b\",\"revisiondate\":\"2012-09-25 02:00:00\",\"source\":\"1177\",\"title\":\"Luftrörskatarr\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Luftrorskatarr/\"},{\"description\":\"Infektioner hos barn – smittguide Allmänt Det är vanligt att barn får infektioner, speciellt under de första åren i förskolan eller annan barnomsorg. En del sjukdomar smittar innan barnet har fått tydliga symtom. Tiden mellan att barnet blivit smittat fram till dess att sjukdomen bryter ut kallas för inkubationstid. Det är alltid bra att vara extra noga med hygienen när man själv eller barnet har \",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_eff6188393a361afec72b86869998d19\",\"id_hash\":\"1177_eff6188393a361afec72b86869998d19\",\"revisiondate\":\"2011-09-02 02:00:00\",\"source\":\"1177\",\"title\":\"Infektioner hos barn – smittguide\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Infektioner-hos-barn--en-smittguide/\"},{\"description\":\"Addisons sjukdom innebär att man har brist på de livsviktiga hormonerna kortisol och aldosteron. Det beror på att binjurarna inte klarar av att bilda hormo\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_2e51e8b9e65bee19881cbc85800f9ae0\",\"id_hash\":\"1177_2e51e8b9e65bee19881cbc85800f9ae0\",\"revisiondate\":\"2011-12-21 01:00:00\",\"source\":\"1177\",\"title\":\"Addisons sjukdom\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Addisons-sjukdom/\"},{\"description\":\"Vuxna drabbas av förkylningar orsakade av virus ungefär två till tre gånger per år. För barn är motsvarande siffra sex till åtta gånger. I samband med förkylningarna blir slemhinnorna i näsan och b...\",\"format\":[\"p-facet.format.webpage\",\"p-facet.format.webpage\"],\"id\":\"LMV_fd6a48cbb652532d876c3bf3fd7fca49\",\"id_hash\":\"LMV_fd6a48cbb652532d876c3bf3fd7fca49\",\"revisiondate\":\"2007-11-15 01:55:07\",\"source\":\"Läkemedelsverket\",\"title\":\"Behandling av rinosinuit (förkylning, inflammation i näsa och bihålor)\",\"url\":\"http://www.lakemedelsverket.se/malgrupp/Allmanhet/Att-anvanda-lakemedel/Sjukdom-och-behandling/Behandlingsrekommendationer---listan/Behandling-av-rinosinuit-forkylning-inflammation-i-nasa-och-bihalor/\"},{\"description\":\"Om man blir förkyld har man fått en virusinfektion med symtom i de övre luftvägarna.\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_76f2bcb438a552803e880f7a104060f2\",\"id_hash\":\"1177_76f2bcb438a552803e880f7a104060f2\",\"revisiondate\":\"2012-09-25 02:00:00\",\"source\":\"1177\",\"title\":\"Förkylning\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Forkylning/\"},{\"description\":\"Om man blir förkyld har man fått en virusinfektion med symtom i de övre luftvägarna.\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_dd272cc2360daa33abc51f8773803f4a\",\"id_hash\":\"1177_dd272cc2360daa33abc51f8773803f4a\",\"revisiondate\":\"2012-09-25 02:00:00\",\"source\":\"1177\",\"title\":\"Förkylning hos barn\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Forkylning-hos-barn/\"},{\"description\":\"Krupp är en sjukdom som orsakas av virus och som drabbar små barn.\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_3902a65eced29ac60b38c5fdb56379de\",\"id_hash\":\"1177_3902a65eced29ac60b38c5fdb56379de\",\"revisiondate\":\"2012-11-28 01:00:00\",\"source\":\"1177\",\"title\":\"Krupp\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Krupp/\"},{\"description\":\"Den vanligaste orsaken till att barn hostar är förkylning.\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_c0a8f7f32fe18eb3dbd646d12a45ccf3\",\"id_hash\":\"1177_c0a8f7f32fe18eb3dbd646d12a45ccf3\",\"revisiondate\":\"2011-10-17 02:00:00\",\"source\":\"1177\",\"title\":\"Hosta hos barn\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Hosta-hos-barn/\"}],\"facets\":{\"entries\":[{\"type\":\"standardFacet\",\"displayName\":\"source\",\"selectableItems\":[{\"displayName\":\"Regional\",\"selected\":false,\"count\":1,\"query\":\"hits=10&q=sjukdom+f%C3%B6rkylning&source_facet=Dokumentlager\"},{\"displayName\":\"1177\",\"selected\":false,\"count\":82,\"query\":\"hits=10&q=sjukdom+f%C3%B6rkylning&source_facet=1177\"},{\"displayName\":\"Läkemedelsverket\",\"selected\":false,\"count\":332,\"query\":\"hits=10&q=sjukdom+f%C3%B6rkylning&source_facet=L%C3%A4kemedelsverket\"},{\"displayName\":\"SBU\",\"selected\":false,\"count\":5,\"query\":\"hits=10&q=sjukdom+f%C3%B6rkylning&source_facet=SBU\"},{\"displayName\":\"Socialstyrelsen\",\"selected\":false,\"count\":62,\"query\":\"hits=10&q=sjukdom+f%C3%B6rkylning&source_facet=Socialstyrelsen\"},{\"displayName\":\"TLV\",\"selected\":false,\"count\":10,\"query\":\"hits=10&q=sjukdom+f%C3%B6rkylning&source_facet=TLV\"}],\"appliedItems\":[]}]},\"searchTimes\":{\"totalTimeInMillis\":297,\"searchEngineTimeInMillis\":0},\"pagination\":{\"offset\":0,\"hitsReturned\":10,\"firstPage\":{\"displayName\":\"1\",\"applied\":false,\"query\":\"hits=10&offset=0&q=sjukdom+f%C3%B6rkylning\"},\"pages\":[{\"displayName\":\"1\",\"applied\":true,\"query\":\"hits=10&offset=0&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"2\",\"applied\":false,\"query\":\"hits=10&offset=10&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"3\",\"applied\":false,\"query\":\"hits=10&offset=20&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"4\",\"applied\":false,\"query\":\"hits=10&offset=30&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"5\",\"applied\":false,\"query\":\"hits=10&offset=40&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"6\",\"applied\":false,\"query\":\"hits=10&offset=50&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"7\",\"applied\":false,\"query\":\"hits=10&offset=60&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"8\",\"applied\":false,\"query\":\"hits=10&offset=70&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"9\",\"applied\":false,\"query\":\"hits=10&offset=80&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"10\",\"applied\":false,\"query\":\"hits=10&offset=90&q=sjukdom+f%C3%B6rkylning\"}],\"next\":{\"displayName\":\"2\",\"applied\":false,\"query\":\"hits=10&offset=10&q=sjukdom+f%C3%B6rkylning\"},\"previous\":null}}}";

        DocumentSearchServiceImpl service = new DocumentSearchServiceImpl(null);
        ReflectionTestUtils.setField(service, "statisticsUrl", statisticsUrl);
        ArgumentCaptor<HttpGet> httpGetArgumentCaptor = ArgumentCaptor.forClass(HttpGet.class);

        HttpClient httpClient = mock(HttpClient.class);
        when(httpClient.getParams()).thenReturn(new BasicHttpParams());
        HttpResponse httpResponse = mock(HttpResponse.class);
        HttpEntity httpEntity = mock(HttpEntity.class);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);

        service.setHttpClientSearch(httpClient);

        // When
        service.sendStatisticsRequest(URLEncoder.encode("rullgardin", "UTF-8"), json, "7fda2hx05", "uc65723547v1", null);

        Thread.sleep(400);

        // Then
        verify(httpClient).execute(httpGetArgumentCaptor.capture());

        String capturedUrl = httpGetArgumentCaptor.getValue().getURI().toString();
        System.out.println(capturedUrl);

        assertEquals("http://hitta.vgregion.se/statistics-service/searchevent?hits=8840&user=uc65723547v1&session=" +
                "7fda2hx05&pageurl=q%3Drullgardin%26filter%3Dscope%3AVGRegionvardaktorsportalen%26scoped%3Dtrue",
                capturedUrl);
    }

    @Test
    public void testSendStatisticsRequestWithFacet() throws Exception {

        // Given
        String json = "{\"numberOfHits\":8840,\"components\":{\"documents\":[{\"dc_date_validto_dt\":\"2014-01-25 00:00:00\",\"description\":\"Regional medicinsk riktlinje för omvårdnad vid hematologiska maligniteter exempelvis lymfom, leukemi och myelom.\",\"format\":[\"p-facet.format.pdf\"],\"id\":\"tag:alfresco.vgregion.se,2011-06-30:1451ccd8-e39b-4188-9782-6a0ae1f9d27b\",\"id_hash\":\"5868f39b3ab26a8d4c2ed69cd3fea405\",\"revisiondate\":\"2013-03-04 15:03:00\",\"source\":\"Dokumentlager\",\"title\":\"Omvårdnad vid hemotologiska maligniteter\",\"url\":\"http://alfresco.vgregion.se/alfresco/service/vgr/storage/node/content/3284?a=false&guest=true\"},{\"description\":\"Kikhosta är en barnsjukdom som de flesta barn numera är vaccinerade emot.\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_8d8d2162fc893aa7773dc6e5cf06674d\",\"id_hash\":\"1177_8d8d2162fc893aa7773dc6e5cf06674d\",\"revisiondate\":\"2012-09-18 02:00:00\",\"source\":\"1177\",\"title\":\"Kikhosta\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Kikhosta/\"},{\"description\":\"Luftrörskatarr Sammanfattning Allmänt Luftrörskatarr är en mycket vanlig sjukdom som oftast orsakas av virusinfektioner. Det leder till att slemhinnorna i luftrören svullnar och bildar mer slem än vanligt. För det mesta går sjukdomen över av sig själv inom tre till fyra veckor. Luftrörskatarr kan ibland leda till lunginflammation. Men det är ovanligt. Symtom Luftrörskatarr börjar ofta som en förky\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_b7b62abcbcaf44a00fc9abb36369721b\",\"id_hash\":\"1177_b7b62abcbcaf44a00fc9abb36369721b\",\"revisiondate\":\"2012-09-25 02:00:00\",\"source\":\"1177\",\"title\":\"Luftrörskatarr\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Luftrorskatarr/\"},{\"description\":\"Infektioner hos barn – smittguide Allmänt Det är vanligt att barn får infektioner, speciellt under de första åren i förskolan eller annan barnomsorg. En del sjukdomar smittar innan barnet har fått tydliga symtom. Tiden mellan att barnet blivit smittat fram till dess att sjukdomen bryter ut kallas för inkubationstid. Det är alltid bra att vara extra noga med hygienen när man själv eller barnet har \",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_eff6188393a361afec72b86869998d19\",\"id_hash\":\"1177_eff6188393a361afec72b86869998d19\",\"revisiondate\":\"2011-09-02 02:00:00\",\"source\":\"1177\",\"title\":\"Infektioner hos barn – smittguide\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Infektioner-hos-barn--en-smittguide/\"},{\"description\":\"Addisons sjukdom innebär att man har brist på de livsviktiga hormonerna kortisol och aldosteron. Det beror på att binjurarna inte klarar av att bilda hormo\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_2e51e8b9e65bee19881cbc85800f9ae0\",\"id_hash\":\"1177_2e51e8b9e65bee19881cbc85800f9ae0\",\"revisiondate\":\"2011-12-21 01:00:00\",\"source\":\"1177\",\"title\":\"Addisons sjukdom\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Addisons-sjukdom/\"},{\"description\":\"Vuxna drabbas av förkylningar orsakade av virus ungefär två till tre gånger per år. För barn är motsvarande siffra sex till åtta gånger. I samband med förkylningarna blir slemhinnorna i näsan och b...\",\"format\":[\"p-facet.format.webpage\",\"p-facet.format.webpage\"],\"id\":\"LMV_fd6a48cbb652532d876c3bf3fd7fca49\",\"id_hash\":\"LMV_fd6a48cbb652532d876c3bf3fd7fca49\",\"revisiondate\":\"2007-11-15 01:55:07\",\"source\":\"Läkemedelsverket\",\"title\":\"Behandling av rinosinuit (förkylning, inflammation i näsa och bihålor)\",\"url\":\"http://www.lakemedelsverket.se/malgrupp/Allmanhet/Att-anvanda-lakemedel/Sjukdom-och-behandling/Behandlingsrekommendationer---listan/Behandling-av-rinosinuit-forkylning-inflammation-i-nasa-och-bihalor/\"},{\"description\":\"Om man blir förkyld har man fått en virusinfektion med symtom i de övre luftvägarna.\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_76f2bcb438a552803e880f7a104060f2\",\"id_hash\":\"1177_76f2bcb438a552803e880f7a104060f2\",\"revisiondate\":\"2012-09-25 02:00:00\",\"source\":\"1177\",\"title\":\"Förkylning\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Forkylning/\"},{\"description\":\"Om man blir förkyld har man fått en virusinfektion med symtom i de övre luftvägarna.\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_dd272cc2360daa33abc51f8773803f4a\",\"id_hash\":\"1177_dd272cc2360daa33abc51f8773803f4a\",\"revisiondate\":\"2012-09-25 02:00:00\",\"source\":\"1177\",\"title\":\"Förkylning hos barn\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Forkylning-hos-barn/\"},{\"description\":\"Krupp är en sjukdom som orsakas av virus och som drabbar små barn.\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_3902a65eced29ac60b38c5fdb56379de\",\"id_hash\":\"1177_3902a65eced29ac60b38c5fdb56379de\",\"revisiondate\":\"2012-11-28 01:00:00\",\"source\":\"1177\",\"title\":\"Krupp\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Krupp/\"},{\"description\":\"Den vanligaste orsaken till att barn hostar är förkylning.\",\"format\":[\"p-facet.format.webpage\"],\"id\":\"1177_c0a8f7f32fe18eb3dbd646d12a45ccf3\",\"id_hash\":\"1177_c0a8f7f32fe18eb3dbd646d12a45ccf3\",\"revisiondate\":\"2011-10-17 02:00:00\",\"source\":\"1177\",\"title\":\"Hosta hos barn\",\"url\":\"http://www.1177.se/Vastra-Gotaland/Fakta-och-rad/Sjukdomar/Hosta-hos-barn/\"}],\"facets\":{\"entries\":[{\"type\":\"standardFacet\",\"displayName\":\"source\",\"selectableItems\":[{\"displayName\":\"Regional\",\"selected\":false,\"count\":1,\"query\":\"hits=10&q=sjukdom+f%C3%B6rkylning&source_facet=Dokumentlager\"},{\"displayName\":\"1177\",\"selected\":false,\"count\":82,\"query\":\"hits=10&q=sjukdom+f%C3%B6rkylning&source_facet=1177\"},{\"displayName\":\"Läkemedelsverket\",\"selected\":false,\"count\":332,\"query\":\"hits=10&q=sjukdom+f%C3%B6rkylning&source_facet=L%C3%A4kemedelsverket\"},{\"displayName\":\"SBU\",\"selected\":false,\"count\":5,\"query\":\"hits=10&q=sjukdom+f%C3%B6rkylning&source_facet=SBU\"},{\"displayName\":\"Socialstyrelsen\",\"selected\":false,\"count\":62,\"query\":\"hits=10&q=sjukdom+f%C3%B6rkylning&source_facet=Socialstyrelsen\"},{\"displayName\":\"TLV\",\"selected\":false,\"count\":10,\"query\":\"hits=10&q=sjukdom+f%C3%B6rkylning&source_facet=TLV\"}],\"appliedItems\":[]}]},\"searchTimes\":{\"totalTimeInMillis\":297,\"searchEngineTimeInMillis\":0},\"pagination\":{\"offset\":0,\"hitsReturned\":10,\"firstPage\":{\"displayName\":\"1\",\"applied\":false,\"query\":\"hits=10&offset=0&q=sjukdom+f%C3%B6rkylning\"},\"pages\":[{\"displayName\":\"1\",\"applied\":true,\"query\":\"hits=10&offset=0&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"2\",\"applied\":false,\"query\":\"hits=10&offset=10&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"3\",\"applied\":false,\"query\":\"hits=10&offset=20&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"4\",\"applied\":false,\"query\":\"hits=10&offset=30&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"5\",\"applied\":false,\"query\":\"hits=10&offset=40&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"6\",\"applied\":false,\"query\":\"hits=10&offset=50&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"7\",\"applied\":false,\"query\":\"hits=10&offset=60&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"8\",\"applied\":false,\"query\":\"hits=10&offset=70&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"9\",\"applied\":false,\"query\":\"hits=10&offset=80&q=sjukdom+f%C3%B6rkylning\"},{\"displayName\":\"10\",\"applied\":false,\"query\":\"hits=10&offset=90&q=sjukdom+f%C3%B6rkylning\"}],\"next\":{\"displayName\":\"2\",\"applied\":false,\"query\":\"hits=10&offset=10&q=sjukdom+f%C3%B6rkylning\"},\"previous\":null}}}";

        DocumentSearchServiceImpl service = new DocumentSearchServiceImpl(null);
        ReflectionTestUtils.setField(service, "statisticsUrl", statisticsUrl);
        ArgumentCaptor<HttpGet> httpGetArgumentCaptor = ArgumentCaptor.forClass(HttpGet.class);

        HttpClient httpClient = mock(HttpClient.class);
        when(httpClient.getParams()).thenReturn(new BasicHttpParams());
        HttpResponse httpResponse = mock(HttpResponse.class);
        HttpEntity httpEntity = mock(HttpEntity.class);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);

        service.setHttpClientSearch(httpClient);

        // When
        service.sendStatisticsRequest(URLEncoder.encode("rullgardin", "UTF-8"), json, "7fda2hx05", "uc65723547v1",
                "Socialstyrelsen");

        Thread.sleep(400);

        // Then
        verify(httpClient).execute(httpGetArgumentCaptor.capture());

        String capturedUrl = httpGetArgumentCaptor.getValue().getURI().toString();
        System.out.println(capturedUrl);

        assertEquals("http://hitta.vgregion.se/statistics-service/searchevent?hits=8840&user=uc65723547v1&session="
                + "7fda2hx05&pageurl=q%3Drullgardin%26filter%3Dscope%3AVGRegionvardaktorsportalen%26scoped%3Dtrue"
                + "%26facet_source%3DSocialstyrelsen", capturedUrl);
    }
}
