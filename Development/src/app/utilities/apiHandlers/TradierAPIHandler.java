package app.utilities.apiHandlers;

//package org.apache.commons.io.IOUtils;
import java.util.*;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * This TradierAPIHandler is used to construct and execute queries
 * against the external Tradier API.
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String buildAPIRequest(String[] inputs) {
        return null;
    }
}
