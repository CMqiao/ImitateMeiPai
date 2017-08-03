package com.yqb.imitatemeipai.data.source.local;

import android.content.Context;

import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.entity.common.HotTopic;

/**
 * Created by QJZ on 2017/8/1.
 */

public class HotTopicDataSource {

    private Context context;
    private String[] topics;
    private String[] urls;
    private String[] playCounts;

    public HotTopicDataSource(Context context){
        this.context = context;
        topics = context.getResources().getStringArray(R.array.hot_topic_name);
        urls = context.getResources().getStringArray(R.array.hot_topic_pic);
        playCounts = context.getResources().getStringArray(R.array.hot_topic_play_count);
    }

    public HotTopic[] generateData(){
        HotTopic[] hotTopics = new HotTopic[topics.length];
        for(int i=0; i<topics.length; i++){
            hotTopics[i] = new HotTopic(urls[i], playCounts[i], topics[i]);
        }
        return hotTopics;
    }

}
