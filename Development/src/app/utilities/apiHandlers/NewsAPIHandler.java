package app.utilities.apiHandlers;

import app.constants.Constants;
import app.exception.BaseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This NewsAPIHandler is used to interact with the newsapi.org API
 */
class NewsAPIHandler extends AAPIHandler{

    /**Local static reference to the AAPIHandler that
     * handles the request from the application.*/
    private static AAPIHandler instance = null;
    private String body = null;

    /**
     * Retrieve an instance of the NewsAPIHandler so that
     * the application may have access to the methods within
     * this class.
     * Instance is a singleton reference.
     * @return instance of this NewsAPIHandler.
     */
    static AAPIHandler getInstance() {
        if(instance == null){
            instance = new NewsAPIHandler();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String buildAPIRequest(String[] inputs) {

        String apiURL = "http://api.ft.com/content/search/v1?apiKey=";
        String NEWS_API_ACCESS_TOKEN = app.getValueFromSettings(Constants.NEWS_API_ACCESS_TOKEN);
        body = "{\"queryString\": \"" + inputs[0] + "\", \"queryContext\": {\"curations\": [\"ARTICLES\"]}, \"resultContext\": {\"maxResults\": \"5\", \"aspects\": [\"title\"]}}";

        if(NEWS_API_ACCESS_TOKEN == null){
            return null;
        }

        //return apiURL;
        return apiURL + NEWS_API_ACCESS_TOKEN;
    }

    /**
     * Handles the execution of an API request to the News API.
     * The return type for this execution is {@link JsonNode}.
     * The calling method should cast the object returned from this
     * execution to {@link JsonNode}.
     * {@inheritDoc}
     */
    @Override
    public Object executeAPIRequest(String request) throws BaseException {

        String returnValue = null;

        try {

            URL myURL = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
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
