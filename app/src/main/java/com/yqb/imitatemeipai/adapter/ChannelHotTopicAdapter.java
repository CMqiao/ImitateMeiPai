package com.yqb.imitatemeipai.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.app.main.channel.ChannelVideoListActivity;
import com.yqb.imitatemeipai.entity.common.HotTopic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by QJZ on 2017/7/31.
 */

public class ChannelHotTopicAdapter extends RecyclerView.Adapter {

    private List<HotTopic> dataList = new ArrayList<>();

    private Context context;

    private LayoutInflater inflate;

    public ChannelHotTopicAdapter(Context context) {
        this.context = context;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChannelHotTopicViewHolder(inflate.inflate(R.layout.item_channel_hot_topic_list, parent, false), context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ChannelHotTopicViewHolder hotTopicViewHolder = (ChannelHotTopicViewHolder) holder;
        hotTopicViewHolder.bind((HotTopic) (dataList.get(position)));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void resetDataList(List dataList) {
        this.dataList.clear();
        appendListData(dataList);
    }

    public void resetDataArray(HotTopic[] dataArray){
        this.dataList.clear();
        appendArrayData(dataArray);
    }

    public void appendListData(List dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void appendArrayData(HotTopic[] dataArray) {
        if (dataArray != null && dataArray.length != 0) {
            appendListData(Arrays.asList(dataArray));
        }
    }

    public static class ChannelHotTopicViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private View rootView;

        private ImageView coverImageView;
        private TextView topicTitle;
        private TextView playCount;

        private ChannelHotTopicViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
        }

        public ChannelHotTopicViewHolder(View itemView, Context context) {
            this(itemView);
            this.context = context;
            findViews();
        }

        public void findViews() {
            coverImageView = (ImageView) rootView.findViewById(R.id.iv_channel_hot_topic_icon);
            topicTitle = (TextView) rootView.findViewById(R.id.tv_channel_hot_topic_name);
            playCount = (TextView) rootView.findViewById(R.id.tv_channel_hot_topic_play_count);
        }

        public void bind(final HotTopic hotTopic) {
            if(null != hotTopic.getCoverUrl()){
                Glide.with(context).load(hotTopic.getCoverUrl()).into(coverImageView);
            }

            if(null != hotTopic.getTopic()){
                topicTitle.setText("#"+hotTopic.getTopic()+"#");
            }

            if(null != hotTopic.getPlayCount()){
                playCount.setText(hotTopic.getPlayCount()+"万播放");
            }

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChannelVideoListActivity.class);
                    intent.putExtra("param", hotTopic);
                    context.startActivity(intent);
                }
            });
        }

    }

}
