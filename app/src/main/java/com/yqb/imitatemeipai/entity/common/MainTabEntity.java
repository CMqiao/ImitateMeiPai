package com.yqb.imitatemeipai.entity.common;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by QJZ on 2017/7/29.
 */

public class MainTabEntity implements CustomTabEntity{
    public String tabTitle;
    public int selectIcon;
    public int unSelectIcon;

    public MainTabEntity(String tabTitle, int selectIcon, int unSelectIcon) {
        this.tabTitle = tabTitle;
        this.selectIcon = selectIcon;
        this.unSelectIcon = unSelectIcon;
    }

    @Override
    public String getTabTitle() {
        return tabTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectIcon;
    }
}
