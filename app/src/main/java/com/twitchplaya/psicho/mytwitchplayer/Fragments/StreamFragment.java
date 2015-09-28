package com.twitchplaya.psicho.mytwitchplayer.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.twitchplaya.psicho.mytwitchplayer.Assets.ApiMethods;
import com.twitchplaya.psicho.mytwitchplayer.Assets.CustomMediaController;
import com.twitchplaya.psicho.mytwitchplayer.Assets.Models.TokenModel;
import com.twitchplaya.psicho.mytwitchplayer.R;

import java.io.IOException;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class StreamFragment extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, CustomMediaController.MediaPlayerControl {

    private String channelTitle;
    private String channelURI;
    private RestAdapter restAdapter;
    private VideoView videoView;

    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private CustomMediaController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stream);

        videoView = (VideoView) findViewById(R.id.stream_vv);
        surfaceView = (SurfaceView) findViewById(R.id.video_surface);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        mediaPlayer = new MediaPlayer();
        controller = new CustomMediaController(this);

        Intent intent = new Intent();
        channelTitle = intent.getStringExtra("channelTitle");
        getStreamToken(channelTitle);
    }

    private void getStreamToken(final String channelName) {


        restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.stream_token_url))
                        //.setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        ApiMethods apiMethods = restAdapter.create(ApiMethods.class);
        apiMethods.getChannelToken(channelName, new Callback<TokenModel>() {
            @Override
            public void success(TokenModel tokenModel, Response response) {
                String token = tokenModel.getToken();
                token = token.replace("\\", "");
                String sig = tokenModel.getSig();

                getStream(channelName, token, sig);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(StreamFragment.this, "Cannot get stream token info", Toast.LENGTH_SHORT).show();
                Toast.makeText(StreamFragment.this, error.getCause().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getStream(String channelName, String token, String sig) {


        restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.stream_video_url))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        ApiMethods apiMethods = restAdapter.create(ApiMethods.class);
        apiMethods.getStream(channelName, "twitchweb", token, sig, "true", "true", "any", 556482, new Callback<Response>() {

            @Override
            public void success(Response callback, Response response) {
                //Toast.makeText(getActivity(), callback.getUrl(), Toast.LENGTH_SHORT).show();

                playStreamFancyWay(response.getUrl());
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(StreamFragment.this, error.getCause().toString(), Toast.LENGTH_LONG).show();
                Log.d("error", error.getCause().getMessage());
            }
        });
    }

    private void playStream(String uri) {
        try {

            MediaController mediaController = new MediaController(StreamFragment.this);
            mediaController.setAnchorView(videoView);

            Uri video = Uri.parse(uri);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(video);
            videoView.requestFocus();
            videoView.setVisibility(View.VISIBLE);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mp) {
                    videoView.start();
                }
            });

        } catch (Exception e) {
            System.out.println("Video Play Error :" + e.toString());
            finish();
        }
    }

    private void playStreamFancyWay(String uri) {
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(StreamFragment.this, Uri.parse(uri));
            mediaPlayer.setOnPreparedListener(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer.setDisplay(holder);
        mediaPlayer.prepareAsync();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void toggleFullscreen() {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        controller.setMediaPlayer(this);
        controller.setAnchorView((FrameLayout) findViewById(R.id.video_container));
        mediaPlayer.start();
    }
}
