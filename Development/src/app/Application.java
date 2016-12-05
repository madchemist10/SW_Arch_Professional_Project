package app;

import app.exception.BaseException;
import app.constants.Constants;
import app.database.DBConstants;
import app.database.DatabaseManager;
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
import com.sun.tools.internal.jxc.ap.Const;
import com.sun.tools.javac.code.Attribute;

import java.io.IOException;
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
        dbManager.updateCustomerBalance(new_balance, Integer.parseInt(user.get(Constants.USER_ID_KEY)));
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
            //spawn a timer to execute the query every second.
            return true;
        }
        return false;
    }

    public void refreshProfitLoss(List<Stock> userStocks){
        String profitLoss = calculateProfitLoss(userStocks);
        currentUser.setUserProfitLoss(profitLoss);
    }
    /**
     * Populates the user from a provided customerID
     * @param ID ID used for DB calls when getting a customer's data
     */
    public void populateUser(int ID){
        currentUser = null;
        currentUser = new User();
        dbManager.insertStockOwnership("1, \"AAPL\", 30, 50, \"Apple\"");
        dbManager.insertStockOwnership("1, \"MSFT\", 20, 40, \"Microsoft\"");
        ArrayList<String[]> userData = dbManager.getCredentials(ID);
        ArrayList<String[]> balance = dbManager.getCustomerBalance(ID);
        ArrayList<String[]> userTransactions = dbManager.getTransactionHistory(ID);
        ArrayList<String[]> userStocks = dbManager.getStockOwnership(ID);
        currentUser.setPortfolio(userTransactions, userStocks);
        currentUser.setUserData(userData.get(0), balance.get(0));
        refreshProfitLoss(getUserStocks());
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
     * Function used to calculate the user's total profit/loss across all owned stocks
     * @param userStocks used to iterate through and determine stock prices for profitLoss calculation
     * @return String of profitLoss variable on success, null on failure
     */
    public String calculateProfitLoss(List<Stock> userStocks){
        double profitLoss = 0.0;
        for (Stock stock : userStocks) {
            String ticker = stock.getData().get(Constants.STOCK_NAME_LABEL_KEY);
            String last = getStockPrice(ticker);
            if (last != null){
                double currPrice = Double.parseDouble(stock.getData().get(Constants.STOCKS_OWNED_LABEL_KEY)) * Double.parseDouble(last);
                double origPrice = Double.parseDouble(stock.getData().get(Constants.STOCKS_OWNED_LABEL_KEY)) * Double.parseDouble(stock.getData().get(Constants.PURCHASED_VALUE_LABEL_KEY));
                profitLoss += currPrice - origPrice;
            }
            else{
                return null;
            }
        }
        return Double.toString(profitLoss);
    }

    /**
     *
     */
    private String getStockPrice(String ticker){
        IAPIHandler tradierAPI = getAPIHandler(APIHandles.TRADIER);
        String request = tradierAPI.buildAPIRequest(new String[]{ticker});
        if(request == null){
            return null;
        }
        Object returnVal;
        try {
            returnVal = tradierAPI.executeAPIRequest(request);
            System.out.println(returnVal);
        } catch (BaseException e) {
            return null;
        }
        if (returnVal == null){
            return null;
        }
        if (returnVal instanceof JsonNode){
            JsonNode result = (JsonNode) returnVal;
            String last = result.get(Constants.QUOTES).get("quote").get(Constants.LAST).toString();
            return last;
        }
        return null;
    }

    public Stock stockOwned(String ticker){
        List<Stock> userStocks = currentUser.getPortfolio().getStocks();
        for (Stock thisStock: userStocks){
            String thisTicker = thisStock.getData().get(Constants.STOCK_NAME_LABEL_KEY);
            if (thisTicker.equals(ticker)) {
                return thisStock;
            }
        }
        String[] dummyValues = new String[6];
        dummyValues[3] = "-1";
        Stock dummyStock = new Stock(dummyValues);
        return dummyStock;
    }

    /**
     * Buy or sell a stock given a map {trade('BUY' or 'SELL'), ticker, current value, quantity, company}
     */
    public void trade(Map<String, String> newStock) throws InsufficientFundsException {
        /* check money
         * insert transaction
         * check (insert or update) stock ownership
         * update customer balance
         * populate user function
         */
        // get stock info
        String newTicker = newStock.get(Constants.TICKER_LABEL_KEY);
        double newPrice = Double.parseDouble(newStock.get(Constants.CURRENT_VALUE_LABEL_KEY));
        int newShares = Integer.parseInt(newStock.get(Constants.SHARE_QTY_LABEL_KEY));
        String newCompany = newStock.get(Constants.COMPANY_NAME_LABEL_KEY);
        String tradeType = newStock.get(Constants.TRADE_TYPE_LABEL_KEY);
        boolean buying = tradeType.equals("BUY");
        double newTransPrice = newPrice * newShares;

        // get user balance and check if user has that amount of money if buying
        Map<String, String> user = currentUser.getUserData();
        double currentBalance = Double.parseDouble(user.get(Constants.ACCOUNT_BALANCE_LABEL_KEY));
        double newBalance = currentBalance + newTransPrice;
        if (buying) {
            newBalance = currentBalance - newTransPrice;
            if (newBalance < 0){
                throw new InsufficientFundsException();
            }
        }

        Date now = new Date();
        String datetimeStr = now.toString();
        String transTime = format.format(now);
        int userID = Integer.parseInt(user.get(Constants.USER_ID_KEY));
        String transString = userID + ", " +
                "\"" + tradeType + "\", " +
                "\"" + newTicker + "\", " +
                newShares + ", " +
                newTransPrice + ", " +
                "\"" + newCompany + "\", " +
                newBalance + ", "+
                "\"" + transTime + "\"";

        dbManager.insertTransaction(transString);

        // check if user already owns these stocks
        Stock checkedStock = stockOwned(newTicker);
        int oldShares = Integer.parseInt(checkedStock.getData().get(Constants.STOCKS_OWNED_LABEL_KEY));
        if (oldShares != -1){
            // owns or owned the stock or selling
            int newQuantity = oldShares - newShares;
            double oldPrice = Double.parseDouble(checkedStock.getData().get(Constants.PURCHASED_VALUE_LABEL_KEY));
            double newCost = oldPrice;
            if (buying) {
                newQuantity = oldShares + newShares;
                newCost = ((oldShares * oldPrice) + (newShares * newPrice)) / newQuantity;
            }
            dbManager.updateStockOwnership(userID, newQuantity, newTicker, newCost);
        } else {
            // never owned the stock but buying
            String stockString = userID + ", " +
                    "\"" + newTicker + "\", " +
                    newShares + ", " +
                    newPrice + ", " +
                    "\"" + newCompany + "\"";
            dbManager.insertStockOwnership(stockString);
        }
        dbManager.updateCustomerBalance(newBalance, userID);
        populateUser(userID);
    }
}
