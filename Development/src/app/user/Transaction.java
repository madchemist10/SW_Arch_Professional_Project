package app.user;

import app.constants.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * This calls forms a Transaction object for the user
 */
public class Transaction {

    private final Map<String, String> object = new HashMap<>();

    /**
     * Initialzer for the Transaction class that maps the passed in data to Transaction elements
     * @param data String array result returned from previous database call
     */
    public Transaction(String[] data){
        int i = 0;
        object.put(Constants.TRADE_TYPE_LABEL_KEY, data[i++]);
        object.put(Constants.TICKER_LABEL_KEY, data[i++]);
        object.put(Constants.SHARE_QTY_LABEL_KEY, data[i++]);
        object.put(Constants.TRANSACTION_COST_LABEL_KEY, data[i++]);
        object.put(Constants.COMPANY_NAME_LABEL_KEY, data[i++]);
        object.put(Constants.NEW_BALANCE_LABEL_KEY, data[i++]);
        object.put(Constants.TIMESTAMP_LABEL_KEY, data[i]);
    }

    /**
     * Get this transaction's data.
     * @return map of this transaction's data.
     */
    public Map<String,String> getData(){
        return object;
    }
}