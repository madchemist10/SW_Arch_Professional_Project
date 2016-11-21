package app.database;

/**
 * Use an the instance of this Database Manager for accessing
 * the database throughout the system.
 */
public class DatabaseManager {

    /**Singleton instance of the database manager.*/
    private static DatabaseManager instance = null;

    /**Singleton instance of the authentication.*/
    private static Authentication authentication = null;

    /**
     * Create a new {@link DatabaseManager}.
     * Retrieve the instance of the {@link Authentication} module.
     */
    private DatabaseManager(){
        authentication = Authentication.getInstance();
    }

    /**
     * Retrieve the instance of this Database Manager,
     * if it does not exist, create it.
     * @return instance of {@link DatabaseManager}
     */
    public static DatabaseManager getInstance(){
        if(instance == null){
            instance = new DatabaseManager();
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
        return authentication.validateLogin(email, password);
    }

    /**
     * Execute the request for creation of a specific table
     * in the given database we are connected to.
     * @param statement create statement for execution on the connected
     *                  database.
     */
    public void executeCreateStatement(String statement){

    }
}
