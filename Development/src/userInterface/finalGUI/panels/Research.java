package userInterface.finalGUI.panels;

import app.exception.BaseException;
import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
import com.fasterxml.jackson.databind.JsonNode;
import twitter4j.QueryResult;
import userInterface.finalGUI.TradeNetGUIConstants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * Panel to display the researched item.
 */
public class Research extends BasePanel implements PropertyChangeListener{
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
    /**Reference to the new results panel.*/
    private final NewsResultsPanel newsResultsPanel = new NewsResultsPanel();

    /**
     * Create a new Research panel.
     */
    Research(){
        super(TradeNetGUIConstants.RESEARCH_PANEL_IDENTIFIER);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        buildPanel();
    }


    @Override
    public void addComponent(Component comp) {
        super.add(comp);
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
        tradierResultsSubPanel = new TradierResultsPanel(userResearch);
        /*Reference to tradier results frame*/
        TradierResultsFrame tradierResultsFrame = new TradierResultsFrame(tradierResultsSubPanel);
        tradierResultsFrame.addPropertyListener(this);
        tradierResultsFrame.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            JsonNode node = executeNewsQuery(userResearch);
            newsResultsPanel.updateResultsPanel(node);
            newsResultsPanel.setVisible(true);
        });
        executeTwitterQuery(userResearch);
        revalidate();
    }

    /**
     * Handle the events thrown from the {@link NewsResultsPanel}.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CustomChangeEvent event = null;
        if(evt instanceof CustomChangeEvent) {
            event = (CustomChangeEvent) evt;
        }
        /*If the event is null, we cannot continue.*/
        if(event == null){
            return;
        }

        AppChangeEvents eventName = event.getEventName();

        switch (eventName){
            case TRADE_STOCK:
                notifyListeners(event);
                break;
        }
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
    TradierResultsPanel getTradierStockData(String query){
        if(query == null){
            return tradierResultsSubPanel;
        }
        return new TradierResultsPanel(query);
    }

    /**
     * Execution of the news query is solo here so that
     * the refresh button is able to call here.
     * @return JsonNode of the return value or null if error occurred.
     */
    private JsonNode executeNewsQuery(String query){
        IAPIHandler newsAPI = app.getAPIHandler(APIHandles.NEWS);
        String request = newsAPI.buildAPIRequest(new String[]{query});

        if(request == null){
            notifyListeners(new CustomChangeEvent(this,AppChangeEvents.INVALID_NEWS_API_CREDENTIALS));
            return null;
        }

        Object returnVal;

        try {
            returnVal = newsAPI.executeAPIRequest(request);
        }catch(BaseException e){
            return null;
        }

        if(returnVal == null){
            notifyListeners(new CustomChangeEvent(this,AppChangeEvents.INVALID_NEWS_API_CREDENTIALS));
            return null;
        }

        if(returnVal instanceof JsonNode){
            return (JsonNode) returnVal;
        }
        return null;
    }

    /**
     * Executes a call to the twitter API
     * @param query the query used to populate the twitter API call
     */
    private void executeTwitterQuery(String query){
        IAPIHandler twitterAPI = app.getAPIHandler(APIHandles.TWITTER);
        String request = twitterAPI.buildAPIRequest(new String[]{query});
        Object returnVal;

        try {
            returnVal = twitterAPI.executeAPIRequest(request);
        } catch (BaseException e) {
            return;
        }

        if(returnVal == null){
            notifyListeners(new CustomChangeEvent(this,AppChangeEvents.INVALID_TWITTER_API_CREDENTIALS));
        }
        if(returnVal instanceof QueryResult){
            QueryResult queryResult = (QueryResult) returnVal;
            /*Construct a new results panel that is to popup and display
            * the results from the search query.*/
            TwitterResultsPanel resultsPanel = new TwitterResultsPanel(query);
            /*Populate the results panel with the given return values.*/
            queryResult.getTweets().forEach(resultsPanel::addEntryToResults);
            /*Show the results panel on the UI Thread.*/
            SwingUtilities.invokeLater(() -> resultsPanel.setVisible(true));
        }
    }
}
