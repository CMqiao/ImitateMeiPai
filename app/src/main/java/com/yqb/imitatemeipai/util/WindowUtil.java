package com.yqb.imitatemeipai.util;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by QJZ on 2017/8/3.
 */

public class WindowUtil {
    /**
     * 得到状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> classR = Class.forName("com.android.internal.R$dimen");
            Object object = classR.newInstance();
            Field field = classR.getField("status_bar_height");
            int dpHeight = Integer.parseInt(field.get(object).toString());
            int statusBarHeight = context.getResources().getDimensionPixelSize(dpHeight);
            return statusBarHeight;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return 0;
    }
}
