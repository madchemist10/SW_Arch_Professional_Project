package userInterface.panels;

import app.constants.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import userInterface.GUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.LinkedList;

/**
 * This frame is a popup for when the user has decided to search
 * for a specific ticker symbol. Display the results from the
 * ticker symbol call.
 */
class NewsResultsPanel extends JFrame{

    /**Maximum number of titles to display.*/
    private static final int MAX_TITLES = 10;
    /**Panel that will contain the results as they are generated.*/
    private final JPanel resultsPanel = new JPanel();
    /**Constraints for label placement within the results panel.*/
    private final GridBagConstraints constraints = new GridBagConstraints();
    /**List of labels used to display news titles..*/
    private final List<JLabel> labels = new LinkedList<>();
    /**Button to refresh the data on the page.*/
    private final JButton refreshButton = new JButton(GUIConstants.REFRESH_BUTTON_TEXT);

    /**List of all listeners that are associated with this class.*/
    private final List<PropertyChangeListener> listeners = new LinkedList<>();

    /**
     * Create a new News Results panel.
     */
    NewsResultsPanel(){
        setTitle(GUIConstants.NEWS_RESULTS_PANEL_TITLE);
        setSize(new Dimension(GUIConstants.DEFAULT_GUI_WIDTH, GUIConstants.DEFAULT_GUI_HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        buildFrame();
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
    private void notifyListeners(CustomChangeEvent event){
        for(PropertyChangeListener listener: listeners){
            listener.propertyChange(event);
        }
    }

    /**
     * Construct the frame.
     * Set default values for adding entries to the panel.
     */
    private void buildFrame(){
        resultsPanel.setLayout(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new BasicPanel(resultsPanel));
        initializePanel();
    }

    /**
     * Set up the default frame of the results panel and add
     * all the supporting labels.
     */
    private void initializePanel(){
        /*Add News Titles*/
        addNewsTitles();

        /*Add Refresh Button*/
        addRefreshButton();
    }

    /**
     * Callback for when the refresh button is pressed.
     * This should be run on a new thread.
     */
    private void refreshCallBack(){
        notifyListeners(new CustomChangeEvent(this, AppChangeEvents.NEWS_REFRESH));
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
     * Add a refresh button to refresh the data that is in this
     * panel.
     */
    private void addRefreshButton(){
        constraints.gridx = 1;
        constraints.gridy++;
        refreshButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread queryCallback = new Thread(() -> refreshCallBack());
                queryCallback.start();
            }
        });
        resultsPanel.add(refreshButton, constraints);
    }

    /**
     * Add Json node to the panel structure.
     * This method can be used to refresh data as well.
     */
    void updateResultsPanel(JsonNode node){
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
}
