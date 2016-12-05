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

    /**Thrown when the user has create a new account.*/
    ACCOUNT_CREATED,

    /**Thrown when the user's account creation has failed.*/
    ACCOUNT_CREATION_FAILED,

    /**Thrown when the user's password input is invalid.*/
    PASSWORD_INVALID,

    /**Thrown when the user's email input is invalid.*/
    EMAIL_INVALID,

    /**Thrown when the user has decided to trade more stock.*/
    TRADE_STOCK,

    /**Thrown when the user has decided to add cash to their account.*/
    ADD_CASH,

    /**Thrown when the refresh button is pressed for a given
     * {@link TradierResultsPanel}.*/
    TRADIER_REFRESH,

    /**Thrown when the Tradier API throws an HTTP error.*/
    TRADIER_HTTP_ERROR,

    /**Thrown when the user's access credentials for Tradier API are invalid.*/
    INVALID_TRADIER_API_CREDENTIALS,

    /**Thrown when the user's access credentials for Twitter API are invalid*/
    INVALID_TWITTER_API_CREDENTIALS,

    /**Thrown when the user's access credentials for News API are invalid*/
    INVALID_NEWS_API_CREDENTIALS

    }
