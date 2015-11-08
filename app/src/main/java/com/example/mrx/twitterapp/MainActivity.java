package com.example.mrx.twitterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mrx.twitterapp.fragments.DirectMessagesFragmentList;
import com.example.mrx.twitterapp.fragments.TweetTimeLineFragmentList;
import com.example.mrx.twitterapp.fragments.UserTimeLineFragment;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static TwitterSession session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.newMessage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        TextView.class.cast(drawer.findViewById(R.id.twitterUserName)).setText(Twitter.getSessionManager().getActiveSession().getUserName());
        navigationView.setNavigationItemSelectedListener(this);

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {//    "Timeline Screen"
            changeFragment(TweetTimeLineFragmentList.newInstance());
        } else if (id == R.id.nav_gallery) {//    "Direct Messages Screen"
            changeFragment(DirectMessagesFragmentList.newInstance());
        } else if (id == R.id.nav_slideshow) {//    "User Timeline Screen"
            changeFragment(UserTimeLineFragment.newInstance());
        } else if (id == R.id.nav_manage) {//    "Tweets Screen"
            changeFragment(TweetTimeLineFragmentList.newInstance());
        }
        if (id == R.id.nav_tab) {//    "Tabbed Activity"
            startActivity(new Intent(MainActivity.this, TabAcitivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Fragment changeFragment(Fragment _fragment) {
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        Fragment fragment = _fragment;
        fragmentManager.replace(R.id.content_frame, fragment, fragment.getClass().getName()).commit();
        return fragment;
    }
}
