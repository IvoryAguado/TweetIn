package com.example.mrx.twitterapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class TwitterPetitionAsyckTask extends AsyncTask<Void, Void, String> {

    private Context context;

    public TwitterPetitionAsyckTask(Context _context) {
        context = _context;
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

    public String getJSON(String address) {
        StringBuilder builder = new StringBuilder();
        HttpURLConnection client = null;
        try {
            client = (HttpURLConnection) new URL("https://api.twitter.com/1.1/statuses/home_timeline.json").openConnection();

            String charset = "UTF-8";

            /**
             * get the time - note: value below zero
             * the millisecond value is used for oauth_nonce later on
             */
            int millis = (int) System.currentTimeMillis() * -1;
            int time = millis / 1000;

            /**
             * Listing of all parameters necessary to retrieve a token
             * (sorted lexicographically as demanded)
             */
            String[][] data = {
                    //{"oauth_callback", "SOME_URL"},
                    {"oauth_consumer_key", BuildConfig.TWITTER_CONSUMER_KEY},
                    {"oauth_nonce", String.valueOf(millis)},
                    {"oauth_signature", ""},
                    {"oauth_signature_method", "HMAC-SHA1"},
                    {"oauth_timestamp", String.valueOf(time)},
                    {"oauth_version", "1.0"}
            };

            /**
             * Generation of the signature base string
             */
            String signature_base_string =
                    "POST&" + URLEncoder.encode(address, "UTF-8") + "&";
            for (int i = 0; i < data.length; i++) {
                // ignore the empty oauth_signature field
                if (i != 3) {
                    signature_base_string +=
                            URLEncoder.encode(data[i][0], "UTF-8") + "%3D" +
                                    URLEncoder.encode(data[i][1], "UTF-8") + "%26";
                }
            }
            // cut the last appended %26
            signature_base_string = signature_base_string.substring(0,
                    signature_base_string.length() - 3);


            /**
             * Sign the request
             */
            Mac m = Mac.getInstance("HmacSHA1");
            m.init(new SecretKeySpec(BuildConfig.TWITTER_CONSUMER_KEY.getBytes(), "HmacSHA1"));
            m.update(signature_base_string.getBytes());
            byte[] res = m.doFinal();
            String sig = String.valueOf(Base64.encodeToString(res, Base64.DEFAULT));
            data[3][1] = sig;

            /**
             * Create the header for the request
             */
            String header = "OAuth ";
            for (String[] item : data) {
                header += item[0] + "=\"" + item[1] + "\", ";
            }
            // cut off last appended comma
            header = header.substring(0, header.length() - 2);


//            client.setRequestProperty("Accept-Charset", charset);
//            client.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
//            client.setRequestProperty("Authorization", header);
            client.setRequestProperty("Authorization", "OAuth oauth_consumer_key=\"DC0sePOBbQ8bYdC8r4Smg\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1446838596\",oauth_nonce=\"-93644592\",oauth_version=\"1.0\",oauth_token=\"284904967-5zgHwMTv8MR5SHXQmDVhztba66GTt2n4DVGjgAHF\",oauth_signature=\"K5mwC4YLCtyZz%2BDWVxlXYW493cU%3D\"Host:api.twitter.com");

            Log.i(getClass().getName(), "Authorization Header: " + client.toString());

            client.connect();

            if (client.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(MainActivity.class.toString(), "Failedet JSON object");
            }

            builder.append(client.getResponseCode());


        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    @Override
    protected String doInBackground(Void... params) {
        return getJSON("https://api.twitter.com/1.1/statuses/mentions_timeline.json");
    }

    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);
        new AlertDialog.Builder(context).setMessage("JSON: " + json).show();//.setMessage(session.getUserName() + " \n" + session.getUserId() + " \n" + session.getAuthToken().secret + "\n" + session.getAuthToken().token + "\n " + session.getAuthToken().isExpired() + " \n" + session.getAuthToken().describeContents()).show();
    }

}
