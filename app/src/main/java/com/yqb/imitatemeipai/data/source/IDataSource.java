package com.yqb.imitatemeipai.data.source;

import java.util.Map;

/**
 * Created by QJZ on 2017/7/31.
 */

public interface IDataSource {

    interface FetchDataCallback {

        void onEntityDataLoaded(Object data);

        void onEntityDataNotAvailable();
    }

    void getJsonData(Map<String, String> params, Class resultDataClass, FetchDataCallback callback);

    void changeURLPath(String urlPath);
}
