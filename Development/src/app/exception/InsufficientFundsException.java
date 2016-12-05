package app.exception;

/**
 * Custom exception for Application Buy method when
 * the user does not have enough money to purchase new stocks.
 */

public class InsufficientFundsException extends BaseException{

    @Override
    public String getMessage() {
        return "Insufficient Funds";
    }
}
