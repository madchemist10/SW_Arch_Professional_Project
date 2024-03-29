package userInterface.finalGUI.panels;
import app.utilities.*;

import app.constants.Constants;
import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


/**
 * Controller for generating the views for the panels as well as
 * handling any display of errors generated from the sub panels.
 */
public class GUIController extends JFrame implements PropertyChangeListener{

    /**Tabbed pane that is responsible for displaying the correct tabs to the user.*/
    private final JTabbedPane tabbedPane = new JTabbedPane();
    /**Reference to the login panel.*/
    private final LoginPanel loginPanel = new LoginPanel();
    /**Reference to the research panel.*/
    private final Research research = new Research();

    private AccountManagement accountManagement;
    private final JPanel stockDataPanel = new JPanel();
    private final JPanel transactionDataPanel = new JPanel();

    /**
     * Generate a new Controller.
     * Should only be called one from the {@link userInterface.finalGUI.TradeNetGUI}.
     */
    public GUIController(){
        super();
        setTitle(TradeNetGUIConstants.TITLE);
        setSize(TradeNetGUIConstants.DEFAULT_GUI_WIDTH, TradeNetGUIConstants.DEFAULT_GUI_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buildFrame();
        research.addPropertyListener(this);
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
     * Handle each event given to this {@link GUIController},
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
                createMessagePopup(message, TradeNetGUIConstants.INVALID_EMAIL_TITLE);
                break;

            /*Password invalid event is thrown.*/
            case PASSWORD_INVALID:
                message = "Password entered is invalid.";
                createMessagePopup(message, TradeNetGUIConstants.INVALID_PASSWORD_TITLE);
                break;

            /*User has successfully logged in.*/
            case LOGIN_SUCCESS:
                executeOnSwing(new AddPanelRunnable(this));
                break;

            /*User has failed to login.*/
            case LOGIN_FAIL:
                message = "Username or password incorrect.";
                createMessagePopup(message, TradeNetGUIConstants.LOGIN_FAILED_TITLE);
                break;

            /*User has successfully created a new account.*/
            case ACCOUNT_CREATED:
                executeOnSwing(new AddLoginPanelRunnable(this));
                break;

            /*User has failed to create a new account.*/
            case ACCOUNT_CREATION_FAILED:
                message = "Account could not be created.";
                createMessagePopup(message, TradeNetGUIConstants.ACCOUNT_CREATED_FAILED_TITLE);
                break;

            /*User has decided to trade stock.*/
            case TRADE_STOCK:
                //allow the query to be passed in as the new value for the event
                Object query = event.getNewValue();
                String search = null;
                if(query != null && query instanceof String){
                    search = (String) query;
                }
                TradierResultsPanel tradierStockData = research.getTradierStockData(search);
                TradePanel panel = new TradePanel(tradierStockData);
                panel.addPropertyListener(this);
                Object source = event.getSource();
                if(source instanceof StockEntryPanel){
                    panel.addStockEntryPanel((StockEntryPanel) source);
                }
                createDecisionPopup(panel);
                accountManagement.update();
                break;

            /*Add User Stock data to tab*/
            case ADD_STOCK_DATA:
                StockPanel stockPanel = (StockPanel) event.getSource();
                Object[][] stockSubPanelArray = stockPanel.getStockData();
                ArrayList<StockEntryPanel> stockEntryList = stockPanel.getStockEntryPanels();
                addStockDataToTable(TradeNetGUIConstants.USER_STOCK_PANEL_IDENTIFIER,stockSubPanelArray, Constants.STOCK_COLUMNS, stockEntryList);
                break;

            /*Add User Transaction data to tab*/
            case ADD_TRANSACTION_DATA:
                TransactionPanel transactionPanel = (TransactionPanel) event.getSource();
                Object[][] transactionSubPanelArray = transactionPanel.getTransactionData();
                addTransactionDataToTable(TradeNetGUIConstants.USER_TRANSACTIONS_PANEL_IDENTIFIER,transactionSubPanelArray, Constants.TRANSACTION_COLUMNS);
                break;

            case INSUFFICIENT_FUNDS:
                message = "Funds for transaction are insufficient.";
                createMessagePopup(message, "Insufficient Funds.");
                break;

            case STOCK_NOT_OWNED:
                message = "You do not own this stock.";
                createMessagePopup(message, "Stock not Owned.");
                break;

            case NOT_ENOUGH_STOCK:
                message = "You do not own enough stock to sell.";
                createMessagePopup(message, "Not Enough Stock.");
                break;
        }
    }

    /**
     * Adds transactions to a created table
     * @param panelName panel name that specifies what type of data panel it is (stock or transaction)
     * @param data object array of data to populate the table with
     * @param columns column names to exist within the table
     */
    private void addTransactionDataToTable(String panelName, Object[][] data, Object[] columns) {
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(data, columns){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table.setModel(model);
        addTableFromData(panelName, table);
    }

    /**
     * Adds stock data to a created table
     * @param panelName the panel name that specifies what type of data panel it is (stock or transaction)
     * @param data Object array of data to populate the table with
     * @param columns the column names for the created table
     * @param stockEntryList ArrayList list of stocks to exist within the table
     */
    private void addStockDataToTable(String panelName, Object[][] data, Object[] columns, ArrayList<StockEntryPanel> stockEntryList){
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(data, columns){
            @Override
            public boolean isCellEditable(int row, int column){
                return column == 5;
            }
        };
        table.setModel(model);
        Action action = new TradeButtonAction(stockEntryList);
        new ButtonColumn(table, action, 5); //accounts for button callbacks
        addTableFromData(panelName, table);
    }

    /**
     * Populates a scrollPane with a table, then passes panel information into that scrollPane
     * @param panelName the panel to be added (either stock or transaction)
     * @param table the passed in table to populate
     */
    private void addTableFromData(String panelName, JTable table){
        JScrollPane scrollPane = new JScrollPane(table);
        safelyAddPanelToTabbedPane(panelName, scrollPane);
    }

    /**
     * Adds a stock or user panel to appropriate location within a pane
     * @param panelName specifies whether it is a stock or user
     * @param component the component to be added to the pane
     */
    private void safelyAddPanelToTabbedPane(String panelName, Component component){
        switch(panelName){
            case TradeNetGUIConstants.USER_STOCK_PANEL_IDENTIFIER:
                stockDataPanel.removeAll();
                stockDataPanel.add(component);
                stockDataPanel.revalidate();
                stockDataPanel.repaint();
                break;
            case TradeNetGUIConstants.USER_TRANSACTIONS_PANEL_IDENTIFIER:
                transactionDataPanel.removeAll();
                transactionDataPanel.add(component);
                transactionDataPanel.revalidate();
                transactionDataPanel.repaint();
                break;
        }
        tabbedPane.repaint();
    }

    /**
     * Helper method to add all the application
     * panels to this gui controller.
     */
    private void addAppPanels(){
        tabbedPane.remove(loginPanel);

        /*Add account management panel*/
        accountManagement = new AccountManagement();
        accountManagement.addPropertyListener(this);
        accountManagement.update();
        tabbedPane.add(accountManagement, accountManagement.getPanelIdentifier());
        
        /*Add research panel*/
        tabbedPane.add(research, research.getPanelIdentifier());

        tabbedPane.add(stockDataPanel, TradeNetGUIConstants.USER_STOCK_PANEL_IDENTIFIER);
        tabbedPane.add(transactionDataPanel, TradeNetGUIConstants.USER_TRANSACTIONS_PANEL_IDENTIFIER);
    }

    /**
     * Add the Login Panel to the tabbed pane and
     * remove the create account panel.
     */
    private void addLoginPanel(){
        tabbedPane.add(loginPanel, loginPanel.getPanelIdentifier());
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
     * Custom runnable to add new panels, after login.
     */
    private static class AddPanelRunnable implements Runnable{
        private final GUIController manager;

        AddPanelRunnable(GUIController manager){
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
        private final GUIController manager;

        AddLoginPanelRunnable(GUIController manager){
            this.manager = manager;
        }

        @Override
        public void run() {
            manager.addLoginPanel();
        }
    }

    /**
     * Create a popup that displays a message with a given title.
     * @param message that is to be displayed to the user.
     * @param title of the popup panel.
     */
    private void createMessagePopup(String message, String title){
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Create a custom decision popup with a given panel.
     * @param panel that is the complete panel to be enclosed in the popup.
     */
    private void createDecisionPopup(BasePanel panel){
        JOptionPane.showOptionDialog(this, panel, panel.getPanelIdentifier(), JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    }
}
