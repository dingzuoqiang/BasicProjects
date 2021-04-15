package com.ding.basic.app;


import com.ding.basic.BuildConfig;

/**
 * Created by dingzuoqiang on 2020/01/13.
 * Email: 530858106@qq.com
 */

public class AppEnvHelper {

    public static AppEnvEnum currentEnv() {
        if (BuildConfig.BUILD_TYPE.equals("release")) {
            return AppEnvEnum.ONLINE;
        }
        return AppEnvEnum.DEBUG;
    }

    public static boolean isOnLine() {
        return currentEnv() == AppEnvEnum.ONLINE;
    }

}
