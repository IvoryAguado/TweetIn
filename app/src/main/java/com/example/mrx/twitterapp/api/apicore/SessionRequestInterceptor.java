package com.example.mrx.twitterapp.api.apicore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.innovacorp.profound.utils.StoreManager;

import retrofit.RequestInterceptor;


/**
 * You just have to check if a user session is opened and add in this
 * case your credentials to the header. So create a file called SessionRequestInterceptor.java
 * and your class has to implement RequestInterceptor and then you have to implement the intercept method.
 */
public class SessionRequestInterceptor implements RequestInterceptor {

    private Context context;

    public SessionRequestInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public void intercept(@NonNull RequestFacade request) {
        if (apiKey != null) {
            request.addHeader("Authorization", "Basic " + apiKey);
            request.addHeader("Content-Type", "application/json");
        }
        Log.i(getClass().getName(), "CLIENT_API_KEY_FOR_CONNECTION= " + apiKey);
    }

    //TODO temporal method for testing to check if user is logged to make API Petitions
    public boolean isLoggedIn() {
        return false;
    }
}
