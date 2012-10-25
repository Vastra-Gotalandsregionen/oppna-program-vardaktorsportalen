package se.vgregion.portal.vap.controller.viewdocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.vgregion.portal.vap.controller.BaseController;
import se.vgregion.portal.vap.domain.searchresult.Document;
import se.vgregion.portal.vap.domain.searchresult.SearchResult;
import se.vgregion.portal.vap.service.DocumentSearchService;
import se.vgregion.portal.vap.service.DocumentSearchServiceException;
import se.vgregion.portal.vap.service.UserEventsService;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author Patrik Bergström
 */
@Controller
@RequestMapping(value = "VIEW")
public class ViewDocumentController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ViewDocumentController.class.getName());
	
    @Autowired
    public ViewDocumentController(DocumentSearchService documentSearchService,
                                  UserEventsService userEventsService) {
        setDocumentSearchService(documentSearchService);
        setUserEventsService(userEventsService);
    }

    @RenderMapping
    public String showViewDocument(RenderRequest request, RenderResponse response) throws DocumentSearchServiceException {
        String documentId = request.getParameter("documentId");

        if (documentId == null) {
            return "view-document";
        }

        logDocumentEvent(request);

        try {
        	SearchResult result = getDocumentSearchService().search(Arrays.asList(documentId));
        	
            List<Document> documents = result.getComponents().getDocuments();

            if (documents.size() < 1) {
                request.setAttribute("errorMessage", "Dokumentet kunde inte hittas.");
            } else if (documents.size() == 1) {
            	
            	Document document = documents.get(0);
            	
            	String documentHtml = document.getExtracted_html();
            	
            	request.setAttribute("document", document);
            	
                request.setAttribute("documentHtml", documentHtml);

            } else {
                throw new DocumentSearchServiceException("There should only be on document with a given id. Id ["
                        + documentId + " has duplicates.");
            }
        	
        } catch(DocumentSearchServiceException e) {
        	LOGGER.info("Something is wrong with the search (DocumentSearchServiceException).");
        	request.setAttribute("errorMessage", "Dokumentet kunde inte hittas.");
        } catch(NullPointerException e) {
        	LOGGER.info("Something is wrong with the search (NullPointerException).");
        	request.setAttribute("errorMessage", "Dokumentet kunde inte hittas.");
        }
        
        return "view-document";
    }

}
