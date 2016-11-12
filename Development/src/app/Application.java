package app;

import app.constants.Constants;
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

    /**Map of settings read in from the user's settings file.*/
    private final static HashMap<String, String> settings = new HashMap<>();

    /**
     * Default constructor to only allow a new
     * instance to be instantiated.
     */
    private Application(){
        loadSettings();
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
        return true;
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
