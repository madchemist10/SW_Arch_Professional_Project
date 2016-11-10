package userInterface.panels;


import userInterface.GUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This panel is required to be displayed first so that
 * the user can login to the application.
 */
class LoginPanel extends BasePanel{

    /**Text field for user email input.*/
    private final JTextField emailField = new JTextField(20);
    /**Text field for user password input.*/
    private final JTextField passwordField = new JTextField(20);
    /**Button to attempt to log the user in to the application.*/
    private final JButton loginButton = new JButton(GUIConstants.LOGIN_BUTTON_TEXT);

    private final JPanel loginSubPanel = new JPanel();

    private final GridBagConstraints subPanelConstraints = new GridBagConstraints();

    /**
     * Create a new {@link LoginPanel}.
     */
    LoginPanel(){
        super(GUIConstants.LOGIN_PANEL_IDENTIFIER);
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
        return app.validateLogin(email, password);
    }

    /**
     * Callback for when the login button is pressed.
     */
    private void loginCallBack(){
        String userEmail = emailField.getText();
        String userPassword = passwordField.getText();
        if(validateLogin(userEmail, userPassword)){
            notifyListeners(new CustomChangeEvent(this, AppChangeEvents.LOGIN_SUCCESS));
        } else{
            notifyListeners(new CustomChangeEvent(this, AppChangeEvents.LOGIN_FAIL));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel(){
        loginSubPanel.setLayout(new GridBagLayout());
        subPanelConstraints.gridx = 0;
        subPanelConstraints.gridy = 0;
        addEmailTextField();
        subPanelConstraints.gridy++;
        addPasswordTextField();
        subPanelConstraints.gridy++;
        addLoginButton();
        addComponent(loginSubPanel);
    }

    /**
     * Add the email text field to this panel.
     */
    private void addEmailTextField(){
        loginSubPanel.add(emailField, subPanelConstraints);
    }

    /**
     * Add the password text field to this panel.
     */
    private void addPasswordTextField(){
        loginSubPanel.add(passwordField, subPanelConstraints);
    }

    /**
     * Add the login button and callback to this panel.
     */
    private void addLoginButton(){
        loginButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginCallBack();
            }
        });
        loginSubPanel.add(loginButton, subPanelConstraints);
    }
}
