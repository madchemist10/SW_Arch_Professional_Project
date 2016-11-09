package app.utilities.apiHandlers;

/**
 * This APIHandler is responsible for handling requests
 * from the application for access to the external API systems.
 * The APIHandler class acts as an interface to the specific
 * APIHandlers used in this package.
 * This APIHandler uses a singleton access architecture.
 */
public class APIHandler {

    /**Local static reference to the APIHandler that
     * handles the request from the application.*/
    private static APIHandler instance = null;

    /**
     * Default constructor.
     * Private to defeat instantiation.
     */
    private APIHandler(){
        //cannot instantiate.
    }

    /**
     * Retrieve an instance of the APIHandler so that
     * the application may have access to the methods within
     * this class.
     * Instance is a singleton reference.
     * @return instance of this APIHandler.
     */
    public static APIHandler getInstance(){
        if(instance == null){
            instance = new APIHandler();
        }
        return instance;
    }

    /**
     * Retrieve a specific APIHandler.
     * @param apiHandle of which type of request to make.
     * @return a handler to the API that made a request.
     */
    public IAPIHandler getAPIHandler(APIHandles apiHandle){
        IAPIHandler iapiHandler = null;
        switch (apiHandle){
            case MAILBOX_LAYER:
                iapiHandler = MailBoxLayerAPIHandler.getInstance();
                break;
            case TRADIER:
                iapiHandler = TradierAPIHandler.getInstance();
                break;
            case TWITTER:
                iapiHandler = TwitterAPIHandler.getInstance();
                break;
            case NEWS:
                break;
        }
        /*Should never return null.*/
        return iapiHandler;
    }
}
