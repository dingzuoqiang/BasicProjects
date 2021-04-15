package com.ding.basic.http;

import com.ding.basic.app.AppEnvEnum;
import com.ding.basic.app.AppEnvHelper;


/**
 * Created by ding on 2017/07/10.
 * Email: 530858106@qq.com
 */

public class Urls {

    private final static String BASE_URL_TEST = "http://183.129.150.2:8777/";//test
    private final static String BASE_URL_ONLINE = "http://api.bitane.io/"; //

    private static String H5_URL_TEST = "http://183.129.150.2:8081/";//test
    private static String H5_URL_ONLINE = "https://m.bitane.io/";

    public static String dynamicBaseUrl() {
        if (AppEnvHelper.currentEnv() == AppEnvEnum.ONLINE) {
            return BASE_URL_ONLINE;
        }
        return BASE_URL_TEST;
    }

    public static String dynamicH5Url() {
        if (AppEnvHelper.currentEnv() == AppEnvEnum.ONLINE) {
            return H5_URL_ONLINE;
        }
        return H5_URL_TEST;
    }


    public static String getAboutUrl() {
        return dynamicH5Url() + "about";
    }
    public static String getVersion() {
        return dynamicBaseUrl() + "system/version";
    }


}
