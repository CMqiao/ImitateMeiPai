package com.yqb.imitatemeipai.util;

import android.content.Context;
import android.os.Environment;

import com.yqb.imitatemeipai.data.source.DataSource;
import com.yqb.imitatemeipai.data.source.IDataSource;

import java.io.File;

/**
 * Created by QJZ on 2017/8/18.
 */

public class DownloadUtil {

    public static void download(Context context, String url, String saveDir, IDataSource.OnDownloadListener listener){
        IDataSource dataSource = new DataSource(context);
        dataSource.download(url, saveDir, listener);
    }

    public static boolean hasDownloaded(String url, String saveDir){
        return FileUtil.isFileExist(url, saveDir);
    }

    public static String getDownloadedFilePath(String saveDir, String url){
        return FileUtil.getDownloadedFilePath(saveDir, url);
    }

}
