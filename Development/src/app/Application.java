package app;

import app.exception.BaseException;
import app.constants.Constants;
import app.database.DBConstants;
import app.database.DatabaseManager;
import app.user.Portfolio;
import app.user.Stock;
import app.user.Transaction;
import app.user.User;
import app.utilities.Utilities;
import app.utilities.apiHandlers.APIHandler;
import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
import com.fasterxml.jackson.databind.JsonNode;

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
            /*System.out.println("CREDENTIALS");
            for (String[] item : dbManager.getCredentials(1)){
                System.out.println(Arrays.toString(item));
            }
            System.out.println("BALANCE");
            for (String[] item : dbManager.getCustomerBalance(1)){
                System.out.println(Arrays.toString(item));
            }
            System.out.println("STOCK OWNERSHIP");
            for (String[] item : dbManager.getStockOwnership(1)){
                System.out.println(Arrays.toString(item));
            }
            System.out.println("TRANSACTION HISTORY");
            for (String[] item : dbManager.getTransactionHistory(1)){
                for (String object : item){
                    System.out.println(object);
                }
            }*/
            System.out.println("Already made, not creating tables");
            return;
        }

        dbManager.executeCreateStatement(DBConstants.DB_MAKE_CUSTOMER_BALANCE);
        dbManager.executeCreateStatement(DBConstants.DB_MAKE_CUSTOMER_CREDENTIALS);
        dbManager.executeCreateStatement(DBConstants.DB_MAKE_TRANSACTION_HISTORY);
        dbManager.executeCreateStatement(DBConstants.DB_MAKE_STOCK_OWNERSHIP);
        /*//dbManager.executeCreateStatement(DBConstants.DB_MAKE_ACCESS_LOGS);
        //dbManager.executeCreateStatement(DBConstants.DB_MAKE_CUSTOMER_INFORMATION);
        //dbManager.executeCreateStatement(DBConstants.DB_MAKE_FUNDS_HISTORY);
        dbManager.insertCredentials("\"hannah@email\", \"password\"");
        dbManager.insertCustomerBalance("1, 2500");
        dbManager.insertStockOwnership("1, \"AAPL\", 30, 50, \"Apple\", \"NASDAQ\"");
        dbManager.insertStockOwnership("1, \"MSFT\", 20, 40, \"Microsoft\", \"NASDAQ\"");
        dbManager.insertTransaction("1, \"BUY\", \"AAPL\", 50, 50, \"Apple\", \"NASDAQ\", \"November 4\", \"19:78\"");
        dbManager.insertTransaction("1, \"SELL\", \"AAPL\", 20, 20, \"Apple\", \"NASDAQ\", \"November 4\", \"20:30\"");
        dbManager.insertTransaction("1, \"BUY\", \"MSFT\", 20, 40, \"Apple\", \"NASDAQ\", \"November 4\", \"19:18\"");*/
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
            /*todo set up the current logged in user
            * by asking the database for the data to generate the
            * new user. Creation of user from the database data
            * should create the supporting portfolio, stock, and
            * transaction data.*/
            currentUser = new User("1");        //change
            ArrayList<String[]> userTransactions = dbManager.getTransactionHistory(1);
            ArrayList<String[]> userStocks = dbManager.getStockOwnership(1);
            return true;
        }
        return false;
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

}
