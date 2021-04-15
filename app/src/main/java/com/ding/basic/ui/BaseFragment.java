package com.ding.basic.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ding.basic.utils.LoadAnimationUtils;
import com.ding.basic.utils.LogUtil;
import com.ding.basic.utils.MessageEvent;
import com.ding.basic.utils.ToastTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ding on 2020/04/17.
 */
public abstract class BaseFragment extends Fragment {

    private boolean isEventBusRegister = false;
    private LoadAnimationUtils loadAnimationUtils;

    protected abstract int getFgLayoutId();

    protected abstract void initFgBaseView(View view);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFgLayoutId(), container, false);
        loadAnimationUtils = new LoadAnimationUtils(getContext());
        initFgBaseView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isEventBusRegister) {
            EventBus.getDefault().register(this);
            isEventBusRegister = true;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        LogUtil.e(getClass(), "onMoonEvent--");
        if (messageEvent == null) return;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        isEventBusRegister = false;
    }

    public void showToast(String content) {
        ToastTool.showToast(getContext(), content);
    }

    public void showToast(int resId) {
        ToastTool.showToast(getContext(), resId);
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

}
