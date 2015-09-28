package com.twitchplaya.psicho.mytwitchplayer.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twitchplaya.psicho.mytwitchplayer.Assets.ApiMethods;
import com.twitchplaya.psicho.mytwitchplayer.Assets.Models.StreamsModel;
import com.twitchplaya.psicho.mytwitchplayer.Assets.RecyclerStreamsAdapter;
import com.twitchplaya.psicho.mytwitchplayer.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GamesFragment extends Fragment implements RecyclerStreamsAdapter.OnRecyclerItemClick {

    private RestAdapter restAdapter;
    private List<StreamsModel.Streams> gameList;
    private String gameTitle;

    private RecyclerView recyclerView;
    private RecyclerStreamsAdapter recyclerStreamsAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_streams, container, false);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(gridLayoutManager);

        gameTitle = getArguments().getString("gameTitle");

        getStreamsByGame(gameTitle);

        return view;
    }

    private void getStreamsByGame(String game) {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.base_kraken_url))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        ApiMethods apiMethods = restAdapter.create(ApiMethods.class);
        apiMethods.getStreamsByGame(game, new Callback<StreamsModel>() {
            @Override
            public void success(StreamsModel streamsModel, Response response) {
                gameList = streamsModel.getStreams();

                recyclerStreamsAdapter = new RecyclerStreamsAdapter(gameList, getActivity());
                recyclerStreamsAdapter.setClickListener(GamesFragment.this);
                recyclerView.setAdapter(recyclerStreamsAdapter);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void itemClicked(View view, int pos) {

        String channelTitle = gameList.get(pos).getChannel().getName();

        Toast.makeText(getActivity(), channelTitle, Toast.LENGTH_SHORT).show();

        StreamFragment streamFragment = new StreamFragment();
        /*Bundle bundle = new Bundle();
        bundle.putString("channelTitle", channelTitle);
        streamFragment.setArguments(bundle);*/

        /*this.getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, streamFragment)
                .addToBackStack(null)
                .commit();*/

        Intent intent = new Intent(getActivity(), StreamFragment.class);
        intent.putExtra("channelTitle", channelTitle);
        getActivity().startActivity(intent);
    }
}
