package se.vgregion.portal.vap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.vgregion.portal.vap.domain.jpa.Flag;
import se.vgregion.portal.vap.domain.jpa.FlagPk;
import se.vgregion.portal.vap.domain.searchresult.Document;
import se.vgregion.portal.vap.domain.searchresult.SearchResult;
import se.vgregion.portal.vap.service.repository.FlagRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link FlagService}.
 *
 * @author Patrik Bergström
 */
@Service
public class FlagServiceImpl implements FlagService {

	private DocumentSearchService documentSearchService;
    private FlagRepository flagRepository;

    /**
     * Constructor.
     *
     * @param flagRepository the {@link FlagRepository}
     * @param documentSearchService the {@link DocumentSearchService}
     */
    @Autowired
    public FlagServiceImpl(FlagRepository flagRepository, DocumentSearchService documentSearchService) {
        this.flagRepository = flagRepository;
        this.documentSearchService = documentSearchService;
    }

    @Override
    @Transactional
    public void persist(Flag flag) {
        flagRepository.persist(flag);
    }

    @Override
    @Transactional
    public Flag merge(Flag flag) {
        return flagRepository.merge(flag);
    }

    @Override
    public Collection<Flag> findAll() {
        return flagRepository.findAll();
    }

    @Override
    @Transactional
    public void remove(FlagPk id) {
        flagRepository.remove(id);
    }

    @Override
    @Transactional
    public void toggleFlag(Long userId, String documentId) {
        FlagPk flagPk = new FlagPk(userId, documentId);

        Flag flag = flagRepository.find(flagPk);

        if (flag == null) {
        	flag = new Flag(flagPk);
        	
        	flag.setCreateDate(new Date());
        	
            flagRepository.persist(flag);
        } else {
            flagRepository.remove(flagPk);
        }
    }

    @Override
    public List<Flag> findUserFlags(Long userId) {
        return flagRepository.findUserFlags(userId);
    }
    
    @Override
    public List<Document> findUserFlagDocuments(Long userId) throws DocumentSearchServiceException {
        List<Flag> flags = flagRepository.findUserFlags(userId);
        
        List<String> flaggedDocumentIds = findUserFlagDocumentIds(userId);
        
        // Do not perform search if there are no stored documentIds for this user
        if(flaggedDocumentIds.size() == 0) {
        	return new ArrayList<Document>();
        }
        
        SearchResult search = documentSearchService.search(flaggedDocumentIds);
        
        List<Document> documents = search.getComponents().getDocuments();
        
        return documents;
    }
    
    
    @Override
    public List<String> findUserFlagDocumentIds(Long userId) {
        List<Flag> flags = flagRepository.findUserFlags(userId);
        
        ArrayList<String> flagIds = new ArrayList<String>();
        
        for(Flag flag : flags) {
        	flagIds.add(flag.getId().getDocumentId());
        }
        
        return flagIds;
    }
    
    
    @Override
    public Map<String, Flag> findUserFlagsMap(Long userId) {
    	List<Flag> userFlags = flagRepository.findUserFlags(userId);
    	
        Map<String, Flag> flagsMap = new HashMap<String, Flag>();
        for (Flag flag : userFlags) {
        	
        	String documentId = flag.getId().getDocumentId();
        	
        	flagsMap.put(documentId, flag);
        }
    	
        return flagsMap;
    }
}
