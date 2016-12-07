package userInterface.finalGUI.panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Specifies how to handle the action associated with the Trade button
 */
class TradeButtonAction extends AbstractAction {

    private final ArrayList<StockEntryPanel> stockEntryList;

    /**
     * Set stockEntryList to the local stockEntryList
     * @param stockEntryList that gets passed along when an action is called
     */
    TradeButtonAction(ArrayList<StockEntryPanel> stockEntryList){
        this.stockEntryList = stockEntryList;
    }

    /**
     * Action performed when the Trade button is pressed
     * @param e event that is thrown from the button click that tells state of button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /*Spawn background thread to keep from locking up the GUI.*/
        Thread tradeButtonThread = new Thread(() -> {
            String line = e.getActionCommand();
            int row = Integer.parseInt(line);
            stockEntryList.get(row).tradeCallBack();
        });
        tradeButtonThread.start();
    }

}
