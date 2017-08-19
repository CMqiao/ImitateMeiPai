package com.yqb.imitatemeipai.presenter;

import android.content.Context;

import com.yqb.imitatemeipai.base.BasePresenter;
import com.yqb.imitatemeipai.data.source.DataSource;
import com.yqb.imitatemeipai.data.source.IDataSource;
import com.yqb.imitatemeipai.data.source.local.APlayDataSource;
import com.yqb.imitatemeipai.view.APlayView;

/**
 * Created by QJZ on 2017/8/18.
 */

public class APlayPresenter extends BasePresenter<APlayView> {

    private IDataSource dataSource;

    public APlayPresenter(Context context) {
        this.context = context;
        dataSource = new DataSource(context);
    }

    @Override
    public void bindIView(APlayView aPlayView) {
        this.view = aPlayView;
    }

    public void loadPlayVideoData(){
        view.onShowVideoData(new APlayDataSource(context).generateData());
    }

}
