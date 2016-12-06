package userInterface.finalGUI.panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 */
class StockPanel extends JPanel {
    private final ArrayList<Component> stockData = new ArrayList<>();

    @Override
    public void add(Component comp, Object constraints) {
        super.add(comp, constraints);
        stockData.add(comp);
    }

    Object[][] getStockData(){
        Object[][] objects = new Object[stockData.size()][6];
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
