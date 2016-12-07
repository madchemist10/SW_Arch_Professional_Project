package userInterface.finalGUI.panels;

import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;


class TradierResultsFrame extends JFrame {
    /**List of all listeners that are associated with this class.*/
    private final List<PropertyChangeListener> listeners = new LinkedList<>();
    /**Button for trading on the researched stock*/
    private final JButton tradeButton = new JButton(TradeNetGUIConstants.TRADE_BUTTON_TEXT);
    /**Reference to the passed in TradePanel*/
    private TradierResultsPanel tradierResultsPanel;

    TradierResultsFrame(TradierResultsPanel tradierResultsPanel){
        this.tradierResultsPanel = tradierResultsPanel;
        setTitle(TradeNetGUIConstants.TRADIER_PANEL_IDENTIFIER);
        setSize(new Dimension(TradeNetGUIConstants.DEFAULT_ACCOUNT_MANAGE_WIDTH, TradeNetGUIConstants.DEFAULT_ACCOUNT_MANAGE_HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        buildFrame();
    }

    /**
     *
     */
    private void buildFrame(){
        setLayout(new FlowLayout());
        add(new BasicFlowPanel(tradierResultsPanel));
        addTradeButton();
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
        add(tradeButton);
    }

    /**
     *
     */
    private void tradeCallBack(){
        notifyListeners(new CustomChangeEvent(this, AppChangeEvents.TRADE_STOCK, tradierResultsPanel));
    }

    /**
     * Add a property change listener to this panel for when this
     * panel should notify its listeners of property changes.
     * @param listener that is to listen for events from this panel.
     */
    void addPropertyListener(PropertyChangeListener listener){
        listeners.add(listener);
    }

    /**
     * Notify all listeners that an event has been created.
     * @param event that is to alert all listeners of an event.
     */
    private void notifyListeners(CustomChangeEvent event){
        for(PropertyChangeListener listener: listeners){
            listener.propertyChange(event);
        }
    }
}
