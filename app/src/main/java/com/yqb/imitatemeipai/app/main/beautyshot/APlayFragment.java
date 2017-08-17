package com.yqb.imitatemeipai.app.main.beautyshot;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.yqb.imitatemeipai.adapter.APlayVideoAdapter;
import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.adapter.APlayVideoAdapter2;
import com.yqb.imitatemeipai.base.BaseFragment;
import com.yqb.imitatemeipai.entity.common.PlayVideo;
import com.yqb.imitatemeipai.widget.VideoSwitchPager2;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by QJZ on 2017/7/30.
 */

public class APlayFragment extends BaseFragment {

    private VideoSwitchPager2 videoSwitchPager;
    private APlayVideoAdapter adapter;
    private List<PlayVideo> videoList;
    private APlayVideoAdapter2 aPlayVideoAdapter2;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_a_play;
    }

    @Override
    protected void findViews() {
        //videoSwitchPager = (VideoSwitchPager) rootView.findViewById(R.id.vsp_video_play_list);
        videoSwitchPager = (VideoSwitchPager2) rootView.findViewById(R.id.rv_a_play_video_list);
    }

    @Override
    protected void init() {
        videoList = new ArrayList<>();

        final PlayVideo playVideo = new PlayVideo();
        playVideo.setUrl("http://mvvideo11.meitudata.com/5987ea2345b505964_H264_7.mp4");
        PlayVideo playVideo1 = new PlayVideo();
        playVideo1.setUrl("http://mvvideo11.meitudata.com/59884e4268d884798_H264_7.mp4");
        PlayVideo playVideo2 = new PlayVideo();
        playVideo2.setUrl("http://mvvideo11.meitudata.com/5987f0103faf04065_H264_7.mp4");
        videoList.add(playVideo);
        videoList.add(playVideo1);
        videoList.add(playVideo2);

   /*     adapter = new APlayVideoAdapter(context, videoList);
        videoSwitchPager.setAdapter(adapter);*/

        aPlayVideoAdapter2 = new APlayVideoAdapter2(context);

        videoSwitchPager.setTriggerOffset(0.15f);
        videoSwitchPager.setSinglePageFling(true);
        videoSwitchPager.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        videoSwitchPager.setHasFixedSize(true);
        videoSwitchPager.setLongClickable(true);

        videoSwitchPager.setAdapter(aPlayVideoAdapter2);

        aPlayVideoAdapter2.appendListData(videoList);
        aPlayVideoAdapter2.notifyDataSetChanged();

    }


}
