package userInterface.panels;

import app.constants.Constants;
import app.exception.BaseException;
import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
import com.fasterxml.jackson.databind.JsonNode;
import userInterface.GUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This panel is required for interaction with
 * the {@link app.utilities.apiHandlers.MailBoxLayerAPIHandler}.
 */
public class MailBoxLayerPanel extends BasePanel {

    /**Text field for the user email.*/
    private final JTextField emailField = new JTextField(20);
    /**Button to attempt to execute the validation based on the value
     * stored in the {@link #emailField}.*/
    private final JButton validateEmailButton = new JButton(GUIConstants.VALIDATE_BUTTON_TEXT);
    /**Panel that contains all of the controls for issuing a validation.*/
    private final JPanel controlsPanel = new JPanel();
    /**Grid constraints on where elements should be placed in the controls panel.*/
    private final GridBagConstraints controlsPanelConstraints = new GridBagConstraints();
    /**Label to give user feedback on validation of their email.*/
    private final JLabel validationLabel = new JLabel();

    /**
     * Create a new {@link MailBoxLayerPanel}.
     */
    MailBoxLayerPanel() {
        super(GUIConstants.MAILBOX_PANEL_IDENTIFIER);
        /*Construct the panel.*/
        buildPanel();
    }

    /**
     * Callback for when the validate button is pressed.
     * This should be run on a new thread.
     */
    private void validateCallBack(){
        String query = emailField.getText();
        if(!validateUserInput(query)){
            return;
        }
        IAPIHandler mailboxAPI = app.getAPIHandler(APIHandles.MAILBOX_LAYER);
        String request = mailboxAPI.buildAPIRequest(new String[]{query});
        if(request == null){
            notifyListeners(new CustomChangeEvent(this, AppChangeEvents.INVALID_MAILBOX_API_CREDENTIALS));
        }
        Object returnVal;
        try {
            returnVal = mailboxAPI.executeAPIRequest(request);
        } catch (BaseException e) {
            return;
        }
        if(returnVal == null){
            notifyListeners(new CustomChangeEvent(this, AppChangeEvents.INVALID_MAILBOX_API_CREDENTIALS));
            return;
        }
        if(returnVal instanceof JsonNode) {
            JsonNode returnNode = (JsonNode) returnVal;
            JsonNode formatNode = returnNode.get(Constants.FORMAT_VALID);
            if(formatNode.booleanValue()){
                validationLabel.setText(GUIConstants.EMAIL_VALID+": ("+query+")");
                return;
            }
            validationLabel.setText(GUIConstants.EMAIL_INVALID+": ("+query+")");
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

        /*Add email Text field.
        * Update values for next element to be added.*/
        addEmailTextField();

        /*Add validate email Button.
        * Update values for next element to be added.*/
        addValidateEmailButton();

        /*Add the sub panel that has the controls, to this
        * Login Panel.*/
        constraints.gridx = 0;
        constraints.gridy = 0;
        addEmailValidationLabel();
        addControlsPanel();
    }

    /**
     * Add the email text field to this {@link #controlsPanel}.
     */
    private void addEmailTextField(){
        controlsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        JLabel emailLabel = new JLabel(GUIConstants.EMAIL_LABEL);
        emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        controlsPanel.add(emailLabel, controlsPanelConstraints);
        controlsPanelConstraints.gridx++;

        /*Allow detection of the 'Enter' key being pressed.*/
        emailField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread validateCallback = new Thread(() -> validateCallBack());
                validateCallback.start();
            }
        });
        controlsPanel.add(emailField, controlsPanelConstraints);

        /*Setup the next items defaults.*/
        controlsPanelConstraints.fill = GridBagConstraints.NONE;
        controlsPanelConstraints.gridx = 0;
        controlsPanelConstraints.gridy++;
    }

    /**
     * Add the validate email button and callback to this {@link #controlsPanel}.
     */
    private void addValidateEmailButton(){
        controlsPanelConstraints.gridwidth = 2;  //have button span two columns
        validateEmailButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread validateCallback = new Thread(() -> validateCallBack());
                validateCallback.start();
            }
        });
        controlsPanel.add(validateEmailButton, controlsPanelConstraints);

        /*Setup the next items defaults.*/
        controlsPanelConstraints.gridwidth = 1;
        controlsPanelConstraints.gridx = 0;
        controlsPanelConstraints.gridy++;
    }

    /**
     * Add email validation label to this panel.
     */
    private void addEmailValidationLabel(){
        constraints.gridy++;
        addComponent(validationLabel);
    }

    /**
     * Add controls panel to this panel.
     */
    private void addControlsPanel(){
        constraints.gridy++;
        addComponent(controlsPanel);
    }

}
