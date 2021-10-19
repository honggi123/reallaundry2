package com.example.myapplication;

public class SearchYoutubeData {
    String videoId;
    String title;
    String ImageUrl;
    String channelTitle;

    public SearchYoutubeData(String videoId, String title, String ImageUrl, String channelTitle) {
        super();
        this.videoId = videoId;
        this.title = title;
        this.ImageUrl = ImageUrl;
        this.channelTitle = channelTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }

    public String getchannelTitle() {
        return channelTitle;
    }

    public void setchannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }



}
