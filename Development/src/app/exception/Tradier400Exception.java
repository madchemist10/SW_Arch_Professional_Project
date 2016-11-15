package app.exception;

/**
 * Custom exception for Tradier return being 400 status code.
 */
public class Tradier400Exception extends TradierException {

    /**
     * Message for {@link Tradier400Exception}.
     * {@inheritDoc}
     */
    @Override
    public String getMessage(){
        return super.getMessage()+"Bad Request.";
    }
}
