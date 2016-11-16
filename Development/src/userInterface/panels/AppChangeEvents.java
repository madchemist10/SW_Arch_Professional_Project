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

    /**Thrown when the user successfully creates new account.*/
    CREATE_ACCOUNT,

    /**Thrown when the user has create a new account.*/
    ACCOUNT_CREATED,

    /**Thrown when the user's account creation has failed.*/
    ACCOUNT_CREATION_FAILED,

    /**Thrown when the user's password input is invalid.*/
    PASSWORD_INVALID,

    /**Thrown when the user's email input is invalid.*/
    EMAIL_INVALID,

    /**Thrown when the user's access credentials for Twitter API are invalid.*/
    INVALID_TWITTER_API_CREDENTIALS,

    /**Thrown when the user's access credentials for Tradier API are invalid.*/
    INVALID_TRADIER_API_CREDENTIALS,

    /**Thrown when the user's access credentials for the Mailbox API are invalid.*/
    INVALID_MAILBOX_API_CREDENTIALS,

    /**Thrown when the user's access credentials for the News API are invalid.*/
    INVALID_NEWS_API_CREDENTIALS,

    /**Thrown when the Tradier API throws an HTTP error.*/
    TRADIER_HTTP_ERROR,

    /**Thrown when the refresh button is pressed for a given
     * {@link TradierResultsPanel}.*/
    TRADIER_REFRESH,

    /**Thrown when the refresh button is pressed for a given
     * {@link NewsResultsPanel}.*/
    NEWS_REFRESH
}
