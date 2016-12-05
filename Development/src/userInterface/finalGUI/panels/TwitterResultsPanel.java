package userInterface.finalGUI.panels;

import app.exception.BaseException;
import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
import twitter4j.QueryResult;
import twitter4j.Status;
import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

/**
 * Twitter results panel that handles its own query executions.
 * {@link GUIController} should be a listener to this class
 * as we need to handle the error conditions that may occur
 * from execution of the Timer for querying Twitter.
 */
class TwitterResultsPanel extends BasePanel{
    /**Ticker symbol for this {@link TwitterResultsPanel}.*/
    private String tickerSymbol;
    /**Panel that will contain the results as they are generated.*/
    private final JPanel resultsPanel = new JPanel();
    /**Constraints for label placement within the results panel.*/
    private final GridBagConstraints resultsConstraints = new GridBagConstraints();
    /**List of all listeners that are associated with this class.*/
    private final List<PropertyChangeListener> listeners = new LinkedList<>();

    /**
     * Create a new Tradier Results panel with a given query.
     * @param query to be added to the title to denote which query's results
     *              are displayed in the panel.
     */
    TwitterResultsPanel(String query){
        super(TradeNetGUIConstants.TWITTER_PANEL_IDENTIFIER);
        tickerSymbol = query;
        buildPanel();
        queryExecution();
//        //spawn a timer to execute the query every 10 seconds.
//        java.util.Timer timer = new Timer();
//        //1000 = 1 sec; 0 to start with no delay.
//        timer.scheduleAtFixedRate(new RefreshTimer(this),0,10000);
    }
    void updateTickerSymbol(String query){
        tickerSymbol = query;
        queryExecution();
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
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        /*Change the speed of the scrolling so that it is more usable to scroll.*/
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        resultsPanel.setLayout(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        addComponent(scrollPane);
        //resultsPanel.add(scrollPane);
        //initializePanel();
    }

    /**
     * Set up the default frame of the results panel and add
     * all the supporting labels.
     */
    private void initializePanel(){
        /*Construct the Description Labels*/
        JLabel senderLabel = new JLabel(TradeNetGUIConstants.SENDER_LABEL);
        JLabel messageLabel = new JLabel(TradeNetGUIConstants.MESSAGE_LABEL);

        /*Wrap the Description Labels in Basic Left Justified Panels*/
        BasicFlowPanel senderPanel = new BasicFlowPanel(senderLabel);
        BasicFlowPanel messagePanel = new BasicFlowPanel(messageLabel);

        resultsPanel.add(senderPanel, resultsConstraints);
        resultsConstraints.gridx++;
        resultsPanel.add(messagePanel, resultsConstraints);
        resultsConstraints.gridy++;
        //reset resultsConstraints for later use
        resultsConstraints.gridx = 0;
    }

    /**
     * Callback for when the query should be executed.
     * This should be run on a new thread.
     */
    private void queryExecution() {
        String query = tickerSymbol;
        if(!validateUserInput(query)){
            return;
        }
        IAPIHandler twitterAPI = app.getAPIHandler(APIHandles.TWITTER);
        String request = twitterAPI.buildAPIRequest(new String[]{query});
        Object returnVal;

        try {
            returnVal = twitterAPI.executeAPIRequest(request);
        } catch (BaseException e) {
            return;
        }

        if(returnVal == null){
            notifyListeners(new userInterface.finalGUI.panels.CustomChangeEvent(this, userInterface.finalGUI.panels.AppChangeEvents.INVALID_TWITTER_API_CREDENTIALS));
        }
        if(returnVal instanceof QueryResult){
            QueryResult queryResult = (QueryResult) returnVal;
            /*Construct a new results panel that is to popup and display
            * the results from the search query.*/
            //userInterface.finalGUI.panels.TwitterResultsPanel resultsPanel = new userInterface.finalGUI.panels.TwitterResultsPanel(query);
            /*Populate the results panel with the given return values.*/
            //queryResult.getTweets().forEach(addEntryToResults());
            queryResult.getTweets().forEach(this::addEntryToResults);
            /*Show the results panel on the UI Thread.*/
            SwingUtilities.invokeLater(() -> resultsPanel.setVisible(true));
        }
    }


    private void addEntryToResults(Status tweet){
        JLabel sender = new JLabel(tweet.getUser().getName());
        BasicFlowPanel senderPanel = new BasicFlowPanel(sender);
        JLabel text = new JLabel(tweet.getText());
        text.setHorizontalAlignment(SwingConstants.LEFT);
        BasicFlowPanel textPanel = new BasicFlowPanel(text);

        /*Add the sender's tag.*/
        resultsConstraints.gridx = 0;
        resultsPanel.add(senderPanel, resultsConstraints);
        /*Add the text of the message.*/
        resultsConstraints.gridx++;
        resultsPanel.add(textPanel, resultsConstraints);
        resultsConstraints.gridy++;
    }

    /**
     * Timer to refresh the data stored in this panel.
     */
    private static class RefreshTimer extends TimerTask{

        private final TwitterResultsPanel results;

        RefreshTimer(TwitterResultsPanel results){
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
