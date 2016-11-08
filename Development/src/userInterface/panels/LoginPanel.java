package userInterface.panels;


/**
 * This panel is required to be displayed first so that
 * the user can login to the application.
 */
class LoginPanel extends BasePanel {

    /**
     * Create a new {@link LoginPanel}.
     */
    public LoginPanel(){
        super();
    }

    /**
     * Callback for validating user login form email and password.
     * @param email for the user's unique identifier.
     * @param password of the user's account.
     * @return true if login is valid, false otherwise.
     */
    private static boolean validateLogin(String email, String password){
        return app.validateLogin(email, password);
    }
}
