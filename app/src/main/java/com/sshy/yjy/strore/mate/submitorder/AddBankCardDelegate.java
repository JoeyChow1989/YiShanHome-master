package com.sshy.yjy.strore.mate.submitorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.pay.PaySuccessDelegate;

import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/9/5
 * create by：周正尧
 */
public class AddBankCardDelegate extends BaseActivity {

    private AppCompatTextView mAddBankCard = null;
    private AppCompatImageView mBack = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_add_bankcard;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mAddBankCard = $(R.id.tv_add_bankcard);
        mAddBankCard.setOnClickListener(v -> {
            Intent intent = new Intent(AddBankCardDelegate.this, PaySuccessDelegate.class);
            startActivity(intent);
        });

        mBack = $(R.id.id_left_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }
}
