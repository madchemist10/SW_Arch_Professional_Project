package userInterface.panels;

/**
 * This is the list of all events that can occur
 * from the gui panels. Each of these need to be handled
 * from the {@link PanelManager}.
 */
enum AppChangeEvents {

    /**Thrown when the user successfully logs in.*/
    LOGIN_SUCCESS,

    /**Thrown when the user has failed to log in.*/
    LOGIN_FAIL,

    /**Thrown when the user decides to create a new account.*/
    CREATE_ACCOUNT,

    /**Thrown when the user has create a new account.*/
    ACCOUNT_CREATED

}
