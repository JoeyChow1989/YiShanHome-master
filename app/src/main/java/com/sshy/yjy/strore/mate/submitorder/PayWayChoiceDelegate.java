package com.sshy.yjy.strore.mate.submitorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.CheckBox;

import com.sshy.yjy.strore.R;

import org.greenrobot.eventbus.EventBus;

import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/9/5
 * create by：周正尧
 */
public class PayWayChoiceDelegate extends BaseActivity {

    private LinearLayoutCompat mAddBankCard = null;
    private AppCompatImageView mBack = null;

    private CheckBox mPayWayWeixin = null;
    private CheckBox mPayWayAipay = null;

    private AppCompatTextView mFinish = null;
    private int mPayWay = -1;

    @Override
    public Object setLayout() {
        return R.layout.delegate_payway_choice;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mAddBankCard = $(R.id.id_ly_add_bank);
        mAddBankCard.setOnClickListener(v -> {
            Intent intent = new Intent(PayWayChoiceDelegate.this, UsersBankCardDelegate.class);
            startActivity(intent);
        });

        mPayWayWeixin = $(R.id.id_checkbox_payway_weixin);
        mPayWayAipay = $(R.id.id_checkbox_payway_zhufubao);

        mPayWayWeixin.setChecked(true);
        mPayWayWeixin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mPayWay = 1;
            } else {
                mPayWay = -1;
            }
        });

        mPayWayAipay.setClickable(false);

        mBack = $(R.id.id_left_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        mFinish = $(R.id.id_right_finish);
        mFinish.setOnClickListener(v -> {
            EventBus.getDefault().post(new MessageEvent(null, null,null, mPayWay, null));
            AppManager.getInstance().finishActivity();
        });
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }
}
