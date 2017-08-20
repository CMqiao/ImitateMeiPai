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
import com.yqb.imitatemeipai.entity.common.VideoCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by QJZ on 2017/7/31.
 */

public class ChannelVideoCategoryAdapter extends RecyclerView.Adapter {

    private List<VideoCategory> dataList = new ArrayList<>();

    private Context context;

    private LayoutInflater inflate;

    public ChannelVideoCategoryAdapter(Context context) {
        this.context = context;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChannelVideoCategoryViewHolder(inflate.inflate(R.layout.item_channel_video_category_list, parent, false), context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ChannelVideoCategoryViewHolder videoCategoryViewHolder = (ChannelVideoCategoryViewHolder) holder;
        videoCategoryViewHolder.bind((VideoCategory) (dataList.get(position)));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void resetDataList(List dataList) {
        this.dataList.clear();
        appendListData(dataList);
    }

    public void resetDataArray(VideoCategory[] dataArray) {
        this.dataList.clear();
        appendArrayData(dataArray);
    }

    public void appendListData(List dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void appendArrayData(VideoCategory[] dataArray) {
        if (dataArray != null && dataArray.length != 0) {
            appendListData(Arrays.asList(dataArray));
        }
    }

    public static class ChannelVideoCategoryViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private View rootView;

        private ImageView categoryIcon;
        private TextView categoryTitle;


        private ChannelVideoCategoryViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
        }

        public ChannelVideoCategoryViewHolder(View itemView, Context context) {
            this(itemView);
            this.context = context;
            findViews();
        }

        public void findViews() {
            categoryIcon = (ImageView) rootView.findViewById(R.id.iv_channel_video_category_icon);
            categoryTitle = (TextView) rootView.findViewById(R.id.tv_channel_video_category_title);
        }

        public void bind(final VideoCategory videoCategory) {
            if (0 != videoCategory.getPicture()) {
                Glide.with(context).load(videoCategory.getPicture()).into(categoryIcon);
            }

            if (null != videoCategory.getTitle()) {
                categoryTitle.setText(videoCategory.getTitle());
            }

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChannelVideoListActivity.class);
                    intent.putExtra("param", videoCategory);
                    context.startActivity(intent);
                }
            });
        }

    }

}
