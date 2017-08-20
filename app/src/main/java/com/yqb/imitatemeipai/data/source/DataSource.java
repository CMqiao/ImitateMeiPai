package com.yqb.imitatemeipai.data.source;

import android.content.Context;

import com.yqb.imitatemeipai.data.source.local.LocalDataSource;
import com.yqb.imitatemeipai.data.source.remote.RemoteDataSource;

import java.util.Map;

/**
 * Created by QJZ on 2017/7/31.
 */

public class DataSource implements IDataSource {

    //维护本地数据源和远程数据源
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    public DataSource(Context context) {
        remoteDataSource = new RemoteDataSource(context);
    }

    public DataSource(String urlPath, Context context) {
        remoteDataSource = new RemoteDataSource(urlPath, context);
    }

    @Override
    public void getJsonData(Map<String, String> params, Class resultDataClass, FetchDataCallback callback) {
        remoteDataSource.getJsonData(params, resultDataClass, callback);
    }

    @Override
    public void changeURLPath(String urlPath) {
        //先去本地缓存中取出数据
        //localDataSource.getEntityData();
        //否者就去网络上请求数据
        remoteDataSource.changeURLPath(urlPath);
    }

    @Override
    public void download(String url, String saveDir, OnDownloadListener listener) {
        remoteDataSource.download(url, saveDir, listener);
    }

}
