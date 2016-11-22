package userInterface.finalGUI;

/**
 * Constants used for {@link TradeNetGUI}.
 */
public class TradeNetGUIConstants {

    /*Generic GUI Constants*/
    /**Title of the application.*/
    public static final String TITLE = "TradeNet";
    /**Default width of the GUI.*/
    public static final int DEFAULT_GUI_WIDTH = 800;
    /**Default height of the GUI.*/
    public static final int DEFAULT_GUI_HEIGHT = 800;
    /**Default width of the internal account management panels.*/
    public static final int DEFAULT_ACCOUNT_MANAGE_WIDTH = 800;
    /**Default height of the internal account management panels.*/
    public static final int DEFAULT_ACCOUNT_MANAGE_HEIGHT = 250;

    /*Text for Labels*/
    /**Label for Email labels to tell the user what belongs in a given text field.*/
    public static final String EMAIL_LABEL = "Email:";
    /**Label for Password labels to tell the user what belongs in a given text field.*/
    public static final String PASSWORD_LABEL = "Password:";

    /*Text for buttons*/
    /**Text to be given to the {@link userInterface.finalGUI.panels.LoginPanel#loginButton}.*/
    public static final String LOGIN_BUTTON_TEXT = "Login";
    /**Text to be given to the {@link userInterface.finalGUI.panels.LoginPanel#createAccountButton}*/
    public static final String CREATE_ACCOUNT_BUTTON_TEXT = "Create Account";
    /**Text to be given to the {@link userInterface.finalGUI.panels.CreateNewAccountPanel#createButton}*/
    public static final String CREATE_BUTTON_TEXT = "Create";
    /**Text to be given to the {@link userInterface.finalGUI.panels.StockEntryPanel#tradeButton}*/
    public static final String TRADE_BUTTON_TEXT = "Trade";
    /**Text to be given to the {@link userInterface.finalGUI.panels.TradePanel}*/
    public static final String BUY_BUTTON_TEXT = "Buy";
    /**Text to be given to the {@link userInterface.finalGUI.panels.TradePanel}*/
    public static final String SELL_BUTTON_TEXT = "Sell";

    /*Panel Identifiers*/
    /**Name of the {@link userInterface.finalGUI.panels.CreateNewAccountPanel}*/
    public static final String CREATE_ACCOUNT_PANEL_IDENTIFIER = "Create Account";
    /**Name of the {@link userInterface.finalGUI.panels.LoginPanel}*/
    public static final String LOGIN_PANEL_IDENTIFIER = "Login";
    /**Name of the {@link userInterface.finalGUI.panels.AccountManagement}*/
    public static final String ACCOUNT_MANAGEMENT_PANEL_IDENTIFIER = "Account Management";
    /**Name of the {@link userInterface.finalGUI.panels.Research}*/
    public static final String RESEARCH_PANEL_IDENTIFIER = "Research";
    /**Name of the {@link userInterface.finalGUI.panels.TradePanel}*/
    public static final String TRADE_PANEL_IDENTIFIER = "Trade";

    /*Warning Dialog Titles*/
    /**Title of the Warning Popup for {@link userInterface.finalGUI.panels.AppChangeEvents#EMAIL_INVALID}*/
    public static final String INVALID_EMAIL_TITLE = "Invalid Email";
    /**Title of the Warning Popup for {@link userInterface.finalGUI.panels.AppChangeEvents#PASSWORD_INVALID}*/
    public static final String INVALID_PASSWORD_TITLE = "Invalid Password";
    /**Title of the Warning Popup for {@link userInterface.finalGUI.panels.AppChangeEvents#LOGIN_FAIL}*/
    public static final String LOGIN_FAILED_TITLE = "Failed Login";
    /**Title of the Warning Popup for {@link userInterface.finalGUI.panels.AppChangeEvents#ACCOUNT_CREATION_FAILED}*/
    public static final String ACCOUNT_CREATED_FAILED_TITLE = "Account Creation Failed";

    /*Popup titles*/
    /**Title of the add cash popup for {@link userInterface.finalGUI.panels.AppChangeEvents#ADD_CASH}*/
    public static final String ADD_CASH = "Add Cash";
}
