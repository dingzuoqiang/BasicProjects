package com.ding.basic.http;

import android.content.Context;
import android.webkit.WebSettings;

import com.ding.basic.app.MyApplicaion;
import com.ding.basic.utils.GsonUtil;
import com.ding.basic.utils.LogUtil;
import com.ding.basic.utils.SpUtil;
import com.ding.basic.utils.TDevice;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Controller {

    private static Map<String, String> headerParams = new HashMap<>();

    public static void resetHeaderParams(Context context) {
        headerParams.clear();
        getHeaderParams(context);
    }

    private static Map<String, String> getHeaderParams(Context context) {
        if (headerParams == null) {
            headerParams = new HashMap<>();
        }
        headerParams.put("token", SpUtil.getString(context, SpUtil.KEY_TOKEN, ""));
        headerParams.put("Client-Version", TDevice.getVersionName());
        headerParams.put("Client-Channel", "ANDROID_QQSK");
//        String shopId = CommonUtils.getShareId();
//        if (!TextUtils.isEmpty(shopId)) {
//            headerParams.put("Shop-Id", shopId);
//        }
        headerParams.put("User-Agent", WebSettings.getDefaultUserAgent(context != null ? context : MyApplicaion.getContext()));
        return headerParams;
    }

    // 设置请求头参数
    private static void addHeaderParams(Context context, RequestParams params) {
        addHeaderParams(context, params, getHeaderParams(context));
    }

    // 设置请求头参数
    private static void addHeaderParams(Context context, RequestParams params, Map<String, String> headerParams) {
        if (params != null && headerParams != null) {
            Set<String> headSet = headerParams.keySet();
            for (String hs : headSet) {
                LogUtil.e("ding", "======addHeaderParams====key===" + hs + "=======value===" + headerParams.get(hs));
                params.addHeader(hs, headerParams.get(hs));
            }
        }
    }

    /**
     * GET获取数据
     */
    public static synchronized <T> void getMyData(Context context, final String path, Map maps, final Class<T> dataBean, final RetrofitListener<T> listener) {
        RequestParams params = new RequestParams(path);
        if (maps != null) {
            //组装常规参数
            Set<String> set = maps.keySet();
            for (String s : set) {
                Object value = maps.get(s);
                params.addBodyParameter(s, value);
                LogUtil.e("ding", "======普通参数====key===" + s + "=======value===" + value);
            }
        }
        addHeaderParams(context, params);
        x.http().get(params, new UserCallback(context, path, dataBean, listener));
    }

    /**
     * POST获取数据
     * 发送json 请求
     */
    public static synchronized <T> void postMyData(Context context, final String path, Map maps, final Class<T> dataBean, final RetrofitListener<T> listener) {
        RequestParams params = new RequestParams(path);
        params.setAsJsonContent(true);
        Map map = new HashMap();
        if (maps != null) {
            //组装常规参数
            Set<String> set = maps.keySet();
            for (String s : set) {
                Object value = maps.get(s);
//                params.addBodyParameter(key, value);
                map.put(s, value);
                LogUtil.e("ding", "======普通参数====key===" + s + "=======value===" + value);
            }
        }
        String param = GsonUtil.toJsonStr(map);
        params.setBodyContent(param);
        addHeaderParams(context, params);
        x.http().post(params, new UserCallback(context, path, dataBean, listener));
    }

    /**
     * POST获取数据
     */
    public static synchronized <T> void postMyData1(Context context, final String path, Map maps, final Class<T> dataBean, final RetrofitListener<T> listener) {
        RequestParams params = new RequestParams(path);
//        params.setAsJsonContent(true);
        if (maps != null) {
            //组装常规参数
            Set<String> set = maps.keySet();
            for (String s : set) {
                Object value = maps.get(s);
                params.addBodyParameter(s, value);
                LogUtil.e("ding", "======普通参数====key===" + s + "=======value===" + value);
            }
        }
        addHeaderParams(context, params);
        x.http().post(params, new UserCallback(context, path, dataBean, listener));
    }

    /**
     * POST获取数据
     */
    public static synchronized <T> void postMyData1(Context context, final String path, Map maps, Map<String, String> headerParams, final Class<T> dataBean, final RetrofitListener<T> listener) {
        RequestParams params = new RequestParams(path);
//        params.setAsJsonContent(true);
        if (maps != null) {
            //组装常规参数
            Set<String> set = maps.keySet();
            for (String s : set) {
                Object value = maps.get(s);
                params.addBodyParameter(s, value);
                LogUtil.e("ding", "======普通参数====key===" + s + "=======value===" + value);
            }
        }
        addHeaderParams(context, params, headerParams);
        x.http().post(params, new UserCallback(context, path, dataBean, listener));
    }

    /**
     * POST获取数据
     */
    public static synchronized <T> void postMyData2(Context context, final String path, Map maps, final Class<T> dataBean, final RetrofitListener<T> listener) {
        RequestParams params = new RequestParams(path);
        params.setAsJsonContent(true);
        if (maps != null) {
            //组装常规参数
            Set<String> set = maps.keySet();
            for (String s : set) {
                Object value = maps.get(s);
//                params.addBodyParameter(key, value);
                params.addQueryStringParameter(s, value);
                LogUtil.e("ding", "======普通参数====key===" + s + "=======value===" + value);
            }
        }
        addHeaderParams(context, params);
        x.http().post(params, new UserCallback(context, path, dataBean, listener));
    }

    /**
     * PUT获取数据
     */
    public static synchronized <T> void putMyData(Context context, final String path, Map maps, final Class<T> dataBean, final RetrofitListener<T> listener) {
        RequestParams params = new RequestParams(path);
        params.setAsJsonContent(true);
        Map map = new HashMap();
        if (maps != null) {
            //组装常规参数
            Set<String> set = maps.keySet();
            for (String s : set) {
                Object value = maps.get(s);
//                params.addBodyParameter(key, value);
                map.put(s, value);
                LogUtil.e("ding", "======普通参数====key===" + s + "=======value===" + value);
            }
        }
        String param = GsonUtil.toJsonStr(map);
        params.setBodyContent(param);
        addHeaderParams(context, params);
        x.http().request(HttpMethod.PUT, params, new UserCallback(context, path, dataBean, listener));
    }

    public static synchronized <T> void putMyData1(Context context, final String path, String content, final Class<T> dataBean, final RetrofitListener<T> listener) {
        RequestParams params = new RequestParams(path);
        params.setAsJsonContent(true);
        LogUtil.e("ding", "======普通参数" + content);
        params.setBodyContent(content);
        addHeaderParams(context, params);
        x.http().request(HttpMethod.PUT, params, new UserCallback(context, path, dataBean, listener));
    }

    public static synchronized <T> void putMyData2(Context context, final String path, Map<String, String> maps, final Class<T> dataBean, final RetrofitListener<T> listener) {
        RequestParams params = new RequestParams(path);
        params.setAsJsonContent(true);
        if (maps != null) {
            //组装常规参数
            Set<String> set = maps.keySet();
            for (String s : set) {
                String value = maps.get(s);
//                params.addBodyParameter(key, value);
                LogUtil.e("ding", "======普通参数====key===" + s + "=======value===" + value);
                params.addQueryStringParameter(s, value);
            }
        }
        addHeaderParams(context, params);
        x.http().request(HttpMethod.PUT, params, new UserCallback(context, path, dataBean, listener));
    }

    public static synchronized <T> void DeleteMyData(Context context, final String path, Map maps, final Class<T> dataBean, final RetrofitListener<T> listener) {
        RequestParams params = new RequestParams(path);
        params.setAsJsonContent(true);
        Map map = new HashMap();
        if (maps != null) {
            //组装常规参数
            Set<String> set = maps.keySet();
            for (String s : set) {
                String key = s;
                Object value = maps.get(s);
                map.put(key, value);
                LogUtil.e("ding", "======普通参数====key===" + s + "=======value===" + value);
            }
        }
        String param = GsonUtil.toJsonStr(map);
        params.setBodyContent(param);
        addHeaderParams(context, params);
        x.http().request(HttpMethod.DELETE, params, new UserCallback(context, path, dataBean, listener));
    }
}
