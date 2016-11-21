package userInterface.finalGUI.panels;


/**
 * This is the list of all events that can occur
 * from the gui panels. Each of these need to be handled
 * from the {@link GUIController}.
 */
enum AppChangeEvents {

    /**Thrown when the user successfully logs in.*/
    LOGIN_SUCCESS,

    /**Thrown when the user has failed to log in.*/
    LOGIN_FAIL,

    /**Thrown when the user successfully creates new account.*/
    CREATE_ACCOUNT,

    /**Thrown when the user has create a new account.*/
    ACCOUNT_CREATED,

    /**Thrown when the user's account creation has failed.*/
    ACCOUNT_CREATION_FAILED,

    /**Thrown when the user's password input is invalid.*/
    PASSWORD_INVALID,

    /**Thrown when the user's email input is invalid.*/
    EMAIL_INVALID
}