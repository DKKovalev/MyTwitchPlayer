package com.twitchplaya.psicho.mytwitchplayer.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.twitchplaya.psicho.mytwitchplayer.Assets.ApiMethods;

import com.twitchplaya.psicho.mytwitchplayer.Assets.Models.TopStreamsModel;
import com.twitchplaya.psicho.mytwitchplayer.Assets.RecyclerTopStreamsAdapter;
import com.twitchplaya.psicho.mytwitchplayer.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TopStreamsFragment extends Fragment implements RecyclerTopStreamsAdapter.OnRecyclerItemClick {

    private RestAdapter restAdapter;

    private RecyclerView recyclerView;
    private RecyclerTopStreamsAdapter recyclerTopStreamsAdapter;


    List<TopStreamsModel.Top> topList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        topList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_streams, container, false);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(gridLayoutManager);

        getTop();

        return view;
    }

    private void getTop() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.base_kraken_url))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        ApiMethods apiMethods = restAdapter.create(ApiMethods.class);
        apiMethods.getTop(10, new Callback<TopStreamsModel>() {
            @Override
            public void success(TopStreamsModel topStreamsModel, Response response) {

                topList = topStreamsModel.getTopList();

                recyclerTopStreamsAdapter = new RecyclerTopStreamsAdapter(topList, getActivity());
                recyclerTopStreamsAdapter.setClickListener(TopStreamsFragment.this);
                recyclerView.setAdapter(recyclerTopStreamsAdapter);

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), error.getCause().toString(), Toast.LENGTH_LONG).show();
                Log.e("Error", error.getCause().toString());
            }
        });

    }

    @Override
    public void itemClicked(View view, int pos) {

        String gameTitle = topList.get(pos).getGame().getName();

        Toast.makeText(getActivity(), gameTitle, Toast.LENGTH_SHORT).show();

        GamesFragment gamesFragment = new GamesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gameTitle", gameTitle);
        gamesFragment.setArguments(bundle);

        this.getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, gamesFragment)
                .addToBackStack(null)
                .commit();

    }
}
