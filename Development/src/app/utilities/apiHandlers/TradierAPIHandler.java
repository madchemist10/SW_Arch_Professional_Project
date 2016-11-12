package app.utilities.apiHandlers;
import app.constants.Constants;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;


/**
 * This TradierAPIHandler is used to construct and execute queries
 * against the external Tradier API.
 */
class TradierAPIHandler extends AAPIHandler{

    private TradierAPIHandler(){
        super();
        getTradier();
    }

    private static JsonNode getTradier(){
            BufferedReader responseBody = null;
            JsonNode jsonObj = null;
            //build client
            HttpClient tradierClient = HttpClientBuilder.create().build();

        try {
            //Form request
            HttpGet tradierReq = new HttpGet("https://api.tradier.com/v1/user/profile");

            //Set headers
            tradierReq.addHeader("Accept", "application/json");
            tradierReq.addHeader("Authorization", Constants.TRADIER_API_ACCESS_TOKEN);

            //Invoke the service
            HttpResponse tradierResponse = tradierClient.execute(tradierReq);

            int statusCode = tradierResponse.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed with HTTP error code: " + statusCode);
            } else {
                responseBody = new BufferedReader(new InputStreamReader(tradierResponse.getEntity().getContent()));
                String line = "";
                StringBuilder tradierString = new StringBuilder();
                while ((line = responseBody.readLine()) != null){
                    tradierString.append(line);
                }
                ObjectMapper mapper = new ObjectMapper();
                jsonObj = mapper.readTree(tradierString.toString());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (responseBody != null){
                try{
                    responseBody.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return jsonObj;
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
        return null;
    }
}
