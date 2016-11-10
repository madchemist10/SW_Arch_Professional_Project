package userInterface.panels;

import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
import twitter4j.QueryResult;
import twitter4j.Status;
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

    private final JPanel controlsPanel = new JPanel();

    private final GridBagConstraints controlsPanelConstraints = new GridBagConstraints();

    private final JPanel resultsPanel = new JPanel();

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
     */
    private void queryCallBack(){
        String query = queryField.getText();
        IAPIHandler twitterAPI = app.getAPIHandler(APIHandles.TWITTER);
        String request = twitterAPI.buildAPIRequest(new String[]{query});
        Object returnVal = twitterAPI.executeAPIRequest(request);
        if(returnVal instanceof QueryResult){
            QueryResult queryResult = (QueryResult) returnVal;
            for(Status status : queryResult.getTweets()){
                System.out.println(status.getText());
                //add to panel somehow
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        controlsPanelConstraints.gridx = 0;
        controlsPanelConstraints.gridy = 0;
        addQueryField();
        controlsPanelConstraints.gridy++;
        addQueryButton();
        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(controlsPanel);
        constraints.gridy++;
        addTwitterResultsPanel();
    }

    /**
     * Add the query text field to this panel.
     */
    private void addQueryField(){
        controlsPanel.add(queryField, controlsPanelConstraints);
    }

    /**
     * Add the query button and callback to this panel.
     */
    private void addQueryButton(){
        queryButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryCallBack();
            }
        });
        controlsPanel.add(queryButton,controlsPanelConstraints);
    }

    private void addTwitterResultsPanel(){
        resultsPanel.setLayout(new GridBagLayout());
        addComponent(buildScrollPane(resultsPanel));
    }

    private static JScrollPane buildScrollPane(JComponent component){
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.add(new BasicPanel(component));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(GUIConstants.DEFAULT_GUI_WIDTH/2, GUIConstants.DEFAULT_GUI_HEIGHT));
        return scrollPane;
    }
}
