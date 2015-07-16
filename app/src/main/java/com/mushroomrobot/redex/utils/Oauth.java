package com.mushroomrobot.redex.utils;

/**
 * Created by NLam.
 */
public class Oauth {

    public static final String OAUTH_CLIENTID = "aDcKr4ajR9PXgQ";
    public static final String OAUTH_RESPONSETYPE = "code";
    public static String OAUTH_STATE = "redex" + String.valueOf((int)(Math.random()*(20000)));
    public static final String OAUTH_REDIRECTURI = "http://www.mushroomrobot.com";
    public static final String OAUTH_DURATION = "permanent";
    public static final String OAUTH_SCOPE = "identity,edit,flair,history,mysubreddits,privatemessages,read,report,save,submit,subscribe,vote,wikiedit,wikiread";

    public static String OAUTH_URL = "https://www.reddit.com/api/v1/authorize.compact?client_id=" + OAUTH_CLIENTID +
            "&response_type=" + OAUTH_RESPONSETYPE +
            "&state=" + OAUTH_STATE +
            "&redirect_uri=" + OAUTH_REDIRECTURI +
            "&duration=" + OAUTH_DURATION +
            "&scope=" + OAUTH_SCOPE;

    // https://www.reddit.com/api/v1/authorize?client_id=aDcKr4ajR9PXgQ&response_type=code&state=ran1234567popcorn&redirect_uri=http://www.mushroomrobot.com&duration=permanent&scope=identity,edit,flair,history,mysubreddits,privatemessages,read,report,save,submit,subscribe,vote,wikiedit,wikiread


}
