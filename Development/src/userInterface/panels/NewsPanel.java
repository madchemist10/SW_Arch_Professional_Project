package userInterface.panels;

import app.exception.BaseException;
import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
import com.fasterxml.jackson.databind.JsonNode;
import userInterface.GUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This panel is required for interaction with
 * the {insert newsAPI handler here}.
 */
public class NewsPanel extends BasePanel implements PropertyChangeListener{

    /**Panel that contains all of the controls for issuing a request.*/
    private final JPanel controlsPanel = new JPanel();
    /**Grid constraints on where elements should be placed in the controls panel.*/
    private final GridBagConstraints controlsPanelConstraints = new GridBagConstraints();
    /**Button to attempt to execute the query.*/
    private final JButton queryButton = new JButton(GUIConstants.QUERY_BUTTON_TEXT);

    /**
     * Create a new {@link NewsPanel}.
     */
    NewsPanel(){
        super(GUIConstants.NEWS_PANEL_IDENTIFIER);
        /*Construct the panel.*/
        buildPanel();
    }

    /**
     * Callback for when the query button is pressed.
     * This should be run on a new thread.
     */
    private void queryCallBack(){
        JsonNode returnNode = executeNewsQuery();
        if(returnNode == null){
            return;
        }
        NewsResultsPanel newsResultsPanel = new NewsResultsPanel();
        newsResultsPanel.addPropertyListener(this);
        newsResultsPanel.updateResultsPanel(returnNode);
        SwingUtilities.invokeLater(() ->newsResultsPanel.setVisible(true));
    }

    /**
     * Execution of the news query is solo here so that
     * the refresh button is able to call here.
     * @return JsonNode of the return value or null if error occurred.
     */
    private JsonNode executeNewsQuery(){
        IAPIHandler newsAPI = app.getAPIHandler(APIHandles.NEWS);
        String request = newsAPI.buildAPIRequest(null);

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
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        /*Set defaults for Controls Panel.*/
        controlsPanel.setLayout(new GridBagLayout());
        controlsPanelConstraints.gridx = 0;
        controlsPanelConstraints.gridy = 0;
        controlsPanelConstraints.ipadx = 5;

        /*Add query Button.
        * Update values for next element to be added.*/
        addQueryButton();

        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(controlsPanel);    //place at (0,0) [parent]
    }

    /**
     * Add the query button and callback to this panel.
     */
    private void addQueryButton(){
        controlsPanelConstraints.gridwidth = 2;
        queryButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread queryCallback = new Thread(() -> queryCallBack());
                queryCallback.start();
            }
        });
        controlsPanel.add(queryButton,controlsPanelConstraints);

        /*Setup the next items defaults.*/
        controlsPanelConstraints.gridwidth = 1;
        controlsPanelConstraints.gridx = 0;
        controlsPanelConstraints.gridy++;
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
        NewsResultsPanel newsResultsPanel = null;
        if(event.getSource() instanceof TradierResultsPanel){
            newsResultsPanel = (NewsResultsPanel) event.getSource();
        }
        /*If the results panel is null, we cannot continue.*/
        if(newsResultsPanel == null){
            return;
        }

        AppChangeEvents eventName = event.getEventName();

        switch (eventName){
            case NEWS_REFRESH:
                JsonNode returnNode = executeNewsQuery();
                final NewsResultsPanel panel = newsResultsPanel;
                SwingUtilities.invokeLater(() -> panel.updateResultsPanel(returnNode));
                break;
        }
    }
}
