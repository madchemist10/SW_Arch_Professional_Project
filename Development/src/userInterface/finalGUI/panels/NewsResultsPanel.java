package userInterface.finalGUI.panels;

import app.constants.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * This frame is a popup for when the user has decided to search
 * ticker symbol call.
 */
class NewsResultsPanel extends JFrame{

    /**Maximum number of titles to display.*/
    private static final int MAX_TITLES = 10;
    /**Panel that will contain the results as they are generated.*/
    private final JPanel resultsPanel = new JPanel();
    /**Constraints for label placement within the results panel.*/
    private final GridBagConstraints constraints = new GridBagConstraints();
    /**List of labels used to display news titles..*/
    private final List<JLabel> labels = new LinkedList<>();

    /**
     * Create a new News Results panel.
     */
    NewsResultsPanel(){
        setTitle(TradeNetGUIConstants.NEWS_PANEL_IDENTIFIER);
        setSize(new Dimension(TradeNetGUIConstants.DEFAULT_GUI_WIDTH, TradeNetGUIConstants.DEFAULT_GUI_HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        buildFrame();
    }

    /**
     * Construct the frame.
     * Set default values for adding entries to the panel.
     */
    private void buildFrame(){
        resultsPanel.setLayout(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new BasicFlowPanel(resultsPanel));
        initializePanel();
    }

    /**
     * Set up the default frame of the results panel and add
     * all the supporting labels.
     */
    private void initializePanel(){
        /*Add News Titles*/
        addNewsTitles();
    }

    /**
     * Add news titles to the panel.
     */
    private void addNewsTitles(){
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        for(int i = 0; i < MAX_TITLES; i++){
            JLabel label = new JLabel();
            labels.add(label);
            resultsPanel.add(label, constraints);
            constraints.gridy++;
        }
        constraints.gridwidth = 1;
    }

    /**
     * Add Json node to the panel structure.
     * This method can be used to refresh data as well.
     */
    void updateResultsPanel(JsonNode node){
        if(node == null){
            return;
        }
        JsonNode results = node.get("results").get(0).get("results");
        if(results == null){
            return;
        }
        for(int i = 0; i < MAX_TITLES; i++){
            JsonNode result = results.get(i);
            if(result == null){
                continue;
            }
            JsonNode titleNode = result.get(Constants.TITLE);
            if(titleNode == null){
                continue;
            }
            String title = titleNode.get(Constants.TITLE).asText();
            JLabel label = labels.get(i);
            label.setText(title);
        }
    }
}
