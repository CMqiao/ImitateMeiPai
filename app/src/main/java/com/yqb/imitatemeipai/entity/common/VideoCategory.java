package com.yqb.imitatemeipai.entity.common;

import java.io.Serializable;

/**
 * Created by QJZ on 2017/8/2.
 */

public class VideoCategory implements Serializable{
    private int id;
    private int picture;
    private String title;

    public VideoCategory(int id, int picture, String title) {
        this.id = id;
        this.picture = picture;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
