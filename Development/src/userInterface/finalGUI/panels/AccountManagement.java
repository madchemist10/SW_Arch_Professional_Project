package userInterface.finalGUI.panels;

import app.user.Stock;
import app.user.Transaction;
import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

    /**
     * Create a new {@link AccountManagement} panel.
     */
    AccountManagement(){
        super(TradeNetGUIConstants.ACCOUNT_MANAGEMENT_PANEL_IDENTIFIER);
        buildPanel();
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
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        addAccountManagementData();

        constraints.gridy++;
        addInternalTransactionPanel();

        constraints.gridy++;
        addInternalStockPanel();
    }

    /**
     * Add the data for the account management data.
     */
    private void addAccountManagementData(){

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
    void addStockEntries(List<Stock> stocks) {
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
    void addTransactionEntries(List<Transaction> transactions){
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
}
