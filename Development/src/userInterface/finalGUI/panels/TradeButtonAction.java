package userInterface.finalGUI.panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Created by hdanielle on 12/5/16.
 */
public class TradeButtonAction extends AbstractAction {

    private final ArrayList<StockEntryPanel> stockEntryList;

    TradeButtonAction(ArrayList<StockEntryPanel> stockEntryList){
        this.stockEntryList = stockEntryList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*Spawn background thread to keep from locking up the GUI.*/
        Thread tradeButtonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String line = e.getActionCommand();
                int row = Integer.parseInt(line);
                stockEntryList.get(row).tradeCallBack();
            }
        });
        tradeButtonThread.start();
    }

}
