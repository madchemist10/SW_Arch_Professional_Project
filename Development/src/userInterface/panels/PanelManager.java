package userInterface.panels;

import userInterface.GUIConstants;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The Panel Manager is the frame that all panels
 * are included in.
 * This handles each of the panels and the swapping between them.
 */
public class PanelManager extends JFrame implements PropertyChangeListener{

    /**Tabbed pane that is responsible for displaying the correct tabs to the user.*/
    private final JTabbedPane tabbedPane = new JTabbedPane();
    /**Reference to the login panel.*/
    private final LoginPanel loginPanel = new LoginPanel();
    /**Reference to the create account panel.*/
    private final CreateNewAccountPanel createNewAccountPanel = new CreateNewAccountPanel();

    /**
     * Allow the creation of new Panel Manager.
     */
    public PanelManager(){
        super();
        setTitle(GUIConstants.TITLE);
        setSize(GUIConstants.DEFAULT_GUI_WIDTH, GUIConstants.DEFAULT_GUI_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buildFrame();
    }

    /**
     * Construct the parent frame that houses all sub class panels.
     */
    private void buildFrame(){
        /*Add the Login Panel.*/
        loginPanel.addPropertyListener(this);
        this.add(tabbedPane);
        addLoginPanel();
    }

    /**
     * Add the Login Panel to the tabbed pane and
     * remove the create account panel.
     */
    private void addLoginPanel(){
        tabbedPane.remove(createNewAccountPanel);
        tabbedPane.add(loginPanel, loginPanel.getPanelIdentifier());
    }

    /**
     * Removes the login panel and
     * add the create account panel.
     */
    private void addCreateAccountPanel(){
        tabbedPane.remove(loginPanel);
        createNewAccountPanel.addPropertyListener(this);
        tabbedPane.add(createNewAccountPanel, createNewAccountPanel.getPanelIdentifier());
    }

    /**
     * Handle each event given to this panel manager,
     * only handle events that are generated by panels,
     * and only events that are of type {@link CustomChangeEvent}.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CustomChangeEvent event = null;
        if(evt instanceof CustomChangeEvent) {
            event = (CustomChangeEvent) evt;
        }
        /*If the event is null, we cannot continue.*/
        if(event == null){
            return;
        }
        AppChangeEvents eventName = event.getEventName();
        String message;
        switch(eventName){
            
            /*Email invalid event is thrown.*/
            case EMAIL_INVALID:
                message = "Email entered is invalid.";
                createPopup(message, GUIConstants.INVALID_EMAIL_TITLE);
                break;

            /*Password invalid event is thrown.*/
            case PASSWORD_INVALID:
                message = "Password entered is invalid.";
                createPopup(message, GUIConstants.INVALID_PASSWORD_TITLE);
                break;

            /*User has successfully logged in.*/
            case LOGIN_SUCCESS:
                executeOnSwing(new AddPanelRunnable(this));
                break;

            /*User has failed to login.*/
            case LOGIN_FAIL:
                message = "Username or password incorrect.";
                createPopup(message, GUIConstants.LOGIN_FAILED_TITLE);
                break;

            /*User has requested to create a new account.*/
            case CREATE_ACCOUNT:
                executeOnSwing(new AddCreateAccountPanelRunnable(this));
                break;

            /*User has successfully created a new account.*/
            case ACCOUNT_CREATED:
                executeOnSwing(new AddLoginPanelRunnable(this));
                break;

            /*User has failed to create a new account.*/
            case ACCOUNT_CREATION_FAILED:
                message = "Username and password combination are already used.";
                createPopup(message, GUIConstants.ACCOUNT_CREATED_FAILED_TITLE);
                break;
        }
    }

    /**
     * Execute a runnable with swing utilities.
     * Used only when the gui would be changed.
     * @param runnable of what should be executed
     *                 on the UI Thread.
     */
    private static void executeOnSwing(Runnable runnable){
        SwingUtilities.invokeLater(runnable);
    }

    /**
     * Helper method to add all the application
     * panels to this panelManager.
     */
    private void addAppPanels(){
        tabbedPane.remove(loginPanel);

        /*Add the twitter panel.*/
        TwitterPanel twitterPanel = new TwitterPanel();
        twitterPanel.addPropertyListener(this);
        tabbedPane.add(twitterPanel, twitterPanel.getPanelIdentifier());

        /*Add the mailbox panel.*/
        MailBoxLayerPanel mailBoxLayerPanel = new MailBoxLayerPanel();
        mailBoxLayerPanel.addPropertyListener(this);
        tabbedPane.add(mailBoxLayerPanel, mailBoxLayerPanel.getPanelIdentifier());

        /*Add the news panel.*/
        NewsPanel newsPanel = new NewsPanel();
        newsPanel.addPropertyListener(this);
        tabbedPane.add(newsPanel, newsPanel.getPanelIdentifier());

        /*Add the tradier panel.*/
        TradierPanel tradierPanel = new TradierPanel();
        tradierPanel.addPropertyListener(this);
        tabbedPane.add(tradierPanel, tradierPanel.getPanelIdentifier());
    }

    /**
     * Custom runnable to add new panels, after login.
     */
    private static class AddPanelRunnable implements Runnable{
        private final PanelManager manager;

        AddPanelRunnable(PanelManager manager){
            this.manager = manager;
        }

        @Override
        public void run() {
            manager.addAppPanels();
        }
    }

    /**
     * Custom runnable to add login panel.
     */
    private static class AddLoginPanelRunnable implements Runnable{
        private final PanelManager manager;

        AddLoginPanelRunnable(PanelManager manager){
            this.manager = manager;
        }

        @Override
        public void run() {
            manager.addLoginPanel();
        }
    }

    /**
     * Custom runnable to add create account panel.
     */
    private static class AddCreateAccountPanelRunnable implements Runnable{
        private final PanelManager manager;

        AddCreateAccountPanelRunnable(PanelManager manager){
            this.manager = manager;
        }

        @Override
        public void run() {
            manager.addCreateAccountPanel();
        }
    }

    /**
     * Create a popup that displays a message with a given title.
     * @param message that is to be displayed to the user.
     * @param title of the popup panel.
     */
    private void createPopup(String message, String title){
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }
}
