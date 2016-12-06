package app.user;

import app.constants.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * This class forms a Stock object for the user
 */
public class Stock {

    private final Map<String, String> object = new HashMap<>();

    /**
     * Initialzer for the Stock class that maps the passed in data to Stock elements
     * @param data String array result returned from previous database call
     */
    public Stock(String[] data){
        int i = 2;
        object.put(Constants.STOCK_NAME_LABEL_KEY, data[i++]);
        object.put(Constants.STOCKS_OWNED_LABEL_KEY, data[i++]);
        object.put(Constants.PURCHASED_VALUE_LABEL_KEY, data[i++]);
        object.put(Constants.COMPANY_NAME_LABEL_KEY, data[i]);
    }

    /**
     * Get this stock's data.
     * @return map of this stocks data.
     */
    public Map<String,String> getData(){

        return object;
    }

    /**
     * Updates a the profit/loss for this stock
     * @param profitLoss the value to change this stock's profit/loss to
     */
    public void updateStockPL(String profitLoss){
        object.put(Constants.PROFIT_LOSS_LABEL_KEY, profitLoss);
    }

    /**
     * Updates the current price for this stock
     * @param currPrice the value to change this stock's current price to
     */
    public void updateCurrPrice(String currPrice){
        object.put(Constants.CURRENT_VALUE_LABEL_KEY, currPrice);
    }
}
