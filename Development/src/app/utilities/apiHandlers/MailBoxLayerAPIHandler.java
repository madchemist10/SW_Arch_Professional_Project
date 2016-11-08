package app.utilities.apiHandlers;

/**
 */
class MailBoxLayerAPIHandler extends AAPIHandler{

    /**
     * Retrieve an instance of the MailBoxLayerAPIHandler so that
     * the application may have access to the methods within
     * this class.
     * Instance is a singleton reference.
     * @return instance of this MailBoxLayerAPIHandler.
     */
    static AAPIHandler getInstance() {
        if(instance == null){
            instance = new MailBoxLayerAPIHandler();
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
}
