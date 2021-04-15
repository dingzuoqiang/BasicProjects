package com.ding.basic.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.ding.basic.app.MyApplicaion;

import java.io.IOException;
import java.net.URL;

public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = getDpScale(context);
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = getDpScale(context);
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = getScale(context);
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = getScale(context);
        return (int) (spValue * fontScale + 0.5f);
    }

    // dp 转换时使用 density
    private static float getDpScale(Context context) {
        return (context != null ? context : MyApplicaion.getContext()).getResources().getDisplayMetrics().density;
    }

    // sp 转换时使用 scaledDensity
    private static float getScale(Context context) {
        final float fontScale = (context != null ? context : MyApplicaion.getContext()).getResources().getDisplayMetrics().scaledDensity;
//        return findScale(fontScale);
        return fontScale;
    }

    public static Drawable loadImageFromNetwork(String imageUrl) {
        Drawable drawable = null;
        try {
            // 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), "image.jpg");
        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "not null drawable");
        }

        return drawable;
    }
}
