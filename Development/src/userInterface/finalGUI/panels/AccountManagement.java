package userInterface.finalGUI.panels;

import app.user.Stock;
import app.user.Transaction;
import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

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
    private final JPanel internalStockPanel = new JPanel();
    /**Constraints for {@link #internalStockPanel} layout.*/
    private final GridBagConstraints internalStockConstraints = new GridBagConstraints();
    /**Internal transaction information panel. Display a list
     * of previous transaction data from the user's {@link app.user.Portfolio}*/
    private final JPanel internalTransactionPanel = new JPanel();
    /**Constraints for {@link #internalTransactionPanel} layout.*/
    private final GridBagConstraints internalTransactionConstraints = new GridBagConstraints();
    /**Internal account data panel.*/
    private final JPanel internalAccountDataPanel = new JPanel();
    /**Constraints for {@link #internalAccountDataPanel} layout.*/
    private final GridBagConstraints internalAccountConstraints = new GridBagConstraints();

    /**
     * Create a new {@link AccountManagement} panel.
     */
    AccountManagement(){
        super(TradeNetGUIConstants.ACCOUNT_MANAGEMENT_PANEL_IDENTIFIER);
        buildPanel();
        //todo remove test code
//        List<Stock> stocks = new LinkedList<>();
//        stocks.add(new Stock());
//        addStockEntries(stocks);
        //todo end of test code
        addStockEntries(app.getUserStocks());
        addTransactionEntries(app.getUserTransactions());
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
        constraints.gridy++;
        addInternalTransactionPanel();

        /*Add internal stock panel.*/
        constraints.gridy++;
        addInternalStockPanel();
    }

    /**
     * Add the data for the account management data.
     */
    private void addAccountManagementData(){
        addAddCashButton();
        addComponent(internalAccountDataPanel);
    }

    /**
     * Add the internal transactions panel in a scroll panel.
     */
    private void addInternalTransactionPanel(){
        JScrollPane scrollPane = new JScrollPane(internalTransactionPanel);
        internalTransactionPanel.setLayout(new GridBagLayout());
        addComponent(scrollPane);
    }

    /**
     * Add the internal stock panel in a scroll panel.
     */
    private void addInternalStockPanel(){
        JScrollPane scrollPane = new JScrollPane(internalStockPanel);
        internalStockPanel.setLayout(new GridBagLayout());
        addComponent(scrollPane);
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
     * Add the specified amount of cash to the account.
     * @param cashAmount to be added to the user account.
     */
    private void addCashToAccount(String cashAmount){
        app.addCashToUser(cashAmount);
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
     * Add cash button to initiate the addition of
     * new cash to the user's account. Add supporting
     * callback to be executed on separate thread.
     */
    private void addAddCashButton(){
        internalAccountConstraints.gridx = 0;
        internalAccountConstraints.gridy = 0;
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
}
