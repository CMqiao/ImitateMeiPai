package com.yqb.imitatemeipai.app.main.beautyshot;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.base.BaseActivity;
import com.yqb.imitatemeipai.entity.response.HotVideo;

public class HotVideoDetailActivity extends BaseActivity {

    private WebView webView;
    private HotVideo hotVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_hot_video_detail;
    }

    @Override
    protected void findViews() {
        webView = (WebView) findViewById(R.id.wv_hot_video_detail);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        hotVideo = (HotVideo) intent.getExtras().get("hot_video");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8") ;
        webSettings.setDomStorageEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(hotVideo.getUrl());
    }
}
