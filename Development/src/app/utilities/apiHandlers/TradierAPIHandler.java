package app.utilities.apiHandlers;

/**
 */
class TradierAPIHandler extends AAPIHandler{

    /**
     * Retrieve an instance of the TradierAPIHandler so that
     * the application may have access to the methods within
     * this class.
     * Instance is a singleton reference.
     * @return instance of this TradierAPIHandler.
     */
    static AAPIHandler getInstance() {
        if(instance == null){
            instance = new TradierAPIHandler();
        }
        return instance;
    }
}
