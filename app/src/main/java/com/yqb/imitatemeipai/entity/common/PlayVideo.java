package com.yqb.imitatemeipai.entity.common;

/**
 * Created by QJZ on 2017/8/8.
 */

public class PlayVideo {
    private String url;
    private int cover;
    private  String avatar;
    private String nickName;

    public PlayVideo(String avatar, int cover, String nickName, String url) {
        this.avatar = avatar;
        this.cover = cover;
        this.nickName = nickName;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "PlayVideo{" +
                "avatar='" + avatar + '\'' +
                ", url='" + url + '\'' +
                ", cover=" + cover +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
