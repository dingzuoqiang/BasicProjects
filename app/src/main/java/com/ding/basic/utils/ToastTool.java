package com.ding.basic.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ding.basic.R;


public class ToastTool {
    final static String TAG = "ToastTool";
    private static final int LONG_DELAY = 3500; // 3.5 seconds
    private static final int SHORT_DELAY = 2000; // 2 seconds
    private static String oldMsg;

    private static long lastTime = 0;

    private Toast toast = null;
    private View layoutToast;
    private TextView tvToast;
    private static ToastTool sToastTool = new ToastTool();

    public static synchronized void showToast(Context context, int resId) {
        if (context != null) {
            showToast(context, context.getResources().getString(resId));
        }
    }

    public static synchronized void showToast(Context context, String content) {//  0
        cancelToast();
        if (context == null) return;
        if (sToastTool.toast == null) {
            sToastTool.toast = new Toast(context);
            sToastTool.toast.setGravity(Gravity.CENTER, 0, 0);
            sToastTool.layoutToast = LayoutInflater.from(context).inflate(R.layout
                    .custom_toast_view, null);

            sToastTool.tvToast = (TextView) sToastTool.layoutToast.findViewById(R.id
                    .custom_toast_tv);
            sToastTool.tvToast.setText(content);
            sToastTool.toast.setView(sToastTool.layoutToast);
            sToastTool.toast.setDuration(Toast.LENGTH_SHORT);
            sToastTool.toast.show();
            lastTime = System.currentTimeMillis();
            oldMsg = content;
        } else {
            //小于Toast的显示时间
            if (System.currentTimeMillis() - lastTime <= SHORT_DELAY) {
                sToastTool.tvToast.setText(content);
                oldMsg = content;
                sToastTool.toast.setDuration(Toast.LENGTH_SHORT);
                sToastTool.toast.show();
                lastTime = System.currentTimeMillis();
            } else {
                sToastTool.tvToast.setText(content);
                sToastTool.toast.setDuration(Toast.LENGTH_SHORT);
                sToastTool.toast.show();
                lastTime = System.currentTimeMillis();
            }
        }
    }

    public static synchronized void cancelToast() {
        if (null != sToastTool.toast) {
            sToastTool.toast.cancel();
        }
        sToastTool.toast = null;
        sToastTool.tvToast = null;
        sToastTool.layoutToast = null;
    }

}
