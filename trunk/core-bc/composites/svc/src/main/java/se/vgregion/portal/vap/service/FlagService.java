package se.vgregion.portal.vap.service;

import se.vgregion.portal.vap.domain.jpa.Flag;
import se.vgregion.portal.vap.domain.jpa.FlagPk;

import java.util.Collection;
import java.util.Map;

/**
 * @author Patrik Bergström
 */
public interface FlagService {
    void persist(Flag flag);

    Flag merge(Flag flag);

    Collection<Flag> findAll();

    void remove(FlagPk id);

    void toggleFlag(Long userId, String documentId);

    Map<String,Flag> findUserFlags(Long userId);
}
