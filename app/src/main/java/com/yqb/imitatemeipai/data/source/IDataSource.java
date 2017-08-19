package com.yqb.imitatemeipai.data.source;

import com.yqb.imitatemeipai.data.source.remote.RemoteDataSource;

import java.util.Map;

/**
 * Created by QJZ on 2017/7/31.
 */

public interface IDataSource {

    interface FetchDataCallback {

        void onEntityDataLoaded(Object data);

        void onEntityDataNotAvailable();
    }

    interface OnDownloadListener{
        void onDownloadSuccess(String path);
        void onChangeProgress(int progress);
        void onDownloadFailed();
    }

    void getJsonData(Map<String, String> params, Class resultDataClass, FetchDataCallback callback);

    void changeURLPath(String urlPath);

    void download(String url, String saveDir, OnDownloadListener listener) ;
}
