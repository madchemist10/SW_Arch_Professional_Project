package userInterface.finalGUI.panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Create stock panel to hold user's stock information
 */
class StockPanel extends JPanel {
    private final ArrayList<Component> stockData = new ArrayList<>();
    private final ArrayList<StockEntryPanel> stockEntryPanels = new ArrayList<>();

    /**
     * Adds a component to the stock panel
     * @param comp the component to be added
     * @param constraints certain restrictions set on the component
     */
    @Override
    public void add(Component comp, Object constraints) {
        super.add(comp, constraints);
        stockData.add(comp);
    }

    /**
     * Gets stock data of this panel
     * @return an object array of stock objects
     */
    Object[][] getStockData(){
        Object[][] objects = new Object[stockData.size()][6];
        for(int i = 0; i < stockData.size(); i++){
            Component component = stockData.get(i);
            if(component instanceof StockEntryPanel) {
                StockEntryPanel stockEntryPanel = (StockEntryPanel) component;
                stockEntryPanels.add(stockEntryPanel);
                objects[i] = stockEntryPanel.getEntries();
            }
        }
        return objects;
    }

    /**
     * Get a the stock entry panels associated with this panel
     * @return ArrayList of StockEntryPanel objects
     */
    ArrayList<StockEntryPanel> getStockEntryPanels(){
        return stockEntryPanels;
    }
}
