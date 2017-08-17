package com.yqb.imitatemeipai.app.main.beautyshot.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.base.BaseActivity;
import com.yqb.imitatemeipai.entity.response.HotVideo;
import com.yqb.imitatemeipai.presenter.HotDetailPresenter;
import com.yqb.imitatemeipai.util.ToastUtil;
import com.yqb.imitatemeipai.view.HotDetailView;

import java.util.HashMap;

public class VideoPlayActivity extends BaseActivity implements HotDetailView, View.OnKeyListener, View.OnClickListener{

    private WebView webView;
    private HotVideo hotVideo;

    private HotDetailPresenter presenter;
    private ImageView backImageView;
    private TextView titleTextView;
    private ProgressBar progressBar;

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
        backImageView = (ImageView) findViewById(R.id.iv_back);
        titleTextView = (TextView) findViewById(R.id.tv_title);
        progressBar = (ProgressBar) findViewById(R.id.pb_hot_video_load);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        hotVideo = (HotVideo) intent.getExtras().get("hot_video");

       titleTextView.setText(hotVideo.getScreen_name());

       backImageView.setOnClickListener(this);

        presenter = new HotDetailPresenter(this);
        presenter.bindIView(this);

        initWebView();

    }

    private void initWebView(){
/*        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);*/

        WebSettings webSettings = webView.getSettings();
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                    if(newProgress==100){
                        progressBar.setVisibility(View.GONE);
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(newProgress);
                    }
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.setOnKeyListener(this);

        presenter.loadUrl(hotVideo.getUrl());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                if(webView.canGoBack()){
                    webView.goBack();
                }else{
                    VideoPlayActivity.this.finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onWebViewLoadUrl(String url) {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Cache-Control", "public, max-age=" + 0);
        webView.loadUrl(url,headerMap);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (event.getAction()){
            case KeyEvent.ACTION_DOWN:
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                break;
            default: break;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    @Override
    public void getConnectFailed() {
        ToastUtil.toast(this, getResources().getString(R.string.error_net_work_disconnect));
    }

}
