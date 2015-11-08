package com.example.mrx.twitterapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.mrx.twitterapp.R;
import com.example.mrx.twitterapp.api.TwitterPetitionAsyckTask;
import com.example.mrx.twitterapp.utils.UIUtils;

import io.fabric.sdk.android.services.network.HttpMethod;

/**
 * Tweet In Send Screen
 * <p/>
 * This will present the users Twitter “Timeline”, including the tweets and retweets posted by the
 * <p/>
 * authenticated user and the people their follow.
 */

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class TweetInFragment extends Fragment implements AbsListView.OnItemClickListener {

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TweetInFragment() {
    }

    @NonNull
    public static TweetInFragment newInstance() {
        TweetInFragment fragment = new TweetInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tweet_send, container, false);
        view.findViewById(R.id.senTw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TwitterPetitionAsyckTask(getActivity()).execute("https://api.twitter.com/1.1/statuses/update.json?status=" + TextView.class
                        .cast(view.findViewById(R.id.twetText)).getText().toString().replace(" ", "%20"), HttpMethod.POST.name());
                TextView.class
                        .cast(view.findViewById(R.id.twetText)).setText("");
                UIUtils.toastThis(getActivity(), "Tweet Tweet!");
            }
        });
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String id);
    }

}
