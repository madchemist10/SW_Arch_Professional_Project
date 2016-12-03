package app.user;

import java.util.ArrayList;
import java.util.List;


/**
 */
public class Portfolio {
    /*List of transaction objects stored in the user's portfolio*/
    private final List<Transaction> transactions;

    /*List of Stock objects stored in the user's portfolio*/
    private final List<Stock> stocks;

    /**
     * Initializer for the Portfolio class, populated by Transaction and Stock objects
     */
    public Portfolio(){
        transactions = new ArrayList<>();
        stocks = new ArrayList<>();
    }
    /**
     * Get a list of the transactions from the
     * use that is associated with this
     * {@link Portfolio}.
     * @return List of {@link Transaction}.
     */
    public List<Transaction> getTransactions(){
        return transactions;
    }

    /**
     * Set a this user's transactions
     * @param userTransactions Array of Strings returned by previous DB call
     */
    public void setTransactions(ArrayList<String[]> userTransactions){
        for (String[] item: userTransactions){
            transactions.add(new Transaction(item));
        }
    }

    /**
     * Get a list of the stocks from the
     * use that is associated with this
     * {@link Portfolio}.
     * @return List of {@link Stock}.
     */
    public List<Stock> getStocks(){
        return stocks;
    }

    /**
     * Set a this user's stocks
     * @param userStocks Array of Strings returned by previous DB call
     */
    public void setStocks(ArrayList<String[]> userStocks){
        for (String[] item : userStocks){
            stocks.add(new Stock(item));
        }
    }
}
