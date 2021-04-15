package com.ding.basic.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;

import com.ding.basic.app.MyApplicaion;

public class CommonUtils {

    /**
     * 跳转到新的activity
     *
     * @param context context
     * @param class1  目标Activity
     * @param pBundle
     */
    public static void openActicity(Context context, Class<?> class1,
                                    Bundle pBundle) {
        openActicity(context, class1, pBundle, 0);
    }

    /**
     * 跳转到新的activity
     *
     * @param activity      现在的Activity
     * @param class1        目标Activity
     * @param pBundle
     * @param closeActivity 是否关闭当前Activity
     */
    public static void openActicity(Activity activity, Class<?> class1,
                                    Bundle pBundle, boolean closeActivity) {
        Intent intent = new Intent(activity, class1);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        activity.startActivity(intent);
        // activity.overridePendingTransition(R.anim.slide_in_right,
        // R.anim.slide_out_left);
        if (closeActivity) {
            activity.finish();
        }

    }

    /**
     * 跳转到新的activity
     *
     * @param context context
     * @param class1  目标Activity
     * @param pBundle
     * @param flags   启动模式
     */
    public static void openActicity(Context context, Class<?> class1,
                                    Bundle pBundle, int flags) {
        Intent intent = new Intent(context, class1);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (flags > 0) {
            intent.setFlags(flags);
        }
        context.startActivity(intent);

    }

    /**
     * 获取app当前的渠道号或application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值 ， 或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String value = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        try {
                            value = applicationInfo.metaData.getString(key);
                        } catch (Exception e) {// 渠道号有可能是 int
                            value = "" + applicationInfo.metaData.getInt(key);
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            return value;
        }
        return value;
    }

    /**
     * 获取 颜色资源的值
     *
     * @return
     */
    public static int getResColorValue(Context context, int colorId) {
        try {
            return (context != null ? context : MyApplicaion.getContext()).getResources().getColor(colorId);
        } catch (Resources.NotFoundException e) {
            return Color.TRANSPARENT;
        }
    }

    /**
     * 获取 颜色资源的值
     *
     * @return
     */
    public static int getResColorValue(int colorId) {
        try {
            return MyApplicaion.getContext().getResources().getColor(colorId);
        } catch (Resources.NotFoundException e) {
            return Color.TRANSPARENT;
        }
    }

    //num 要转换的数 from源数的进制 to要转换成的进制
    public static String change(String num, int from, int to) {
        try {
            return new java.math.BigInteger(num, from).toString(to);
        } catch (Exception e) {
            return null;
        }
    }


    // 是否允许通知
    public static boolean isNotificationEnabled(Context context) {
        boolean isOpened = false;
        try {
            isOpened = NotificationManagerCompat.from(context).areNotificationsEnabled();
        } catch (Exception e) {
            e.printStackTrace();
            isOpened = false;
        }
        return isOpened;

    }

    // 打开设置页面
    public static void goToSettingActivity(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            // android 8.0引导
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) {
            // android 5.0-7.0
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else {
            // 其他
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
