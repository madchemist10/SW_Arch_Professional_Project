package app.utilities.apiHandlers;

import app.constants.Constants;
import app.exception.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * This TradierAPIHandler is used to construct and execute queries
 * against the external Tradier API.
 */
class TradierAPIHandler extends AAPIHandler{

    /**Local static reference to the AAPIHandler that
     * handles the request from the application.*/
    private static AAPIHandler instance = null;

    /**
     * Create a new tradier APIHandler.
     */
    private TradierAPIHandler(){
        super();
    }

    /**
     * Retrieve an instance of the TradierAPIHandler so that
     * the application may have access to the methods within
     * this class.
     * Instance is a singleton reference.
     * @return instance of this TradierAPIHandler.
     */
    static AAPIHandler getInstance(){
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
        return "https://sandbox.tradier.com/v1/markets/quotes?symbols=" + inputs[0];
    }

    /**
     * Handles the execution of an API request to the Tradier API.
     * The return type for this execution is {@link JsonNode}.
     * The calling method should cast the object returned from this
     * execution to {@link JsonNode}.
     * {@inheritDoc}
     */
    @Override
    public Object executeAPIRequest(String request) throws BaseException {
        //String tradierAPIToken = app.getValueFromSettings(Constants.TRADIER_API_ACCESS_TOKEN);
        String tradierAPIToken = "DkosD1KKwNn8ifLJdyigHjfA2fhy";
        if(tradierAPIToken == null){
            return null;
        }
        BufferedReader responseBody = null;
        JsonNode jsonObj = null;

        try {
            //Form request
            URL tradeNetURL = new URL(request);

            //Open connection and set headers
            HttpURLConnection con = (HttpURLConnection)tradeNetURL.openConnection();
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + tradierAPIToken);

            //Check response
            int statusCode = con.getResponseCode();
            if (statusCode != 200) {
                /*Throw custom Tradier Exceptions for error codes.*/
                switch(statusCode){
                    case 404:
                        throw new Tradier404Exception();
                    case 400:
                        throw new Tradier400Exception();
                    case 401:
                        throw new Tradier401Exception();
                    case 501:
                        throw new Tradier501Exception();
                    case 500:
                        throw new Tradier500Exception();
                    default:
                        throw new TradierException(statusCode);
                }
            } else {
                responseBody = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuilder tradierString = new StringBuilder();
                while ((line = responseBody.readLine()) != null){
                    tradierString.append(line);
                }
                ObjectMapper mapper = new ObjectMapper();
                jsonObj = mapper.readTree(tradierString.toString());
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            if (responseBody != null){
                try{
                    responseBody.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return jsonObj;

    }
}
