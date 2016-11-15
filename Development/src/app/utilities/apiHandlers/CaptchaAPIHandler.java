package app.utilities.apiHandlers;

/**
 */
class CaptchaAPIHandler extends AAPIHandler {

    private CaptchaAPIHandler(){
        super();
    }

    /**
     * Retrieve an instance of the TwitterAPIHandler so that
     * the application may have access to the methods within
     * this class.
     * Instance is a singleton reference.
     * @return instance of this TwitterAPIHandler.
     */
    static AAPIHandler getInstance() {
        if(instance == null){
            instance = new CaptchaAPIHandler();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String buildAPIRequest(String[] inputs) {
        return null;
    }

    /**
     * Return value is of type {@link java.awt.image.BufferedImage}.
     * Must be cast to be used.
     * {@inheritDoc}
     */
    @Override
    public Object executeAPIRequest(String request){
        return null;
    }
}
