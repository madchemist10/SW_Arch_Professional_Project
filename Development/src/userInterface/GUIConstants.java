package userInterface;

/**
 * Constants that are used throughout gui development.
 */
public class GUIConstants {

    /*Generic GUI Constants*/
    /**Title of the application.*/
    public static final String TITLE = "SW Arch Professional Project";
    /**Default width of the GUI.*/
    public static final int DEFAULT_GUI_WIDTH = 300;
    /**Default height of the GUI.*/
    public static final int DEFAULT_GUI_HEIGHT = 300;

    /*Text for buttons*/
    /**Text to be given to the {@link userInterface.panels.LoginPanel#loginButton}.*/
    public static final String LOGIN_BUTTON_TEXT = "Login";
    /**Text to be given to the {@link userInterface.panels.TwitterPanel#queryButton}.*/
    public static final String QUERY_BUTTON_TEXT = "Query";

    /*Panel Identifiers*/
    /**Name of the {@link userInterface.panels.LoginPanel}.*/
    public static final String LOGIN_PANEL_IDENTIFIER = "Login";
    /**Name of the {@link userInterface.panels.TwitterPanel}.*/
    public static final String TWITTER_PANEL_IDENTIFIER = "Twitter";
}
