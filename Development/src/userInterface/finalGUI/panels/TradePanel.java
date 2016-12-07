package userInterface.finalGUI.panels;

import app.constants.Constants;
import app.exception.BaseException;
import app.exception.InsufficientFundsException;
import app.exception.StockNotOwnedException;
import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * This panel represents the user wanting to make a new trade.
 * Buying and Selling are both valid options from this panel.
 */
public class TradePanel extends BasePanel {

    /**Buy button for issuing buy command on this trade.*/
    private final JButton buyButton = new JButton(TradeNetGUIConstants.BUY_BUTTON_TEXT);
    /**Sell button for issuing sell command on this trade.*/
    private final JButton sellButton = new JButton(TradeNetGUIConstants.SELL_BUTTON_TEXT);
    /**Text field for user to input number of shares to trade.*/
    private final JTextField sharesTextField = new JTextField(20);
    /**Reference to the current research stock panel. This is used to present
     * the user the current researched stock while in this trade panel.*/
    private final TradierResultsPanel tradierStockDataPanel;

    /**
     * Create a new {@link TradePanel}.
     */
    TradePanel(TradierResultsPanel tradierStockData){
        super(TradeNetGUIConstants.TRADE_PANEL_IDENTIFIER);
        tradierStockDataPanel = tradierStockData;
        buildPanel();
    }

    /**
     * Adds a component to the TradePanel
     * @param component the component to be added
     */
    private void addSelfComponent(Component component){
        super.add(component, constraints);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        /*Add tradier data panel*/
        addTradierPanel();

        constraints.gridy++;
        /*Add shares text field*/
        addSharesTextField();

        constraints.gridy++;
        addButtons();
    }

    /**
     * Callback for executing a buy action.
     * Close current window when button is pressed.
     */
    private void buyCallBack(){
        trade(Constants.BUY);
        closeWindow(buyButton);
    }

    /**
     * Callback for executing a sell action.
     * Close current window when button is pressed.
     */
    private void sellCallBack(){
        trade(Constants.SELL);
        closeWindow(sellButton);
    }

    /**
     * Implements buying and/or selling a stock
     * @param type specifies whether it is buy/sell
     */
    private void trade(String type){
        String ticker = tradierStockDataPanel.getTickerSymbol();
        String currentVal = tradierStockDataPanel.getCurrentVal();
        String shareQty = sharesTextField.getText();
        String companyName = tradierStockDataPanel.getCompanyName();
        Map<String, String> tradeData = new HashMap<>();
        tradeData.put(Constants.TICKER_LABEL_KEY,ticker);
        tradeData.put(Constants.CURRENT_VALUE_LABEL_KEY,currentVal);
        tradeData.put(Constants.SHARE_QTY_LABEL_KEY,shareQty);
        tradeData.put(Constants.COMPANY_NAME_LABEL_KEY,companyName);
        tradeData.put(Constants.TRADE_TYPE_LABEL_KEY,type);
        try {
            app.trading(tradeData);
        } catch (BaseException e) {
            if(e instanceof InsufficientFundsException) {
                notifyListeners(new CustomChangeEvent(this, AppChangeEvents.INSUFFICIENT_FUNDS));
            }
            else if(e instanceof StockNotOwnedException) {
                notifyListeners(new CustomChangeEvent(this, AppChangeEvents.STOCK_NOT_OWNED));
            }
        }
    }

    /**
     * Close the window where a given button is enclosed within.
     * @param button that exists in the window to be closed.
     */
    private void closeWindow(JButton button){
        Window window = SwingUtilities.getWindowAncestor(button);
        if(window != null){
            window.setVisible(false);
        }
    }

    /**
     * Adds a stock entry to the stock panel
     * @param stockEntryPanel The entry to be added
     */
    void addStockEntryPanel(StockEntryPanel stockEntryPanel){
        constraints.gridy++;
        stockEntryPanel.removeTradeButton();
        addSelfComponent(stockEntryPanel);
    }

    /**
     * Add Buy button to this panel.
     */
    private void addBuyButton(){
        buyButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread buyButtonThread = new Thread(()-> buyCallBack());
                buyButtonThread.start();
            }
        });
    }

    /**
     * Add Sell button to this panel.
     */
    private void addSellButton(){
        sellButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread sellButtonThread = new Thread(()-> sellCallBack());
                sellButtonThread.start();
            }
        });
    }

    /**
     * Add shares text field to this panel.
     */
    private void addSharesTextField(){
        //create inner panel to hold the label and text field.
        JPanel innerSharesPanel = new JPanel();

        //assign the layout
        innerSharesPanel.setLayout(new GridBagLayout());
        GridBagConstraints sharesConstraints = new GridBagConstraints();

        //create the shares label
        JLabel sharesLabel = new JLabel(TradeNetGUIConstants.SHARES_LABEL);

        //create the flow panels for placement in the inner panel
        BasicFlowPanel sharesFlowPanel = new BasicFlowPanel(sharesLabel);
        BasicFlowPanel sharesTextFlowPanel = new BasicFlowPanel(sharesTextField);

        //place the panels in the inner shares panel
        sharesConstraints.gridx = 0;
        sharesConstraints.gridy = 0;
        innerSharesPanel.add(sharesFlowPanel, sharesConstraints);
        sharesConstraints.gridx = 1;
        innerSharesPanel.add(sharesTextFlowPanel, sharesConstraints);

        //place the inner shares panel in this Trade Panel
        addSelfComponent(innerSharesPanel);
    }

    /**
     * Adds buttons to the trade panel
     */
    private void addButtons(){
        JPanel innerButtonPanel = new JPanel();
        innerButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        addBuyButton();
        innerButtonPanel.add(buyButton, gbc);
        gbc.gridx++;
        addSellButton();
        innerButtonPanel.add(sellButton, gbc);
        addSelfComponent(innerButtonPanel);
    }

    /**
     * Add tradier panel of data to the trade panel.
     */
    private void addTradierPanel(){
        if(tradierStockDataPanel == null){
            return;
        }
        addSelfComponent(tradierStockDataPanel);
    }
}
