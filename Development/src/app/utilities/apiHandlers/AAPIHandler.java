package app.utilities.apiHandlers;

import app.Application;
import app.exception.BaseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * This abstract APIHandler is responsible for ensuring
 * each API functionality is the same on the surface for
 * the application when it executes methods on each
 * subclass of AAPIHandler.
 */
abstract class AAPIHandler implements IAPIHandler {

    /**Local static reference to the AAPIHandler that
     * handles the request from the application.*/
    static AAPIHandler instance = null;
    /**Reference to the Application to gain access to the settings file.*/
    static Application app;

    /**
     * Create a new {@link AAPIHandler}.
     * Retrieve the instance of the {@link Application}.
     */
    AAPIHandler(){
        app = Application.getInstance();
    }

    /**
     * Allows for a default execution of any request
     * and receiving of a return value.
     * {@inheritDoc}
     */
    @Override
    public Object executeAPIRequest(String request) throws BaseException {
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

        return returnValue;
    }
}
