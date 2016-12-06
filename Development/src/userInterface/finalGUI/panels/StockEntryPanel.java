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
        addComponent(new BasicFlowPanel(stockNameLabel));

        /*Add current value label*/
        constraints.gridx++;
        addComponent(new BasicFlowPanel(currentValueLabel));

        /*Add purchased value label*/
        constraints.gridx++;
        addComponent(new BasicFlowPanel(purchasedValueLabel));

        /*Add stocks owned label*/
        constraints.gridx++;
        addComponent(new BasicFlowPanel(stocksOwnedLabel));

        /*Add profit lost label*/
        constraints.gridx++;
        addComponent(new BasicFlowPanel(profitLostLabel));

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
                Thread tradeButtonThread = new Thread(()-> tradeCallBack());
                tradeButtonThread.start();
            }
        });
        addComponent(tradeButton);
    }
}
