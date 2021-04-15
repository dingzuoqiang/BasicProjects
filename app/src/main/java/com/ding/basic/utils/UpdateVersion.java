package com.ding.basic.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.ding.basic.app.MyApplicaion;
import com.ding.basic.http.Urls;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;
import com.vector.update_app.utils.AppUpdateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateVersion {

    private static boolean isup;

    public static void updateDiy(final Activity mContext) {
//
        Map<String, String> params = new HashMap<String, String>();

        params.put("appKey", "ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f");
        params.put("appVersion", AppUpdateUtils.getVersionName(mContext));
//        params.put("appCode", AppUpdateUtils.getVersionCode(mContext) + "");
        params.put("key1", "value2");
        params.put("key2", "value3");


        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(mContext)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new UpdateAppHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(Urls.getVersion())
                //全局异常捕获
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                //以下设置，都是可选
                //设置请求方式，默认get
                .setPost(false)
                //不显示通知栏进度条
//                .dismissNotificationProgress()
                //是否忽略版本
//               .showIgnoreVersion()
                //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
                .setParams(params)
                //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度，如果是强制更新，则设置无效
//                .hideDialogOnDownloading()
                //设置头部，不设置显示默认的图片，设置图片后自动识别主色调，然后为按钮，进度条设置颜色
//                .setTopPic(R.mipmap.top_8)
                //为按钮，进度条设置颜色。
                .setThemeColor(0xffffac5d)
                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
//                .setTargetPath(path)
                //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
//                .setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")
                .setUpdateDialogFragmentListener(new IUpdateDialogFragmentListener() {
                    @Override
                    public void onUpdateNotifyDialogCancel(UpdateAppBean updateApp) {
                        //用户点击关闭按钮，取消了更新，如果是下载完，用户取消了安装，则可以在 onActivityResult 监听到。

                    }
                })
                //不自动，获取
//                .setIgnoreDefParams(true)
                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {
//                            JSONObject jsonObject = new JSONObject(json);
//                            final String newVersion = jsonObject.optString("new_version");
                            JSONObject jsonObject = new JSONObject(json);
                            JSONObject mJson = jsonObject.getJSONObject("data");
                            String update = mJson.getString("update");
                            String new_version = mJson.getString("android_new_version");
                            LogUtil.e(UpdateVersion.class, new_version);

                            String apk_file_url = mJson.getString("apk_file_url");
                            String update_log = mJson.getString("update_log");
                            int isUpdate = mJson.getInt("isUpdate");
                            if (isUpdate == 1) {
                                isup = true;
                            } else {
                                isup = false;
                            }
                            updateAppBean
                                    //（必须）是否更新Yes,No
                                    .setUpdate(mJson.getString("update"))
                                    //（必须）新版本号，
                                    .setNewVersion(new_version)
                                    //（必须）下载地址
                                    .setApkFileUrl(apk_file_url)
                                    //测试下载路径是重定向路径
//                                    .setApkFileUrl("http://openbox.mobilem.360.cn/index/d/sid/3282847")
//                                    .setUpdateDefDialogTitle(String.format("AppUpdate 是否升级到%s版本？", newVersion))
                                    //（必须）更新内容
//                                    .setUpdateLog(jsonObject.optString("update_log"))
                                    //测试内容过度
//                                    .setUpdateLog("测试")
                                    .setUpdateLog(update_log)
                                    //大小，不设置不显示大小，可以不设置
//                                    .setTargetSize(jsonObject.optString("target_size"))
                                    //是否强制更新，可以不设置
                                    .setConstraint(isup);
                            //设置md5，可以不设置
//                                    .setNewMd5(jsonObject.optString("new_md5"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();

////                        //自定义对话框
//                        showDiyDialog(updateApp, updateAppManager);
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {

//                        CProgressDialogUtils.showProgressDialog(mContext);
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
//                        CProgressDialogUtils.cancelProgressDialog(mContext);
                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp(String error) {
//                        CProgressDialogUtils.cancelProgressDialog(mContext);
//                        Toast.makeText(mContext, "当前是最新版", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /**
     * 自定义对话框
     *
     * @param updateApp
     * @param updateAppManager
     */
    private static void showDiyDialog(final UpdateAppBean updateApp, final UpdateAppManager updateAppManager) {
        String targetSize = updateApp.getTargetSize();
        String updateLog = updateApp.getUpdateLog();

        String msg = "";

        if (!TextUtils.isEmpty(targetSize)) {
            msg = "新版本大小：" + targetSize + "\n\n";
        }

        if (!TextUtils.isEmpty(updateLog)) {
            msg += updateLog;
        }

        new AlertDialog.Builder(MyApplicaion.getContext())
                .setTitle(String.format("是否升级到%s版本？", updateApp.getNewVersion()))
                .setMessage(msg)
                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //不显示下载进度
                        updateAppManager.download();

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

}
