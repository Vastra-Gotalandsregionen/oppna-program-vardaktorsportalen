package se.vgregion.portal.vap.service;

/**
 * @author Patrik Bergström
 */
public class DocumentSearchServiceException extends Throwable {

    public DocumentSearchServiceException(String message) {
        super(message);
    }

    public DocumentSearchServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
