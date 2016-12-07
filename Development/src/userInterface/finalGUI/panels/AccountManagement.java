package userInterface.finalGUI.panels;

import app.user.Stock;
import app.user.Transaction;
import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

/**
 * Account management profile panel that displays a user's
 * account information.
 * |---------------------------------------------------|
 * |                                                   |
 * |               Account Management Data             |
 * |                                                   |
 * |---------------------------------------------------|
 * |                                                   |
 * |               Transaction History                 |
 * |                                                   |
 * |---------------------------------------------------|
 * |                                                   |
 * |                    Stock Data                     |
 * |                                                   |
 * |---------------------------------------------------|
 */
class AccountManagement extends BasePanel implements PropertyChangeListener {

    /**Internal stock information panel. Display a list of
     * stock data from the user's {@link app.user.Portfolio}*/
    private final StockPanel internalStockPanel = new StockPanel();
    /**Constraints for {@link #internalStockPanel} layout.*/
    private final GridBagConstraints internalStockConstraints = new GridBagConstraints();
    /**Internal transaction information panel. Display a list
     * of previous transaction data from the user's {@link app.user.Portfolio}*/
    private final TransactionPanel internalTransactionPanel = new TransactionPanel();
    /**Constraints for {@link #internalTransactionPanel} layout.*/
    private final GridBagConstraints internalTransactionConstraints = new GridBagConstraints();
    /**Internal account data panel.*/
    private final JPanel internalAccountDataPanel = new JPanel();
    /**Constraints for {@link #internalAccountDataPanel} layout.*/
    private final GridBagConstraints internalAccountConstraints = new GridBagConstraints();
    /**User data panel that can be updated.*/
    private final UserDataPanel userDataPanel = new UserDataPanel();

    /**
     * Requires the user to be logged in before this constructor
     * can be successfully called.
     * Create a new {@link AccountManagement} panel.
     */
    AccountManagement(){
        super(TradeNetGUIConstants.ACCOUNT_MANAGEMENT_PANEL_IDENTIFIER);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        buildPanel();
        addStockEntries(app.getUserStocks());
        addTransactionEntries(app.getUserTransactions());
        addUserData(app.getUserData());
    }

    /**
     * Handle any events that may be spawned from
     * sub panels included in this panel.
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
            /*User has decided to trade a stock.*/
            case TRADE_STOCK:
                /*Pass the event up to the next parent panel
                * as we need to execute the trade panel. The event
                * originated from the stock that wants to be traded.
                * So the listener can get all necessary data from the
                * calling stock panel.*/
                notifyListeners(event);
                break;

            /*User has decided to add cash to their account.*/
            case ADD_CASH:
                createAddCashPopup();
                updateUserData();
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        /*Assign default sizes to panels*/
        Dimension panelDim = new Dimension(TradeNetGUIConstants.DEFAULT_ACCOUNT_MANAGE_WIDTH,TradeNetGUIConstants.DEFAULT_ACCOUNT_MANAGE_HEIGHT);
        internalAccountDataPanel.setMinimumSize(panelDim);
        internalTransactionPanel.setMinimumSize(panelDim);
        internalStockPanel.setMinimumSize(panelDim);

        /*Add account management data.*/
        constraints.gridx = 0;
        constraints.gridy = 0;
        addAccountManagementData();

        /*Add internal transaction panel.*/
        addInternalTransactionPanel();

        /*Add internal stock panel.*/
        addInternalStockPanel();
    }

    @Override
    public void addComponent(Component comp) {
        super.add(comp);
    }

    /**
     * Add the data for the account management data.
     */
    private void addAccountManagementData(){
        internalAccountConstraints.gridx = 0;
        internalAccountConstraints.gridy = 0;
        addUserDataPanel();
        internalAccountConstraints.gridy++;
        addAddCashButton();
        addComponent(internalAccountDataPanel);
    }

    /**
     * Add the internal transactions panel in a scroll panel.
     */
    private void addInternalTransactionPanel(){
        internalTransactionPanel.setLayout(new GridBagLayout());
    }

    /**
     * Add the internal stock panel in a scroll panel.
     */
    private void addInternalStockPanel(){
        internalStockPanel.setLayout(new GridBagLayout());
    }

    /**
     * Add list of stocks to this account panel.
     * @param stocks list of stock for the logged in user.
     */
    private void addStockEntries(List<Stock> stocks) {
        if(stocks == null){
            return;
        }
        internalStockConstraints.gridx = 0;
        internalStockConstraints.gridy = 0;
        for(Stock stock: stocks){
            //create a new stock entry panel
            StockEntryPanel stockEntryPanel = new StockEntryPanel();

            //add parent panel as listener for events
            stockEntryPanel.addPropertyListener(this);

            //update the labels from the given stock
            stockEntryPanel.updateStockLabels(stock.getData());

            //add this stock entry panel to the internal stock panel
            internalStockPanel.add(stockEntryPanel, internalStockConstraints);

            //increment the constraints to go to next row
            internalStockConstraints.gridy++;
        }
    }

    /**
     * Add list of transactions to this account panel.
     * @param transactions list of transaction for the logged in user.
     */
    private void addTransactionEntries(List<Transaction> transactions){
        if(transactions == null){
            return;
        }
        internalTransactionConstraints.gridx = 0;
        internalTransactionConstraints.gridy = 0;
        for(Transaction transaction: transactions){
            //create a new transaction entry panel
            TransactionEntryPanel transactionEntryPanel = new TransactionEntryPanel();

            //add parent panel as listener for events
            transactionEntryPanel.addPropertyListener(this);

            //update the labels from the given transaction
            transactionEntryPanel.updateTransactionLabels(transaction.getData());

            //add this transaction entry panel to the internal transaction panel
            internalTransactionPanel.add(transactionEntryPanel, internalTransactionConstraints);

            //increment the constraints to go to next row
            internalTransactionConstraints.gridy++;
        }
    }

    /**
     * Add user data to this panel.
     * @param userData map of user data.
     */
    private void addUserData(Map<String, String> userData){
        if(userData == null){
            //user data is null
            return;
        }
        if(userDataPanel == null){
            //panel not defined
            return;
        }
        userDataPanel.updateLabels(userData);
    }

    /**
     * Helper method to update user data.
     * Called whenever user information would be changed.
     */
    private void updateUserData(){

        addUserData(app.getUserData());
    }

    /**
     * Add the specified amount of cash to the account.
     * @param cashAmount to be added to the user account.
     */
    private void addCashToAccount(String cashAmount){
        app.addCashToUser(cashAmount);
        addTransactionEntries(app.getUserTransactions());
    }

    /**
     * Create add cash popup and wait for user input.
     * If the user entered a value and hit "OK" then add
     * that amount of cash to the user's account.
     */
    private void createAddCashPopup(){
        String userInput = JOptionPane.showInputDialog(this,
                TradeNetGUIConstants.ADD_CASH,
                TradeNetGUIConstants.ADD_CASH,
                JOptionPane.NO_OPTION);

        /*User hit OK*/
        if(userInput != null){
            addCashToAccount(userInput);
        }
    }

    /**
     * Add user data to the internal account data panel.
     */
    private void addUserDataPanel(){
        BasicFlowPanel userFlowPanel = new BasicFlowPanel(userDataPanel);
        internalAccountDataPanel.add(userFlowPanel, internalAccountConstraints);
    }

    /**
     * Add cash button to initiate the addition of
     * new cash to the user's account. Add supporting
     * callback to be executed on separate thread.
     */
    private void addAddCashButton(){
        JButton button = new JButton(TradeNetGUIConstants.ADD_CASH);
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread createAddCash = new Thread(()-> createAddCashPopup());
                createAddCash.start();
            }
        });
        internalAccountDataPanel.add(button,internalAccountConstraints);
    }

    /**
     * Listeners for when stock/transaction data needs to be updated
     */
    void update(){
        notifyListeners(new CustomChangeEvent(internalStockPanel,AppChangeEvents.ADD_STOCK_DATA));
        notifyListeners(new CustomChangeEvent(internalTransactionPanel, AppChangeEvents.ADD_TRANSACTION_DATA));
    }
}
