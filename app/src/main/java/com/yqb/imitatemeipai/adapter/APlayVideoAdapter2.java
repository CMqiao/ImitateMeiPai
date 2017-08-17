package com.yqb.imitatemeipai.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.app.main.beautyshot.detail.VideoPlayActivity;
import com.yqb.imitatemeipai.entity.common.PlayVideo;
import com.yqb.imitatemeipai.entity.response.HotVideo;
import com.yqb.imitatemeipai.widget.CircleImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by QJZ on 2017/7/31.
 */

public class APlayVideoAdapter2 extends RecyclerView.Adapter {

    private List<PlayVideo> dataList = new ArrayList<>();

    private Context context;

    private LayoutInflater inflate;

    public APlayVideoAdapter2(Context context) {
        this.context = context;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        APlayViewHolder viewHolder = new APlayViewHolder(inflate.inflate(R.layout.layout_video_item, parent, false), context);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final APlayViewHolder aPlayViewHolder = (APlayViewHolder) holder;
        aPlayViewHolder.bind((PlayVideo) (dataList.get(position)));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void resetDataList(List dataList) {
        this.dataList.clear();
        appendListData(dataList);
    }

    public void resetDataArray(PlayVideo[] dataArray){
        this.dataList.clear();
        appendArrayData(dataArray);
    }

    public void appendListData(List dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void appendArrayData(PlayVideo[] dataArray) {
        if (dataArray != null && dataArray.length != 0) {
            appendListData(Arrays.asList(dataArray));
        }
    }

    public static class APlayViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private View rootView;

        private ImageView play;

        private MediaPlayer player = new MediaPlayer();

        private SurfaceView surfaceView;

        private APlayViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
        }

        public APlayViewHolder(View itemView, Context context) {
            this(itemView);
            this.context = context;
            findViews();
        }

        public void findViews() {
            play = (ImageView) rootView.findViewById(R.id.iv_video_play);
            surfaceView = (SurfaceView) rootView.findViewById(R.id.sv_video_play);
        }

        public void bind(final PlayVideo playVideo) {

            //设置第一帧图片
/*            MediaMetadataRetriever mmr=new MediaMetadataRetriever();
            String path= playVideo.getUrl();
            mmr.setDataSource(path, new HashMap());
            Bitmap bitmap=mmr.getFrameAtTime();
            surfaceView.setBackgroundDrawable(new BitmapDrawable(bitmap));*/

            player.reset();
            SurfaceHolder holder = surfaceView.getHolder();
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    player.setDisplay(holder);
                }
                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }
                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    player.pause();
                    player.release();
                }
            });
            try {
                player.setDataSource(context, Uri.parse(playVideo.getUrl()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(player.isPlaying()){
                        play.setImageResource(R.mipmap.ic_video_pause);
                        player.pause();
                    }else{
                        play.setImageResource(R.mipmap.ic_video_play);
                        player.start();
                    }
                }
            });

        }

    }

}
