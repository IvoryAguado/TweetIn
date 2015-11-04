package com.example.mrx.twitterapp.api.apicore;

import android.support.annotation.NonNull;


import com.example.mrx.twitterapp.BuildConfig;

import io.fabric.sdk.android.Fabric;
import retrofit.Callback;
import retrofit.RetrofitError;

public abstract class RestApiCallback<T> implements Callback<T> {
    public abstract void failure(RestError restError);


    @Override
    public void failure(@NonNull RetrofitError error) {
        try {
            RestError restError = (RestError) error.getBodyAs(RestError.class);

            if (restError != null)
                failure(restError);
            else {
                failure(new RestError(error.getMessage()));
            }
        } catch (Exception conversionException) {
            failure(new RestError("Error in the connection (500)"));
            if (!BuildConfig.DEBUG)
                Fabric.getLogger().e("REST_API","Error in the connection (500)");
        }
    }
}