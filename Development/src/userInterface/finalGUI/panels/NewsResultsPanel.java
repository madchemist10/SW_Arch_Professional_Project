package userInterface.finalGUI.panels;

import app.constants.Constants;
import app.exception.BaseException;
import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
import com.fasterxml.jackson.databind.JsonNode;
import userInterface.finalGUI.TradeNetGUIConstants;

import java.beans.PropertyChangeEvent;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

/**
 * NewsResultsPanel creates a panel with the results of the News API
 */
class NewsResultsPanel extends BasePanel{
    /**Maximum number of titles to display.*/
    private static final int MAX_TITLES = 10;
    /**Panel that will contain the results as they are generated.*/
    private final JPanel resultsPanel = new JPanel();
    /**Constraints for label placement within the results panel.*/
    private final GridBagConstraints constraints = new GridBagConstraints();
    /**query from user from the user*/
    private String tickerSymbol = "";
    /**List of all listeners that are associated with this class.*/
    private final List<PropertyChangeListener> listeners = new LinkedList<>();
    /**List of labels used to display news titles..*/
    private final List<JLabel> labels = new LinkedList<>();


    NewsResultsPanel(String query){
        super(TradeNetGUIConstants.NEWS_PANEL_IDENTIFIER);
        tickerSymbol = query;
        buildPanel();
        queryExecution();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel(){
        resultsPanel.setLayout(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new BasicFlowPanel(resultsPanel));
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
     * Callback for when the query button is pressed.
     * This should be run on a new thread.
     */
    private void queryExecution(){
        JsonNode returnNode = executeNewsQuery(null);
        if(returnNode == null){
            return;
        }
        updateResultsPanel(returnNode);
        SwingUtilities.invokeLater(() ->resultsPanel.setVisible(true));
    }
    /**
     * Execution of the news query is solo here so that
     * the refresh button is able to call here.
     * @return JsonNode of the return value or null if error occurred.
     */
    private JsonNode executeNewsQuery(String query){
        IAPIHandler newsAPI = app.getAPIHandler(APIHandles.NEWS);
        String request = newsAPI.buildAPIRequest(new String[] {query});

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
     * Add Json node to the panel structure.
     * This method can be used to refresh data as well.
     */
    private void updateResultsPanel(JsonNode node){
        JsonNode articlesNode = node.get(Constants.ARTICLES);
        if(articlesNode == null){
            return;
        }
        for(int i = 0; i < MAX_TITLES; i++){
            JsonNode articleNode = articlesNode.get(i);
            if(articleNode == null){
                continue;
            }
            JsonNode titleNode = articleNode.get(Constants.TITLE);
            if(titleNode == null){
                continue;
            }
            String title = titleNode.asText();
            JLabel label = labels.get(i);
            label.setText(title);
        }
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

}
