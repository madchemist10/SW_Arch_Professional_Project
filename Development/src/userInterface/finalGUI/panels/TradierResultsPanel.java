package userInterface.finalGUI.panels;

import app.constants.Constants;
import app.exception.BaseException;
import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
import com.fasterxml.jackson.databind.JsonNode;
import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * Tradier results panel that handles its own query executions.
 * {@link GUIController} should be a listener to this class
 * as we need to handle the error conditions that may occur
 * from execution of the Timer for querying Tradier network.
 */
class TradierResultsPanel extends BasePanel{

    /**Ticker symbol for this {@link TradierResultsPanel}.*/
    private String tickerSymbol;
    /**Company name for this results session.*/
    private String companyName;
    /**Panel that will contain the results as they are generated.*/
    private final JPanel resultsPanel = new JPanel();
    /**Constraints for label placement within the results panel.*/
    private final GridBagConstraints constraints = new GridBagConstraints();
    /**Data label for ticker symbol.*/
    private final JLabel tickerDataLabel = new JLabel();
    /**Data label for last price.*/
    private final JLabel lastPriceDataLabel = new JLabel();
    /**Data label for daily net change.*/
    private final JLabel dailyNetChangeDataLabel = new JLabel();
    /**Data label for volume.*/
    private final JLabel volumeDataLabel = new JLabel();
    /**List of all listeners that are associated with this class.*/
    private final List<PropertyChangeListener> listeners = new LinkedList<>();

    /**
     * Create a new Tradier Results panel with a given query.
     * @param query to be added to the title to denote which query's results
     *              are displayed in the panel.
     */
    TradierResultsPanel(String query){
        super(TradeNetGUIConstants.TRADIER_PANEL_IDENTIFIER);
        tickerDataLabel.setText(query);
        tickerSymbol = query;
        buildPanel();
        queryExecution();
        //spawn a timer to execute the query every second.
        java.util.Timer timer = new Timer();
        //1000 = 1 sec; 0 to start with no delay.
        timer.scheduleAtFixedRate(new RefreshTimer(this),0,1000);
    }

    String getTickerSymbol(){
        return tickerSymbol;
    }
    String getCurrentVal(){
        return lastPriceDataLabel.getText();
    }
    String getCompanyName(){
        return companyName;
    }

    /**
     * Update ticker symbol with new symbol from
     * user input.
     * @param query to reset this tradier panel ticker symbol.
     */
    void updateTickerSymbol(String query){
        tickerDataLabel.setText(query);
        tickerSymbol = query;
    }

    /**
     * Add a property change listener to this panel for when this
     * panel should notify its listeners of property changes.
     * @param listener that is to listen for events from this panel.
     */
    void addPropertyListener(PropertyChangeListener listener){
        listeners.add(listener);
    }

    /**
     * Notify all listeners that an event has been created.
     * @param event that is to alert all listeners of an event.
     */
    void notifyListeners(CustomChangeEvent event){
        for(PropertyChangeListener listener: listeners){
            listener.propertyChange(event);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
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
        /*Construct the Description Labels*/
        JLabel tickerSymbolLabel = new JLabel(TradeNetGUIConstants.TICKER_SYMBOL_LABEL);
        JLabel lastPriceLabel = new JLabel(TradeNetGUIConstants.LAST_PRICE_LABEL);
        JLabel dailyNetChangeLabel = new JLabel(TradeNetGUIConstants.DAILY_NET_CHANGE_LABEL);
        JLabel volumeLabel = new JLabel(TradeNetGUIConstants.VOLUME_LABEL);

        /*Wrap the Description Labels in Basic Left Justified Panels*/
        BasicFlowPanel tickerPanel = new BasicFlowPanel(tickerSymbolLabel);
        BasicFlowPanel lastPricePanel = new BasicFlowPanel(lastPriceLabel);
        BasicFlowPanel dailyNetChangePanel = new BasicFlowPanel(dailyNetChangeLabel);
        BasicFlowPanel volumePanel = new BasicFlowPanel(volumeLabel);

        /*Wrap the Data Labels in Basic Left Justified Panels*/
        BasicFlowPanel tickerDataPanel = new BasicFlowPanel(tickerDataLabel);
        BasicFlowPanel lastPriceDataPanel = new BasicFlowPanel(lastPriceDataLabel);
        BasicFlowPanel dailyNetChangeDataPanel = new BasicFlowPanel(dailyNetChangeDataLabel);
        BasicFlowPanel volumeDataPanel = new BasicFlowPanel(volumeDataLabel);

        /*Add Ticker Labels*/
        constraints.gridy++;
        resultsPanel.add(tickerPanel, constraints);
        constraints.gridx++;
        resultsPanel.add(tickerDataPanel, constraints);
        constraints.gridx = 0;

        /*Add Last Price Labels*/
        constraints.gridy++;
        resultsPanel.add(lastPricePanel, constraints);
        constraints.gridx++;
        resultsPanel.add(lastPriceDataPanel, constraints);
        constraints.gridx = 0;

        /*Add Daily Net Change Labels*/
        constraints.gridy++;
        resultsPanel.add(dailyNetChangePanel, constraints);
        constraints.gridx++;
        resultsPanel.add(dailyNetChangeDataPanel, constraints);
        constraints.gridx = 0;

        /*Add Volume Labels*/
        constraints.gridy++;
        resultsPanel.add(volumePanel, constraints);
        constraints.gridx++;
        resultsPanel.add(volumeDataPanel, constraints);
        constraints.gridx = 0;
    }

    /**
     * Add Json node to the panel structure.
     * This method can be used to refresh data as well.
     */
    private void updateResultsEntry(JsonNode node){
        JsonNode quotes = node.get(Constants.QUOTES);
        JsonNode singleQuote = null;

        /*Loop over all quotes until we find the one that matches
        * the user's request exactly.*/
        for(JsonNode quote: quotes){
            JsonNode symbolNode = quote.get(Constants.SYMBOL);
            String currentQuote = tickerDataLabel.getText();
            String nodeSymbol = symbolNode.textValue();
            if(currentQuote.toLowerCase().equals(nodeSymbol.toLowerCase())){
                singleQuote = quote;
                break;
            }
        }
        if(singleQuote == null){
            return;
        }

        /*Get the sub nodes that we need to display*/
        JsonNode lastNode = singleQuote.get(Constants.LAST);
        JsonNode dailyNetChangeNode = singleQuote.get(Constants.CHANGE);
        JsonNode volumeNode = singleQuote.get(Constants.VOLUME);

        if(lastNode == null || dailyNetChangeNode == null || volumeNode == null){
            return;
        }

//        companyName = singleQuote.get("").asText();

        /*Get the sub nodes' text values to display*/
        String lastPrice = lastNode.asText();
        String dailyNetChange = dailyNetChangeNode.asText();
        String volumeLabel = volumeNode.asText();

        /*Update the text values for the sub nodes' text*/
        lastPriceDataLabel.setText(lastPrice);
        dailyNetChangeDataLabel.setText(dailyNetChange);
        volumeDataLabel.setText(volumeLabel);
    }

    /**
     * Callback for when the query should be executed.
     * This should be run on a new thread.
     */
    private void queryExecution() {
        final JsonNode returnNode = executeTradierQuery(tickerSymbol);
        if(returnNode == null){
            return;
        }
        SwingUtilities.invokeLater(() -> updateResultsEntry(returnNode));
    }

    /**
     * Execution of the tradier query is solo here so that
     * the refresh button is able to call here.
     * @param query that is from the user's input.
     * @return JsonNode of the return value or null if error occurred.
     */
    private JsonNode executeTradierQuery(String query){
        IAPIHandler tradierAPI = app.getAPIHandler(APIHandles.TRADIER);
        String request = tradierAPI.buildAPIRequest(new String[]{query});
        Object returnVal;

        try {
            returnVal = tradierAPI.executeAPIRequest(request);
        }catch(BaseException e){
            notifyListeners(new CustomChangeEvent(this, AppChangeEvents.TRADIER_HTTP_ERROR, e.getMessage()));
            return null;
        }

        if(returnVal == null){
            notifyListeners(new CustomChangeEvent(this,AppChangeEvents.INVALID_TRADIER_API_CREDENTIALS));
            return null;
        }

        if(returnVal instanceof JsonNode){
            return (JsonNode) returnVal;
        }
        return null;
    }

    /**
     * Timer to refresh the data stored in this panel.
     */
    private static class RefreshTimer extends TimerTask{

        private final TradierResultsPanel results;

        RefreshTimer(TradierResultsPanel results){
            this.results = results;
        }

        @Override
        public void run() {
            /*Spawn background thread to keep from locking up the GUI.*/
            Thread helperThread = new Thread(results::queryExecution);
            helperThread.start();
        }
    }
}
