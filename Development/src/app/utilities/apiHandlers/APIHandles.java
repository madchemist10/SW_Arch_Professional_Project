package app.utilities.apiHandlers;

/**
 * This enum represents all possible APIHandlers
 * that can be used by the system. The enum values
 * from this enum are used for triggering on which
 * API calls to make from the {@link APIHandler} class.
 */
public enum APIHandles {

    /**Gives access to the MailboxLayer API.*/
    MAILBOX_LAYER,

    /**Gives access to the Tradier API.*/
    TRADIER,

    /**Gives access to the Twitter API.*/
    TWITTER
}
