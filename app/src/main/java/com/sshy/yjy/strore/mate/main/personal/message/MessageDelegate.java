package com.sshy.yjy.strore.mate.main.personal.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;

import com.sshy.yjy.strore.R;

import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2019/3/12
 * create by：周正尧
 */
public class MessageDelegate extends BaseActivity {

    private AppCompatImageView mBack = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_message;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {

        mBack = $(R.id.id_message_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }
}
