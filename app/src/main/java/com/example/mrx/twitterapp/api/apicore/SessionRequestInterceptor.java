package com.example.mrx.twitterapp.api.apicore;

import android.content.Context;
import android.support.annotation.NonNull;

import retrofit.RequestInterceptor;


/**
 * You just have to check if a user session is opened and add in this
 * case your credentials to the header. So create a file called SessionRequestInterceptor.java
 * and your class has to implement RequestInterceptor and then you have to implement the intercept method.
 */
public class SessionRequestInterceptor implements RequestInterceptor {

    private static final String TWITTER_KEY = "S5Cygwj0DfzU0p96xVlABJrkV";
    private static final String TWITTER_SECRET = "RngIjOSEqLM4BvdEK62GHLnUXx5uhb7jCGrdCY549VtF1KApRR";
    private Context context;
    public SessionRequestInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public void intercept(@NonNull RequestFacade request) {
        request.addHeader("oauth_access_token", "application/json");
        request.addHeader("oauth_access_token_secret", "application/json");
        request.addHeader("consumer_key", "application/json");
        request.addHeader("consumer_secret", "application/json");
    }

    //TODO temporal method for testing to check if user is logged to make API Petitions
    public boolean isLoggedIn() {
        return false;
    }
}
