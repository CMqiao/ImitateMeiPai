package com.yqb.imitatemeipai.entity.common;

import java.io.Serializable;

/**
 * Created by QJZ on 2017/8/1.
 */

public class HotTopic implements Serializable {
    private String topic;
    private String coverUrl;
    private String playCount;

    public HotTopic(String coverUrl, String playCount, String topic) {
        this.coverUrl = coverUrl;
        this.playCount = playCount;
        this.topic = topic;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "HotTopic{" +
                "coverUrl='" + coverUrl + '\'' +
                ", topic='" + topic + '\'' +
                ", playCount=" + playCount +
                '}';
    }
}
