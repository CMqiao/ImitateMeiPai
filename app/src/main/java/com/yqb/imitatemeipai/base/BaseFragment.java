package com.yqb.imitatemeipai.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by QJZ on 2017/7/29.
 */

public abstract class BaseFragment extends Fragment {

    protected Context context;
    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == context) {
            context = getContext();
        }
        rootView = inflater.inflate(getLayoutResource(), container, false);
        findViews();
        init();
        return rootView;
    }

    protected abstract int getLayoutResource();

    protected abstract void findViews();

    protected abstract void init();
}
