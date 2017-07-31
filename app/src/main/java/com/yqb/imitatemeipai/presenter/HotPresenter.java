package com.yqb.imitatemeipai.presenter;

import android.content.Context;

import com.yqb.imitatemeipai.base.BasePresenter;
import com.yqb.imitatemeipai.data.source.DataSource;
import com.yqb.imitatemeipai.data.source.IDataSource;
import com.yqb.imitatemeipai.entity.response.HotVideo;
import com.yqb.imitatemeipai.util.URLUtil;
import com.yqb.imitatemeipai.view.HotView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by QJZ on 2017/7/31.
 */

public class HotPresenter  extends BasePresenter<HotView> {

    private IDataSource dataSource;

    public HotPresenter(Context context){
        this.context = context;
        dataSource = new DataSource(URLUtil.VIDEO_LIST, context);
    }

    @Override
    public void bindIView(HotView hotView) {
            this.view=hotView;
    }

    public void loadHotVideoData(Map<String, String> params){
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

    public void loadMoreVideoData(Map<String, String> params){
        dataSource.getJsonData(params, HotVideo[].class, new IDataSource.FetchDataCallback() {
            @Override
            public void onEntityDataLoaded(Object data) {
                HotVideo[] hotVideos = (HotVideo[]) data;
                view.onShowMoreData(hotVideos);
            }
            @Override
            public void onEntityDataNotAvailable() {

            }
        });
    }

}
