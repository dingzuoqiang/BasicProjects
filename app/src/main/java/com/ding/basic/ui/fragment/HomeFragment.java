package com.ding.basic.ui.fragment;

import android.os.Handler;
import android.view.View;

import com.ding.basic.R;
import com.ding.basic.ui.LazyBaseFragment;
import com.ding.basic.utils.LogUtil;

public class HomeFragment extends LazyBaseFragment {

    @Override
    protected int getFgLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initFgBaseView(View view) {
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        lazyLoad();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        showToast("首页可见");
        LogUtil.e("ddd", "home->onVisible");

    }

    @Override
    protected void onInvisible() {
        super.onInvisible();

    }

    @Override
    public void lazyLoad() {
        LogUtil.e("ddd", "home->lazyLoad");
        showProcess();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeProcess();
            }
        }, 3000);

    }
}
