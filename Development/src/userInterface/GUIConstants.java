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

    /*Text for Labels*/
    /**Label for Email labels to tell the user what belongs in a given text field.*/
    public static final String EMAIL_LABEL = "Email:";
    /**Label for Password labels to tell the user what belongs in a given text field.*/
    public static final String PASSWORD_LABEL = "Password:";
    /**Label for Ticker Symbol labels to tell the user what belongs in a given text field.*/
    public static final String TICKER_LABEL = "Ticker Symbol:";
    /**Label for Query labels to tell the user what belongs in a given text field.*/
    public static final String QUERY_LABEL = "Query: ";

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

    /*Warning Dialog Messages*/
    /**Message of the Warning Popup for {@link userInterface.panels.AppChangeEvents#INVALID_TWITTER_API_CREDENTIALS}*/
    public static final String INVALID_CREDENTIALS_TWITTER_API = "Invalid Credentials for Twitter API found.";
}
