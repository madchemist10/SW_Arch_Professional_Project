package app.utilities.apiHandlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

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
        String MAILBOX_API_ACCESS_TOKEN = "7ee10ff559ad1c3520217a93599cf167";

        returnValue = API_URL + MAILBOX_API_ACCESS_TOKEN + API_EMAIL + inputs[0];

        return returnValue;
    }

    /**
     * Perform the request for a given url.
     * @param request that is to be queried.
     * @return String representation of the body of the return from the
     * get request.
     */

    @Override
    public Object executeAPIRequest(String request) {

        String returnValue = null;

        try {
            URL myURL = new URL(request);
            URLConnection connection = myURL.openConnection();
            InputStream inputStream = connection.getInputStream();
            String encoding = connection.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            returnValue =  IOUtils.toString(inputStream, encoding);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (returnValue == null){
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
