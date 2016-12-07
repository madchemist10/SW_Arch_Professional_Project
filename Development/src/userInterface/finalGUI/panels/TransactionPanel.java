package userInterface.finalGUI.panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Implements a panel to hold transaction objects
 */
class TransactionPanel extends JPanel{

    /**Initialize ArrayList of components that act as transaction data*/
    private final ArrayList<Component> transactionData = new ArrayList<>();

    /**
     * Adds a component to the TransactionPanel
     * @param comp the component to be added
     * @param constraints the constraints on the component
     */
    @Override
    public void add(Component comp, Object constraints) {
        super.add(comp, constraints);
        transactionData.add(comp);
    }

        /**
         * Get transaction data from the transaction panel
         * @return object array of transaction data
         */
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
