package app.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Populates a user object
 */
public class User {

    private final Portfolio portfolio;
    private final Map<String, String> userData;
    private final String custID;

    /**
     * Initializer for the user class to populate user elements
     * @param custID used to set the user's ID
     */
    public User(String custID){
        portfolio = new Portfolio();
        userData = new HashMap<>();
        this.custID = custID;
    }

    /**
     * Get this user's {@link Portfolio}.
     * @return Portfolio for this user.
     */
    public Portfolio getPortfolio(){
        return portfolio;
    }

    public void setPortfolio(ArrayList<String[]> userTransactions, ArrayList<String[]> userStocks){

    }
    /**
     * Get this user's user data.
     * @return Map of user data.
     */
    public Map<String, String> getUserData(){
        return userData;
    }

}
