package com.ding.basic.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.ding.basic.R;
import com.ding.basic.ui.activity.LoginActivity;
import com.ding.basic.ui.activity.WelcomeActivity;
import com.ding.basic.utils.CommonUtils;
import com.ding.basic.utils.SpUtil;

import java.lang.ref.WeakReference;


/**
 * Created by ding on 2020/04/17.
 */

public class SplashActivity extends Activity {
    private Handler mStaticHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //加载用户信息
        if (isTaskRoot()) {
            if (isLogin()) {
                loadData();
            }
            mStaticHandler = new StaticHandler(this, this);
            mStaticHandler.sendEmptyMessageDelayed(0, 3000);
        } else {
            finish();
            return;
        }
    }

    private static class StaticHandler extends Handler {
        private final WeakReference<Context> weakContext;
        private final WeakReference<SplashActivity> mParent;

        public StaticHandler(Context context, SplashActivity view) {
            weakContext = new WeakReference<>(context);
            mParent = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            Context context = weakContext.get();
            SplashActivity parent = mParent.get();
            if (null != context && null != parent) {
                parent.initGo();
                super.handleMessage(msg);
            }
        }
    }

    private void initGo() {
        //判断是否是第一次开启应用
        boolean isFirstOpen = SpUtil.getBoolean(SplashActivity.this, SpUtil.KEY_IS_FIRST_OPEN, false);
        //如果是首次启动的话进入引导页
        if (!isFirstOpen) {
            CommonUtils.openActicity(SplashActivity.this, WelcomeActivity.class, null, true);
        } else {
//            SpUtil.putString(this, SpUtil.KEY_TOKEN, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJpc3MiOiJxcXNrIiwiZXhwIjoxNTg0MDg3ODg3LCJ1c2VySWQiOiIxMjY5MzIzMyIsImlhdCI6MTU4MzQ4MzA4N30.JedfhWEdro061_HO_FW-JmApZByPDzbKFejfk_O_GNo");
            if (isLogin()) {
                CommonUtils.openActicity(SplashActivity.this, MainActivity.class, null, true);
            } else {
                CommonUtils.openActicity(SplashActivity.this, LoginActivity.class, null, true);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadData() {
//        //店铺信息
//        Controller2.getMyData(this, Constants.SELECTUSERMEMBERINFO1, null, SelectUserMemberInfoBean.class, this);
//        //请求广告页
//        Controller2.getMyData(this, Constants.GET_BILL_IMG, null, BillBean.class, this);
    }

//    @Override
//    public void onSuccess(Object data) {
//
//        if (data instanceof BillBean) {
//            billBean = (BillBean) data;
//        }
//
//        if (data instanceof SelectUserMemberInfoBean) {
//            selectUserMemberInfoBean = (SelectUserMemberInfoBean) data;
//        }
//
//        if (selectUserMemberInfoBean != null) {
//            if (selectUserMemberInfoBean.getStatus() != 200) return;
//
//            //存储基本对象数据
//            UserSession userSession = new UserSession(selectUserMemberInfoBean.getData().getUserMemberRole(),
//                    selectUserMemberInfoBean.getData().getUserId() + "",
//                    selectUserMemberInfoBean.getData().getShopName(),
//                    selectUserMemberInfoBean.getData().getHeadImgUrl(),
//                    selectUserMemberInfoBean.getData().getParentUserId() + "",
//                    selectUserMemberInfoBean.getData().getParentImgUrl(),
//                    selectUserMemberInfoBean.getData().getParentShopName(), selectUserMemberInfoBean.getData().getChannelCode(),
//                    selectUserMemberInfoBean.getData().getOwnerName(), selectUserMemberInfoBean.getData().getUserName(), selectUserMemberInfoBean.getData().getParentOwerName(),
//                    selectUserMemberInfoBean.getData().getParentLoginMobile());
//            Constants.userSession = userSession;
//            SharedPreferencesUtil.putBean(this, SharedPreferencesUtil.KEY_USER_SESSION, userSession);
//        }
//
//        if (selectUserMemberInfoBean != null && billBean != null) {
//            if (billBean.getData() == null) {
//                Intent intent = new Intent();
//                intent.setClass(SplashActivity.this, MainActivity.class);
//                startActivity(intent);
//                //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
//                finish();
//            } else {
//                Intent intent = new Intent();
//                intent.setClass(SplashActivity.this, BillPageActivity.class);
//                intent.putExtra("billData", billBean);
//                startActivity(intent);
//                finish();
//            }
//
//        }
//    }
//
//    @Override
//    public void onError(String description) {
//
//    }
//
//    @Override
//    public void closeRefresh() {
//
//    }

    /*
     * 是否有token
     * */
    public boolean isLogin() {
        String token = SpUtil.getString(this, SpUtil.KEY_TOKEN, "");
        return (!TextUtils.isEmpty(token) && !"null".equals(token));
    }

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 清空Handler 消息,防止多次跳转
            mStaticHandler.removeCallbacksAndMessages(null);
        }
        return super.onKeyDown(keyCode, event);
    }

}