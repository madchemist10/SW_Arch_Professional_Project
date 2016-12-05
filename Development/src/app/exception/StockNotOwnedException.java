package app.exception;

/**
 * Custom exception for Application Buy method when
 * the user does not own the stock they are attempting to sell.
 */

public class StockNotOwnedException extends BaseException{

    @Override
    public String getMessage() {
        return "Stock not Owned.";
    }
}
