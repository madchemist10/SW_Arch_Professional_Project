package app.database;

/**
 */
public class DBConstants {

    /**Makes Customer_Information Table*/
    public static final String DB_MAKE_CUSTOMER_INFORMATION =
            "CREATE TABLE Customer_Information (" +
                    "Cust_ID INT PRIMARY KEY NOT NULL, " +
                    "First_Name TEXT NOT NULL," +
                    "Middle_Name TEXT," +
                    "Last_Name TEXT NOT NULL,"+
                    "DOB TEXT NOT NULL," +
                    "SSN INT NOT NULL," +
                    "Gender TEXT NOT NULL," +
                    "Address TEXT NOT NULL," +
                    "City TEXT NOT NULL," +
                    "State TEXT," +
                    "Zip TEXT," +
                    "Country TEXT NOT NULL)";

    /**Makes Customer_Balance Table*/
    public static final String DB_MAKE_CUSTOMER_BALANCE =
            "CREATE TABLE IF NOT EXISTS Customer_Balance(" +
                    "Cust_ID INT PRIMARY KEY NOT NULL," +
                    "Balance REAL NOT NULL," +
                    "FOREIGN KEY(Cust_ID) REFERENCES Customer_Credentials(Cust_ID))";

    /**Makes Funds_History Table*/
    public static final String DB_MAKE_FUNDS_HISTORY =
            "CREATE TABLE Funds_History(" +
                    "Cust_ID INT PRIMARY KEY NOT NULL," +
                    "Amount REAL NOT NULL," +
                    "Time TEXT NOT NULL," +
                    "FOREIGN KEY(Cust_ID) REFERENCES Customer_Information(Cust_ID))";

    /**Makes Access_Logs Table*/
    public static final String DB_MAKE_ACCESS_LOGS =
            "CREATE TABLE Access_Logs(" +
                    "Log_ID INT PRIMARY KEY NOT NULL," +
                    "Cust_ID INT NOT NULL," +
                    "Time TEXT NOT NULL," +
                    "FOREIGN KEY(Cust_ID) REFERENCES Customer_Information(Cust_ID))";

    /**Makes Transaction_History Table*/
    public static final String DB_MAKE_TRANSACTION_HISTORY =
            "CREATE TABLE IF NOT EXISTS Transaction_History(" +
                    "Trade_ID INT PRIMARY KEY NOT NULL AUTOINCREMENT," +
                    "Cust_ID INT NOT NULL," +
                    "Type TEXT NOT NULL," +
                    "Ticker TEXT NOT NULL," +
                    "Shares INT NOT NULL" +
                    "Price REAL NOT NULL," +
                    "Company TEXT NOT NULL," +
                    "Exchange TEXT NOT NULL," +
                    "Date TEXT NOT NULL," +
                    "Time TEXT NOT NULL," +
                    "FOREIGN KEY(Cust_ID) REFERENCES Customer_Information(Cust_ID))";

    /**Makes Customer_Credentials Table*/
    public static final String DB_MAKE_CUSTOMER_CREDENTIALS =
            "CREATE TABLE IF NOT EXISTS Customer_Credentials(" +
                    "Cust_ID INT PRIMARY KEY NOT NULL AUTOINCREMENT," +
                    "Email TEXT NOT NULL," +
                    "Password TEXT NOT NULL," +
                    "FOREIGN KEY(Cust_ID) REFERENCES Customer_Information(Cust_ID))";

    /**Makes Stock_Ownership Table*/
    public static final String DB_MAKE_STOCK_OWNERSHIP =
            "CREATE TABLE IF NOT EXISTS Stock_Ownership(" +
                    "Ownership_ID INT PRIMARY KEY NOT NULL AUTOINCREMENT," +
                    "Cust_ID INT NOT NULL," +
                    "Ticker TEXT NOT NULL," +
                    "Shares INT NOT NULL," +
                    "Purchase_Price REAL NOT NULL," +
                    "Company TEXT NOT NULL," +
                    "Exchange TEXT NOT NULL," +
                    "FOREIGN KEY(Cust_ID) REFERENCES Customer_Information(Cust_ID))";

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
    public static final String PRICE = "Price";
    public static final String COMPANY = "Company";
    public static final String EXCHANGE = "Exchange";
    public static final String DATE = "Date";
    public static final String TIME = "Time";
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Password";
    public static final String PURCHASE_PRICE = "Purchase_Price";
}