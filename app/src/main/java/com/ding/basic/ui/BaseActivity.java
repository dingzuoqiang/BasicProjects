package com.ding.basic.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ding.basic.R;
import com.ding.basic.app.AppManager;
import com.ding.basic.utils.BindLayout;
import com.ding.basic.utils.LoadAnimationUtils;
import com.ding.basic.utils.LogUtil;
import com.ding.basic.utils.MessageEvent;
import com.ding.basic.utils.SpUtil;
import com.ding.basic.utils.StatusBarUtil;
import com.ding.basic.utils.ToastTool;
import com.ding.basic.widget.BaseTitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by dingzuoqiang on 2017/07/10.
 * Email: 530858106@qq.com
 */

public abstract class BaseActivity extends AppCompatActivity {

    public String userId = "";
    public Activity mActivity = null;
    public boolean isFirstOnResume = true;
    protected BaseTitleBar baseTitleBar;
    private ViewGroup rootView;
    protected ImageView imvLeftBack, imvShare, imvCollect;
    protected TextView tvTitle, tvRightTitle;
    protected boolean isOnResume = true;
    protected boolean isCanRequest = true;// true 可以请求
    protected boolean notSetStatusBarTintResource = false;// 是否 不改变状态栏
    protected int statusBarTintResource;
    protected boolean fitSystemWindows = true;

    public String classSimpleName;
    protected Unbinder mBind;
    private LoadAnimationUtils loadAnimationUtils;

    protected abstract int getLayoutId();

    protected abstract void initEventAndData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classSimpleName = getClass().getSimpleName();
        LogUtil.e(getClass(), "classSimpleName = " + classSimpleName);
        statusBarTintResource = R.color.color_statusbar_bg;

        if (!notSetStatusBarTintResource) {
//            StatusBarUtil.transparencyBar(this);
            StatusBarUtil.setStatusBarColor(this, statusBarTintResource);
            StatusBarUtil.StatusBarLightMode(this);
        }
        mActivity = this;
//        if (!CommonUtil.isNetworkConnected(this)) {
//            showToast(R.string.noInternet);
//        }
        AppManager.getAppManager().addActivity(this);
        EventBus.getDefault().register(this);
        setContentView(getLayoutId() > 0 ? getLayoutId() : getBindLayoutId());
        setFitsSystemWindows(fitSystemWindows);
        mBind = ButterKnife.bind(this);
        loadAnimationUtils = new LoadAnimationUtils(this);
        initBaseTitleBar();
        initEventAndData();
    }

    public int getBindLayoutId() {
        BindLayout bindLayout = this.getClass().getAnnotation(BindLayout.class);
        return bindLayout.value();
    }

    private void setFitsSystemWindows(boolean fitSystemWindows) {
        ViewGroup contentFrameLayout = findViewById(Window.ID_ANDROID_CONTENT);
        if (contentFrameLayout != null) {
            View parentView = contentFrameLayout.getChildAt(0);
            if (parentView != null && Build.VERSION.SDK_INT >= 14) {
                parentView.setFitsSystemWindows(fitSystemWindows);
                // 作用 同 在 布局文件 根布局 里  添加 android:fitsSystemWindows="true"
            }
        }
    }

    public void initBaseTitleBar() {
        baseTitleBar = findViewById(R.id.base_title_bar);
        if (baseTitleBar != null) {
            imvLeftBack = baseTitleBar.getImvLeftBack();
            tvTitle = baseTitleBar.getTvTitle();
            imvShare = baseTitleBar.getImvShare();
            imvCollect = baseTitleBar.getImvCollect();
            tvRightTitle = baseTitleBar.getTvRightText();
            if (!TextUtils.isEmpty(getTitle()))
                baseTitleBar.setTitle(getTitle());

            baseTitleBar.setLayLeftBackClickListener(view -> imgBack());
            baseTitleBar.setImvCollectClickListener(view -> imgCollect());
            baseTitleBar.setImvShareClickListener(view -> imgShare());
            baseTitleBar.setTvRightTextClickListener(view -> tvRightBtn());
        }
    }

    protected void imgBack() {
        finish();
    }

    protected void imgCollect() {

    }

    protected void imgShare() {

    }

    protected void tvRightBtn() {

    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (tvTitle != null)
            tvTitle.setText(titleId);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (tvTitle != null)
            tvTitle.setText(title);
    }

    public void getLoginData() {

    }


    public void onResume() {
        super.onResume();
        isOnResume = true;
        getLoginData();
    }


//    public boolean isLogin() {
//        return userInfoVO != null;
//    }

    public void onPause() {
        super.onPause();
        isOnResume = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (mBind != null) {
            mBind.unbind();
        }
//        System.gc();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        LogUtil.e(getClass(), "onMoonEvent--");
        if (messageEvent == null) return;
    }

    public void showToast(String message) {
        try {
            ToastTool.showToast(getApplicationContext(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast(int msgResId) {
        try {
            ToastTool.showToast(getApplicationContext(), getString(msgResId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProcess() {
        showProcess(null);
    }

    public void showProcess(String content) {
        loadAnimationUtils.showProcess(content);
    }

    public void closeProcess() {
        loadAnimationUtils.closeProcess();
    }

    /*
     * 是否有token
     * */
    public boolean isLogin() {
        String token = SpUtil.getString(this, SpUtil.KEY_TOKEN, "");
        return (!TextUtils.isEmpty(token) && !"null".equals(token));
    }

//    public void openLoginActicity(BaseResultBean resultBean) {
//        openLoginActicity(resultBean, true);
//
//    }
//
//    public void openLoginActicity(BaseResultBean resultBean, boolean showToast) {
//        if (resultBean == null) return;
//        if (resultBean.code == ResultCodeEnum.TOKEN_INVALID.getType()) {// token 过期
//            if (showToast)
//                showToast(resultBean.resultMsg);
//            CommonUtil2.clearLoginData();
//            CommonUtil2.goToLogin(mActivity);
//        } else if (resultBean.code == ResultCodeEnum.USER_BANNED.getType()) {// 用户被禁言
//            showToast(R.string.string_banned_message);
//        } else {
//            if (showToast)
//                showToast(resultBean.resultMsg);
//        }
//
//    }


}
