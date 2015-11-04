package com.example.mrx.twitterapp.api;

import android.content.Context;

import com.example.mrx.twitterapp.BuildConfig;
import com.example.mrx.twitterapp.api.apicore.ItemTypeAdapterFactory;
import com.example.mrx.twitterapp.api.apicore.SessionRequestInterceptor;
import com.example.mrx.twitterapp.api.apicore.HttpURLConnectionTimeOut;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by ivor.aguado on 27/08/2015.
 */
public class RESTAPIClient {

    private TweetInRestAPI apiService;
    private RestAdapter restAdapter;


    public RESTAPIClient(Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory()) // This is the important line ;)
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setEndpoint(BuildConfig.REST_API_URL)
                .setClient(new HttpURLConnectionTimeOut())
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(new SessionRequestInterceptor(context)) // This is the important line to check if user is logged and has permissions to speak to API;)
                .build();

        apiService = restAdapter.create(TweetInRestAPI.class);
    }


    public TweetInRestAPI getApiService() {
        return apiService;
    }

}
