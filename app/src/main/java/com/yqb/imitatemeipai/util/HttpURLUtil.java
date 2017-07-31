package com.yqb.imitatemeipai.util;

/**
 * Created by QJZ on 2017/7/29.
 */

public class HttpURLUtil {

    public static final int TYPE_IP = 0x1000;
    public static final int TYPE_DOMAIN = 0x2000;

    private static String IP_ADDRESS = "";

    private static String DOMAIN_NAME = "http://newapi.meipai.com";

    public static String getURL(String path) {
        return IP_ADDRESS + path;
    }

    public static String getURL(String path, int type) {
        switch (type) {
            case TYPE_IP:
                return IP_ADDRESS + path;
            case TYPE_DOMAIN:
                return DOMAIN_NAME + path;
        }
        return DOMAIN_NAME + path;
    }

}
