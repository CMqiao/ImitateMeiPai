package com.yqb.imitatemeipai.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by QJZ on 2017/8/17.
 */

public class CloseUtil {

    /**
     *  关闭可关闭的对象
     * @param closeable
     */
    public static void close(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
