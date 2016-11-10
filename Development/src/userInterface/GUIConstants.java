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

    /*Text for buttons*/
    /**Text to be given to the {@link userInterface.panels.LoginPanel#loginButton}.*/
    public static final String LOGIN_BUTTON_TEXT = "Login";
    /**Text to be given to the {@link userInterface.panels.TwitterPanel#queryButton}.*/
    public static final String QUERY_BUTTON_TEXT = "Query";
    /**Text to be given to the {@link userInterface.panels.MailBoxLayerPanel#validateEmailButton}*/
    public static final String VALIDATE_BUTTON_TEXT = "Validate";

    /*Panel Identifiers*/
    /**Name of the {@link userInterface.panels.LoginPanel}.*/
    public static final String LOGIN_PANEL_IDENTIFIER = "Login";
    /**Name of the {@link userInterface.panels.TwitterPanel}.*/
    public static final String TWITTER_PANEL_IDENTIFIER = "Twitter";
    /**Name of the {@link userInterface.panels.MailBoxLayerPanel}*/
    public static final String MAILBOX_PANEL_IDENTIFIER = "Mailbox";
    /**Name of the {@link userInterface.panels.NewsPanel}*/
    public static final String NEWS_PANEL_IDENTIFIER = "News";
}
