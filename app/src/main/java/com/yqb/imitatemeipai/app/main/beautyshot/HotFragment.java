package com.yqb.imitatemeipai.app.main.beautyshot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.adapter.HotVideoAdapter;
import com.yqb.imitatemeipai.base.BaseFragment;
import com.yqb.imitatemeipai.entity.response.HotVideo;
import com.yqb.imitatemeipai.presenter.HotPresenter;
import com.yqb.imitatemeipai.util.ToastUtil;
import com.yqb.imitatemeipai.view.HotView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by QJZ on 2017/7/30.
 */

public class HotFragment extends BaseFragment implements HotView, DefaultRefreshHeaderCreater, DefaultRefreshFooterCreater,
        OnLoadmoreListener, OnRefreshListener {

    private RecyclerView hotVideoList;

    private HotPresenter presenter;
    private HotVideoAdapter adapter;
    private Map<String, String> params = new HashMap<>();
    private int pageNumber = 1;

    private SmartRefreshLayout smartRefreshLayout;

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_pager_hot;
    }

    @Override
    protected void findViews() {
        hotVideoList = (RecyclerView) rootView.findViewById(R.id.rv_hot_video_list);
        smartRefreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.srl_refresh_hot_pager_data);
    }

    @Override
    protected void init() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(this);
        SmartRefreshLayout.setDefaultRefreshFooterCreater(this);

        params.put("id", "1");
        params.put("count", "20");
        params.put("page", String.valueOf(pageNumber));

        adapter = new HotVideoAdapter(context);

        hotVideoList.setLayoutManager(new GridLayoutManager(context, 2));
        hotVideoList.setNestedScrollingEnabled(false);
        hotVideoList.setAdapter(adapter);

        presenter = new HotPresenter(getContext());
        presenter.bindIView(this);
        presenter.loadHotVideoData(params);

        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
    }

    @Override
    public void onShowVideoData(HotVideo[] hotVideos) {
        adapter.appendArrayData(hotVideos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNumber += 1;
        params.put("page", String.valueOf(pageNumber));
        presenter.loadMoreVideoData(params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNumber = 1;
        params.put("page", String.valueOf(pageNumber));
        presenter.refreshData(params);
    }

    @Override
    public void onShowMoreData(HotVideo[] hotVideos) {
        smartRefreshLayout.finishLoadmore();
        adapter.appendArrayData(hotVideos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreDataFailed() {
        smartRefreshLayout.finishLoadmore();
        ToastUtil.toast(context, "加载失败");
    }

    @Override
    public void onRefreshData(HotVideo[] hotVideos) {
        smartRefreshLayout.finishRefresh();
        adapter.resetDataArray(hotVideos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshDataFailed() {
        smartRefreshLayout.finishRefresh();
        ToastUtil.toast(context, "刷新失败");
    }

    @Override
    public void getConnectFailed() {
        ToastUtil.toast(context, context.getResources().getString(R.string.error_net_work_disconnect));
    }

    @NonNull
    @Override
    public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
        ClassicsHeader classicsHeader = new ClassicsHeader(context);
        classicsHeader.setPrimaryColors(context.getResources().getColor(R.color.colorListBackground),
                context.getResources().getColor(R.color.colorTextWhite));
        return classicsHeader;
    }

    @NonNull
    @Override
    public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
        ClassicsFooter classicsFooter = new ClassicsFooter(context);
        classicsFooter.setAccentColor(context.getResources().getColor(R.color.colorTextWhite));
        return classicsFooter;
    }

}
