package userInterface.finalGUI.panels;

import app.constants.Constants;
import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;

/**
 * Individual stock entry panel.
 * This panel is a representation of an owned stock.
 * Button callback for each stock allows the user to
 * purchase or sell more of a given stock.
 */
class StockEntryPanel extends BasePanel {

    /**Label to display the stock name.*/
    private final JLabel stockNameLabel = new JLabel();
    /**Label to display the current value.*/
    private final JLabel currentValueLabel = new JLabel();
    /**Label to display the purchased value.*/
    private final JLabel purchasedValueLabel = new JLabel();
    /**Label to display the stocks owned.*/
    private final JLabel stocksOwnedLabel = new JLabel();
    /**Label to display the profit lost.*/
    private final JLabel profitLostLabel = new JLabel();
    /**Button to trade more of this stock.*/
    private final JButton tradeButton = new JButton(TradeNetGUIConstants.TRADE_BUTTON_TEXT);

    /**
     * Create a new stock entry panel.
     */
    StockEntryPanel(){
        super();
        buildPanel();
    }

    /**
     * Get the stock name for this panel.
     * @return the name from the {@link #stockNameLabel}.
     */
    String getStock(){
        return stockNameLabel.getText();
    }

    /**
     * Get the current value for this panel.
     * @return the name from the {@link #currentValueLabel}.
     */
    String getCurrentValue(){
        return currentValueLabel.getText();
    }

    /**
     * Get the purchased value for this panel.
     * @return the name from the {@link #purchasedValueLabel}.
     */
    String getPurchasedValue(){
        return purchasedValueLabel.getText();
    }

    /**
     * Get the stock owned for this panel.
     * @return the name from the {@link #stocksOwnedLabel}.
     */
    String getStocksOwned(){
        return stocksOwnedLabel.getText();
    }

    /**
     * Get the profit lost for this panel.
     * @return the name from the {@link #profitLostLabel}.
     */
    String getProfitLost(){
        return profitLostLabel.getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        /*Add stock name label*/
        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(stockNameLabel);

        /*Add current value label*/
        constraints.gridx++;
        addComponent(currentValueLabel);

        /*Add purchased value label*/
        constraints.gridx++;
        addComponent(purchasedValueLabel);

        /*Add stocks owned label*/
        constraints.gridx++;
        addComponent(stocksOwnedLabel);

        /*Add profit lost label*/
        constraints.gridx++;
        addComponent(profitLostLabel);

        /*Add trade button*/
        constraints.gridx++;
        addTradeButton();
    }

    /**
     * Callback for when the trade button is pressed.
     * This should be run on a new thread.
     * Notify the listener of this entry that the user
     * wishes to trade for this stock.
     */
    private void tradeCallBack(){
        notifyListeners(new CustomChangeEvent(this, AppChangeEvents.TRADE_STOCK));
    }

    /**
     * Update the transaction labels from a map of transaction data.
     * @param transaction map of data associated with transaction.
     */
    void updateTransactionLabels(Map<String,String> transaction){
        /*Assign each entry to the specified labels*/
        String stockName = transaction.get(Constants.STOCK_NAME_LABEL_KEY);
        String currentValue = transaction.get(Constants.CURRENT_VALUE_LABEL_KEY);
        String purchasedValue = transaction.get(Constants.PURCHASED_VALUE_LABEL_KEY);
        String stocksOwned = transaction.get(Constants.STOCKS_OWNED_LABEL_KEY);
        String profitLost = transaction.get(Constants.PROFIT_LOST_LABEL_KEY);
        if(stockName == null || currentValue == null || purchasedValue == null || stocksOwned == null || profitLost == null){
            return;
        }
        stockNameLabel.setText(stockName);
        currentValueLabel.setText(currentValue);
        purchasedValueLabel.setText(purchasedValue);
        stocksOwnedLabel.setText(stocksOwned);
        profitLostLabel.setText(profitLost);
    }

    /**
     * Add the trade button to this panel.
     */
    private void addTradeButton(){
        tradeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread loginCallback = new Thread(()-> tradeCallBack());
                loginCallback.start();
            }
        });
        addComponent(tradeButton);
    }
}
