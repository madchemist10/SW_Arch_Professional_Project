package app.exception;

/**
 * Custom exception for Tradier return a status code.
 */
public class TradierException extends BaseException {
    private final int statusCode;
    private final static String MESSAGE = "Tradier API Error: ";

    TradierException(){
        statusCode = -1;
    }

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
