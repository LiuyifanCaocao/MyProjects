package com.jredu.myprojects.entity;

/**
 * Created by Administrator on 2016/10/11.
 */
public class Song {
    private String title;
    private String artist;
    private String uriData;
    private long size;

    public Song(String title, String artist, String uriData, long size) {
        this.title = title;
        this.artist = artist;
        this.uriData = uriData;
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUriData() {
        return uriData;
    }

    public void setUriData(String uriData) {
        this.uriData = uriData;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
