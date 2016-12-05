package userInterface.finalGUI.panels;

import app.exception.BaseException;
import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
import twitter4j.QueryResult;
import twitter4j.Status;
import userInterface.GUIConstants;
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

        setSize(new Dimension(GUIConstants.DEFAULT_GUI_WIDTH, GUIConstants.DEFAULT_GUI_HEIGHT));
        tickerSymbol = query;
        buildPanel();
        queryExecution();
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
            /*Populate the results panel with the given return values.*/
            final TwitterResultsPanel self = this;
            SwingUtilities.invokeLater(() -> queryResult.getTweets().forEach(self::addEntryToResults));
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
}
