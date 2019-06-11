package com.sshy.yjy.strore.mate.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.submitorder.SubOrderDelegate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_ORDER_NO;

/**
 * create date：2018/9/5
 * create by：周正尧
 */
public class PaySuccessDelegate extends BaseActivity {

    private AppCompatImageView mBack = null;
    private AppCompatTextView mOrderNo = null;
    private AppCompatTextView mTime = null;
    private AppCompatButton mFinish = null;

    private int waitTime = 4;
    private int awaitTime = 2;

    //创建线程池
    ScheduledExecutorService mScheduledExecutorService = Executors.newScheduledThreadPool(2);

    private void initTimer() {
        // 延时任务
        mScheduledExecutorService.scheduleAtFixedRate(() -> {
            waitTime--;
            mTime.setText(waitTime);
            try {
                if (waitTime < 1) {
                    if (mScheduledExecutorService != null) {
                        mScheduledExecutorService.shutdown();
                        mScheduledExecutorService = null;
                        AppManager.getInstance().finishActivity();
                        if (!mScheduledExecutorService.awaitTermination(awaitTime, TimeUnit.MILLISECONDS)) {
                            // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                            mScheduledExecutorService.shutdownNow();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mScheduledExecutorService.shutdownNow();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String orderNo) {
        Intent intent = new Intent(activity, PaySuccessDelegate.class);
        intent.putExtra(ARG_ORDER_NO, orderNo);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_pay_success;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mBack = $(R.id.id_pay_success_back);
        mOrderNo = $(R.id.id_pay_success_ordernum);
        mTime = $(R.id.id_pay_success_time);
        mFinish = $(R.id.id_pay_success_finish);

        initTimer();

        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity(SubOrderDelegate.class);
            AppManager.getInstance().finishActivity();
        });

        mFinish.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity(SubOrderDelegate.class);
            AppManager.getInstance().finishActivity();
        });

        mOrderNo.setText(getIntent().getStringExtra(ARG_ORDER_NO));

    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }
}
