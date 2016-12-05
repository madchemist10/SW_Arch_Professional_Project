package app.exception;

/**
 * Custom exception for Application sell method when
 * the user does not own enough stock they are attempting to sell.
 */

public class NotEnoughStockException extends BaseException{

    @Override
    public String getMessage() {
        return "Not enough stock owned.";
    }
}
