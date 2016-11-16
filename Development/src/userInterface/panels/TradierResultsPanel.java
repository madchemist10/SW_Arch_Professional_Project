package userInterface.panels;

import app.constants.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import userInterface.GUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

/**
 * This frame is a popup for when the user has decided to search
 * for a specific ticker symbol. Display the results from the
 * ticker symbol call.
 */
class TradierResultsPanel extends JFrame{

    /**Ticker symbol for this {@link TradierResultsPanel}.*/
    private final String tickerSymbol;

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
    /**Button to refresh the data on the page.*/
    private final JButton refreshButton = new JButton(GUIConstants.REFRESH_BUTTON_TEXT);

    /**List of all listeners that are associated with this class.*/
    private final List<PropertyChangeListener> listeners = new LinkedList<>();

    /**
     * Create a new Twitter Results panel with a given query.
     * @param query to be added to the title to denote which query's results
     *              are displayed in the panel.
     */
    TradierResultsPanel(String query){
        setTitle(GUIConstants.TRADIER_RESULTS_PANEL_TITLE+": "+query);
        tickerDataLabel.setText(query);
        tickerSymbol = query;
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
     * The current ticker symbol for this panel.
     * @return String representation of the ticker symbol for this panel.
     */
    String getTickerSymbol(){
        return tickerSymbol;
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
        /*Construct the Description Labels*/
        JLabel tickerSymbolLabel = new JLabel(GUIConstants.TICKER_SYMBOL_LABEL);
        JLabel lastPriceLabel = new JLabel(GUIConstants.LAST_PRICE_LABEL);
        JLabel dailyNetChangeLabel = new JLabel(GUIConstants.DAILY_NET_CHANGE_LABEL);
        JLabel volumeLabel = new JLabel(GUIConstants.VOLUME_LABEL);

        /*Wrap the Description Labels in Basic Left Justified Panels*/
        BasicPanel tickerPanel = new BasicPanel(tickerSymbolLabel);
        BasicPanel lastPricePanel = new BasicPanel(lastPriceLabel);
        BasicPanel dailyNetChangePanel = new BasicPanel(dailyNetChangeLabel);
        BasicPanel volumePanel = new BasicPanel(volumeLabel);

        /*Wrap the Data Labels in Basic Left Justified Panels*/
        BasicPanel tickerDataPanel = new BasicPanel(tickerDataLabel);
        BasicPanel lastPriceDataPanel = new BasicPanel(lastPriceDataLabel);
        BasicPanel dailyNetChangeDataPanel = new BasicPanel(dailyNetChangeDataLabel);
        BasicPanel volumeDataPanel = new BasicPanel(volumeDataLabel);

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

        /*Add Refresh Button*/
        addRefreshButton();
    }

    /**
     * Callback for when the refresh button is pressed.
     * This should be run on a new thread.
     */
    private void refreshCallBack(){
        notifyListeners(new CustomChangeEvent(this, AppChangeEvents.TRADIER_REFRESH));
    }

    /**
     * Add a refresh button to refresh the data that is in this
     * panel.
     */
    private void addRefreshButton(){
        constraints.gridx = 0;
        constraints.gridwidth = 2;
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
        constraints.gridwidth = 1;
    }

    /**
     * Add Json node to the panel structure.
     * This method can be used to refresh data as well.
     */
    void updateResultsEntry(JsonNode node){
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

        /*Get the sub nodes' text values to display*/
        String lastPrice = lastNode.asText();
        String dailyNetChange = dailyNetChangeNode.asText();
        String volumeLabel = volumeNode.asText();

        /*Update the text values for the sub nodes' text*/
        lastPriceDataLabel.setText(lastPrice);
        dailyNetChangeDataLabel.setText(dailyNetChange);
        volumeDataLabel.setText(volumeLabel);
    }
}
