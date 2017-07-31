package com.yqb.imitatemeipai.view;

import android.support.v4.app.Fragment;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.yqb.imitatemeipai.base.IBaseView;

import java.util.ArrayList;

/**
 * Created by QJZ on 2017/7/29.
 */

public interface MainView extends IBaseView{
    void onShowTabData(ArrayList<CustomTabEntity>tabEntities, ArrayList<Fragment> fragments);

}
