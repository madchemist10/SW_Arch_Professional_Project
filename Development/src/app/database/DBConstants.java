package app.database;

/**
 */
public class DBConstants {

    /**Makes Customer_Balance Table*/
    public static final String DB_MAKE_CUSTOMER_BALANCE =
            "CREATE TABLE IF NOT EXISTS Customer_Balance(" +
                    "Cust_ID INT PRIMARY KEY NOT NULL," +
                    "Balance REAL NOT NULL," +
                    "FOREIGN KEY(Cust_ID) REFERENCES Customer_Credentials(Cust_ID))";

    /**Makes Transaction_History Table*/
    public static final String DB_MAKE_TRANSACTION_HISTORY =
            "CREATE TABLE IF NOT EXISTS Transaction_History(" +
                    "Trade_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Cust_ID INT NOT NULL," +
                    "Type TEXT NOT NULL," +
                    "Ticker TEXT NOT NULL," +
                    "Shares INT NOT NULL," +
                    "Trans_Cost REAL NOT NULL," +
                    "Company TEXT NOT NULL," +
                    "New_Balance TEXT NOT NULL," +
                    "Timestamp TEXT NOT NULL," +
                    "FOREIGN KEY(Cust_ID) REFERENCES Customer_Credentials(Cust_ID))";

    /**Makes Customer_Credentials Table*/
    public static final String DB_MAKE_CUSTOMER_CREDENTIALS =
            "CREATE TABLE IF NOT EXISTS Customer_Credentials(" +
                    "Cust_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Email TEXT NOT NULL," +
                    "Password TEXT NOT NULL)";

    /**Makes Stock_Ownership Table*/
    public static final String DB_MAKE_STOCK_OWNERSHIP =
            "CREATE TABLE IF NOT EXISTS Stock_Ownership(" +
                    "Ownership_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Cust_ID INT NOT NULL," +
                    "Ticker TEXT NOT NULL," +
                    "Shares INT NOT NULL," +
                    "Purchase_Price REAL NOT NULL," +
                    "Company TEXT NOT NULL," +
                    "Exchange TEXT NOT NULL," +
                    "FOREIGN KEY(Cust_ID) REFERENCES Customer_Credentials(Cust_ID))";

    /**Database table names*/
    public static final String STOCK_OWNERSHIP_TABLE = "Stock_Ownership";
    public static final String TRANSACTION_HISTORY_TABLE = "Transaction_History";
    public static final String CUSTOMER_CREDENTIALS_TABLE = "Customer_Credentials";
    public static final String CUSTOMER_BALANCE_TABLE = "Customer_Balance";

    /**Database table column names*/
    public static final String BALANCE = "Balance";
    public static final String CUST_ID = "Cust_ID";
    public static final String SHARES = "Shares";
    public static final String TYPE = "Type";
    public static final String TICKER = "Ticker";
    public static final String TRANS_COST = "Trans_Cost";
    public static final String NEW_BALANCE = "New_Balance";
    public static final String COMPANY = "Company";
    public static final String EXCHANGE = "Exchange";
    public static final String TIMESTAMP = "Timestamp";
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Password";
    public static final String PURCHASE_PRICE = "Purchase_Price";
}