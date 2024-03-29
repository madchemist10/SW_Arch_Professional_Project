package app.database;
import java.util.ArrayList;

/**
 * Use an the instance of this Database Manager for accessing
 * the database throughout the system.
 * The DatabaseConn is used to execute the queries onto the database.
 * This is stored as a static reference when the database is created.
 * Each call to the database requires the make connection to be called prior
 * to execution of the queries.
 */
public class DatabaseManager {

    /**Singleton instance of the database manager.*/
    private static DatabaseManager instance = null;

    /**Singleton instance of database connection. */
    private static DatabaseConn connection = null;

    /**Name of the DB that has been given to this manager.*/
    private String dbName;

    /**
     * Create a new {@link DatabaseManager}.
     */
    private DatabaseManager(String dbName){
        this.dbName = dbName;
    }

    /**
     * Retrieve the instance of this Database Manager,
     * if it does not exist, create it.
     * @param dbName database path for this manager.
     * @return instance of {@link DatabaseManager}
     */
    public static DatabaseManager getInstance(String dbName){
        if(instance == null){
            instance = new DatabaseManager(dbName);
        }
        if(connection == null) {
            connection = DatabaseConn.getInstance(instance.dbName);
        }
        return instance;
    }

    /**
     * Allow the user interface to validate a user login.
     * Callback for validating user login from email and password.
     * @param email for the user's unique identifier.
     * @param password of the user's account.
     * @return true if login is valid, false otherwise.
     */
    public String[] validateLogin(String email, String password){
        connection.makeConnection();

        String statement = DBStatementBuilder.selectStatement("*") +
                DBStatementBuilder.fromStatement(DBConstants.CUSTOMER_CREDENTIALS_TABLE);
                String where_statement = DBConstants.EMAIL + " = \""+email+"\" AND "+DBConstants.PASSWORD
                        +" = \""+password+"\"";
        statement += DBStatementBuilder.whereStatement(where_statement);

        ArrayList<String[]> returnVal = connection.selectFromTable(statement);

        if (returnVal.size() == 0){
            return null;
        }

        //assumes only one entry and that the one entry is the correct entry
        return returnVal.get(0);
    }

    /**
     * Execute the request for creation of a specific table
     * in the given database we are connected to.
     * @param statement create statement for execution on the connected
     *                  database.
     */
    public void executeCreateStatement(String statement){
        connection.makeConnection();
        connection.makeTable(statement);
    }

    /**
     * Insert's a set of credentials into the credential table
     * @param credentials string of credentials column values for insertion into Customer_Credentials table
     */
    public void insertCredentials(String credentials){
        connection.makeConnection();
        String values = DBStatementBuilder.valueStatement(credentials);
        String columns = DBConstants.EMAIL + ", " + DBConstants.PASSWORD;
        String statement = DBStatementBuilder.insertStatement(DBConstants.CUSTOMER_CREDENTIALS_TABLE, columns, values);
        connection.insertIntoTable(statement);
    }

    /**
     * Retrieves credentials of a provided customer ID
     * @param custID customer ID constraint on which table entry to return
     * @return array of customer credentials information
     */
    public ArrayList<String[]> getCredentials(int custID){
        connection.makeConnection();
        String statement = DBStatementBuilder.selectStatement("*") +
                DBStatementBuilder.fromStatement(DBConstants.CUSTOMER_CREDENTIALS_TABLE) +
                DBStatementBuilder.whereStatement(DBConstants.CUST_ID) +
                " = " + custID;
        return connection.selectFromTable(statement);
    }

    /**
     * Insert a customer balance for a new customer in the Customer_Balance table
     * @param balanceEntry string of customer balance column values for insertion into Customer_Balance table
     */
    public void insertCustomerBalance(String balanceEntry){
        connection.makeConnection();
        String values = DBStatementBuilder.valueStatement(balanceEntry);
        String columns = DBConstants.CUST_ID + " , " + DBConstants.BALANCE;
        String statement = DBStatementBuilder.insertStatement(DBConstants.CUSTOMER_BALANCE_TABLE, columns, values);
        connection.insertIntoTable(statement);
    }

    /**
     * Update customer balance in the database for a specific user
     * @param balance new balance to set in the Customer_Balance table
     */
    public void updateCustomerBalance(double balance, int custID){
        connection.makeConnection();
        String statement = DBStatementBuilder.updateStatement(DBConstants.CUSTOMER_BALANCE_TABLE) +
                DBStatementBuilder.setStatement(DBConstants.BALANCE) +
                " = " + balance + DBStatementBuilder.whereStatement(DBConstants.CUST_ID) + " = " + custID;
        connection.updateTableEntry(statement);
    }

    /**
     * Retrieve customer balance from the database for a specific user
     * @param custID customer ID constraint on which table entry to return
     * @return array of customer balance information
     */
    public ArrayList<String[]> getCustomerBalance(int custID){
        connection.makeConnection();
        String statement = DBStatementBuilder.selectStatement(DBConstants.BALANCE) +
                           DBStatementBuilder.fromStatement(DBConstants.CUSTOMER_BALANCE_TABLE) +
                           DBStatementBuilder.whereStatement(DBConstants.CUST_ID) +
                           " = " + custID;
        return connection.selectFromTable(statement);
    }

    /**
     * Insert a stock that the user owns into the Stock_Ownership tab
     * @param stock string of stock column values for insertion into the Stock_Ownership table
     */
    public void insertStockOwnership(String stock){
        connection.makeConnection();
        String values = DBStatementBuilder.valueStatement(stock);
        String columns = DBConstants.CUST_ID + ", " + DBConstants.TICKER + ", " + DBConstants.SHARES + ", " +
                DBConstants.PURCHASE_PRICE + ", " + DBConstants.COMPANY;
        String statement = DBStatementBuilder.insertStatement(DBConstants.STOCK_OWNERSHIP_TABLE, columns, values);
        connection.insertIntoTable(statement);
    }

    /**
     * Update stock ownership data for a specific user
     * @param custID customer ID constraint on which table entry to update
     * @param shares new quantity of shares in the appropriate entry in the Stock_Ownership table
     * @param ticker ticker constraint on which table entry to update
     * @param cost new average cost per share
     */
    public void updateStockOwnership(int custID, int shares, String ticker, double cost){
        connection.makeConnection();
        String statement = DBStatementBuilder.updateStatement(DBConstants.STOCK_OWNERSHIP_TABLE) +
                DBStatementBuilder.setStatement(DBConstants.SHARES) +
                " = " + shares + ", " + DBConstants.PURCHASE_PRICE + " = " + cost +
                DBStatementBuilder.whereStatement(DBConstants.CUST_ID) +
                " = " + custID + " AND " + DBConstants.TICKER + " = \"" + ticker + "\"";
        connection.updateTableEntry(statement);
    }

    /**
     * Retrieve stock ownership data for a specific user
     * @param custID customer ID constraint on which table entry to return
     * @return array of stock ownership data
     */
    public ArrayList<String[]> getStockOwnership(int custID){
        connection.makeConnection();
        String statement = DBStatementBuilder.selectStatement("*") +
                DBStatementBuilder.fromStatement(DBConstants.STOCK_OWNERSHIP_TABLE) +
                DBStatementBuilder.whereStatement(DBConstants.CUST_ID) +
                " = " + custID;
        return connection.selectFromTable(statement);
    }

    /**
     * Retrieve stock data from a given owner.
     * @param custID customer ID constraint on which table entry to return
     * @param ticker ticker constraint on which table entry to update
     * @return list of stock data. (Should be one string[])
     */
    public ArrayList<String[]> getStockFromOwner(int custID, String ticker){
        connection.makeConnection();
        String statement = DBStatementBuilder.selectStatement("*") +
                DBStatementBuilder.fromStatement(DBConstants.STOCK_OWNERSHIP_TABLE) +
                DBStatementBuilder.whereStatement(DBConstants.CUST_ID) +
                " = " + custID + " AND "+ DBConstants.TICKER +" = \"" + ticker + "\"";
        return connection.selectFromTable(statement);
    }

    /**
     * Insert a buy/sell transaction into the transaction table
     * @param transaction string of transaction column values for insertion into Transaction_History table
     */
    public void insertTransaction(String transaction){
        connection.makeConnection();
        String values = DBStatementBuilder.valueStatement(transaction);
        String columns = DBConstants.CUST_ID + ", " + DBConstants.TYPE + ", " + DBConstants.TICKER + ", " +
                DBConstants.SHARES + ", " + DBConstants.TRANS_COST + ", " + DBConstants.COMPANY + ", " +
                DBConstants.NEW_BALANCE + ", " + DBConstants.TIMESTAMP;
        String statement = DBStatementBuilder.insertStatement(DBConstants.TRANSACTION_HISTORY_TABLE, columns, values);
        connection.insertIntoTable(statement);
    }

    /**
     * Retrieve the transaction history from the Transaction_History table
     * @param custID customer ID constraint on which table entries to return
     * @return array of transaction history
     */
    public ArrayList<String[]> getTransactionHistory(int custID){
        connection.makeConnection();
        String statement = DBStatementBuilder.selectStatement("*") +
                           DBStatementBuilder.fromStatement(DBConstants.TRANSACTION_HISTORY_TABLE) +
                           DBStatementBuilder.whereStatement(DBConstants.CUST_ID) +
                           " = " + custID;
        return connection.selectFromTable(statement);
    }
}
