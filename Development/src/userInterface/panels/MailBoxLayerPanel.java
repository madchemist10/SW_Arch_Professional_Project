package userInterface.panels;

import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
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

    /**
     * Create a new {@link MailBoxLayerPanel}.
     */
    MailBoxLayerPanel() {
        super(GUIConstants.MAILBOX_PANEL_IDENTIFIER);
        buildPanel();
    }

    /**
     * Callback for when the validate button is pressed.
     * This should be run on a new thread.
     */
    private void validateCallBack(){
        String query = emailField.getText();
        IAPIHandler mailboxAPI = app.getAPIHandler(APIHandles.MAILBOX_LAYER);
        String request = mailboxAPI.buildAPIRequest(new String[]{query});
        Object returnVal = mailboxAPI.executeAPIRequest(request);
        //todo need to do something with return value.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        controlsPanelConstraints.gridx = 0;
        controlsPanelConstraints.gridy = 0;
        addEmailField();
        controlsPanelConstraints.gridy++;
        addValidateEmailButton();
        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(controlsPanel);
    }

    /**
     * Add the email text field to this {@link #controlsPanel}.
     */
    private void addEmailField(){
        controlsPanel.add(emailField, controlsPanelConstraints);
    }

    /**
     * Add the validate email button and callback to this {@link #controlsPanel}.
     */
    private void addValidateEmailButton(){
        validateEmailButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread validateCallback = new Thread(() -> validateCallBack());
                validateCallback.start();
            }
        });
        controlsPanel.add(validateEmailButton, controlsPanelConstraints);
    }

}
