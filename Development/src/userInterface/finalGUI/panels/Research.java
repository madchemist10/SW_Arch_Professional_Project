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

    private TradierResultsPanel tradierResultsSubPanel = null;


    /**
     * Create a new Research panel.
     */
    Research(){
        super(TradeNetGUIConstants.RESEARCH_PANEL_IDENTIFIER);
        buildPanel();
    }

    /**
     * Callback for executing a research action.
     * Will call TradierResultsPanel
     */
    private void researchCallBack(){
        String userResearch = researchField.getText();
        if(userResearch.equals("")){
            return;
        }
        tradierResultsSubPanel = new TradierResultsPanel(userResearch);
        //closeWindow(searchButton);
    }

    private void closeWindow(JButton button){
        Window window = SwingUtilities.getWindowAncestor(button);
        if(window != null){
            window.setVisible(false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        subPanelConstraints.gridx = 0;
        subPanelConstraints.gridy = 0;
        addResearchTextField();
        addSearchButton();
    }

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

    /**
     * Add the research text field to this panel.
     * The research Label is left aligned.
     */
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
        subPanelConstraints.gridx = 0;
        subPanelConstraints.gridy++;
    }

    BasePanel getTradierStockData(){
        return tradierResultsSubPanel;
    }
}
