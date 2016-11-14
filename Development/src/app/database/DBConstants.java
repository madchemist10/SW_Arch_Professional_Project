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
            "CREATE TABLE Customer_Balance(" +
                    "Cust_ID INT PRIMARY KEY NOT NULL," +
                    "Balance REAL NOT NULL," +
                    "FOREIGN KEY(Cust_ID) REFERENCES Customer_Information(Cust_ID))";

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
            "CREATE TABLE Transaction_History(" +
                    "Trade_ID INT PRIMARY KEY NOT NULL," +
                    "Cust_ID INT NOT NULL," +
                    "Type TEXT NOT NULL," +
                    "Ticker TEXT NOT NULL," +
                    "Shares INT NOT NULL," +
                    "Price REAL NOT NULL," +
                    "Company TEXT NOT NULL," +
                    "Exchange TEXT NOT NULL," +
                    "Date TEXT NOT NULL," +
                    "FOREIGN KEY(Cust_ID) REFERENCES Customer_Information(Cust_ID))";

    /**Makes Customer_Credentials Table*/
    public static final String DB_MAKE_CUSTOMER_CREDENTIALS =
            "CREATE TABLE Customer_Credentials(" +
                    "Cust_ID INT PRIMARY KEY NOT NULL," +
                    "User_ID INT NOT NULL," +
                    "Email TEXT NOT NULL," +
                    "Password TEXT NOT NULL," +
                    "FOREIGN KEY(Cust_ID) REFERENCES Customer_Information(Cust_ID))";

}