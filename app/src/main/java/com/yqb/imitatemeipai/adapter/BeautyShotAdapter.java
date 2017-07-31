package com.yqb.imitatemeipai.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yqb.imitatemeipai.R;

import java.util.ArrayList;

/**
 * Created by QJZ on 2017/7/30.
 */

public class BeautyShotAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentList;
    private Context context;

    public BeautyShotAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList, Context context) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getResources().getString(R.string.title_hot);
            case 1:
                return context.getResources().getString(R.string.title_a_play);
            case 2:
                return context.getResources().getString(R.string.title_same_city);
        }
        return super.getPageTitle(position);
    }
}
