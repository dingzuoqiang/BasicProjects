package com.ding.basic.ui;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ding.basic.R;
import com.ding.basic.app.AppManager;
import com.ding.basic.ui.fragment.HomeFragment;
import com.ding.basic.ui.fragment.MyFragment;
import com.ding.basic.utils.CommonUtils;
import com.ding.basic.utils.DingDialogUtil;
import com.ding.basic.utils.SpUtil;
import com.ding.basic.utils.TimeUtlis;
import com.ding.basic.utils.UpdateVersion;
import com.ding.basic.widget.NoScorllViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private int INSTALL_PERMISS_CODE = 3000;

    @BindView(R.id.vp_content)
    NoScorllViewPager vpContent;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_my)
    RadioButton rbMy;
    @BindView(R.id.rg_content)
    RadioGroup rgContent;

    public List<LazyBaseFragment> mFragments;
    private int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.transparencyBar(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }

//        openNotification();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setInstallPermission();
        } else {
            UpdateVersion.updateDiy(this);
        }

        mFragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        mFragments.add(0, homeFragment);
        MyFragment myFragment = new MyFragment();
        mFragments.add(1, myFragment);
        vpContent.setOffscreenPageLimit(2);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager());
        vpContent.setAdapter(pageAdapter);
        rgContent.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if (checkedId == R.id.rb_home) {
                currentItem = 0;
            } else if (checkedId == R.id.rb_my) {
                currentItem = 1;
            }
//            if (currentItem == 0) {
//                StatusBarUtil.StatusBarLightMode(this);
//            } else
//                StatusBarUtil.StatusBarDarkMode(this);
            vpContent.setCurrentItem(currentItem, false);
        });
        rbHome.setChecked(true);
    }

    // 引导打开允许通知
    private void openNotification() {
        boolean open = SpUtil.getBoolean(this, SpUtil.KEY_OPEN_NOTIFICATION, false);
        String time = TimeUtlis.ms2DateOnlyDay(System.currentTimeMillis());
        if (!time.equals(SpUtil.getString(this, SpUtil.KEY_OPEN_NOTIFICATION_TIME, ""))) {
            open = false;
            SpUtil.putString(this, SpUtil.KEY_OPEN_NOTIFICATION_TIME, time);
        }
        if (!open) {
            if (!CommonUtils.isNotificationEnabled(this)) {
                DingDialogUtil.showDialog(mActivity, "开启消息通知提醒", "平台直播等信息第一时间了解", (dialog, which) -> {
                    CommonUtils.goToSettingActivity(mActivity);
                }, null, false);
                SpUtil.putBoolean(this, SpUtil.KEY_OPEN_NOTIFICATION, true);
            }
        }
    }

    public void setInstallPermission() {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先判断是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {
                //弹框提示用户手动打开
                DingDialogUtil.showDialog(mActivity, "安装权限", "需要打开允许来自此来源，请去设置中开启此权限。", (dialog, which) -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        //此方法需要API>=26才能使用
                        toInstallPermissionSettingIntent();
                    }
                }, (dialog, which) -> {
                    UpdateVersion.updateDiy(MainActivity.this);
                }, false);
            } else {
                UpdateVersion.updateDiy(MainActivity.this);
            }
        }
    }

    /**
     * 开启安装未知来源权限
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toInstallPermissionSettingIntent() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, INSTALL_PERMISS_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INSTALL_PERMISS_CODE) {
            UpdateVersion.updateDiy(this);
        }
    }


    class PageAdapter extends FragmentStatePagerAdapter {


        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    private boolean mExitFlag;// 退出标记

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_UP) {
            if (mExitFlag) {
                AppManager.getAppManager().appExit(this);
            } else {
                showToast(R.string.app_will_exit);
                mExitFlag = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mExitFlag = false;
                    }
                }, 3000);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
