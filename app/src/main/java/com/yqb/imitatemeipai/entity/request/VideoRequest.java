package com.yqb.imitatemeipai.entity.request;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by QJZ on 2017/7/31.
 */

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class VideoRequest {

    private int id;
    private String topic_name;
    private int count;
    private int page;
    private String feature;

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "VideoRequest{" +
                "count=" + count +
                ", id=" + id +
                ", topic_name='" + topic_name + '\'' +
                ", page=" + page +
                ", feature='" + feature + '\'' +
                '}';
    }
}
