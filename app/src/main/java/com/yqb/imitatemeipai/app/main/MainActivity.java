package com.yqb.imitatemeipai.app.main;

import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.base.BaseActivity;
import com.yqb.imitatemeipai.presenter.MainPresenter;
import com.yqb.imitatemeipai.view.MainView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MainView{

    private CommonTabLayout commonTabLayout;
    private FrameLayout frameLayout;
    private MainPresenter presenter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        commonTabLayout = (CommonTabLayout)findViewById(R.id.tl_main_bottom);
        frameLayout = (FrameLayout)findViewById(R.id.fl_main_content);
    }

    @Override
    protected void init() {
        presenter = new MainPresenter(this);
        presenter.bindIView(this);
        presenter.showTabData();
    }

    @Override
    public void onShowTabData(ArrayList<CustomTabEntity> tabEntities, ArrayList<Fragment> fragments) {
        commonTabLayout.setTabData(tabEntities, this, R.id.fl_main_content, fragments);
    }

    @Override
    public void getConnectFailed() {
        onShowToast(getResources().getString(R.string.error_net_work_disconnect));
    }
}
