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

    /*Captcha API Access Credentials*/
    /**Access site key for Captcha API Calls.*/
    public static final String CAPTCHA_API_SITE_KEY = "CAPTCHA_API_SITE_KEY";

    /**Access secret key for Captcha API Calls.*/
    public static final String CAPTCHA_API_SECRET_KEY = "CAPTCHA_API_SECRET_KEY";

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
}
