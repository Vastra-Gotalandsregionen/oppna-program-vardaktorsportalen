package se.vgregion.portal.vap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.vgregion.portal.vap.domain.jpa.Flag;
import se.vgregion.portal.vap.domain.jpa.FlagPk;
import se.vgregion.portal.vap.service.repository.FlagRepository;

import java.util.Collection;
import java.util.Map;

/**
 * Implementation of {@link FlagService}.
 *
 * @author Patrik Bergström
 */
@Service
public class FlagServiceImpl implements FlagService {

    private FlagRepository flagRepository;

    /**
     * Constructor.
     *
     * @param flagRepository the {@link FlagRepository}
     */
    @Autowired
    public FlagServiceImpl(FlagRepository flagRepository) {
        this.flagRepository = flagRepository;
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
            flagRepository.persist(new Flag(flagPk));
        } else {
            flagRepository.remove(flagPk);
        }
    }

    @Override
    public Map<String, Flag> findUserFlags(Long userId) {
        return flagRepository.findUserFlags(userId);
    }
}
