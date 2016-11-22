package userInterface.finalGUI.panels;

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
    private final JTextField sharesTextField = new JTextField();

    /**
     * Create a new {@link TradePanel}.
     */
    TradePanel(){
        super(TradeNetGUIConstants.TRADE_PANEL_IDENTIFIER);
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
        constraints.fill = GridBagConstraints.HORIZONTAL;
        addSharesTextField();

        /*Add buy button*/
        constraints.gridy++;
        addBuyButton();

        /*Add sell button*/
        constraints.gridx = 1;
        addSellButton();
    }

    /**
     * Callback for executing a buy action.
     */
    private void buyCallBack(){

    }

    /**
     * Callback for executing a sell action.
     */
    private void sellCallBack(){

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
        addComponent(buyButton);
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
        addComponent(sellButton);
    }

    /**
     * Add shares text field to this panel.
     */
    private void addSharesTextField(){
        JPanel innerSharesPanel = new JPanel();
        innerSharesPanel.setLayout(new GridBagLayout());

        JLabel sharesLabel = new JLabel("Shares");
        BasicFlowPanel sharesFlowPanel = new BasicFlowPanel(sharesLabel);

        constraints.gridwidth = 2;
        addComponent(sharesTextField);
        constraints.gridwidth = 1;
    }
}
