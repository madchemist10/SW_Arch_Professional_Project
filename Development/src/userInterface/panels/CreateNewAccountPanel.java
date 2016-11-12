package userInterface.panels;

import app.utilities.Utilities;
import userInterface.GUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This panel is required for creation of a new
 * user account.
 */
public class CreateNewAccountPanel extends BasePanel{

    /**Text field for user email input.*/
    private final JTextField emailField = new JTextField(20);
    /**Text field for user password input.*/
    private final JPasswordField passwordField = new JPasswordField(20);
    /**Button to attempt to create a new user for the application.*/
    private final JButton createButton = new JButton(GUIConstants.CREATE_BUTTON_TEXT);
    /**Inner panel for handling the controls for account creation.*/
    private final JPanel createAccountSubPanel = new JPanel();
    /**Constraints for the inner {@link #createAccountSubPanel}.*/
    private final GridBagConstraints subPanelConstraints = new GridBagConstraints();

    /**
     * Create a new {@link CreateNewAccountPanel}.
     */
    CreateNewAccountPanel(){
        super(GUIConstants.CREATE_ACCOUNT_PANEL_IDENTIFIER);
        /*Construct the panel.*/
        buildPanel();
    }

    /**
     * Allow the user interface to create a new account.
     * Callback for creating a new user from email and password.
     * @param email for the new user's unique identifier.
     * @param password of the new user's account.
     * @return true if create account was success, false otherwise.
     */
    private static boolean createAccount(String email, String password){
        return app.createAccount(email, password);
    }

    /**
     * Callback for when the create account button is pressed.
     * This should be run on a new thread.
     */
    private void createAccountCallBack(){
        String userEmail = emailField.getText();
        if(!validateUserInput(userEmail)){
            notifyListeners(new CustomChangeEvent(this, AppChangeEvents.EMAIL_INVALID));
            return;
        }
        String userPassword = new String(passwordField.getPassword());
        if(!validateUserInput(userPassword)){
            notifyListeners(new CustomChangeEvent(this, AppChangeEvents.PASSWORD_INVALID));
            return;
        }
        String hashedPassword = Utilities.getMD5Hash(new String(passwordField.getPassword()));
        if(createAccount(userEmail, hashedPassword)){
            /*Throw event for Account Created so the Panel Manager knows how to proceed.*/
            notifyListeners(new CustomChangeEvent(this, AppChangeEvents.ACCOUNT_CREATED));
        } else{
            /*Throw event for Account Creation Failed so the Panel Manager knows how to proceed.*/
            notifyListeners(new CustomChangeEvent(this, AppChangeEvents.ACCOUNT_CREATION_FAILED));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        /*Set defaults for Create Account Panel.*/
        createAccountSubPanel.setLayout(new GridBagLayout());
        subPanelConstraints.gridx = 0;
        subPanelConstraints.gridy = 0;
        subPanelConstraints.ipadx = 5;

        /*Add email Text field.
        * Update values for next element to be added.*/
        addEmailTextField();

        /*Add password Text field.
        * Update values for next element to be added.*/
        addPasswordTextField();

        /*Add login Button.
        * Update values for next element to be added.*/
        addCreateAccountButton();

        /*Add the sub panel that has the controls, to this
        * Create Account Panel.*/
        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(createAccountSubPanel);
    }

    /**
     * Add the email text field to this panel.
     * The Email Label is left aligned.
     */
    private void addEmailTextField(){
        subPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        JLabel emailLabel = new JLabel(GUIConstants.EMAIL_LABEL);
        emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        createAccountSubPanel.add(emailLabel, subPanelConstraints);
        subPanelConstraints.gridx++;

        /*Allow detection of the 'Enter' key being pressed.*/
        emailField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread loginCallback = new Thread(()-> createAccountCallBack());
                loginCallback.start();
            }
        });
        createAccountSubPanel.add(emailField, subPanelConstraints);

        /*Setup the next items defaults.*/
        subPanelConstraints.fill = GridBagConstraints.NONE;
        subPanelConstraints.gridx = 0;
        subPanelConstraints.gridy++;
    }

    /**
     * Add the password text field to this panel.
     * The Password Label is left aligned.
     */
    private void addPasswordTextField(){
        subPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        JLabel passwordLabel = new JLabel(GUIConstants.PASSWORD_LABEL);
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
        createAccountSubPanel.add(passwordLabel, subPanelConstraints);
        subPanelConstraints.gridx++;

        /*Allow detection of the 'Enter' key being pressed.*/
        passwordField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread loginCallback = new Thread(()-> createAccountCallBack());
                loginCallback.start();
            }
        });
        createAccountSubPanel.add(passwordField, subPanelConstraints);

        /*Setup the next items defaults.*/
        subPanelConstraints.fill = GridBagConstraints.NONE;
        subPanelConstraints.gridx = 0;
        subPanelConstraints.gridy++;
    }

    /**
     * Add the login button and callback to this panel.
     * The Create Account Button spans two grid columns.
     */
    private void addCreateAccountButton(){
        subPanelConstraints.gridwidth = 2;  //have button span two columns
        createButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread loginCallback = new Thread(()-> createAccountCallBack());
                loginCallback.start();
            }
        });
        createAccountSubPanel.add(createButton, subPanelConstraints);

        /*Setup the next items defaults.*/
        subPanelConstraints.gridwidth = 1;
        subPanelConstraints.gridx = 0;
        subPanelConstraints.gridy++;
    }
}
