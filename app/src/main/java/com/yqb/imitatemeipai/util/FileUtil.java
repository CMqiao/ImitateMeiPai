package com.yqb.imitatemeipai.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by QJZ on 2017/8/17.
 */

public class FileUtil {

    public static String isExistDir(String saveDir) throws IOException {

        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    public static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

}
