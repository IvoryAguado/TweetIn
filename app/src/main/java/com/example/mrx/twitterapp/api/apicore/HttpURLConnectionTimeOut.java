package com.example.mrx.twitterapp.api.apicore;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit.client.Request;
import retrofit.client.UrlConnectionClient;

public final class HttpURLConnectionTimeOut extends UrlConnectionClient {

    @Override
    protected HttpURLConnection openConnection(@NonNull Request request) throws IOException {
        HttpURLConnection connection = super.openConnection(request);//TODO HTTPURLCONNECTION FOR THE CONNECTION AND PETITIONS TO THE API
        connection.setConnectTimeout(5 * 60 * 1000);
        connection.setReadTimeout(5 * 60 * 1000);
        return connection;
    }
}

