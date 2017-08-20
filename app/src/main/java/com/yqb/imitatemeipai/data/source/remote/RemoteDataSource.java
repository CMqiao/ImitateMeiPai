package com.yqb.imitatemeipai.data.source.remote;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.yqb.imitatemeipai.data.source.IDataSource;
import com.yqb.imitatemeipai.util.CloseUtil;
import com.yqb.imitatemeipai.util.FileUtil;
import com.yqb.imitatemeipai.util.HttpURLUtil;
import com.yqb.imitatemeipai.util.JsonParserUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class RemoteDataSource implements IDataSource {
    private OkHttpClient client;
    private String urlString;
    private Handler handler;
    private Context context;

    public RemoteDataSource(Context context) {
        client = OkHttpClientManager.getInstance().getClient();
        handler = new Handler(Looper.getMainLooper());
        this.context = context;
    }

    public RemoteDataSource(String urlPath, Context context) {
        this(context);
        urlString = HttpURLUtil.getURL(urlPath, HttpURLUtil.TYPE_DOMAIN);
        this.context = context;
    }

    /**
     * @param params
     * @param resultDataClass
     * @param callback
     */
    @Override
    public void getJsonData(Map<String, String> params, final Class resultDataClass, final FetchDataCallback callback) {
        StringBuffer urlStringTemp = new StringBuffer(urlString);
        urlStringTemp.append(generateMosaicParams(params));

        Log.d("urlString", urlStringTemp.toString());

        final Request request = new Request.Builder().url(urlStringTemp.toString()).get().build();
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

    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDownloadFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int len = 0;
                byte[] buf = new byte[2048];
                InputStream is = null;
                FileOutputStream fos = null;
                String savePath = FileUtil.isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    final File file = new File(savePath, FileUtil.getNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        final int progress = (int) (sum * 1.0f / total * 100);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onChangeProgress(progress);
                            }
                        });
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadSuccess(file.getAbsolutePath());
                        }
                    });
                    fos.flush();
                } catch (Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadFailed();
                        }
                    });
                } finally {
                    CloseUtil.close(is);
                    CloseUtil.close(fos);
                }
            }
        });
    }

    /**
     * @param urlPath
     */
    @Override
    public void changeURLPath(String urlPath) {
        urlString = HttpURLUtil.getURL(urlPath, HttpURLUtil.TYPE_IP);
    }

    /**
     * @param params
     * @return
     */
    public String generateMosaicParams(Map<String, String> params) {
        StringBuffer paramsString = new StringBuffer("?");
        if (null != params && params.size() != 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    paramsString.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                paramsString.append("&");
            }
            paramsString.deleteCharAt(paramsString.length() - 1);
        }
        return paramsString.toString();
    }

}
