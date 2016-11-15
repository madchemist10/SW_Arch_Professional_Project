package app.utilities.apiHandlers;

import app.constants.Constants;
import app.exception.BaseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * This MailBoxAPIHandler is used to interact with the Mailboxlayer.com API
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

        String returnValue;
        String API_URL = "http://apilayer.net/api/check?access_key=";
        String API_EMAIL = "&email=";

        //remove this line once the key is implemented
        String MAILBOX_API_ACCESS_TOKEN = app.getValueFromSettings(Constants.MAILBOX_API_ACCESS_TOKEN);

        if(MAILBOX_API_ACCESS_TOKEN == null){
            return null;
        }

        returnValue = API_URL + MAILBOX_API_ACCESS_TOKEN + API_EMAIL + inputs[0];

        return returnValue;
    }

    /**
     * Handles the execution of an API request to the Mailbox API.
     * The return type for this execution is {@link JsonNode}.
     * The calling method should cast the object returned from this
     * execution to {@link JsonNode}.
     * {@inheritDoc}
     */
    @Override
    public Object executeAPIRequest(String request) throws BaseException {
        Object superReturn =  super.executeAPIRequest(request);

        if (superReturn == null){
            return null;
        }

        String returnValue = null;

        if(superReturn instanceof String ){
            returnValue = (String) superReturn;
        }

        if(returnValue == null){
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = null;

        try {
            jsonNode = mapper.readValue(returnValue,JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonNode;
    }
}
