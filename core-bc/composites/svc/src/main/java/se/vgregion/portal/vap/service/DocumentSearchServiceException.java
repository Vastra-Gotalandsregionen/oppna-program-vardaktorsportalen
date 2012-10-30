package se.vgregion.portal.vap.service;

/**
 * Exception class that is thrown when exception occurs in {@link DocumentSearchService}.
 *
 * @author Patrik Bergström
 */
public class DocumentSearchServiceException extends Exception {

    /**
     * Constructor.
     *
     * @param message the message
     */
    public DocumentSearchServiceException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message the message
     * @param cause the cause
     */
    public DocumentSearchServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
