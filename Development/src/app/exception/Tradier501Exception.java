package app.exception;

/**
 * Custom exception for Tradier return being 501 status code.
 */
public class Tradier501Exception extends TradierException {

    /**
     * Message for {@link Tradier501Exception}.
     * {@inheritDoc}
     */
    @Override
    public String getMessage(){
        return super.getMessage()+"Feature Not Implemented.";
    }
}
