package com.yqb.imitatemeipai.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.app.main.beautyshot.detail.VideoPlayActivity;
import com.yqb.imitatemeipai.entity.response.HotVideo;
import com.yqb.imitatemeipai.widget.CircleImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by QJZ on 2017/7/31.
 */

public class HotVideoAdapter extends RecyclerView.Adapter {

    private List<HotVideo> dataList = new ArrayList<>();

    private Context context;

    private LayoutInflater inflate;

    public HotVideoAdapter(Context context) {
        this.context = context;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotVideoViewHolder(inflate.inflate(R.layout.item_hot_video_list, parent, false), context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HotVideoViewHolder hotVideoViewHolder = (HotVideoViewHolder) holder;
        hotVideoViewHolder.bind((HotVideo) (dataList.get(position)));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void resetDataList(List dataList) {
        this.dataList.clear();
        appendListData(dataList);
    }

    public void resetDataArray(HotVideo[] dataArray) {
        this.dataList.clear();
        appendArrayData(dataArray);
    }

    public void appendListData(List dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void appendArrayData(HotVideo[] dataArray) {
        if (dataArray != null && dataArray.length != 0) {
            appendListData(Arrays.asList(dataArray));
        }
    }

    public static class HotVideoViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private View rootView;

        private ImageView cover;
        private CircleImageView avatar;
        private TextView nickName;
        private TextView likeCount;

        private HotVideoViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
        }

        public HotVideoViewHolder(View itemView, Context context) {
            this(itemView);
            this.context = context;
            findViews();
        }

        public void findViews() {
            cover = (ImageView) rootView.findViewById(R.id.iv_hot_video_list_item_cover);
            avatar = (CircleImageView) rootView.findViewById(R.id.iv_hot_video_list_avatar);
            nickName = (TextView) rootView.findViewById(R.id.tv_hot_video_list_nick_name);
            likeCount = (TextView) rootView.findViewById(R.id.tv_hot_video_like_count);
        }

        public void bind(final HotVideo hotVideo) {
            Glide.with(context).load(hotVideo.getCover_pic()).into(cover);
            Glide.with(context).load(hotVideo.getAvatar()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    avatar.setImageBitmap(resource);
                }
            });

            nickName.setText(hotVideo.getScreen_name());
            likeCount.setText(String.valueOf(hotVideo.getLikes_count()));
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VideoPlayActivity.class);
                    intent.putExtra("hot_video", hotVideo);
                    context.startActivity(intent);
                }
            });
        }

    }

}
