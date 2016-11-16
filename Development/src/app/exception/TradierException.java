package app.exception;

/**
 * Custom exception for Tradier return a status code.
 */
public class TradierException extends BaseException {
    /**Code given to this exception from {@link app.utilities.apiHandlers.TradierAPIHandler}.*/
    private final int statusCode;
    /**Default error message.*/
    private final static String MESSAGE = "Tradier API Error: ";

    /**
     * Create a new {@link TradierException} with the default
     * status code of -1.
     */
    TradierException(){
        statusCode = -1;
    }

    /**
     * Create a new {@link TradierException} with
     * a given status code.
     * @param statusCode of the exception from the HTTP request.
     */
    public TradierException(int statusCode){
        this.statusCode = statusCode;
    }

    /**
     * Message for {@link TradierException}.
     * {@inheritDoc}
     */
    @Override
    public String getMessage(){
        if(statusCode == -1){
            return MESSAGE;
        }
        return MESSAGE+statusCode;
    }
}
