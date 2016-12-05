package app.constants;

/**
 * Constants that are used throughout the application.
 */
public class Constants {

    /*Twitter API Access Credentials*/
    /**Consumer key for the Twitter API Calls.*/
    public static final String TWITTER_API_CONSUMER_KEY =
            "TWITTER_API_CONSUMER_KEY";

    /**Consumer secret for the Twitter API Calls.*/
    public static final String TWITTER_API_CONSUMER_SECRET =
            "TWITTER_API_CONSUMER_SECRET";

    /**Access token for the Twitter API Calls.*/
    public static final String TWITTER_API_ACCESS_TOKEN =
            "TWITTER_API_ACCESS_TOKEN";

    /**Access token secret for the Twitter API Calls.*/
    public static final String TWITTER_API_ACCESS_TOKEN_SECRET =
            "TWITTER_API_ACCESS_TOKEN_SECRET";

    /**Access token for Tradier API Calls.*/
    public static final String TRADIER_API_ACCESS_TOKEN =
            "TRADIER_API_ACCESS_TOKEN";

    /**Access token for the MailboxLayer API Calls.*/
    public static final String MAILBOX_API_ACCESS_TOKEN =
            "MAILBOX_API_ACCESS_TOKEN";

    /**Access token for the News API Calls.*/
    public static final String NEWS_API_ACCESS_TOKEN =
            "NEWS_API_ACCESS_TOKEN";

    /**Identifier for MD5 algorithm.*/
    public static final String MD5_ALGORITHM = "MD5";

    /**Local path for the settings file.*/
    public static final String SETTINGS_FILE = "settings.txt";

    /*Mailbox JsonNode Constants*/
    /**Json Node key for valid format.*/
    public static final String FORMAT_VALID = "format_valid";

    /*Tradier JsonNode Constants*/
    /**Json Node key for getting all the quotes from a request.*/
    public static final String QUOTES = "quotes";
    /**Json Node key for getting the last price.*/
    public static final String LAST = "last";
    /**Json Node key for getting symbol of the quote.*/
    public static final String SYMBOL = "symbol";
    /**Json Node key for getting the daily change value.*/
    public static final String CHANGE = "change";
    /**Json Node key for getting volume of the quote.*/
    public static final String VOLUME = "volume";

    /*News JsonNode Constants*/
    /**Json Node key for getting title of the quote.*/
    public static final String TITLE = "title";
    /**Json Node key for getting articles of the quote.*/
    public static final String ARTICLES = "articles";


    /**Local path for the database file.*/
    public static final String DB_FILE = "tradeNet.db";

    /*Stock Entry Panel label keys for pulling from stock data map.*/
    /**Key value for Stock name*/
    public static final String STOCK_NAME_LABEL_KEY = "STOCK_NAME_LABEL_KEY";
    /**Key value for Stocks owned*/
    public static final String STOCKS_OWNED_LABEL_KEY = "STOCKS_OWNED_LABEL_KEY";
    /**Key value for Purchased value*/
    public static final String PURCHASED_VALUE_LABEL_KEY = "PURCHASED_VALUE_LABEL_KEY";
    /**Key value for Company name*/
    public static final String COMPANY_NAME_LABEL_KEY = "COMPANY_NAME_LABEL_KEY";
    /**Key value for Exchange name*/
    public static final String EXCHANGE_NAME_LABEL_KEY = "EXCHANGE_NAME_LABEL_KEY";
    /**Key value for Current value*/
    public static final String CURRENT_VALUE_LABEL_KEY = "CURRENT_VALUE_LABEL_KEY";
    /**Key value for Profit lost*/
    public static final String PROFIT_LOST_LABEL_KEY = "PROFIT_LOST_LABEL_KEY";

    /*Transaction History Entry Panel label keys for pulling from transaction data map.*/
    /**Key value for trade type*/
    public static final String TRADE_TYPE_LABEL_KEY = "TRADE_TYPE_LABEL_KEY";
    /**Key value for ticker*/
    public static final String TICKER_LABEL_KEY = "TICKER_LABEL_KEY";
    /**Key value for share qty*/
    public static final String SHARE_QTY_LABEL_KEY = "SHARE_QTY_LABEL_KEY";
    /**Key value for transaction cost*/
    public static final String TRANSACTION_COST_LABEL_KEY = "TRANSACTION_COST_LABEL_KEY";
    /**Key value for new balance*/
    public static final String NEW_BALANCE_LABEL_KEY = "NEW_BALANCE_LABEL_KEY";
    /**Key value for timestamp*/
    public static final String TIMESTAMP_LABEL_KEY = "TIMESTAMP_LABEL_KEY";
    /**Key value for trade item*/
    public static final String TRADE_ITEM_LABEL_KEY = "TRADE_ITEM_LABEL_KEY";

    /*User Data Panel label keys for pulling from user data map.*/
    /**Key value for user ID*/
    public static final String USER_ID_KEY = "USER_ID_KEY";
    /**Key value for username*/
    public static final String USERNAME_LABEL_LABEL_KEY = "USERNAME_LABEL_LABEL_KEY";
    /**Key value for account balance*/
    public static final String ACCOUNT_BALANCE_LABEL_KEY = "ACCOUNT_BALANCE_LABEL_KEY";
    /**Key value for total profit lost*/
    public static final String TOTAL_PROFIT_LOSS_LABEL_KEY = "TOTAL_PROFIT_LOSS_LABEL_KEY";

    /*Date*/
    /**SimpleDateFormat string*/
    public static final String SIMPLE_DATE_FORMAT_STRING = "MMMMM dd yyyy HH:mm:ss";
}
