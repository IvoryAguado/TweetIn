package com.example.mrx.twitterapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrX on 26/09/2015.
 */
public class StoreManager {

    public final static String PREFF_TWEET_IN = "PREFF_TWEET_IN";

    public final static String USERNAME = "USERNAME";
    public final static String EMAIL = "EMAIL";
    public final static String PHOTO = "PHOTO";
    private static final String SECRET_TOKEN = "SECRET_TOKEN";
    private static final String TOKEN_AUTH = "TOKEN_AUTH";

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    private Context context;

    public StoreManager(@NonNull Context _context) {
        context = _context;
        settings = _context.getSharedPreferences(EncryptionSuite.encrypt(PREFF_TWEET_IN), Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public void saveUserName(String username) {
        editor.putString(USERNAME, username).commit();
    }

    public void savePassword(String pass) {
        editor.putString(USERNAME, pass).commit();
    }

    public void saveEmail(String email) {
        editor.putString(EMAIL, email).commit();
    }

    public void saveCredentials(String token, String tokenSecret) {
        editor.putString(SECRET_TOKEN, tokenSecret).commit();
        editor.putString(TOKEN_AUTH, token).commit();
    }

    /**************************************************************************************************/

    public String getUserName() {
        return settings.getString(USERNAME, null);
    }

    public String getPassword() {
        return settings.getString(USERNAME, null);
    }

    public String getEmail() {
        return settings.getString(EMAIL, null);
    }

    public Map<String, String> getCredentials() {
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put(SECRET_TOKEN, settings.getString(SECRET_TOKEN, null));
        stringStringMap.put(TOKEN_AUTH, settings.getString(TOKEN_AUTH, null));
        return stringStringMap;
    }

    public void clearA5ll() {
        editor.clear().commit();
    }
}
