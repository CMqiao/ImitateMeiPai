package com.yqb.imitatemeipai.app;

import android.content.Intent;

import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.app.main.MainActivity;
import com.yqb.imitatemeipai.base.BaseActivity;

public class AppStartActivity extends BaseActivity {

    private static final int SLEEP_TIME = 2000;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_start;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        new LauncherThread().start();
    }

    class LauncherThread extends Thread{
        @Override
        public void run() {
            super.run();
            long start = System.currentTimeMillis();
            /**
             * 做启动初始化工作
             */
            long costTime = System.currentTimeMillis() - start;
            try {
                Thread.sleep(SLEEP_TIME - costTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(AppStartActivity.this, MainActivity.class));
            AppStartActivity.this.finish();
        }
    }

}
