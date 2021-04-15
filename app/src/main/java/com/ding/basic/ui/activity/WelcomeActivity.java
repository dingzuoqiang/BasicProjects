package com.ding.basic.ui.activity;


import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ding.basic.R;
import com.ding.basic.ui.BaseActivity;
import com.ding.basic.utils.CommonUtils;
import com.ding.basic.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    @BindView(R.id.v_point1)
    View vPoint1;
    @BindView(R.id.v_point2)
    View vPoint2;
    @BindView(R.id.v_point3)
    View vPoint3;
    @BindView(R.id.v_point4)
    View vPoint4;
    View vLogin;

    private List<View> pointViews;
    private ViewPager vp;
    private GuideViewPagerAdapter adapter;
    // 引导页图片资源
    private static final int[] pics = {R.layout.guid_view1,
            R.layout.guid_view2, R.layout.guid_view3, R.layout.guid_view4};
    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fitSystemWindows = false;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initEventAndData() {
        SpUtil.putBoolean(this, SpUtil.KEY_IS_FIRST_OPEN, true);
        ButterKnife.bind(this);

        pointViews = new ArrayList<>();
        pointViews.add(vPoint1);
        pointViews.add(vPoint2);
        pointViews.add(vPoint3);
        pointViews.add(vPoint4);

        List<View> views = new ArrayList<>();

        // 初始化引导页视图列表
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);
            if (i == pics.length - 1) {
                vLogin = view.findViewById(R.id.v_login);
                vLogin.setOnClickListener(v -> {
                    CommonUtils.openActicity(WelcomeActivity.this, LoginActivity.class, null, true);
                });
            }
            views.add(view);
        }
        vp = (ViewPager) findViewById(R.id.vp_guide);
        // 初始化adapter
        adapter = new GuideViewPagerAdapter(views);
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentIndex = i;
                setCurDot();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setCurDot() {
        for (int i = 0; i < pointViews.size(); i++) {
            pointViews.get(i).setBackgroundResource(currentIndex == i ? R.drawable.bg_red_oval : R.drawable.bg_ddd_oval);
        }
    }


    public class GuideViewPagerAdapter extends PagerAdapter {
        private List<View> views;

        public GuideViewPagerAdapter(List<View> views) {
            super();
            this.views = views;
        }

        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(views.get(position), 0);
            return views.get(position);
        }

    }

}
