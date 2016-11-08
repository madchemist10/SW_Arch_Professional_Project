package app;

/**
 * Represents the portal for the user interface module to
 * have access to the application's functionality.
 */
public class Application {

    /**Singleton instance of the application.*/
    private static Application instance = null;

    /**
     * Default constructor to only allow a new
     * instance to be instantiated.
     */
    private Application(){
        //do nothing
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
     * Callback for validating user login form email and password.
     * @param email for the user's unique identifier.
     * @param password of the user's account.
     * @return true if login is valid, false otherwise.
     */
    public boolean validateLogin(String email, String password){
        return true;
    }
}
