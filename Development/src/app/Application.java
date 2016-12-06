package app;

import app.exception.BaseException;
import app.constants.Constants;
import app.database.DBConstants;
import app.database.DatabaseManager;
import app.exception.NotEnoughStockException;
import app.exception.StockNotOwnedException;
import app.exception.InsufficientFundsException;
import app.user.Portfolio;
import app.user.Stock;
import app.user.Transaction;
import app.user.User;
import app.utilities.Utilities;
import app.utilities.apiHandlers.APIHandler;
import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
import com.fasterxml.jackson.databind.JsonNode;

import java.text.SimpleDateFormat;

import java.util.*;

/**
 * Represents the portal for the user interface module to
 * have access to the application's functionality.
 */
public class Application {

    /**Singleton instance of the application.*/
    private static Application instance = null;

    /**Singleton instance of the database manager.*/
    private static DatabaseManager dbManager = null;

    /**Map of settings read in from the user's settings file.*/
    private final static HashMap<String, String> settings = new HashMap<>();

    /**Current User that is logged in.*/
    private static User currentUser = null;


    /**Simple Date Format for use when inserting transactions.*/
    private static SimpleDateFormat format = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT_STRING);

    /**
     * Default constructor to only allow a new
     * instance to be instantiated.
     */
    private Application(){
        dbManager = DatabaseManager.getInstance(Constants.DB_FILE);
        loadSettings();
        createDB();
    }

    /**
     * Load the settings from the user file.
     * If file does not exist, it is possible we get null.
     * Only assign value for settings map if the return is not null.
     */
    private void loadSettings(){
        HashMap<String, String> tempSettings = Utilities.loadSettingsFile(Constants.SETTINGS_FILE);
        if(tempSettings == null){
            return;
        }
        /*Add all settings pulled from user file.*/
        settings.putAll(tempSettings);
    }

    /**
     * Create the database with correct tables if the database
     * does not already exist.
     */
    private void createDB(){
        if(Utilities.fileExists(Constants.DB_FILE)){
            return;
        }
        dbManager.executeCreateStatement(DBConstants.DB_MAKE_CUSTOMER_BALANCE);
        dbManager.executeCreateStatement(DBConstants.DB_MAKE_CUSTOMER_CREDENTIALS);
        dbManager.executeCreateStatement(DBConstants.DB_MAKE_TRANSACTION_HISTORY);
        dbManager.executeCreateStatement(DBConstants.DB_MAKE_STOCK_OWNERSHIP);
    }

    /**
     * Retrieve a value for the settings stored in this file.
     * @param key of which entry to pull from the map.
     * @return value from the settings map.
     */
    public String getValueFromSettings(String key){
        return settings.get(key);
    }

    /**
     * Retrieve the instance of this Application,
     * if it does not exist, create it.
     * @return instance of {@link Application}
     */
    public static Application getInstance(){
        if(instance == null){
            instance = new Application();
        }
        return instance;
    }

    /**
     * Allow the user interface to create a new account.
     * Callback for creating a new user from email and password.
     * @param email for the new user's unique identifier.
     * @param password of the new user's account.
     * @return true if create account was success, false otherwise.
     */
    public boolean createAccount(String email, String password){

        /*
         * Use Mailbox API to make sure that the inserted email is valid
         * before allowing account to be created
         */

        IAPIHandler mailboxAPI = getAPIHandler(APIHandles.MAILBOX_LAYER);
        String request = mailboxAPI.buildAPIRequest(new String[]{email});
        if(request == null){
            return false;
        }
        Object returnVal;
        try {
            returnVal = mailboxAPI.executeAPIRequest(request);
        } catch (BaseException e) {
            return false;
        }
        if(returnVal == null){
            return false;
        }
        if(returnVal instanceof JsonNode) {
            JsonNode returnNode = (JsonNode) returnVal;
            JsonNode formatNode = returnNode.get(Constants.FORMAT_VALID);
            if(formatNode.booleanValue()){
                String credentials = "\""+email+"\",\""+password+"\"";
                dbManager.insertCredentials(credentials);
                String[] userEntry = dbManager.validateLogin(email, password);
                dbManager.insertCustomerBalance(Integer.parseInt(userEntry[0]) + ", 0.00");
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieve the desired API Handler from the given {@link APIHandles}.
     * @param handle for the Handler to retrieve.
     * @return {@link IAPIHandler} for the given {@link APIHandler}.
     */
    public IAPIHandler getAPIHandler(APIHandles handle){
        return APIHandler.getInstance().getAPIHandler(handle);
    }

    /**
     * Add cash to the current logged in user.
     * @param cash to be added to the user's account.
     */
    public void addCashToUser(String cash){
        Map<String, String> user = currentUser.getUserData();
        double new_balance = Double.parseDouble(user.get(Constants.ACCOUNT_BALANCE_LABEL_KEY)) + Double.parseDouble(cash);
        int ID = Integer.parseInt(user.get(Constants.USER_ID_KEY));
        dbManager.updateCustomerBalance(new_balance, ID);
        updateUserData(ID);
    }

    /**
     * Allow the user interface to validate a user login.
     * Logs in the user.
     * Before any action can be taken for a given user,
     * this method must return true. Otherwise, null pointer
     * exception are possible.
     * Callback for validating user login from email and password.
     * @param email for the user's unique identifier.
     * @param password of the user's account.
     * @return true if login is valid, false otherwise.
     */
    public boolean loginUser(String email, String password){
        String[] userData = dbManager.validateLogin(email, password);
        if(userData != null){
            currentUser = new User();
            populateUser(Integer.parseInt(userData[0]));
            return true;
        }
        return false;
    }

    private void refreshProfitLoss(List<Stock> userStocks){
        if(userStocks == null){
            return;
        }
        double totalProfitLoss = 0.0;
        for(Stock stock: userStocks){
            String profitLoss = getStockProfitLoss(stock);
            if(profitLoss != null){
                stock.updateStockPL(profitLoss);
                totalProfitLoss += Double.parseDouble(profitLoss);
            }
        }
        String profitLoss = Double.toString(totalProfitLoss);
        currentUser.setUserProfitLoss(profitLoss);
    }

    /**
     * Populates the user from a provided customerID
     * We assume that the list returned from each dbManager call
     * only contains one entry.
     * @param ID ID used for DB calls when getting a customer's data
     */
    private void populateUser(int ID){
        updateUserData(ID);
        ArrayList<String[]> userTransactions = dbManager.getTransactionHistory(ID);
        ArrayList<String[]> userStocks = dbManager.getStockOwnership(ID);
        currentUser.setPortfolio(userTransactions, userStocks);
        refreshProfitLoss(getUserStocks());
    }

    /**
     * Helper method to update a user's data.
     * @param ID user id for db calls.
     */
    private void updateUserData(int ID){
        ArrayList<String[]> userData = dbManager.getCredentials(ID);
        ArrayList<String[]> balance = dbManager.getCustomerBalance(ID);
        currentUser.setUserData(userData.get(0), balance.get(0));
    }

    /**
     * Get list of {@link Transaction} the belongs to the portfolio
     * of the logged in user.
     * User must be logged in prior to this method being
     * executed.
     * @return List of {@link Transaction}.
     */
    public List<Transaction> getUserTransactions(){
        if(currentUser == null){
            //user not logged in
            return null;
        }
        Portfolio portfolio = currentUser.getPortfolio();
        if(portfolio == null){
            //user has no portfolio
            return null;
        }
        return portfolio.getTransactions();
    }

    /**
     * Get list of {@link Stock} the belongs to the portfolio
     * of the logged in user.
     * User must be logged in prior to this method being
     * executed.
     * @return List of {@link Stock}.
     */
    public List<Stock> getUserStocks(){
        if(currentUser == null){
            //user not logged in
            return null;
        }
        Portfolio portfolio = currentUser.getPortfolio();
        if(portfolio == null){
            //user has no portfolio
            return null;
        }
        return portfolio.getStocks();
    }

    /**
     * Get a map of the user's data.
     * @return Map of User Data.
     */
    public Map<String, String> getUserData(){
        if(currentUser == null){
            //user not logged in
            return null;
        }
        return currentUser.getUserData();
    }

    /**
     * Retrieve the stock profit loss from a give stock.
     * @param stock that is to have profit loss calculated.
     * @return String representation of the loss for the given
     *          stock.
     */
    private String getStockProfitLoss(Stock stock){
        Map<String, String> stockData = stock.getData();
        String ticker = stockData.get(Constants.STOCK_NAME_LABEL_KEY);
        String purchasePrice = stockData.get(Constants.PURCHASED_VALUE_LABEL_KEY);
        String qtyPurchased = stockData.get(Constants.STOCKS_OWNED_LABEL_KEY);

        double stockPurchasePrice = Double.parseDouble(purchasePrice);
        int stockQtyPurchased = Integer.parseInt(qtyPurchased);
        String currentPrice = getStockCurrentPrice(ticker);
        if(currentPrice == null){
            return null;
        }
        stock.updateCurrPrice(currentPrice);
        double stockCurrentPrice = Double.parseDouble(currentPrice);
        /*To calculate a stock's profit loss, we are assuming that we want
        * the profit loss to be positive for profit and negative for loss.
        * To calculate this we subtract current value by purchased value and multiple
        * by the number of stocks the user owns.*/
        double stockProfitLoss = (stockQtyPurchased) * (stockCurrentPrice - stockPurchasePrice);
        return Double.toString(stockProfitLoss);
    }

    /**
     * Get the current price for the ticker symbol
     * that is given.
     * @param query ticker symbol to be queried for.
     * @return String representation of the last value for the
     *              stock's ticker symbol.
     */
    private String getStockCurrentPrice(String query){
        JsonNode node = executeTradierQuery(query);
        if(node == null){
            return null;
        }

        JsonNode quotesNode = node.get(Constants.QUOTES);
        if( quotesNode == null) {
            return null;
        }
        JsonNode singleQuotesNode = quotesNode.get(Constants.QUOTE);
        if(singleQuotesNode == null) {
            return null;
        }

        JsonNode last =  singleQuotesNode.get(Constants.LAST);
        if(last == null){
            return null;
        }
        return last.asText();
    }

    /**
     * Execution of the tradier query.
     * @param query that is from the user's input.
     * @return JsonNode of the return value or null if error occurred.
     */
    private JsonNode executeTradierQuery(String query){
        IAPIHandler tradierAPI = getAPIHandler(APIHandles.TRADIER);
        String request = tradierAPI.buildAPIRequest(new String[]{query});
        Object returnVal;

        try {
            returnVal = tradierAPI.executeAPIRequest(request);
        }catch(BaseException e){
            return null;
        }

        if(returnVal == null){
            return null;
        }

        if(returnVal instanceof JsonNode){
            return (JsonNode) returnVal;
        }
        return null;
    }

    /**
     * Called from the GUI panels that initiates a new trade.
     * @param tradeData map of the trading data from the gui form.
     * @throws BaseException when error occurs.
     */
    public void trading(Map<String, String> tradeData) throws BaseException {
        //get all trading data from map
        String ticker = tradeData.get(Constants.TICKER_LABEL_KEY);
        String currentPrice = tradeData.get(Constants.CURRENT_VALUE_LABEL_KEY);
        String shareQtyToPurchase = tradeData.get(Constants.SHARE_QTY_LABEL_KEY);
        String companyName = tradeData.get(Constants.COMPANY_NAME_LABEL_KEY);
        String tradeType = tradeData.get(Constants.TRADE_TYPE_LABEL_KEY);

        //true if buy, false if sell
        boolean buy = tradeType.equals(Constants.BUY);

        //translate trading data to the number representatives
        double tradeCurrentPrice = Double.parseDouble(currentPrice);
        int tradeShareQty = Integer.parseInt(shareQtyToPurchase);
        double transactionCost = tradeCurrentPrice * tradeShareQty;

        //get user's balance
        Map<String, String> userData = currentUser.getUserData();
        String userBalance = userData.get(Constants.ACCOUNT_BALANCE_LABEL_KEY);
        double userBal = Double.parseDouble(userBalance);

        //get user id
        String userID = userData.get(Constants.USER_ID_KEY);

        int id = Integer.parseInt(userID);

        //ensure funds are available for user to purchase stock.
        if(userBal < transactionCost){
            throw new InsufficientFundsException();
        }

        //check if the user owns the stock before selling
        Stock userStockObj = userOwnsStock(id, ticker);
        Map<String, String> stockData = null;
        if(userStockObj != null){
            stockData = userStockObj.getData();
        }
        boolean stockOwned = userStockObj != null;

        double newBalance = userBal;
        //user is either buying or selling the traded stock
        if(buy){
            newBalance -= transactionCost;
        }else{
            if(!stockOwned){
                throw new StockNotOwnedException();
            }
            newBalance += transactionCost;
        }

        //get date of transaction
        Date now = new Date();
        String transactionTime = format.format(now);


        /*After the transaction has been inserted.
        * If the user owns the stock
        * we need to update the user's stock data. Otherwise, we can
        * just insert a new stock ownership for the user.*/
        String stockSQL;
        //user owns stock
        if(stockOwned){
            String currentStockQtyOwned = stockData.get(Constants.STOCKS_OWNED_LABEL_KEY);
            int newShareQty = Integer.parseInt(currentStockQtyOwned);
            if(buy){
                newShareQty += tradeShareQty;
            } else{
                newShareQty -= tradeShareQty;
                //error, not enough stocks to sell.
                if(newShareQty < 0){
                    throw new NotEnoughStockException();
                }
            }
            dbManager.updateStockOwnership(id, newShareQty, ticker, tradeCurrentPrice);
        }
        //user does not own stock
        else{
            stockSQL = userID + ",\"" + ticker + "\"," + shareQtyToPurchase + "," + tradeCurrentPrice + ",\"" + companyName + "\"";
            dbManager.insertStockOwnership(stockSQL);
        }

        //build sql statement to update user data
        //{userID},"{tradeType}","{ticker}",{shareQty},{transactionCost},"{companyName}",{newBal},"{transactionTime}"
        String transactionSQL = userID + ", \"" +
                tradeType + "\",\"" +
                ticker + "\"," +
                shareQtyToPurchase + "," +
                transactionCost + ",\"" +
                companyName + "\"," +
                newBalance + ",\"" +
                transactionTime + "\"";
        dbManager.insertTransaction(transactionSQL);

        dbManager.updateCustomerBalance(newBalance, id);
        populateUser(id);
    }

    /**
     * Determine if the user owns a given stock.
     * @param userID of the user to search
     * @param ticker of the stock to search
     * @return Stock that is from the database data.
     */
    private Stock userOwnsStock(int userID, String ticker){
        ArrayList<String[]> ownedStock = dbManager.getStockFromOwner(userID, ticker);
        if(ownedStock == null || ownedStock.size() == 0){
            return null;
        }
        //assume there is only one entry in table that matches both
        //user id AND stock name
        return new Stock(ownedStock.get(0));
    }
}
