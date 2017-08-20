package com.yqb.imitatemeipai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.entity.common.PlayVideo;
import com.yqb.imitatemeipai.widget.VideoSwitchPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QJZ on 2017/8/8.
 */

public class APlayVideoAdapter extends VideoSwitchPager.Adapter {

    private Context context;
    private List<PlayVideo> dataList = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();

    public APlayVideoAdapter(Context context, List<PlayVideo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public View getView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_video_item, null);
        viewList.add(view);
        return view;
    }

    @Override
    public String getVideoUrl(int position) {
        return dataList.get(position).getUrl();
    }

    public List<PlayVideo> getDataList() {
        return dataList;
    }

    public void setDataList(List<PlayVideo> dataList) {
        this.dataList = dataList;
    }
}
