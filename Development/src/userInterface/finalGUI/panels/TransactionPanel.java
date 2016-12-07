package userInterface.finalGUI.panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 */
class TransactionPanel extends JPanel {
    private final ArrayList<Component> transactionData = new ArrayList<>();

    @Override
    public void add(Component comp, Object constraints) {
        super.add(comp, constraints);
        transactionData.add(comp);
    }

    Object[][] getTransactionData(){
        Object[][] objects = new Object[transactionData.size()][6];
        for(int i = 0; i < transactionData.size(); i++){
            Component component = transactionData.get(i);
            if(component instanceof TransactionEntryPanel) {
                TransactionEntryPanel transactionEntryPanel = (TransactionEntryPanel) component;
                objects[i] = transactionEntryPanel.getEntries();
            }
        }
        return objects;
    }
}
