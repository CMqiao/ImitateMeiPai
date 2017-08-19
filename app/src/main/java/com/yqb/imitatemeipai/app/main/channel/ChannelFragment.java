package com.yqb.imitatemeipai.app.main.channel;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.adapter.ChannelHotTopicAdapter;
import com.yqb.imitatemeipai.adapter.ChannelVideoCategoryAdapter;
import com.yqb.imitatemeipai.base.BaseFragment;
import com.yqb.imitatemeipai.data.source.local.HotTopicDataSource;
import com.yqb.imitatemeipai.data.source.local.VideoCategoryDataSource;
import com.yqb.imitatemeipai.entity.response.HotVideo;

/**
 * Created by QJZ on 2017/7/29.
 */

public class ChannelFragment extends BaseFragment {

    private RecyclerView videoCategoryList;
    private RecyclerView hotTopicList;

    private ChannelVideoCategoryAdapter categoryAdapter;
    private ChannelHotTopicAdapter hotTopicAdapter;

    public static ChannelFragment newInstance() {
        ChannelFragment fragment = new ChannelFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_channel;
    }

    @Override
    protected void findViews() {
        videoCategoryList = (RecyclerView) rootView.findViewById(R.id.rv_video_category_list);
        hotTopicList = (RecyclerView) rootView.findViewById(R.id.rv_hot_topic_list);
    }

    @Override
    protected void init() {


        HotVideo[] hotVideos = new HotVideo[10];
        for (int i = 0; i < hotVideos.length; i++) {
            hotVideos[i] = new HotVideo();
        }

        categoryAdapter = new ChannelVideoCategoryAdapter(context);
        videoCategoryList.setLayoutManager(new GridLayoutManager(context, 4));
        videoCategoryList.setNestedScrollingEnabled(false);
        videoCategoryList.setAdapter(categoryAdapter);
        categoryAdapter.appendArrayData(new VideoCategoryDataSource(context).generateData());
        categoryAdapter.notifyDataSetChanged();

        hotTopicAdapter = new ChannelHotTopicAdapter(context);
        hotTopicList.setLayoutManager(new LinearLayoutManager(context));
        hotTopicList.setNestedScrollingEnabled(false);
        hotTopicList.setAdapter(hotTopicAdapter);
        hotTopicAdapter.appendArrayData(new HotTopicDataSource(context).generateData());
        hotTopicAdapter.notifyDataSetChanged();

    }


}
