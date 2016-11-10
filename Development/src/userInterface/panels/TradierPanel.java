package userInterface.panels;

import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
import userInterface.GUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This panel is required for interaction with
 * the {@link app.utilities.apiHandlers.TradierAPIHandler}.
 */
public class TradierPanel extends BasePanel{

    /**Text field for ticker symbol input.*/
    private final JTextField tickerTextField = new JTextField(20);
    /**Button to attempt to execute the query based on the value
     * stored in the {@link #tickerTextField}.*/
    private final JButton queryButton = new JButton(GUIConstants.QUERY_BUTTON_TEXT);
    /**Panel that contains all of the controls for issuing a request.*/
    private final JPanel controlsPanel = new JPanel();
    /**Grid constraints on where elements should be placed in the controls panel.*/
    private final GridBagConstraints controlsPanelConstraints = new GridBagConstraints();

    /**
     * Create a new {@link TradierPanel}.
     */
    TradierPanel(){
        super(GUIConstants.TRADIER_PANEL_IDENTIFIER);
        /*Construct the panel.*/
        buildPanel();
    }

    /**
     * Callback for when the query button is pressed.
     * This should be run on a new thread.
     */
    private void queryCallBack() {
        String query = tickerTextField.getText();
        if(!validateUserInput(query)){
            return;
        }
        IAPIHandler tradierAPI = app.getAPIHandler(APIHandles.TRADIER);
        String request = tradierAPI.buildAPIRequest(new String[]{query});
        Object returnVal = tradierAPI.executeAPIRequest(request);
        //todo need to do something with return value.
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

        /*Add ticker symbol Text field.
        * Update values for next element to be added.*/
        addTickerTextField();

        /*Add query Button.
        * Update values for next element to be added.*/
        addQueryButton();

        /*Add the controls panel that has the controls, to this
        * Tradier Panel.*/
        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(controlsPanel);    //place at (0,0) [parent]
    }

    /**
     * Add the ticker text field to this panel.
     * The Ticker Label is left aligned.
     */
    private void addTickerTextField(){
        controlsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        JLabel tickerLabel = new JLabel(GUIConstants.TICKER_LABEL);
        tickerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        controlsPanel.add(tickerLabel, controlsPanelConstraints);
        controlsPanelConstraints.gridx++;
        controlsPanel.add(tickerTextField, controlsPanelConstraints);

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
