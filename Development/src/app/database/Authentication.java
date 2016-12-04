package app.database;

/**
 * Authentication module for handling that authentication of the
 * user's login.
 */
class Authentication {

    /**Singleton instance of the database manager.*/
    private static Authentication instance = null;

    /**
     * Create a new {@link Authentication}.
     */
    private Authentication(){
        //do nothing
    }

    /**
     * Retrieve the instance of this Authentication Module,
     * if it does not exist, create it.
     * @return instance of {@link Authentication}
     */
    static Authentication getInstance(){
        if(instance == null){
            instance = new Authentication();
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
    boolean validateLogin(String email, String password){ return true; }
}
