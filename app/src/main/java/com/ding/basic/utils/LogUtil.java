package com.ding.basic.utils;

import android.util.Log;

import com.ding.basic.app.AppEnvEnum;

/**
 * Created by dingzuoqiang on 2017/07/10.
 * Email: 530858106@qq.com
 */

public class LogUtil {
    private final static String TAG = "LogUtil";
    private static AppEnvEnum appEnvEnum = AppEnvEnum.DEBUG;

    public static void setAppEnvEnum(AppEnvEnum appEnvEnum) {
        LogUtil.appEnvEnum = appEnvEnum;
    }

    public static void d(Class classz, String str) {
        if (appEnvEnum != null && appEnvEnum == AppEnvEnum.DEBUG)
            Log.d(TAG, classz.getCanonicalName() + "--->" + str);
    }

    public static void i(Class classz, String str) {
        if (appEnvEnum != null && appEnvEnum == AppEnvEnum.DEBUG)
            Log.i(TAG, classz.getCanonicalName() + "--->" + str);
    }

    public static void e(Class classz, String str) {
        if (appEnvEnum != null && appEnvEnum == AppEnvEnum.DEBUG)
            Log.e(TAG, classz.getCanonicalName() + "--->" + str);
    }

    public static void e(String tag, String str) {
        if (appEnvEnum != null && appEnvEnum == AppEnvEnum.DEBUG)
            Log.e(tag != null ? tag : TAG, TAG + "--->" + str);
    }

    public static void v(Class classz, String str) {
        if (appEnvEnum != null && appEnvEnum == AppEnvEnum.DEBUG)
            Log.v(TAG, classz.getCanonicalName() + "--->" + str);
    }

    public static void LogException(Class c, Throwable e) {
        if (appEnvEnum != null && appEnvEnum == AppEnvEnum.DEBUG) {
            try {
                StringBuilder exceptionInfo = new StringBuilder();
                if (e == null) {
                    exceptionInfo.append("Exception:"
                            + "e is null,probably null pointer exception"
                            + "\n");
                } else {
                    e.printStackTrace();
                    exceptionInfo.append(e.getClass().getCanonicalName() + ":"
                            + e.getMessage() + "\n");
                    StackTraceElement[] stes = e.getStackTrace();
                    for (StackTraceElement ste : stes) {
                        exceptionInfo.append("at " + ste.getClassName() + "$"
                                + ste.getMethodName() + "$" + ste.getFileName()
                                + ":" + ste.getLineNumber() + "\n");
                    }
                }
                e(c, exceptionInfo.toString());
            } catch (Exception ex) {
                e(c, ex.toString());
            }
        }

    }
}
