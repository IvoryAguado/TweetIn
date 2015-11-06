package com.example.mrx.twitterapp.api;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.example.mrx.twitterapp.BuildConfig;
import com.example.mrx.twitterapp.api.apicore.HttpURLConnectionTimeOut;
import com.example.mrx.twitterapp.api.apicore.ItemTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

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
                .setConverter(new Converter() {
                    @Override
                    public Object fromBody(TypedInput body, Type type) throws ConversionException {
                        return "";
                    }

                    @Override
                    public TypedOutput toBody(Object object) {
                        return null;
                    }
                })
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {

                        String auth = createSignature(Twitter.getSessionManager().getActiveSession());
                        request.addHeader("Authorization", auth);
                        Log.i("Authorization", auth);
                    }
                }) // This is the important line to check if user is logged and has permissions to speak to API;)
                .build();

        apiService = restAdapter.create(TweetInRestAPI.class);
    }

    public static String sha1(String s, String keyString) throws
            UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");

        mac.init(key);
        byte[] bytes = mac.doFinal(s.getBytes("UTF-8"));

        return Base64.encodeToString(bytes, Base64.URL_SAFE);
    }

    public String createSignature(TwitterSession session) {
        byte[] b = new byte[32];
        new Random().nextBytes(b);
        String randomBytes = null;

        try {
            randomBytes = URLEncoder.encode(String.valueOf(b), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("encoding error", e.getMessage());
        }

        long currentTimeInMillis = System.currentTimeMillis();
        TwitterAuthToken authToken = session.getAuthToken();
        String token = session.getAuthToken().token;
        String signature = String.format("oauth_consumer_key=%s&oauth_nonce=%s&oauth_signature_method=HMAC-SHA1&oauth_timestamp=%s&oauth_token=%s&oauth_version=1.0", BuildConfig.TWITTER_CONSUMER_KEY, randomBytes, currentTimeInMillis, token);
        String finalSignature = null;
        try {
            finalSignature = sha1(signature, BuildConfig.TWITTER_CONSUMER_SECRET + "&" + authToken.secret);
        } catch (UnsupportedEncodingException e) {
            Log.e("encoding error", e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Log.e("algorithm error", e.getMessage());
        } catch (InvalidKeyException e) {
            Log.e("key error", e.getMessage());
        }

        String header = String.format("OAuth oauth_consumer_key=\"%s\", oauth_nonce=\"%s\", oauth_signature=\"%s\", " +
                "oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"%s\", oauth_token=\"%s\", oauth_version=\"1.0\")", BuildConfig.TWITTER_CONSUMER_KEY, randomBytes, finalSignature, currentTimeInMillis, token);
        Log.i("header", header);

        return header;
    }

    public TweetInRestAPI getApiService() {
        return apiService;
    }

}
