package com.ding.basic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.ding.basic.app.MyApplicaion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SpUtil {
    private static final String FILENAME = "ding";
    public static final String KEY_TOKEN = "key_token";// 登录TOKEN
    public static final String KEY_IS_FIRST_OPEN = "key_is_first_open";// 第一次打开app
    public static final String KEY_OPEN_NOTIFICATION = "key_open_notification";// 打开了允许通知
    public static final String KEY_OPEN_NOTIFICATION_TIME = "key_open_notification_time";// pop通知时间 一天提示一次

    private static String getFileName(Context context) {
//        2020-03-18 19:58:02.853 32575-32575/com.qqsk E/dzq: GlobalApp.getContext().getPackageName() + FILENAME = com.qqskHFKJ
//        2020-03-18 19:58:02.853 32575-32575/com.qqsk E/dzq: context.getPackageName() + FILENAME = com.qqskHFKJ
//        if (context != null) {
//            return context.getPackageName() + FILENAME;
//        } else
//            return GlobalApp.getContext().getPackageName() + FILENAME;

        return "app_sp_" + FILENAME;
    }

    public static String getString(Context context, String key, String defValue) {
        return getSharedPreferences(context).getString(key, defValue);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putString(key, value);
        edit.commit(); // 保存数据信息
    }

    public static int getInt(Context context, String key, int defValue) {
        return getSharedPreferences(context).getInt(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putInt(key, value);
        edit.commit(); // 保存数据信息
    }

    public static long getLong(Context context, String key, long defValue) {
        return getSharedPreferences(context).getLong(key, defValue);
    }

    public static void putLong(Context context, String key, long value) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putLong(key, value);
        edit.commit(); // 保存数据信息
    }

    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putBoolean(key, value);
        edit.commit(); // 保存数据信息
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        if (context != null)
            return context.getSharedPreferences(getFileName(context), Context.MODE_MULTI_PROCESS);
        else
            return MyApplicaion.getContext().getSharedPreferences(
                    getFileName(MyApplicaion.getContext()), Context.MODE_MULTI_PROCESS);
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                getFileName(context), Context.MODE_MULTI_PROCESS);
    }


    /**
     * 存放实体类以及任意类型
     *
     * @param context 上下文对象
     * @param key
     * @param obj
     */
    public static void putBean(Context context, String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(), 0));
                SharedPreferences.Editor editor = getSharedPreferences(context).edit();
                editor.putString(key, string64).commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException("the obj must implement Serializble");
        }

    }

    public static Object getBean(Context context, String key) {
        Object obj = null;
        try {
            String base64 = getSharedPreferences(context).getString(key, "");
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}