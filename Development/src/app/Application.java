package app;

import app.constants.Constants;
import app.database.DBConstants;
import app.database.DatabaseManager;
import app.utilities.Utilities;
import app.utilities.apiHandlers.APIHandler;
import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;

import java.util.HashMap;

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

    /**
     * Default constructor to only allow a new
     * instance to be instantiated.
     */
    private Application(){
        dbManager = DatabaseManager.getInstance();
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
        dbManager.executeCreateStatement(DBConstants.DB_MAKE_ACCESS_LOGS);
        dbManager.executeCreateStatement(DBConstants.DB_MAKE_CUSTOMER_BALANCE);
        dbManager.executeCreateStatement(DBConstants.DB_MAKE_CUSTOMER_CREDENTIALS);
        dbManager.executeCreateStatement(DBConstants.DB_MAKE_CUSTOMER_INFORMATION);
        dbManager.executeCreateStatement(DBConstants.DB_MAKE_FUNDS_HISTORY);
        dbManager.executeCreateStatement(DBConstants.DB_MAKE_TRANSACTION_HISTORY);
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
     * Allow the user interface to validate a user login.
     * Callback for validating user login from email and password.
     * @param email for the user's unique identifier.
     * @param password of the user's account.
     * @return true if login is valid, false otherwise.
     */
    public boolean validateLogin(String email, String password){
        return dbManager.validateLogin(email, password);
    }

    /**
     * Allow the user interface to create a new account.
     * Callback for creating a new user from email and password.
     * @param email for the new user's unique identifier.
     * @param password of the new user's account.
     * @return true if create account was success, false otherwise.
     */
    public boolean createAccount(String email, String password){
        return true;
    }

    /**
     * Retrieve the desired API Handler from the given {@link APIHandles}.
     * @param handle for the Handler to retrieve.
     * @return {@link IAPIHandler} for the given {@link APIHandler}.
     */
    public IAPIHandler getAPIHandler(APIHandles handle){
        return APIHandler.getInstance().getAPIHandler(handle);
    }
}
