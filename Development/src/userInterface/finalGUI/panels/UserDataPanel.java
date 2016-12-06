package userInterface.finalGUI.panels;

import app.constants.Constants;
import userInterface.finalGUI.TradeNetGUIConstants;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

/**
 * Panel that contains all the user data.
 */
public class UserDataPanel extends BasePanel{

    /**Label to hold the username data.*/
    private final JLabel usernameDataLabel = new JLabel();
    /**Label to hold the total profit lost data.*/
    private final JLabel totalProfitLostLabel = new JLabel();
    /**Label to hold the account balance data data.*/
    private final JLabel accountBalanceLabel = new JLabel();
    /**Sub panel that contains the labels and data for the user.*/
    private final JPanel subPanel = new JPanel();
    /**Constraints for label placement within the sub panel.*/
    private final GridBagConstraints subPanelConstants = new GridBagConstraints();

    /**
     * Create and initialize a new UerDataPanel.
     * There should only be a new panel created when a new user
     * has logged into the application.
     */
    UserDataPanel(){
        super(TradeNetGUIConstants.USER_PANEL_IDENTIFIER);
        buildPanel();
        initializePanel();
        //execute timer to handle data updates
        Timer userDataTimer = new Timer();
        //schedule task to run every 10 seconds.
        userDataTimer.scheduleAtFixedRate(new UpdateUserDataTimerTask(this), 0, 10*1000);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        subPanel.setLayout(new GridBagLayout());
        subPanelConstants.gridx = 0;
        subPanelConstants.gridy = 0;
        subPanelConstants.fill = GridBagConstraints.HORIZONTAL;
        add(new BasicFlowPanel(subPanel));
    }

    /**
     * Set up the default panel of the user data panel and add
     * all the supporting labels.
     */
    private void initializePanel(){
        /*Construct the Description Labels*/
        JLabel usernameLabel = new JLabel(TradeNetGUIConstants.USERNAME_LABEL);
        JLabel totalProfitLabel = new JLabel(TradeNetGUIConstants.TOTAL_PROFIT_LABEL);
        JLabel accountBalanceLabel = new JLabel(TradeNetGUIConstants.ACCOUNT_BALANCE_LABEL);

        /*Wrap the Description Labels in Basic Left Justified Panels*/
        BasicFlowPanel usernamePanel = new BasicFlowPanel(usernameLabel);
        BasicFlowPanel totalProfitPanel = new BasicFlowPanel(totalProfitLabel);
        BasicFlowPanel accountBalancePanel = new BasicFlowPanel(accountBalanceLabel);

        /*Wrap the Data Labels in Basic Left Justified Panels*/
        BasicFlowPanel usernameDataPanel = new BasicFlowPanel(this.usernameDataLabel);
        BasicFlowPanel totalProfitDataPanel = new BasicFlowPanel(this.totalProfitLostLabel);
        BasicFlowPanel accountBalanceDataPanel = new BasicFlowPanel(this.accountBalanceLabel);

        /*Add Ticker Labels*/
        subPanelConstants.gridy++;
        subPanel.add(usernamePanel, subPanelConstants);
        subPanelConstants.gridx++;
        subPanel.add(usernameDataPanel, subPanelConstants);
        subPanelConstants.gridx = 0;

        /*Add Last Price Labels*/
        subPanelConstants.gridy++;
        subPanel.add(totalProfitPanel, subPanelConstants);
        subPanelConstants.gridx++;
        subPanel.add(totalProfitDataPanel, subPanelConstants);
        subPanelConstants.gridx = 0;

        /*Add Daily Net Change Labels*/
        subPanelConstants.gridy++;
        subPanel.add(accountBalancePanel, subPanelConstants);
        subPanelConstants.gridx++;
        subPanel.add(accountBalanceDataPanel, subPanelConstants);
        subPanelConstants.gridx = 0;
    }

    /**
     * Update the user data for the data labels.
     * @param userData map of use data from the current logged
     *                 in user object.
     */
    void updateLabels(Map<String, String> userData){
        /*Assign each entry to the specified labels*/
        String username = userData.get(Constants.USERNAME_LABEL_LABEL_KEY);
        String totalProfitLost = userData.get(Constants.TOTAL_PROFIT_LOSS_LABEL_KEY);
        String accountBalance = userData.get(Constants.ACCOUNT_BALANCE_LABEL_KEY);
        if(username == null || totalProfitLost == null || accountBalance == null){
            return;
        }
        usernameDataLabel.setText(username);
        totalProfitLostLabel.setText(totalProfitLost);
        accountBalanceLabel.setText(accountBalance);
    }

    /**
     * Update user data on a timer.
     * Accounts for when the user account balance or
     * profit lost changes.
     */
    private static class UpdateUserDataTimerTask extends TimerTask{

        private final UserDataPanel dataPanel;

        UpdateUserDataTimerTask(UserDataPanel dataPanel){
            this.dataPanel = dataPanel;
        }

        @Override
        public void run() {
            final Map<String, String> userData = app.getUserData();
            if(userData == null){
                //user is not logged in
                return;
            }
            /*Spawn background thread to keep from locking up the GUI.*/
            Thread helperThread = new Thread(() -> dataPanel.updateLabels(userData));
            helperThread.start();
        }
    }
}
