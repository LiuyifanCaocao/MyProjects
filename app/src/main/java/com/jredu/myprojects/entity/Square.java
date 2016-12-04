package com.jredu.myprojects.entity;

/**
 * Created by Administrator on 2016/10/10.
 */
public class Square {
    private int type;
    private String title;
    private String content;
    private int img;
    private int leftimg;
    private int centerimg;
    private int rightimg;
    private int bottomimg;

    public Square(int type, String title, String content, int img, int leftimg, int centerimg, int rightimg, int bottomimg) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.img = img;
        this.leftimg = leftimg;
        this.centerimg = centerimg;
        this.rightimg = rightimg;
        this.bottomimg = bottomimg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getLeftimg() {
        return leftimg;
    }

    public void setLeftimg(int leftimg) {
        this.leftimg = leftimg;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getRightimg() {
        return rightimg;
    }

    public void setRightimg(int rightimg) {
        this.rightimg = rightimg;
    }

    public int getCenterimg() {
        return centerimg;
    }

    public void setCenterimg(int centerimg) {
        this.centerimg = centerimg;
    }

    public int getBottomimg() {
        return bottomimg;
    }

    public void setBottomimg(int bottomimg) {
        this.bottomimg = bottomimg;
    }
}
