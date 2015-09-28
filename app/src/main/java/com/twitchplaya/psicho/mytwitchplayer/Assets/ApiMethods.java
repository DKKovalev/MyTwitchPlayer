package com.twitchplaya.psicho.mytwitchplayer.Assets;

import com.twitchplaya.psicho.mytwitchplayer.Assets.Models.ChannelModel;
import com.twitchplaya.psicho.mytwitchplayer.Assets.Models.StreamsModel;
import com.twitchplaya.psicho.mytwitchplayer.Assets.Models.TokenModel;
import com.twitchplaya.psicho.mytwitchplayer.Assets.Models.TopStreamsModel;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


public interface ApiMethods {
    @GET("/streams/{channel}")
    void getChannel(@Path("channel") String channel, Callback<ChannelModel> channelCallback);

    //Response getChannel(@Path("channel") String channel, Callback<ChannelModel> channelCallback);
    @GET("/channels/{channel}/access_token")
    void getChannelToken(@Path("channel") String channel, Callback<TokenModel> tokenCallback);

    @GET("/channel/hls/{channel}.m3u8")
    void getStream(@Path("channel") String channel
            , @Query("player") String player
            , @Query("token") String token
            , @Query("sig") String sig
            , @Query("allow_audio_only") String audioOnly
            , @Query("allow_source") String allowSource, @Query("type") String type
            , @Query("p") int randomInt
            , Callback<Response> streamCallback);

    @GET("/games/top")
    void getTop(@Query("limit") int limit, Callback<TopStreamsModel> topStreamsModelCallback);

    @GET("/streams")
    void getStreamsByGame(@Query("game") String game, Callback<StreamsModel> streamsModelCallback);
}
