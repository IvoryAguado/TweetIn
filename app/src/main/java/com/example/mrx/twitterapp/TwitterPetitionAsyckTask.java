package com.example.mrx.twitterapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.internal.oauth.OAuth1aHeaders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class TwitterPetitionAsyckTask extends AsyncTask<String, Void, String> {

    List<String> addStringTweet = new LinkedList<>();
    private AbsListView listViewToUpdate;
    private Context context;

    public TwitterPetitionAsyckTask(Context _context) {
        context = _context;
    }

    public String getJSON(String address) {
        StringBuilder builder = new StringBuilder();
        HttpURLConnection client = null;
        OAuth1aHeaders oAuth1aHeaders = new OAuth1aHeaders();

        TwitterAuthToken authToken = Twitter.getSessionManager().getActiveSession().getAuthToken();

        try {

            String header = oAuth1aHeaders.getAuthorizationHeader(new TwitterAuthConfig(BuildConfig.TWITTER_CONSUMER_KEY, BuildConfig.TWITTER_CONSUMER_SECRET), authToken, null, "GET", address, null);

            client = (HttpURLConnection) new URL(address).openConnection();

            client.setRequestProperty("Authorization", header);

            Log.i(getClass().getName(), "Authorization: " + header);

            client.connect();

            if (client.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    JSONArray ja = new JSONArray(line);

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);
                        addStringTweet.add(jo.getString("text"));
                    }

                }
            } else {
                return client.getResponseMessage();
            }

            builder.append(client.getResponseCode());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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
        listViewToUpdate.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, addStringTweet));
    }

    public void setListViewToUpdate(AbsListView listViewToUpdate) {
        this.listViewToUpdate = listViewToUpdate;
    }
}
