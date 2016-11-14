package app.utilities.apiHandlers;

import app.constants.Constants;
import app.exception.BaseException;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This TwitterAPIHandler is used to construct and execute queries
 * against the external Twitter API.
 */
class TwitterAPIHandler extends AAPIHandler{

    /**Local singleton reference to the twitter api library.*/
    private static Twitter twitter = null;

    /**
     * Create a new twitter APIHandler.
     * Ensure the creation of the Twitter singleton
     * from the Twitter API Library.
     */
    private TwitterAPIHandler(){
        super();
        getTwitter();
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
            instance = new TwitterAPIHandler();
        }
        return instance;
    }

    /**
     * Retrieve a singleton instance of Twitter.
     * @return instance of Twitter.
     */
    private static Twitter getTwitter(){
        if(twitter == null){
            /*Verify the keys and secrets are available from the settings.*/
            String consumerKey = app.getValueFromSettings(Constants.TWITTER_API_CONSUMER_KEY);
            String consumerSecret = app.getValueFromSettings(Constants.TWITTER_API_CONSUMER_SECRET);
            String accessToken = app.getValueFromSettings(Constants.TWITTER_API_ACCESS_TOKEN);
            String accessTokenSecret = app.getValueFromSettings(Constants.TWITTER_API_ACCESS_TOKEN_SECRET);
            if(consumerKey == null || consumerSecret == null || accessToken == null || accessTokenSecret == null){
                return null;
            }
            ConfigurationBuilder config = new ConfigurationBuilder();
            config.setDebugEnabled(true)
                    .setOAuthConsumerKey(consumerKey)
                    .setOAuthConsumerSecret(consumerSecret)
                    .setOAuthAccessToken(accessToken)
                    .setOAuthAccessTokenSecret(accessTokenSecret);
            TwitterFactory factory = new TwitterFactory(config.build());
            twitter = factory.getInstance();
        }
        return twitter;
    }

    /**
     * For the twitter API Request, we only need the first value
     * in the input as we are pulling this directly from the
     * user's input in the
     * {@link userInterface.panels.TwitterPanel#queryField}.
     * {@inheritDoc}
     */
    @Override
    public String buildAPIRequest(String[] inputs) {
        return inputs[0];
    }

    /**
     * Handles the execution of an API request to the Twitter API.
     * The return type for this execution is {@link QueryResult}.
     * The calling method should cast the object returned from this
     * execution to {@link QueryResult}.
     * The queryResult object contains a set of tweets received
     * by the query execution.
     * {@inheritDoc}
     */
    @Override
    public Object executeAPIRequest(String request) throws BaseException {
        if(twitter == null){
            return null;
        }
        QueryResult result = null;
        try {
            Query query = new Query(request);
            result = twitter.search(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
