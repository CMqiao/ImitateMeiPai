package com.yqb.imitatemeipai.app.main.beautyshot;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.yqb.imitatemeipai.adapter.APlayVideoAdapter;
import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.adapter.APlayVideoAdapter2;
import com.yqb.imitatemeipai.base.BaseFragment;
import com.yqb.imitatemeipai.entity.common.PlayVideo;
import com.yqb.imitatemeipai.presenter.APlayPresenter;
import com.yqb.imitatemeipai.util.ToastUtil;
import com.yqb.imitatemeipai.view.APlayView;
import com.yqb.imitatemeipai.widget.VideoSwitchPager2;

import java.util.Arrays;

/**
 * Created by QJZ on 2017/7/30.
 */

public class APlayFragment extends BaseFragment implements APlayView{

    private APlayVideoAdapter adapter;

    private VideoSwitchPager2 videoSwitchPager;
    private APlayVideoAdapter2 aPlayVideoAdapter2;

    private APlayPresenter presenter;

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

   /*     adapter = new APlayVideoAdapter(context, videoList);
        videoSwitchPager.setAdapter(adapter);*/

        aPlayVideoAdapter2 = new APlayVideoAdapter2(context);

        videoSwitchPager.setTriggerOffset(0.15f);
        videoSwitchPager.setLongClickable(true);
        videoSwitchPager.setSinglePageFling(true);
        videoSwitchPager.setHasFixedSize(true);
        videoSwitchPager.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        videoSwitchPager.setAdapter(aPlayVideoAdapter2);

        presenter = new APlayPresenter(context);
        presenter.bindIView(this);

        onLoadVideoData();
    }

    @Override
    public void onLoadVideoData() {
        presenter.loadPlayVideoData();
    }

    @Override
    public void onShowVideoData(PlayVideo[] playVideos) {
        Log.d("APlay", Arrays.toString(playVideos));
        aPlayVideoAdapter2.appendArrayData(playVideos);
        aPlayVideoAdapter2.notifyDataSetChanged();
    }

    @Override
    public void getConnectFailed() {
        ToastUtil.toast(context, context.getString(R.string.error_net_work_disconnect));
    }
}
