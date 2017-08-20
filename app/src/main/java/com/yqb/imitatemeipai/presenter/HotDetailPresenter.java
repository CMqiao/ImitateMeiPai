package com.yqb.imitatemeipai.presenter;

import android.content.Context;

import com.yqb.imitatemeipai.base.BasePresenter;
import com.yqb.imitatemeipai.data.source.DataSource;
import com.yqb.imitatemeipai.data.source.IDataSource;
import com.yqb.imitatemeipai.util.URLUtil;
import com.yqb.imitatemeipai.view.HotDetailView;

/**
 * Created by QJZ on 2017/8/1.
 */

public class HotDetailPresenter extends BasePresenter<HotDetailView> {

    private IDataSource dataSource;

    public HotDetailPresenter(Context context) {
        this.context = context;
        dataSource = new DataSource(URLUtil.VIDEO_LIST, context);
    }

    @Override
    public void bindIView(HotDetailView hotDetailView) {
        this.view = hotDetailView;
    }

    public void loadUrl(String url) {
        view.onWebViewLoadUrl(url);
    }


}
