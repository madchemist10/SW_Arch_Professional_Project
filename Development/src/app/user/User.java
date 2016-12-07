package app.user;

import app.constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Populates a user object
 */
public class User {

    private final Portfolio portfolio;
    private final Map<String, String> userData;

    /**
     * Initializer for the user class to populate user elements
     */
    public User(){
        portfolio = new Portfolio();
        userData = new HashMap<>();
    }

    /**
     * Get this user's {@link Portfolio}.
     * @return Portfolio for this user.
     */
    public Portfolio getPortfolio(){
        return portfolio;
    }

    /**
     * Builds a user's portfolio from the passed in results of previous DB calls
     * @param userTransactions an array of string values resulting from a previous DB call
     * @param userStocks an array of string values resulting from a previous DB call
     */
    public void setPortfolio(ArrayList<String[]> userTransactions, ArrayList<String[]> userStocks){
        portfolio.setStocks(userStocks);
        portfolio.setTransactions(userTransactions);
    }

    /**
     * Get this user's user data.
     * @return Map of user data.
     */
    public Map<String, String> getUserData(){
        return userData;
    }

    /**
     * Sets the users account data
     * @param userData string of the user's ID and email
     * @param balance user's current account balance
     */
    public void setUserData(String[] userData, String[] balance){
        int i = 0;
        this.userData.put(Constants.USER_ID_KEY, userData[i++]);
        this.userData.put(Constants.USERNAME_LABEL_LABEL_KEY, userData[i]);
        this.userData.put(Constants.ACCOUNT_BALANCE_LABEL_KEY, balance[0]);
    }

    /**
     * Sets the user's total profit/loss amount
     * @param profitLoss the amount to set as the user's profit loss
     */
    public void setUserProfitLoss(String profitLoss){
        this.userData.put(Constants.TOTAL_PROFIT_LOSS_LABEL_KEY, profitLoss);
    }
}
