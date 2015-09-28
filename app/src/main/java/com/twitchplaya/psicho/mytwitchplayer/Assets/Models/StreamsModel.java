package com.twitchplaya.psicho.mytwitchplayer.Assets.Models;


import java.util.List;

public class StreamsModel {
    private List<Streams> streams;

    public StreamsModel(List<Streams> streams) {
        this.streams = streams;
    }

    public List<Streams> getStreams() {
        return streams;
    }

    public void setStreams(List<Streams> streams) {
        this.streams = streams;
    }

    public class Streams {
        private int viewers;
        private Channel channel;
        private _links links;
        private Preview preview;

        public Preview getPreview() {
            return preview;
        }

        public void setPreview(Preview preview) {
            this.preview = preview;
        }

        public int getViewers() {
            return viewers;
        }

        public void setViewers(int viewers) {
            this.viewers = viewers;
        }

        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }

        public _links getLinks() {
            return links;
        }

        public void setLinks(_links links) {
            this.links = links;
        }
    }

    public class Channel {
        private String display_name;
        private String logo;
        private String status;
        private String name;

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class _links {
        private String self;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }
    }

    public class Preview{
        private String small;
        private String medium;
        private String large;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }
}
