# Introduktion #

Denna sida beskriver indexering och processering av information för Vårdaktörsportal-sökets källor. Nuvarande källor:
  * Regionala medicinska riktlinjer
  * 1177.se
  * Läkemedelsverket
  * SBU
  * Socialstyrelsen
  * TLV

# Dokumentprocessering och indexering #
Dokument samlas in, bearbetas, och matas till Solr genom ramverket Open Pipeline. Inom Open Pipeline konfigureras ett antal Pipelines, som i sig består av en insamlingsagent och en serie processeringssteg. Solr är indexeringsmotorn där alla processerade dokument lagras. Open Pipeline och Solr är utvecklade som java webbapplikationer och körs på samma Tomcat på Indexeringsservern.

**Adress till Open Pipeline:** http://vgas0488.vgregion.se:8180/docproc/ <br>
<b>Namn på webbapplikation:</b> docproc<br>
<br>
<b>Adress till Solr:</b> <a href='http://vgas0488.vgregion.se:8080/solr/VAP-index'>http://vgas0488.vgregion.se:8080/solr/VAP-index</a><br>
<b>Namn på webbapplikation:</b> solr<br>
<br>
<h2>Definitioner</h2>
<b>Collection</b> – Innehållsmässigt avdelad mängd innehåll, motsvarar i allmänhet en pipeline. <i>Exempel: SBU, Läkemedelsverket</i><br>
<b>Insamlingsagent, konnektor</b> – Program som extraherar dokument från ett källsystem.<br>
<b>Pipeline</b> – Insamllingsagent och processeringssteg.<br>
<b>Processeringssteg</b> – Funktionalitet som bearbetar ett dokument.<br>

<h1>Insamlingsagenter</h1>
<h2>Web</h2>
Webcrawlning sker med hjälp av två konnektorer: Länkar från rss-flöden samlas in av en rss-konnektor, som uppdaterar crawlerns databas med nya länkar som hittats, och eventuellt med information om dokumenten  har uppdaterats sedan de senast crawlades. Matning och bearbetning hanteras av crawlern (web-konnektor), som konsumerar information i crawlerdatabasen.<br>
<br>
<h2>Solr</h2>
Dokument som processerats av Open Pipeline lagras i ett index hos Solr. Varje dokument består av ett antal fält som innhåller dokumentets information. Exempel på dessa är brödtext (body), titel, författare, datum och källa. För hög tillgänlighet är Solr uppsatt med replikering. Replikering sker var 20:e minut. Det innebär att indexet för indexering replikeras (kopieras) till det index som sökningar görs mot. Detta möjligör att man kan köra sök-indexet på en eller flera separata serverar i produktion.<br>
<br>
<h2>Index-schema</h2>
Index-schemat specificerar vilka fält som finns och hur de används. Schemat definieras i en konfigurationsfil hos Solr som heter schema.xml. Vårdaktörsportalen kör just nu exakt samma schema som hitta för enklare underhåll, det gör att det kan finnas fält i schemat som inte används.<br>
<br>
Ett fält definieras genom dess namn, datatyp och om det är indexerat och/eller lagrat. Ett indexerat fält är sökbart i Solr, medan ett lagrat fält kan visas i användargränssnittet. Ett indexerat fält som inte är lagrat används för filtreringsmöjligheter, exempelvis datum. Ett lagrat fält som inte är indexerat är exempelvis filstorlek som man inte vill söka på men ändå kunna visa i gränssnittet.<br>
<br>
Nedan följer en specifikation av index-schemat. Kolumnen ”Highlight” visar i vilka fält som sökord blir markerade i sökresultatet.