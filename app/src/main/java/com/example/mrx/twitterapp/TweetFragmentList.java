package com.example.mrx.twitterapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.LinkedList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class TweetFragmentList extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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
    public TweetFragmentList() {
    }

    // TODO: Rename and change types of parameters
    public static TweetFragmentList newInstance(String param1, String param2) {
        TweetFragmentList fragment = new TweetFragmentList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tweet, container, false);

        // Set the adapter
        Twitter.getApiClient(Twitter.getSessionManager().getActiveSession()).getStatusesService().homeTimeline(500, null, null, false, false, false, false, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                List<String> tweetsStrings = new LinkedList<String>();
                for (Tweet t : result.data) {
                    tweetsStrings.add(t.text);
                }
                mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, tweetsStrings);
                mListView = (AbsListView) view.findViewById(android.R.id.list);
                mListView.setAdapter(mAdapter);
                mListView.setOnItemClickListener(TweetFragmentList.this);

            }

            @Override
            public void failure(TwitterException e) {

            }
        });

        Twitter.getApiClient(Twitter.getSessionManager().getActiveSession()).getStatusesService().homeTimeline(500, null, null, false, false, false, false, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                List<String> tweetsStrings = new LinkedList<String>();
                for (Tweet t : result.data) {
                    tweetsStrings.add(t.text);
                }
                mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, tweetsStrings);
                mListView = (AbsListView) view.findViewById(android.R.id.list);
                mListView.setAdapter(mAdapter);
                mListView.setOnItemClickListener(TweetFragmentList.this);

            }

            @Override
            public void failure(TwitterException e) {

            }
        });

        // Set OnItemClickListener so we can be notified on item clicks

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
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(String id);
    }

}
