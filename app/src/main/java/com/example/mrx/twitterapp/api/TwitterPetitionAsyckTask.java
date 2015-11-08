package com.example.mrx.twitterapp.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import com.example.mrx.twitterapp.BuildConfig;
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

import io.fabric.sdk.android.services.network.HttpMethod;

public class TwitterPetitionAsyckTask extends AsyncTask<String, Void, String> {

    @NonNull
    private List<String> addStringTweet = new LinkedList<>();
    private AbsListView listViewToUpdate;
    private Context context;
    private ProgressDialog waitDialog;
    private HttpURLConnection client = null;
    private String httpMethod;


    public TwitterPetitionAsyckTask(Context _context) {
        context = _context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        waitDialog = new ProgressDialog(context);
        waitDialog.setMessage("Loading... Please wait");
        waitDialog.setIndeterminate(true);
        waitDialog.setCancelable(false);
        waitDialog.show();
    }

    public String getJSON(@NonNull String address) {
        StringBuilder builder = new StringBuilder();

        OAuth1aHeaders oAuth1aHeaders = new OAuth1aHeaders();

        TwitterAuthToken authToken = Twitter.getSessionManager().getActiveSession().getAuthToken();

        try {
            //Create Signature String for the header OAuth, I been having a lot of problems creating the signature as I couldnt generate it from scratch in the Commits you can see a procress when I was trying to implement it.
            String header = oAuth1aHeaders.getAuthorizationHeader(new TwitterAuthConfig(BuildConfig.TWITTER_CONSUMER_KEY, BuildConfig.TWITTER_CONSUMER_SECRET), authToken, null, httpMethod, address, null);

            client = (HttpURLConnection) new URL(address).openConnection();

            client.setRequestProperty("Authorization", header);

            Log.i(getClass().getName(), "Authorization: " + header);

            client.setRequestMethod(httpMethod);

            client.connect();

            if (client.getResponseCode() == HttpURLConnection.HTTP_OK) {
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
                if (httpMethod.equalsIgnoreCase(HttpMethod.POST.name())) {
                    new AlertDialog.Builder(context).setMessage(addStringTweet.toString()).show();
                }
            } else {
                return client.getResponseMessage();
            }

            builder.append(client.getResponseCode());

        } catch (IOException | JSONException e) {
        } finally {
            client.disconnect();
        }
        return builder.toString();
    }

    @Override
    protected String doInBackground(String... params) {
        httpMethod = params[1];
        return getJSON(params[0]);
    }

    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);
        if (listViewToUpdate != null) {
            listViewToUpdate.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, addStringTweet));
        }
        if (waitDialog != null && waitDialog.isShowing()) {
            waitDialog.dismiss();
        }
    }

    public void setListViewToUpdate(AbsListView listViewToUpdate) {
        this.listViewToUpdate = listViewToUpdate;
    }
}
