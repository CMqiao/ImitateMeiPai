package com.yqb.imitatemeipai.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by QJZ on 2017/7/29.
 */

public class ToastUtil {

    public static void toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
