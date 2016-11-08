package app.utilities.apiHandlers;

/**
 */
class TwitterAPIHandler extends AAPIHandler{

    /**
     * Retrieve an instance of the TwitterAPIHandler so that
     * the application may have access to the methods within
     * this class.
     * Instance is a singleton reference.
     * @return instance of this TwitterAPIHandler.
     */
    static AAPIHandler getInstance() {
        if(instance == null){
            instance = new TwitterAPIHandler();
        }
        return instance;
    }
}
