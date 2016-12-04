package com.jredu.myprojects.entity;

/**
 * Created by Administrator on 2016/10/10.
 */
public class MyMsg {

    private String title;
    private String content;
    private int img;

    public MyMsg(String title, String content, int img) {
        this.title = title;
        this.content = content;
        this.img = img;
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
}
