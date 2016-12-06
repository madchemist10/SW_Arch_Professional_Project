package userInterface.finalGUI.panels;

import app.constants.Constants;
import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import java.awt.*;
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
    private JButton tradeButton = null;

    private final Object[] entries = new Object[6];

    /**
     * Create a new stock entry panel.
     */
    StockEntryPanel(){
        setLayout(new FlowLayout(FlowLayout.LEFT));
        buildTradeButton();
        buildPanel();
    }

    private void buildTradeButton(){
        tradeButton = new JButton(TradeNetGUIConstants.TRADE_BUTTON_TEXT){

            @Override
            public String toString(){
                return tradeButton.getText();
            }
        };
    }

    Object[] getEntries(){
        return entries;
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
        add(stockNameLabel);

        /*Add current value label*/
        add(currentValueLabel);

        /*Add purchased value label*/
        add(purchasedValueLabel);

        /*Add stocks owned label*/
        add(stocksOwnedLabel);

        /*Add profit lost label*/
        add(profitLostLabel);

        /*Add trade button*/
        addTradeButton();
    }

    /**
     * Callback for when the trade button is pressed.
     * This should be run on a new thread.
     * Notify the listener of this entry that the user
     * wishes to trade for this stock.
     */
    void tradeCallBack(){
        notifyListeners(new CustomChangeEvent(this, AppChangeEvents.TRADE_STOCK, getStock()));
    }

    /**
     * Update the stock data labels from a map of stock data.
     * @param stockData map of data associated with this stock's data.
     */
    void updateStockLabels(Map<String,String> stockData){
        /*Assign each entry to the specified labels*/
        String stockName = stockData.get(Constants.STOCK_NAME_LABEL_KEY);
        String currentValue = stockData.get(Constants.CURRENT_VALUE_LABEL_KEY);
        String purchasedValue = stockData.get(Constants.PURCHASED_VALUE_LABEL_KEY);
        String stocksOwned = stockData.get(Constants.STOCKS_OWNED_LABEL_KEY);
        String profitLost = stockData.get(Constants.PROFIT_LOSS_LABEL_KEY);
        if(stockName == null || currentValue == null || purchasedValue == null || stocksOwned == null || profitLost == null){
            return;
        }
        entries[0] = stockName;
        entries[1] = currentValue;
        entries[2] = purchasedValue;
        entries[3] = stocksOwned;
        entries[4] = profitLost;

        stockNameLabel.setText(stockName);
        currentValueLabel.setText(currentValue);
        purchasedValueLabel.setText(purchasedValue);
        stocksOwnedLabel.setText(stocksOwned);
        profitLostLabel.setText(profitLost);
        revalidate();
    }

    /**
     * Add the trade button to this panel.
     */
    private void addTradeButton(){
        tradeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread tradeButtonThread = new Thread(()-> tradeCallBack());
                tradeButtonThread.start();
            }
        });
        entries[5] = tradeButton;
        add(tradeButton);
    }
}
