package app.exception;

/**
 * Custom exception for Tradier return being 500 status code.
 */
public class Tradier500Exception extends TradierException {

    /**
     * Message for {@link Tradier500Exception}.
     * {@inheritDoc}
     */
    @Override
    public String getMessage(){
        return super.getMessage()+"Internal Server Error.";
    }
}
