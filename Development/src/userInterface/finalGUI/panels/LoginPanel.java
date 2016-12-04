package userInterface.finalGUI.panels;


import app.constants.Constants;
import app.utilities.Utilities;
import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.MessageDigest;

/**
 * This panel is required to be displayed first so that
 * the user can login to the application.
 */
class LoginPanel extends BasePanel{

    /**Text field for user email input.*/
    private final JTextField emailField = new JTextField(20);
    /**Text field for user password input.*/
    private final JPasswordField passwordField = new JPasswordField(20);
    /**Button to attempt to log the user in to the application.*/
    private final JButton loginButton = new JButton(TradeNetGUIConstants.LOGIN_BUTTON_TEXT);
    /**Button to attempt to log the user in to the application.*/
    private final JButton createAccountButton = new JButton(TradeNetGUIConstants.CREATE_ACCOUNT_BUTTON_TEXT);
    /**Inner panel for handling the controls for login.*/
    private final JPanel loginSubPanel = new JPanel();
    /**Constraints for the inner {@link #loginSubPanel}.*/
    private final GridBagConstraints subPanelConstraints = new GridBagConstraints();

    /**
     * Create a new {@link LoginPanel}.
     */
    LoginPanel(){
        super(TradeNetGUIConstants.LOGIN_PANEL_IDENTIFIER);
        /*Construct the panel.*/
        buildPanel();
    }

    /**
     * Callback for validating user login form email and password.
     * @param email for the user's unique identifier.
     * @param password of the user's account.
     * @return true if login is valid, false otherwise.
     */
    private static boolean validateLogin(String email, String password){
        return app.loginUser(email, password);
    }

    /**
     * Callback for when the login button is pressed.
     * This should be run on a new thread.
     */
    private void loginCallBack(){
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
        String hashedPassword = getMD5Hash(new String(passwordField.getPassword()));
        if(validateLogin(userEmail, hashedPassword)){
            /*Throw event for Login Success so the Panel Manager knows how to proceed.*/
            notifyListeners(new CustomChangeEvent(this, AppChangeEvents.LOGIN_SUCCESS));
        } else{
            /*Throw event for Login Fail so the Panel Manager knows how to proceed.*/
            notifyListeners(new CustomChangeEvent(this, AppChangeEvents.LOGIN_FAIL));
        }
    }

    /**
     * Callback for when the create account button is pressed.
     * This should be run on a new thread.
     */
    private void createAccountCallBack(){
        notifyListeners(new CustomChangeEvent(this, AppChangeEvents.CREATE_ACCOUNT));
    }

    /**
     * Generate an MD5 hash for a given word.
     * Used for {@link #passwordField}.
     * @param word that is to have md5 hash generated.
     * @return md5 hash of the given word.
     */
    private static String getMD5Hash(String word){
        String md5Hash = "";
        try {
            MessageDigest digest = MessageDigest.getInstance(Constants.MD5_ALGORITHM);
            md5Hash = new String(digest.digest(word.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel(){
        /*Set defaults for Login Panel.*/
        loginSubPanel.setLayout(new GridBagLayout());
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
        addLoginButton();

        /*Add create account button.
        * Update values for next element to be added.*/
        addCreateAccountButton();

        /*Add the sub panel that has the controls, to this
        * Login Panel.*/
        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(loginSubPanel);
    }

    /**
     * Add the email text field to this panel.
     * The Email Label is left aligned.
     */
    private void addEmailTextField(){
        subPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        JLabel emailLabel = new JLabel(TradeNetGUIConstants.EMAIL_LABEL);
        emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        loginSubPanel.add(emailLabel, subPanelConstraints);
        subPanelConstraints.gridx++;

        /*Allow detection of the 'Enter' key being pressed.*/
        emailField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread loginCallback = new Thread(()-> loginCallBack());
                loginCallback.start();
            }
        });
        loginSubPanel.add(emailField, subPanelConstraints);

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
        JLabel passwordLabel = new JLabel(TradeNetGUIConstants.PASSWORD_LABEL);
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
        loginSubPanel.add(passwordLabel, subPanelConstraints);
        subPanelConstraints.gridx++;

        /*Allow detection of the 'Enter' key being pressed.*/
        passwordField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread loginCallback = new Thread(()-> loginCallBack());
                loginCallback.start();
            }
        });
        loginSubPanel.add(passwordField, subPanelConstraints);

        /*Setup the next items defaults.*/
        subPanelConstraints.fill = GridBagConstraints.NONE;
        subPanelConstraints.gridx = 0;
        subPanelConstraints.gridy++;
    }

    /**
     * Add the login button and callback to this panel.
     * The Login Button spans two grid columns.
     */
    private void addLoginButton(){
        subPanelConstraints.gridwidth = 2;  //have button span two columns
        subPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        loginButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread loginCallback = new Thread(()-> loginCallBack());
                loginCallback.start();
            }
        });
        loginSubPanel.add(loginButton, subPanelConstraints);

        /*Setup the next items defaults.*/
        subPanelConstraints.fill = GridBagConstraints.NONE;
        subPanelConstraints.gridwidth = 1;
        subPanelConstraints.gridx = 0;
        subPanelConstraints.gridy++;
    }

    /**
     * Add the create account button and callback to this panel.
     * The Create Account Button spans two grid columns.
     */
    private void addCreateAccountButton(){
        subPanelConstraints.gridwidth = 2;  //have button span two columns
        subPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        createAccountButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Spawn background thread to keep from locking up the GUI.*/
                Thread loginCallback = new Thread(()-> createAccountCallBack());
                loginCallback.start();
            }
        });
        loginSubPanel.add(createAccountButton, subPanelConstraints);

        /*Setup the next items defaults.*/
        subPanelConstraints.fill = GridBagConstraints.NONE;
        subPanelConstraints.gridwidth = 1;
        subPanelConstraints.gridx = 0;
        subPanelConstraints.gridy++;
    }
}
