package com.yqb.imitatemeipai.data.source.remote;

import okhttp3.OkHttpClient;

/**
 * Created by QJZ on 2017/7/31.
 */

public class OkHttpClientManager {

    public static OkHttpClientManager instance;

    private OkHttpClient client;

    private OkHttpClientManager() {
        client = new OkHttpClient();
    }

    synchronized public static OkHttpClientManager getInstance() {
        if (null == instance) {
            instance = new OkHttpClientManager();
        }
        return instance;
    }

    public OkHttpClient getClient() {
        return client;
    }

}
