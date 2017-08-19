package com.yqb.imitatemeipai.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yqb.imitatemeipai.R;

/**
 * Created by QJZ on 2017/8/14.
 */

public class DownloadBar extends FrameLayout {

    private ProgressBar progressBar;
    private TextView textView;

    public DownloadBar(Context context) {
        this(context, null);
    }

    public DownloadBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownloadBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_download_bar, this);
        progressBar = (ProgressBar) view.findViewById(R.id.pb_download);
        textView = (TextView) view.findViewById(R.id.tv_progress_download);
    }

    public void onChangeProgress(int progress){
        progressBar.setProgress(progress);
        textView.setText(progress+"%");
        invalidate();
    }

}
