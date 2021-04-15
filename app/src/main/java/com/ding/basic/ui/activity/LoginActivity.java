package com.ding.basic.ui.activity;


import android.widget.TextView;

import com.ding.basic.R;
import com.ding.basic.ui.BaseActivity;
import com.ding.basic.ui.MainActivity;
import com.ding.basic.utils.CommonUtils;
import com.ding.basic.utils.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_login)
    TextView tvLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        ButterKnife.bind(this);
        tvLogin.setOnClickListener(view -> {
            SpUtil.putString(this, SpUtil.KEY_TOKEN, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJpc3MiOiJxcXNrIiwiZXhwIjoxNTg0MDg3ODg3LCJ1c2VySWQiOiIxMjY5MzIzMyIsImlhdCI6MTU4MzQ4MzA4N30.JedfhWEdro061_HO_FW-JmApZByPDzbKFejfk_O_GNo");
            CommonUtils.openActicity(LoginActivity.this, MainActivity.class, null, true);
        });
    }

}
