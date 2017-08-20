package com.yqb.imitatemeipai.view;

import com.yqb.imitatemeipai.base.IBaseView;
import com.yqb.imitatemeipai.entity.common.PlayVideo;

/**
 * Created by QJZ on 2017/8/18.
 */

public interface APlayView extends IBaseView {

    void onLoadVideoData();

    void onShowVideoData(PlayVideo[] playVideos);

}
