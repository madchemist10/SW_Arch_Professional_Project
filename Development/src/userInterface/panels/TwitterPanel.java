package userInterface.panels;

import app.exception.BaseException;
import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
import twitter4j.QueryResult;
import userInterface.GUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This panel is required for interaction with
 * the {@link app.utilities.apiHandlers.TwitterAPIHandler}.
 */
public class TwitterPanel extends BasePanel{

    /**Text field for user query input.*/
    private final JTextField queryField = new JTextField(20);
    /**Button to attempt to execute the query based on the value
     * stored in the {@link #queryField}.*/
    private final JButton queryButton = new JButton(GUIConstants.QUERY_BUTTON_TEXT);
    /**Panel that contains all of the controls for issuing a request.*/
    private final JPanel controlsPanel = new JPanel();
    /**Grid constraints on where elements should be placed in the controls panel.*/
    private final GridBagConstraints controlsPanelConstraints = new GridBagConstraints();

    /**
     * Create a new {@link TwitterPanel}.
     */
    TwitterPanel(){
        super(GUIConstants.TWITTER_PANEL_IDENTIFIER);
        /*Construct the panel.*/
        buildPanel();
    }

    /**
     * Callback for when the query button is pressed.
     * This should be run on a new thread.
     */
    private void queryCallBack(){
        String query = queryField.getText();
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

        /*Add query Text field.
        * Update values for next element to be added.*/
        addQueryField();

        /*Add query Button.
        * Update values for next element to be added.*/
        addQueryButton();

        /*Add the controls panel that has the controls, to this
        * Twitter Panel.*/
        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(controlsPanel);    //place at (0,0) [parent]
    }

    /**
     * Add the query text field to this {@link #controlsPanel}.
     * The Query Label is left aligned.
     */
    private void addQueryField(){
        controlsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        JLabel queryLabel = new JLabel(GUIConstants.QUERY_LABEL);
        queryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        controlsPanel.add(queryLabel, controlsPanelConstraints);
        controlsPanelConstraints.gridx++;

        /*Allow detection of the 'Enter' key being pressed.*/
        queryField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread queryCallback = new Thread(() -> queryCallBack());
                queryCallback.start();
            }
        });
        controlsPanel.add(queryField, controlsPanelConstraints);

        /*Setup the next items defaults.*/
        controlsPanelConstraints.fill = GridBagConstraints.NONE;
        controlsPanelConstraints.gridx = 0;
        controlsPanelConstraints.gridy++;
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
}
