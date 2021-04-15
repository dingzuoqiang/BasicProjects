package com.ding.basic.ui.fragment;

import android.os.Handler;
import android.view.View;

import com.ding.basic.R;
import com.ding.basic.ui.LazyBaseFragment;
import com.ding.basic.utils.LogUtil;

public class MyFragment extends LazyBaseFragment {

    @Override
    protected int getFgLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initFgBaseView(View view) {
        LogUtil.e("ddd", "my->initFgBaseView");
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        lazyLoad();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();

    }

    @Override
    public void lazyLoad() {
        showProcess();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeProcess();
            }
        }, 3000);
    }
}
