package app.exception;

/**
 * Custom exception for Tradier return being 401 status code.
 */
public class Tradier401Exception extends TradierException {

    /**
     * Message for {@link Tradier401Exception}.
     * {@inheritDoc}
     */
    @Override
    public String getMessage(){
        return super.getMessage()+"Unauthorized.";
    }
}
