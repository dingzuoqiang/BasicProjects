package com.ding.basic.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.ding.basic.utils.GsonUtil;

import org.json.JSONException;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;

public class UserCallback<T> implements Callback.CommonCallback<String> {
    private String tag = "UserCallback";
    private Context context;
    private String path;
    private Class<T> dataBean;
    private RetrofitListener<T> listener;

    public UserCallback(Context context, String path, Class<T> dataBean, RetrofitListener<T> listener) {
        this.context = context;
        this.path = path;
        this.dataBean = dataBean;
        this.listener = listener;
    }

    @Override
    public void onSuccess(String result) {
        Log.e(tag, "=====" + path + "====\n" + result);
        try {
            T data = GsonUtil.getGson().fromJson(result, dataBean);
            //请求成功并解析成功反馈结果
            if (listener != null) {
                listener.onSuccess(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //解析出错
//                    if(listener != null){
//                        listener.onError("解析异常！");
//                    }
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        Log.e(tag, "=====" + path + "=  onError===" + ex.getMessage());
        if (ex != null)
            Log.e(tag, "=====" + path + "=  onError===" + GsonUtil.toJsonStr(ex));

        //请求错误反馈信息
        // Caused by: java.lang.ClassCastException: java.net.ConnectException cannot be cast to org.xutils.ex.HttpException
        // Caused by: java.lang.ClassCastException: java.net.SocketTimeoutException cannot be cast to org.xutils.ex.HttpException
        // Caused by: java.lang.ClassCastException: java.net.UnknownHostException cannot be cast to org.xutils.ex.HttpException
        if (listener != null) {
            String a = "";
            if (ex instanceof HttpException) {
                HttpException e = (HttpException) ex;
                try {
                    org.json.JSONObject obj = new org.json.JSONObject(e.getResult());
                    a = obj.getString("msg");
                } catch (JSONException exc) {
                    exc.printStackTrace();
                }
            } else {
                a = "请求失败";
            }
            if (TextUtils.isEmpty(a) || a.equals("null")) {
                a = "服务器出错";
            }
            listener.onError(a);
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        //取消请求反馈信息
        if (listener != null) {
            listener.onError(cex.getMessage());
        }
    }

    @Override
    public void onFinished() {
        //关闭刷新
        if (listener != null) {
            listener.closeRefresh();
        }
    }
}
