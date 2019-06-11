package com.sshy.yjy.strore.mate.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.EcBottomDelegate;
import com.sshy.yjy.strore.mate.sign.SignInDelegate;
import com.sshy.yjy.ui.launcher.ILauncherListener;
import com.sshy.yjy.ui.launcher.OnLauncherFinishTag;
import com.sshy.yjy.ui.launcher.ScrollLauncherTag;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.app.AccountManager;
import strore.yjy.sshy.com.mate.app.IUserChecker;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

public class LauncherDelegate extends BaseActivity implements
        ILauncherListener {

    private int waitTime = 4;
    private int awaitTime = 2;
    private ILauncherListener mILauncherListener;

    //创建线程池
    ScheduledExecutorService mScheduledExecutorService = Executors.newScheduledThreadPool(2);

    private void initTimer() {
        // 延时任务
        mScheduledExecutorService.scheduleAtFixedRate(() -> {
            waitTime--;
            try {
                if (waitTime < 1) {
                    if (mScheduledExecutorService != null) {
                        mScheduledExecutorService.shutdown();
                        mScheduledExecutorService = null;
                        checkIsShowScroll();
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mILauncherListener = this;
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        initTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                //ToastUtils.showLong("启动结束，用户登录了");
                EcBottomDelegate.startAction(this);
                AppManager.getInstance().finishActivity(this);
                break;
            case NOT_SIGNED:
                //ToastUtils.showLong("启动结束，用户没登录");
                SignInDelegate.startAction(this);
                AppManager.getInstance().finishActivity(this);
                break;
            default:
                break;
        }
    }

    //判断是否显示滑动启动页
    private void checkIsShowScroll() {
        if (!MatePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            LauncherScrollDelegate.startAction(this);
            AppManager.getInstance().finishActivity();
        } else {
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void OnSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void OnNotSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
