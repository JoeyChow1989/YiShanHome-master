package com.sshy.yjy.strore.mate.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sshy.yjy.strore.R;

import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.delegates.MateDelegate;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/8/7
 * create by：周正尧
 */
public class SignForgetSuccessDelegate extends BaseActivity {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_forget_suc;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }
}
