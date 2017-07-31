package com.yqb.imitatemeipai.data.source.remote;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.yqb.imitatemeipai.data.source.IDataSource;
import com.yqb.imitatemeipai.util.HttpURLUtil;
import com.yqb.imitatemeipai.util.JsonParserUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by QJZ on 2017/7/31.
 */

public class RemoteDataSource implements IDataSource{
    private OkHttpClient client;
    private StringBuffer urlString;
    private Handler handler;
    private Context context;

    public RemoteDataSource(Context context) {
        client = OkHttpClientManager.getInstance().getClient();
        handler = new Handler(Looper.getMainLooper());
        this.context = context;
    }

    public RemoteDataSource(String urlPath, Context context) {
        this(context);
        urlString = new StringBuffer(HttpURLUtil.getURL(urlPath, HttpURLUtil.TYPE_DOMAIN));
        this.context = context;
    }

    @Override
    public void getJsonData(Map<String, String> params, final Class resultDataClass, final FetchDataCallback callback) {
        urlString.append("?");
        if (null != params && params.size() != 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    urlString.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                urlString.append("&");
            }
            urlString.deleteCharAt(urlString.length() - 1);
        }

        Log.d("urlString", urlString.toString());

        final Request request = new Request.Builder().url(urlString.toString()).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onEntityDataNotAvailable();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final Object resultEntity = JsonParserUtil.decode(response.body().string(), resultDataClass);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onEntityDataLoaded(resultEntity);
                    }
                });
            }
        });

    }

    @Override
    public void changeURLPath(String urlPath) {
        urlString=new StringBuffer(HttpURLUtil.getURL(urlPath, HttpURLUtil.TYPE_IP));
    }
}
