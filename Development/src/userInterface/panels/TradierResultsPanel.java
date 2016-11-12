package userInterface.panels;

import userInterface.GUIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * This frame is a popup for when the user has decided to search
 * for a specific ticker symbol. Display the results from the
 * ticker symbol call.
 */
class TradierResultsPanel extends JFrame{

    /**Panel that will contain the results as they are generated.*/
    private final JPanel resultsPanel = new JPanel();
    /**Constraints for label placement within the results panel.*/
    private final GridBagConstraints constraints = new GridBagConstraints();

    /**
     * Create a new Twitter Results panel with a given query.
     * @param query to be added to the title to denote which query's results
     *              are displayed in the panel.
     */
    TradierResultsPanel(String query){
        setTitle(GUIConstants.TRADIER_RESULTS_PANEL_TITLE+": "+query);
        setSize(new Dimension(GUIConstants.DEFAULT_GUI_WIDTH, GUIConstants.DEFAULT_GUI_HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        buildFrame();
    }

    /**
     * Construct the frame and scrollPane wrapper.
     * Update the scroll speeds.
     * Set default values for adding entries to the panel.
     */
    private void buildFrame(){
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        /*Change the speed of the scrolling so that it is more usable to scroll.*/
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        resultsPanel.setLayout(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(scrollPane);
    }

    /**
     */
    void addEntryToResults(){
    }
}
