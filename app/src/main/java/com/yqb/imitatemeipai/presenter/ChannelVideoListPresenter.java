package com.yqb.imitatemeipai.presenter;

import android.content.Context;

import com.yqb.imitatemeipai.base.BasePresenter;
import com.yqb.imitatemeipai.data.source.DataSource;
import com.yqb.imitatemeipai.data.source.IDataSource;
import com.yqb.imitatemeipai.entity.response.HotVideo;
import com.yqb.imitatemeipai.util.URLUtil;
import com.yqb.imitatemeipai.view.ChannelVideoListView;

import java.util.Map;

/**
 * Created by QJZ on 2017/8/2.
 */

public class ChannelVideoListPresenter extends BasePresenter<ChannelVideoListView> {

    private IDataSource dataSource;

    public ChannelVideoListPresenter(Context context) {
        this.context = context;
        dataSource = new DataSource(URLUtil.VIDEO_LIST, context);
    }


    @Override
    public void bindIView(ChannelVideoListView channelVideoListView) {
        this.view = channelVideoListView;
    }

    public void loadVideoData(Map<String, String> params) {
        dataSource.getJsonData(params, HotVideo[].class, new IDataSource.FetchDataCallback() {
            @Override
            public void onEntityDataLoaded(Object data) {
                HotVideo[] hotVideos = (HotVideo[]) data;
                view.onShowVideoData(hotVideos);
            }

            @Override
            public void onEntityDataNotAvailable() {

            }
        });
    }

    public void loadMoreVideoData(Map<String, String> params) {
        dataSource.getJsonData(params, HotVideo[].class, new IDataSource.FetchDataCallback() {
            @Override
            public void onEntityDataLoaded(Object data) {
                HotVideo[] hotVideos = (HotVideo[]) data;
                view.onShowMoreData(hotVideos);
            }

            @Override
            public void onEntityDataNotAvailable() {
                view.onLoadMoreDataFailed();
            }
        });
    }

    public void refreshData(Map<String, String> params) {
        dataSource.getJsonData(params, HotVideo[].class, new IDataSource.FetchDataCallback() {
            @Override
            public void onEntityDataLoaded(Object data) {
                HotVideo[] hotVideos = (HotVideo[]) data;
                view.onRefreshData(hotVideos);
            }

            @Override
            public void onEntityDataNotAvailable() {
                view.onRefreshDataFailed();
            }
        });
    }

}
