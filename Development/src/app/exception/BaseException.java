package app.exception;

/**
 * Internal Exception.
 */
public abstract class BaseException extends Exception {

    /**
     * Defined get unique message for any BaseException.
     * Must be overrode for the sub class.
     * @return String representation of a message for the exception.
     */
    public abstract String getMessage();
}
