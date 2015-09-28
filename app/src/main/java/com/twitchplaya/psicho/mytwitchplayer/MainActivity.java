package com.twitchplaya.psicho.mytwitchplayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.twitchplaya.psicho.mytwitchplayer.Assets.ApiMethods;
import com.twitchplaya.psicho.mytwitchplayer.Assets.Models.StreamsModel;
import com.twitchplaya.psicho.mytwitchplayer.Assets.Models.TokenModel;
import com.twitchplaya.psicho.mytwitchplayer.Assets.Models.TopStreamsModel;
import com.twitchplaya.psicho.mytwitchplayer.Assets.RecyclerTopStreamsAdapter;
import com.twitchplaya.psicho.mytwitchplayer.Fragments.TopStreamsFragment;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends FragmentActivity {

    private static final String BASE_KRAKEN_URL = "https://api.twitch.tv/kraken/";
    private static final String STREAM_TOKEN_URL = "http://api.twitch.tv/api/";
    private static final String STREAM_URL = "http://usher.twitch.tv/api";
    private static final String MIME = "application/vnd.twitchtv.v3+json";
    private static final String CLIENT_ID = "4xrv2me643mrppdemy0wamt069yvrgh";

    private FragmentManager fragmentManager;
    private Fragment topListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        topListFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (topListFragment == null) {
            topListFragment = new TopStreamsFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, topListFragment)
                    .commit();
        }
    }

}