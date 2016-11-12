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
        switch(eventName){
            case LOGIN_SUCCESS:
                System.out.println("Login Success");
                executeOnSwing(new AddPanelRunnable(this));
                break;
            case LOGIN_FAIL:
                System.out.println("Login Fail");
                break;
            case CREATE_ACCOUNT:
                System.out.println("Need to Create New Account");
                executeOnSwing(new AddCreateAccountPanelRunnable(this));
                break;
            case ACCOUNT_CREATED:
                System.out.println("New Account Created");
                executeOnSwing(new AddLoginPanelRunnable(this));
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
}
