package com.yqb.imitatemeipai.base;

import android.content.Context;

/**
 * Created by QJZ on 2017/7/29.
 */

public abstract class BasePresenter<IView extends IBaseView> {

    protected Context context;
    protected IView view;

    public abstract void bindIView(IView view);

    /**
     * 连接网络失败
     */
    protected void connectFailed() {
        if (null != view) {
            view.getConnectFailed();
        }
    }

}
