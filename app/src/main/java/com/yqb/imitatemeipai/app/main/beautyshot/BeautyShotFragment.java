package com.yqb.imitatemeipai.app.main.beautyshot;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.adapter.BeautyShotAdapter;
import com.yqb.imitatemeipai.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by QJZ on 2017/7/29.
 */

public class BeautyShotFragment extends BaseFragment {

    private ViewPager beautyShotViewPager;
    private TabLayout beautyShotTabLayout;
    private BeautyShotAdapter beautyShotAdapter;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    public static BeautyShotFragment newInstance() {
        BeautyShotFragment fragment = new BeautyShotFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_beauty_shot;
    }

    @Override
    protected void findViews() {
        beautyShotTabLayout = (TabLayout) rootView.findViewById(R.id.tl_beauty_shot_tab);
        beautyShotViewPager = (ViewPager) rootView.findViewById(R.id.vp_beauty_shot_tab_content);
    }

    @Override
    protected void init() {
        fragmentList.add(new HotFragment());
        fragmentList.add(new APlayFragment());
        fragmentList.add(new SameCityFragment());

        beautyShotAdapter = new BeautyShotAdapter(getFragmentManager(), fragmentList, context);

        beautyShotViewPager.setAdapter(beautyShotAdapter);

        beautyShotTabLayout.setupWithViewPager(beautyShotViewPager);

    }


}
