package app.exception;

/**
 * Custom exception for Tradier return being 404 status code.
 */
public class Tradier404Exception extends TradierException {

    /**
     * Message for {@link Tradier404Exception}.
     * {@inheritDoc}
     */
    @Override
    public String getMessage(){
        return super.getMessage()+"Page not Found.";
    }
}
