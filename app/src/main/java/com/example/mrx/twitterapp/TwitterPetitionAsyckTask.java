package com.example.mrx.twitterapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.internal.oauth.OAuth1aHeaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class TwitterPetitionAsyckTask extends AsyncTask<String, Void, String> {

    private static final String HMAC_SHA1 = "HMAC-SHA1";

    private Context context;
    private String oauth_consumer_key;
    private String oauth_timestamp;
    private String oauth_signature_method;
    private String oauth_nonce;
    private String oauth_version;
    private String oauth_token;
    private String oauth_signature;

    public TwitterPetitionAsyckTask(Context _context) {
        context = _context;
    }

    /**
     * Generates an OAuth 1.0 signature
     *
     * @param httpMethod  The HTTP method of the request
     * @param url         The request URL
     * @param oauthParams The associative set of signable oAuth parameters
     * @param requestBody The serialized POST/PUT message body
     * @param secret      Alphanumeric string used to validate the identity of the education partner (Private Key)
     * @return A string containing the Base64-encoded signature digest
     * @throws UnsupportedEncodingException
     */
    private static String generateSignature(
            String httpMethod,
            URL url,
            Map<String, String> oauthParams,
            byte[] requestBody,
            String secret
    ) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
        // Ensure the HTTP Method is upper-cased
        httpMethod.toUpperCase();

        // Construct the URL-encoded OAuth parameter portion of the signature base string
        String encodedParams = normalizeParams(httpMethod, url, oauthParams, requestBody);

        // URL-encode the relative URL
        String encodedUri = URLEncoder.encode(url.getPath(), "UTF-8");

        // Build the signature base string to be signed with the Consumer Secret
        String baseString = String.format("%s&%s&%s", httpMethod, encodedUri, encodedParams);

        return sha1(secret, baseString);
    }

    /**
     * Normalizes all OAuth signable parameters and url query parameters according to OAuth 1.0
     *
     * @param httpMethod  The upper-cased HTTP method
     * @param url         The request URL
     * @param oauthParams The associative set of signable oAuth parameters
     * @param requestBody The serialized POST/PUT message body
     * @return A string containing normalized and encoded oAuth parameters
     * @throws UnsupportedEncodingException
     */
    private static String normalizeParams(
            String httpMethod,
            URL url,
            Map<String, String> oauthParams,
            byte[] requestBody
    ) throws UnsupportedEncodingException {

        // Use a new LinkedHashMap for the OAuth signature creation
        Map<String, String> kvpParams = new LinkedHashMap<String, String>();
        kvpParams.putAll(oauthParams);

        // Place any query string parameters into a key value pair using equals ("=") to mark
        // the key/value relationship and join each parameter with an ampersand ("&")
        if (url.getQuery() != null) {
            for (String keyValue : url.getQuery().split("&")) {
                String[] p = keyValue.split("=");
                kvpParams.put(URLEncoder.encode(p[0], "UTF-8"), URLEncoder.encode(p[1], "UTF-8"));
            }

        }

        // Include the body parameter if dealing with a POST or PUT request
        if ("POST".equals(httpMethod) || "PUT".equals(httpMethod)) {
            String body = Base64.encodeToString(requestBody, Base64.DEFAULT).replaceAll("\r\n", "");
            body = URLEncoder.encode(body, "UTF-8");
            kvpParams.put("body", URLEncoder.encode(body, "UTF-8"));
        }

        // Sort the parameters in lexicographical order, 1st by Key then by Value; separate with ("=")
        TreeMap<String, String> sortedParams = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        sortedParams.putAll(kvpParams);

        // Remove unwanted characters and replace the comma delimiter with an ampersand
        String stringParams = sortedParams.toString().replaceAll("[{} ]", "");
        stringParams = stringParams.replace(",", "&");

        // URL-encode the equals ("%3D") and ampersand ("%26")
        String encodedParams = URLEncoder.encode(stringParams, "UTF-8");

        return encodedParams;
    }

    public static String sha1(String s, String keyString) throws
            UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");

        mac.init(key);
        byte[] bytes = mac.doFinal(s.getBytes("UTF-8"));

        return Base64.encodeToString(bytes, Base64.URL_SAFE);
    }

    public String getJSON(String address) {
        StringBuilder builder = new StringBuilder();
        HttpURLConnection client = null;


        try {
            TwitterAuthToken authToken = Twitter.getSessionManager().getActiveSession().getAuthToken();
            long currentMillis = System.currentTimeMillis();

            oauth_consumer_key = BuildConfig.TWITTER_CONSUMER_KEY;
            oauth_nonce = String.valueOf(currentMillis * -1).substring(4);
            oauth_timestamp = String.valueOf(currentMillis / 1000);
            oauth_signature_method = HMAC_SHA1;
            oauth_token = authToken.token;
            oauth_version = "1.0";

//            String header = String.format("OAuth oauth_consumer_key=\"%s\", oauth_nonce=\"%s\", oauth_signature=\"%s\", " +
//                    "oauth_signature_method=\"" + HMAC_SHA1 + "\", oauth_timestamp=\"%s\", oauth_token=\"%s\", oauth_version=\"1.0\")", oauth_consumer_key, oauth_nonce, oauth_signature, oauth_timestamp, authToken.secret);

//            Log.i("header", header);

            Map<String, String> aparams = new LinkedHashMap<>();

            aparams.put("oauth_consumer_key", oauth_consumer_key);
            aparams.put("oauth_signature_method", oauth_signature_method);
            aparams.put("oauth_timestamp", oauth_timestamp);
            aparams.put("oauth_nonce", oauth_nonce);
            aparams.put("oauth_version", oauth_version);
            aparams.put("oauth_token", oauth_token);
            aparams.put("oauth_consumer_key", oauth_consumer_key);

            StringBuilder stringBuilderHeader = new StringBuilder("OAuth ");

//            int i = 0;
//            for (Map.Entry entry : aparams.entrySet()) {
//                if (i != 0) {
//                    stringBuilderHeader.append(",");
//                }
//                i++;
//                stringBuilderHeader.append(entry.getKey() + "=\"" + entry.getValue() + "\"");
//
//            }
//
            oauth_signature = generateSignature("GET", new URL(address), aparams, null, authToken.secret);

//            stringBuilderHeader.append(",oauth_signature=\"" + oauth_signature + "\"");
            stringBuilderHeader.append(" " +
                    "oauth_consumer_key=\"" + oauth_consumer_key + "\"" +
                    ", oauth_nonce=\"" + oauth_nonce + "\"" +
                    ", oauth_signature=\"" + oauth_signature + "\"" +
                    ", oauth_signature_method=\"HMAC-SHA1\"" +
                    ", oauth_timestamp=\"" + currentMillis / 1000 + "\"" +
                    ", oauth_token=\"" + authToken.token + "\"" +
                    ", oauth_version=\"1.0\"" +
                    "");

            Log.i(getClass().getName(), "Authorization: " + stringBuilderHeader.toString());

            OAuth1aHeaders
                    oAuth1aHeaders = new OAuth1aHeaders();


            client = (HttpURLConnection) new URL(address).openConnection();


            client.setRequestProperty("Authorization", oAuth1aHeaders.getAuthorizationHeader(new TwitterAuthConfig(BuildConfig.TWITTER_CONSUMER_KEY, BuildConfig.TWITTER_CONSUMER_SECRET), authToken, null, "GET", address, null));

            for (Map.Entry s : client.getHeaderFields().entrySet()) {
                Log.i(getClass().getName(), s.getKey() + ": " + s.getValue().toString());
            }

            client.connect();

            if (client.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(getClass().getName(), "Error: " + client.getResponseMessage());
            }

            builder.append(client.getResponseCode());

        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    @Override
    protected String doInBackground(String... params) {
        return getJSON(params[0]);
    }

    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);
        new AlertDialog.Builder(context).setMessage("JSON: " + json).show();//.setMessage(session.getUserName() + " \n" + session.getUserId() + " \n" + session.getAuthToken().secret + "\n" + session.getAuthToken().token + "\n " + session.getAuthToken().isExpired() + " \n" + session.getAuthToken().describeContents()).show();
    }
}
