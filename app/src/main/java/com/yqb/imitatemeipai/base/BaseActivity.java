package com.yqb.imitatemeipai.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yqb.imitatemeipai.util.ToastUtil;

/**
 * Created by QJZ on 2017/7/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        findViews();
        init();
    }

    protected abstract int getLayoutResource();

    protected abstract void findViews();

    protected abstract void init();

    protected void onShowToast(String msg) {
        ToastUtil.toast(this, msg);
    }

}
