package userInterface.finalGUI.panels;

import jdk.nashorn.internal.scripts.JO;
import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
    private final BasePanel tradierStockDataPanel;

    /**
     * Create a new {@link TradePanel}.
     */
    TradePanel(BasePanel tradierStockData){
        super(TradeNetGUIConstants.TRADE_PANEL_IDENTIFIER);
        tradierStockDataPanel = tradierStockData;
        buildPanel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        /*Add shares text field*/
        constraints.gridx = 0;
        constraints.gridy = 0;
        addSharesTextField();

        constraints.fill = GridBagConstraints.HORIZONTAL;

        /*Add buy button*/
        constraints.gridy++;
        addBuyButton();

        /*Add sell button*/
        constraints.gridx = 1;
        addSellButton();
    }

    /**
     * Callback for executing a buy action.
     * Close current window when button is pressed.
     */
    private void buyCallBack(){
        closeWindow(buyButton);
    }

    /**
     * Callback for executing a sell action.
     * Close current window when button is pressed.
     */
    private void sellCallBack(){
        closeWindow(sellButton);
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
        addComponent(new BasicFlowPanel(buyButton));
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
        addComponent(new BasicFlowPanel(sellButton));
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
        constraints.gridwidth = 2;
        addComponent(innerSharesPanel);
        constraints.gridwidth = 1;
    }
}
