package userInterface.finalGUI.panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 */
class StockPanel extends JPanel {
    private final ArrayList<Component> stockData = new ArrayList<>();

    public Component add(Component comp){
        Component component = super.add(comp);
        stockData.add(comp);
        return component;
    }

    Object[][] getStockData(){
        Object[][] objects = new Object[stockData.size()][5];
        for(int i = 0; i < stockData.size(); i++){
            Component component = stockData.get(i);
            if(component instanceof StockEntryPanel) {
                StockEntryPanel stockEntryPanel = (StockEntryPanel) component;
                objects[i] = stockEntryPanel.getEntries();
            }
        }
        return objects;
    }
}
