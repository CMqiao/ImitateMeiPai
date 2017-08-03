package com.yqb.imitatemeipai.view;

import com.yqb.imitatemeipai.base.IBaseView;
import com.yqb.imitatemeipai.entity.response.HotVideo;

/**
 * Created by QJZ on 2017/8/2.
 */

public interface ChannelVideoListView extends IBaseView {

    void onShowVideoData(HotVideo[] hotVideos);

    void onShowMoreData(HotVideo[] hotVideos);

    void onLoadMoreDataFailed();

    void onRefreshData(HotVideo[] hotVideos);

    void onRefreshDataFailed();

}
