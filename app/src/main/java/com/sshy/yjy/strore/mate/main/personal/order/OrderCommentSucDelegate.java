package com.sshy.yjy.strore.mate.main.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.sshy.yjy.strore.R;

import strore.yjy.sshy.com.mate.ActivityManager.AppManagerDelegate;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/9/21
 * create by：周正尧
 */
public class OrderCommentSucDelegate extends BaseActivity {

    private AppCompatButton mFinish;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment_succ;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mFinish = $(R.id.bt_order_comment_succ);
        mFinish.setOnClickListener(v -> {
            AppManagerDelegate.getInstance().finishActivity();
        });
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }
}