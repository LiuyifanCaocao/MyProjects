package com.jredu.myprojects.entity;

/**
 * Created by Administrator on 2016/9/23.
 */
public class News {
    private String title;
    private String content;
    private int img;
    private int icon;

    public News(String title, String content, int img, int icon) {
        this.title = title;
        this.content = content;
        this.img = img;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
