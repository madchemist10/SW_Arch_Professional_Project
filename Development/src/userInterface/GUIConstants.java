package userInterface;

/**
 * Constants that are used throughout gui development.
 */
public class GUIConstants {

    /*Generic GUI Constants*/
    /**Title of the application.*/
    public static final String TITLE = "SW Arch Professional Project";
    /**Default width of the GUI.*/
    public static final int DEFAULT_GUI_WIDTH = 600;
    /**Default height of the GUI.*/
    public static final int DEFAULT_GUI_HEIGHT = 300;
    /**Title of the Twitter results panel that is generated when the user
     * has executed a query from the {@link userInterface.panels.TwitterPanel}*/
    public static final String TWITTER_RESULTS_PANEL_TITLE = "Twitter Results";
    /**Title of the Tradier results panel that is generated when the user
     * has executed a query from the {@link userInterface.panels.TradierPanel}*/
    public static final String TRADIER_RESULTS_PANEL_TITLE = "Tradier Results";
    /**Title of the News results panel that is generated when the user
     * has executed a query from the {@link userInterface.panels.NewsPanel}*/
    public static final String NEWS_RESULTS_PANEL_TITLE = "News Results";

    /*Text for Labels*/
    /**Label for Email labels to tell the user what belongs in a given text field.*/
    public static final String EMAIL_LABEL = "Email:";
    /**Label for Password labels to tell the user what belongs in a given text field.*/
    public static final String PASSWORD_LABEL = "Password:";
    /**Label for Ticker Symbol labels to tell the user what belongs in a given text field.*/
    public static final String TICKER_LABEL = "Ticker Symbol:";
    /**Label for Query labels to tell the user what belongs in a given text field.*/
    public static final String QUERY_LABEL = "Query: ";

    /*Tradier Labels*/
    /**Text to be given to the {@link userInterface.panels.TradierPanel} ticker symbol label.*/
    public static final String TICKER_SYMBOL_LABEL = "Ticker Symbol: ";
    /**Text to be given to the {@link userInterface.panels.TradierPanel} last price label.*/
    public static final String LAST_PRICE_LABEL = "Last Price: ";
    /**Text to be given to the {@link userInterface.panels.TradierPanel} daily net change label.*/
    public static final String DAILY_NET_CHANGE_LABEL = "Daily Net Change: ";
    /**Text to be given to the {@link userInterface.panels.TradierPanel} volume label.*/
    public static final String VOLUME_LABEL = "Volume: ";

    /*Mailbox Labels*/
    /**Text to be given to the {@link userInterface.panels.MailBoxLayerPanel} email valid label.*/
    public static final String EMAIL_VALID = "Email Valid Format";
    /**Text to be given to the {@link userInterface.panels.TradierPanel} email invalid label.*/
    public static final String EMAIL_INVALID = "Email Invalid Format";

    /*Text for buttons*/
    /**Text to be given to the {@link userInterface.panels.LoginPanel#loginButton}.*/
    public static final String LOGIN_BUTTON_TEXT = "Login";
    /**Text to be given to the {@link userInterface.panels.TwitterPanel#queryButton}.*/
    public static final String QUERY_BUTTON_TEXT = "Query";
    /**Text to be given to the {@link userInterface.panels.MailBoxLayerPanel#validateEmailButton}*/
    public static final String VALIDATE_BUTTON_TEXT = "Validate";
    /**Text to be given to the {@link userInterface.panels.CreateNewAccountPanel#createButton}*/
    public static final String CREATE_BUTTON_TEXT = "Create";
    /**Text to be given to the {@link userInterface.panels.LoginPanel#createAccountButton}*/
    public static final String CREATE_ACCOUNT_BUTTON_TEXT = "Create Account";
    /**Text to be given to the {@link userInterface.panels.TradierResultsPanel#refreshButton}*/
    public static final String REFRESH_BUTTON_TEXT = "Refresh";

    /*Panel Identifiers*/
    /**Name of the {@link userInterface.panels.CreateNewAccountPanel}*/
    public static final String CREATE_ACCOUNT_PANEL_IDENTIFIER = "Create Account";
    /**Name of the {@link userInterface.panels.LoginPanel}*/
    public static final String LOGIN_PANEL_IDENTIFIER = "Login";
    /**Name of the {@link userInterface.panels.TwitterPanel}.*/
    public static final String TWITTER_PANEL_IDENTIFIER = "Twitter";
    /**Name of the {@link userInterface.panels.MailBoxLayerPanel}*/
    public static final String MAILBOX_PANEL_IDENTIFIER = "Mailbox";
    /**Name of the {@link userInterface.panels.NewsPanel}*/
    public static final String NEWS_PANEL_IDENTIFIER = "News";
    /**Name of the {@link userInterface.panels.TradierPanel}*/
    public static final String TRADIER_PANEL_IDENTIFIER = "Tradier";

    /*Warning Dialog Titles*/
    /**Title of the Warning Popup for {@link userInterface.panels.AppChangeEvents#EMAIL_INVALID}*/
    public static final String INVALID_EMAIL_TITLE = "Invalid Email";
    /**Title of the Warning Popup for {@link userInterface.panels.AppChangeEvents#PASSWORD_INVALID}*/
    public static final String INVALID_PASSWORD_TITLE = "Invalid Password";
    /**Title of the Warning Popup for {@link userInterface.panels.AppChangeEvents#LOGIN_FAIL}*/
    public static final String LOGIN_FAILED_TITLE = "Failed Login";
    /**Title of the Warning Popup for {@link userInterface.panels.AppChangeEvents#ACCOUNT_CREATION_FAILED}*/
    public static final String ACCOUNT_CREATED_FAILED_TITLE = "Account Creation Failed";
    /**Title of the Warning Popup for {@link userInterface.panels.AppChangeEvents#INVALID_TWITTER_API_CREDENTIALS}*/
    public static final String INVALID_CREDENTIALS_TWITTER_API_TITLE = "Twitter API Credential Mismatch";
    /**Title of the Warning Popup for {@link userInterface.panels.AppChangeEvents#INVALID_TRADIER_API_CREDENTIALS}*/
    public static final String INVALID_CREDENTIALS_TRADIER_API_TITLE = "Tradier API Credential Mismatch";
    /**Title of the Warning Popup for {@link userInterface.panels.AppChangeEvents#INVALID_MAILBOX_API_CREDENTIALS}*/
    public static final String INVALID_CREDENTIALS_MAILBOX_API_TITLE = "Mailbox API Credential Mismatch";
    /**Title of the Warning Popup for {@link userInterface.panels.AppChangeEvents#INVALID_NEWS_API_CREDENTIALS}*/
    public static final String INVALID_CREDENTIALS_NEWS_API_TITLE = "News API Credential Mismatch";
    /**Title of the Warning Popup for {@link userInterface.panels.AppChangeEvents#TRADIER_HTTP_ERROR}*/
    public static final String TRADIER_API_HTTP_ERROR_TITLE = "Tradier API HTTP Error";

    /*Warning Dialog Messages*/
    /**Message of the Warning Popup for {@link userInterface.panels.AppChangeEvents#INVALID_TWITTER_API_CREDENTIALS}*/
    public static final String INVALID_CREDENTIALS_TWITTER_API = "Invalid Credentials for Twitter API found.";
    /**Message of the Warning Popup for {@link userInterface.panels.AppChangeEvents#INVALID_TRADIER_API_CREDENTIALS}*/
    public static final String INVALID_CREDENTIALS_TRADIER_API = "Invalid Credentials for Tradier API found.";
    /**Message of the Warning Popup for {@link userInterface.panels.AppChangeEvents#INVALID_MAILBOX_API_CREDENTIALS}*/
    public static final String INVALID_CREDENTIALS_MAILBOX_API = "Invalid Credentials for Mailbox API found.";
    /**Message of the Warning Popup for {@link userInterface.panels.AppChangeEvents#INVALID_NEWS_API_CREDENTIALS}*/
    public static final String INVALID_CREDENTIALS_NEWS_API = "Invalid Credentials for News API found.";
}
