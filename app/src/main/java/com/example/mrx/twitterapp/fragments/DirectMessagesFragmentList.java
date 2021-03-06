package com.example.mrx.twitterapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.example.mrx.twitterapp.R;
import com.example.mrx.twitterapp.api.TwitterPetitionAsyckTask;
import com.example.mrx.twitterapp.utils.UIUtils;

import io.fabric.sdk.android.services.network.HttpMethod;

/**
 * Direct Screen
 * <p/>
 * This will present the users Twitter “Direct Messages"
 * <p/>
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
public class DirectMessagesFragmentList extends Fragment implements AbsListView.OnItemClickListener {

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DirectMessagesFragmentList() {
    }

    @NonNull
    public static DirectMessagesFragmentList newInstance() {
        DirectMessagesFragmentList fragment = new DirectMessagesFragmentList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tweet, container, false);

        /*
      The Adapter which will be used to populate the ListView/GridView with
      Views.
     */
        ListAdapter mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(DirectMessagesFragmentList.this);
        TwitterPetitionAsyckTask twitterPetitionAsyckTask = new TwitterPetitionAsyckTask(getActivity());
        twitterPetitionAsyckTask.setListViewToUpdate(mListView);
        twitterPetitionAsyckTask.execute("https://api.twitter.com/1.1/direct_messages.json", HttpMethod.GET.name());
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UIUtils.toastThis(getActivity(), view.toString());
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
