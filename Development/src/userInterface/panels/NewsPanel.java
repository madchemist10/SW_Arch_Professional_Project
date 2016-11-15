package userInterface.panels;

import userInterface.GUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This panel is required for interaction with
 * the {insert newsAPI handler here}.
 */
public class NewsPanel extends BasePanel {

    /**Panel that contains all of the controls for issuing a request.*/
    private final JPanel controlsPanel = new JPanel();
    /**Grid constraints on where elements should be placed in the controls panel.*/
    private final GridBagConstraints controlsPanelConstraints = new GridBagConstraints();
    /**Text field for query input.*/
    private final JTextField queryTextField = new JTextField(20);
    /**Button to attempt to execute the query based on the value
     * stored in the {@link #queryTextField}.*/
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
        addQueryTextField();

        /*Add query Button.
        * Update values for next element to be added.*/
        addQueryButton();

        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(controlsPanel);    //place at (0,0) [parent]
    }

    /**
     * Add the ticker text field to this panel.
     * The Query Label is left aligned.
     */
    private void addQueryTextField(){
        controlsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        JLabel queryLabel = new JLabel(GUIConstants.QUERY_LABEL);
        queryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        controlsPanel.add(queryLabel, controlsPanelConstraints);
        controlsPanelConstraints.gridx++;

        /*Allow detection of the 'Enter' key being pressed.*/
        queryTextField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread queryCallback = new Thread(() -> queryCallBack());
                queryCallback.start();
            }
        });
        controlsPanel.add(queryTextField, controlsPanelConstraints);

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
