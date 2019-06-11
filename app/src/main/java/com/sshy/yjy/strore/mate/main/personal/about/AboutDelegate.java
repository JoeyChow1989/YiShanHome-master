package com.sshy.yjy.strore.mate.main.personal.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.sshy.yjy.strore.R;

import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.delegates.MateDelegate;
import strore.yjy.sshy.com.mate.net.callback.ISuccess;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/4/26
 * create by：周正尧
 */
public class AboutDelegate extends BaseActivity implements ISuccess {

    private AppCompatImageView mBack = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mBack = $(R.id.id_about_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    @Override
    public void onSuccess(String response) {

    }
}
