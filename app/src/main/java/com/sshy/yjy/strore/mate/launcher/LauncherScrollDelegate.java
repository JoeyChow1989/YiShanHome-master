package com.sshy.yjy.strore.mate.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.EcBottomDelegate;
import com.sshy.yjy.strore.mate.sign.SignInDelegate;
import com.sshy.yjy.ui.launcher.ILauncherListener;
import com.sshy.yjy.ui.launcher.LauncherHolderCreator;
import com.sshy.yjy.ui.launcher.OnLauncherFinishTag;
import com.sshy.yjy.ui.launcher.ScrollLauncherTag;

import java.util.ArrayList;

import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.app.AccountManager;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.app.IUserChecker;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

/**
 * Created by zzy on 2018/3/12/012.
 */

public class LauncherScrollDelegate extends BaseActivity implements OnItemClickListener, ILauncherListener {

    private ConvenientBanner<Integer> mConvenientBanner = null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ILauncherListener mILauncherListener;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, LauncherScrollDelegate.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }


    private void initBanner() {
        INTEGERS.add(R.drawable.ic_welcome_1);
        INTEGERS.add(R.drawable.ic_welcome_bg);

        mConvenientBanner
                .setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(this);
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        initBanner();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mILauncherListener = this;
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }


    @Override
    public void onItemClick(int position) {
        //如果点击的是最后一个
        if (position == INTEGERS.size() - 1) {
            MatePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void OnSignIn() {
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                }

                @Override
                public void OnNotSignIn() {
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                }
            });
        }

    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                //Toast.makeText(this, "启动结束，用户登录了", Toast.LENGTH_LONG).show();
                EcBottomDelegate.startAction(this);
                AppManager.getInstance().finishActivity();
                break;
            case NOT_SIGNED:
                //Toast.makeText(this, "启动结束，用户没登录", Toast.LENGTH_LONG).show();
                SignInDelegate.startAction(this);
                AppManager.getInstance().finishActivity(this);
                break;
            default:
                break;
        }
    }
}
