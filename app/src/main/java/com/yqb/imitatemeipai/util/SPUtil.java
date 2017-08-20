package com.yqb.imitatemeipai.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by QJZ on 2017/7/29.
 */

public class SPUtil {

    private static SPUtil instance = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SPUtil(Context context) {
        sharedPreferences = context.getSharedPreferences("SP_FILE_NAME", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    synchronized public static SPUtil getInstance(Context context) {
        if (null == instance) {
            instance = new SPUtil(context);
        }
        return instance;
    }

}
