package com.yqb.imitatemeipai.app.main.channel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.yqb.imitatemeipai.adapter.ChannelVideoListAdapter;
import com.yqb.imitatemeipai.base.BaseActivity;
import com.yqb.imitatemeipai.entity.common.HotTopic;
import com.yqb.imitatemeipai.entity.common.VideoCategory;
import com.yqb.imitatemeipai.entity.response.HotVideo;
import com.yqb.imitatemeipai.presenter.ChannelVideoListPresenter;
import com.yqb.imitatemeipai.util.ToastUtil;
import com.yqb.imitatemeipai.view.ChannelVideoListView;

import java.util.HashMap;
import java.util.Map;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

public class ChannelVideoListActivity extends BaseActivity implements ChannelVideoListView, View.OnClickListener,
        DefaultRefreshHeaderCreater, DefaultRefreshFooterCreater, OnLoadmoreListener, OnRefreshListener {

    private TextView back;
    private TextView title;
    private SmartRefreshLayout smartRefreshLayout;

    private RecyclerView channelVideoList;
    private ChannelVideoListAdapter adapter;

    private ChannelVideoListPresenter presenter;
    private int pageNumber = 1;

    private Map<String, String> requestParams = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_channel_video_list;
    }

    @Override
    protected void findViews() {
        back = (TextView) findViewById(R.id.tv_back);
        title = (TextView) findViewById(R.id.tv_title);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh_channel_data);
        channelVideoList = (RecyclerView) findViewById(R.id.rv_channel_video_list);
    }

    @Override
    protected void init() {

        SmartRefreshLayout.setDefaultRefreshHeaderCreater(this);
        SmartRefreshLayout.setDefaultRefreshFooterCreater(this);

        back.setOnClickListener(this);

        Intent intent = getIntent();
        Object param = intent.getExtras().get("param");

        if (param instanceof HotTopic) {
            HotTopic hotTopic = (HotTopic) param;
            title.setText(hotTopic.getTopic());
            requestParams.put("topic_name", hotTopic.getTopic());
        }

        if (param instanceof VideoCategory) {
            VideoCategory videoCategory = (VideoCategory) param;
            title.setText(videoCategory.getTitle());
            requestParams.put("id", String.valueOf(videoCategory.getId()));
        }

        requestParams.put("count", "20");
        requestParams.put("page", String.valueOf(pageNumber));

        adapter = new ChannelVideoListAdapter(this);

        channelVideoList.setLayoutManager(new GridLayoutManager(this, 2));
        channelVideoList.setNestedScrollingEnabled(false);
        channelVideoList.addOnScrollListener(new MyRecyclerViewScrollListener(this));
        channelVideoList.setAdapter(adapter);

        presenter = new ChannelVideoListPresenter(this);
        presenter.bindIView(this);
        presenter.loadVideoData(requestParams);


        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onShowVideoData(HotVideo[] hotVideos) {
        adapter.appendArrayData(hotVideos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNumber += 1;
        requestParams.put("page", String.valueOf(pageNumber));
        presenter.loadMoreVideoData(requestParams);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNumber = 1;
        requestParams.put("page", String.valueOf(pageNumber));
        presenter.refreshData(requestParams);
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
        ToastUtil.toast(this, "加载失败");
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
        ToastUtil.toast(this, "刷新失败");
    }


    @Override
    public void getConnectFailed() {
        ToastUtil.toast(this, getResources().getString(R.string.error_net_work_disconnect));
    }

    @NonNull
    @Override
    public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
        ClassicsFooter classicsFooter = new ClassicsFooter(context);
        classicsFooter.setAccentColor(context.getResources().getColor(R.color.colorTextWhite));
        return classicsFooter;
    }

    @NonNull
    @Override
    public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
        ClassicsHeader classicsHeader = new ClassicsHeader(context);
        classicsHeader.setPrimaryColors(context.getResources().getColor(R.color.colorListBackground),
                context.getResources().getColor(R.color.colorTextWhite));
        return classicsHeader;
    }

    class MyRecyclerViewScrollListener extends OnScrollListener {

        private Context context;

        public MyRecyclerViewScrollListener(Context context) {
            this.context = context;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case SCROLL_STATE_IDLE:
                    Glide.with(context).resumeRequests();
                    break;
                case SCROLL_STATE_DRAGGING:
                    Glide.with(context).pauseRequests();
                    break;
                case SCROLL_STATE_SETTLING:
                    Glide.with(context).pauseRequests();
                    break;
            }
        }
    }

}
