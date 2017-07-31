package com.yqb.imitatemeipai.view;

import com.yqb.imitatemeipai.base.IBaseView;
import com.yqb.imitatemeipai.entity.response.HotVideo;

/**
 * Created by QJZ on 2017/7/31.
 */

public interface HotView extends IBaseView {
    void onShowVideoData(HotVideo[] hotVideos);
    void onShowMoreData(HotVideo[] hotVideos);
}
