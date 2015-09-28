package com.twitchplaya.psicho.mytwitchplayer.Assets.Models;

public class ChannelModel {

    private Stream stream;


    public ChannelModel(Stream stream) {
        this.stream = stream;
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

    public class Stream {

        private Channel channel;
        private String game;

        public Stream(Channel channel, String game) {
            this.channel = channel;
            this.game = game;
        }

        public String getGame() {
            return game;
        }

        public void setGame(String game) {
            this.game = game;
        }

        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }
    }

    public class Channel {
        private String url;

        public Channel(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}