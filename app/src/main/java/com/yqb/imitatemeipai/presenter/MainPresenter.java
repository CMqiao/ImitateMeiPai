package com.yqb.imitatemeipai.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.app.main.beautyshot.BeautyShotFragment;
import com.yqb.imitatemeipai.app.main.channel.ChannelFragment;
import com.yqb.imitatemeipai.base.BasePresenter;
import com.yqb.imitatemeipai.entity.common.MainTabEntity;
import com.yqb.imitatemeipai.view.MainView;

import java.util.ArrayList;

/**
 * Created by QJZ on 2017/7/29.
 */

public class MainPresenter extends BasePresenter<MainView> {

    private ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public MainPresenter(Context context){
        this.context = context;
    }

    @Override
    public void bindIView(MainView mainView) {
        this.view=mainView;
    }

    public void showTabData(){
        if (null == view) {
            return;
        }

        fragments.add(BeautyShotFragment.newInstance());
        fragments.add(ChannelFragment.newInstance());

        tabEntities.add(new MainTabEntity(context.getResources().getString(R.string.title_beatuty_shot),
                R.mipmap.ic_beauty_shot_tab_select, R.mipmap.ic_beauty_shot_tab_unselect));
        tabEntities.add(new MainTabEntity(context.getResources().getString(R.string.title_channel),
                R.mipmap.ic_channel_tab_select, R.mipmap.ic_channel_tab_unselect));

        view.onShowTabData(tabEntities, fragments);
    }

}
