package userInterface.finalGUI.panels;

import userInterface.finalGUI.TradeNetGUIConstants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


/**
 * Panel to display the researched item.
 */
public class Research extends BasePanel {
    /**Search button for starting the query on the APIs.*/
    private final JButton searchButton = new JButton(TradeNetGUIConstants.SEARCH_BUTTON_TEXT);
    /**Text field for user ticker symbol input.*/
    private final JTextField researchField= new JTextField(20);
    /**Constraints for the inner {@link #researchSubPanel}.*/
    private final GridBagConstraints subPanelConstraints = new GridBagConstraints();
    /**Create a new panel for research bar*/
    private final JPanel researchSubPanel = new JPanel();
    /**Create a new panel for TradierResultsPanel*/
    private TradierResultsPanel tradierResultsSubPanel = null;
    /**Create a new panel for TwitterResultsPanel*/
    private TwitterResultsPanel twitterResultsSubPanel = null;
    /**Create a new panel for NewsResultsPanel*/
    private NewsResultsPanel newsResultsSubPanel = null;

    /**
     * Create a new Research panel.
     */
    Research(){
        super(TradeNetGUIConstants.RESEARCH_PANEL_IDENTIFIER);
        buildPanel();
    }

    /**
     * Callback for executing a research action.
     * Will call TradierResultsPanel, TwitterResultsPanel, and NewsResultsPanel
     */
    private void researchCallBack(){
        String userResearch = researchField.getText();
        if(userResearch.equals("")) {
            return;
        }
        if(tradierResultsSubPanel == null) {
            tradierResultsSubPanel = new TradierResultsPanel(userResearch);
            addComponent(new BasicFlowPanel(tradierResultsSubPanel));
            constraints.gridy++;
        }
        else{
            tradierResultsSubPanel.updateTickerSymbol(userResearch);
        }
        if(twitterResultsSubPanel == null){
            twitterResultsSubPanel = new TwitterResultsPanel(userResearch);
            addComponent(new BasicFlowPanel(twitterResultsSubPanel));
            constraints.gridy++;
        }
        else{
            twitterResultsSubPanel.updateTickerSymbol(userResearch);
        }
        if(newsResultsSubPanel == null){
            newsResultsSubPanel = new NewsResultsPanel(userResearch);
            addComponent(new BasicFlowPanel(newsResultsSubPanel));
            constraints.gridy++;
        }
        else{
            newsResultsSubPanel.updateTickerSymbol(userResearch);
        }
        revalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        researchSubPanel.setLayout(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        subPanelConstraints.gridx = 0;
        subPanelConstraints.gridy = 0;
        addComponent(new BasicFlowPanel(researchSubPanel));
        addResearchPanel();
    }

    /**Adds all of the components to the researchSubPanel*/
    private void addResearchPanel(){
        addResearchTextField();
        addSearchButton();
        constraints.gridy++;
        constraints.gridx = 0;
    }

    /**Adds the research text field so the user can enter the ticker symbol*/
    private void addResearchTextField(){
        subPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        JLabel researchLabel = new JLabel(TradeNetGUIConstants.RESEARCH_LABEL);
        researchLabel.setHorizontalAlignment(SwingConstants.LEFT);
        researchSubPanel.add(researchLabel, subPanelConstraints);
        subPanelConstraints.gridx++;

        /*Allow detection of the 'Enter' key being pressed.*/
        researchField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread loginCallback = new Thread(()-> researchCallBack());
                loginCallback.start();
            }
        });
        researchSubPanel.add(researchField, subPanelConstraints);

        /*Setup the next items defaults.*/
        subPanelConstraints.fill = GridBagConstraints.NONE;
        subPanelConstraints.gridx++;
        subPanelConstraints.gridy = 0;
    }

    /**Adds the search button to execute the call back function for the APIs*/
    private void addSearchButton(){
        subPanelConstraints.gridx++;
        searchButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread searchButtonThread = new Thread(()-> researchCallBack());
                searchButtonThread.start();
            }
        });
        researchSubPanel.add(new BasicFlowPanel(searchButton), subPanelConstraints);

        /*Setup the next items defaults.*/
        subPanelConstraints.fill = GridBagConstraints.NONE;
        subPanelConstraints.gridx = 0;
        subPanelConstraints.gridy++;
    }

    /**function to allow Trade button to get stock data for purchase*/
    BasePanel getTradierStockData(){
        return tradierResultsSubPanel;
    }
}
