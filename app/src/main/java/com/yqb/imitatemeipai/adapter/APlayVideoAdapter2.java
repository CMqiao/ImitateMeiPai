package com.yqb.imitatemeipai.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.data.source.IDataSource;
import com.yqb.imitatemeipai.entity.common.PlayVideo;
import com.yqb.imitatemeipai.util.DownloadUtil;
import com.yqb.imitatemeipai.util.ToastUtil;
import com.yqb.imitatemeipai.widget.CircleImageView;
import com.yqb.imitatemeipai.widget.DownloadBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void resetDataArray(PlayVideo[] dataArray) {
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

    public static class APlayViewHolder extends RecyclerView.ViewHolder implements MediaPlayer.OnCompletionListener, View.OnClickListener,
            SurfaceHolder.Callback {

        private static final String SAVE_DIR = "/meipai";

        private Context context;
        private View rootView;

        private CircleImageView avatar;
        private TextView nickName;
        private ImageView cover;
        private ImageView playView;
        private SurfaceView surfaceView;
        private ImageView download;
        private DownloadBar downloadBar;

        private PlayVideo playVideo;
        private MediaPlayer mediaPlayer = new MediaPlayer();
        private MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

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
            cover = (ImageView) rootView.findViewById(R.id.iv_bg_cover);
            playView = (ImageView) rootView.findViewById(R.id.iv_video_play);
            surfaceView = (SurfaceView) rootView.findViewById(R.id.sv_video_play);
            avatar = (CircleImageView) rootView.findViewById(R.id.iv_a_play_avatar);
            nickName = (TextView) rootView.findViewById(R.id.tv_a_play_nick_name);
            download = (ImageView) rootView.findViewById(R.id.iv_video_download);
            downloadBar = (DownloadBar) rootView.findViewById(R.id.db_video_download_progress);
        }

        public void bind(final PlayVideo playVideo) {
            this.playVideo = playVideo;

//            设置第一帧图片, 网络过差时会导致黑屏
//            MediaMetadataRetriever mmr=new MediaMetadataRetriever();
//            String path= playVideo.getUrl();
//            mmr.setDataSource(path, new HashMap());
//            Bitmap bitmap=mmr.getFrameAtTime();
//            cover.setImageBitmap(bitmap);

            cover.setImageResource(playVideo.getCover());
            nickName.setText(playVideo.getNickName());
            Glide.with(context).load(playVideo.getAvatar()).into(avatar);

            Glide.with(context).load(playVideo.getAvatar()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    avatar.setImageBitmap(resource);
                }
            });

            surfaceView.getHolder().addCallback(this);

            download.setOnClickListener(this);

            if (DownloadUtil.hasDownloaded(playVideo.getUrl(), SAVE_DIR)) {
                download.setVisibility(View.INVISIBLE);
                setVideoData(DownloadUtil.getDownloadedFilePath(SAVE_DIR, playVideo.getUrl()));
            } else {
                download.setVisibility(View.VISIBLE);
            }
        }

        public void setVideoData(String path) {
            playView.setVisibility(View.VISIBLE);
            try {
                mediaMetadataRetriever.setDataSource(path);
                mediaPlayer.setDataSource(context, Uri.parse(path));
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            playView.setOnClickListener(this);
            mediaPlayer.setOnCompletionListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_video_play:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        playView.setImageResource(R.mipmap.ic_video_pause);
                    } else {
                        if (cover.getVisibility() == View.VISIBLE) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    cover.setVisibility(View.INVISIBLE);
                                }
                            }, 300);
                        }
                        mediaPlayer.start();
                        playView.setImageResource(R.mipmap.ic_video_play);
                    }
                    break;
                case R.id.iv_video_download:
                    download.setVisibility(View.INVISIBLE);
                    downloadBar.setVisibility(View.VISIBLE);
                    if (playVideo != null) {
                        DownloadUtil.download(context, playVideo.getUrl(), SAVE_DIR, new IDataSource.OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess(String path) {
                                downloadBar.setVisibility(View.INVISIBLE);
                                setVideoData(path);
                            }

                            @Override
                            public void onChangeProgress(int progress) {
                                downloadBar.onChangeProgress(progress);
                            }

                            @Override
                            public void onDownloadFailed() {
                                ToastUtil.toast(context, "下载失败");
                            }
                        });
                    }
                default:
                    break;
            }
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            playView.setImageResource(R.mipmap.ic_video_pause);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mediaPlayer.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }

            playView.setImageResource(R.mipmap.ic_video_pause);

            if (DownloadUtil.hasDownloaded(playVideo.getUrl(), SAVE_DIR)) {
                Bitmap bmFrame = mediaMetadataRetriever.getFrameAtTime(mediaPlayer.getCurrentPosition() * 1000);
                cover.setImageBitmap(bmFrame);
            }
            cover.setVisibility(View.VISIBLE);
        }

    }

}
