package com.jredu.myprojects.entity;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/9/29.
 */
public class AppleNews {
    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private  String url;
    private Bitmap bitmap;


    public AppleNews(String title, String description, String url, Bitmap bitmap) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.bitmap = bitmap;
    }

    public AppleNews(String ctime, String title, String description, String picUrl, String url, Bitmap bitmap) {
        this.ctime = ctime;
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
        this.bitmap = bitmap;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
