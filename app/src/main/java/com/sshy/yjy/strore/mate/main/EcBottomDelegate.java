package com.sshy.yjy.strore.mate.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.detail.TabEntity;
import com.sshy.yjy.strore.mate.main.community.CommuinityDelegate;
import com.sshy.yjy.strore.mate.main.index.IndexDelegate;
import com.sshy.yjy.strore.mate.main.personal.PersonalDelegate;
import com.sshy.yjy.strore.mate.main.personal.order.OrderListDelegate;

import java.util.ArrayList;

import qiu.niorgai.StatusBarCompat;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2019/3/10
 * create by：周正尧
 */
public class EcBottomDelegate extends BaseActivity {

    CommonTabLayout mTabLayout;

    private String[] mTitles = {"首页", "社区", "订单", "我的"};
    private int[] mIconUnSelectIds = {
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_community, R.drawable.ic_bottom_order, R.drawable.ic_bottom_mine};
    private int[] mIconSelectIds = {
            R.drawable.ic_bottom_index_checked, R.drawable.ic_bottom_community_checked, R.drawable.ic_bottom_order_checked, R.drawable.ic_bottom_mine_checked};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private IndexDelegate mIndexDelegate;
    private CommuinityDelegate mCommuinityDelegate;
    private OrderListDelegate mOrderListDelegate;
    private PersonalDelegate mPersonalDelegate;

    //更新进度条
    private ProgressDialog dialog;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, EcBottomDelegate.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
        initProgressDialog();
        //更新
        checkUpdate();
    }

    @Override
    public void setStatusBar() {
        //StatusBarUtil.setStatusBarMode(this, false, R.color.colorPrimary);
        StatusBarCompat.translucentStatusBar(this, true);
    }

    private void checkUpdate() {
        new PgyUpdateManager.Builder()
                .setForced(false)                //设置是否强制更新,非自定义回调更新接口此方法有用
                .setUserCanRetry(false)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setDeleteHistroyApk(false)     // 检查更新前是否删除本地历史 Apk
                .setUpdateManagerListener(new UpdateManagerListener() {
                    @Override
                    public void onNoUpdateAvailable() {
                        //没有更新是回调此方法
                        MateLogger.d("pgyer", "there is no new version");
                    }

                    @Override
                    public void onUpdateAvailable(AppBean appBean) {
                        //没有更新是回调此方法
                        MateLogger.d("pgyer", "there is new version can update"
                                + "new versionCode is " + appBean.getVersionCode());

                        String msg = appBean.getReleaseNote();
                        String downloadUrl = appBean.getDownloadURL();

                        upDateDialog(msg, downloadUrl);
                    }

                    @Override
                    public void checkUpdateFailed(Exception e) {
                        //更新检测失败回调
                        MateLogger.e("pgyer", "check update failed ");
                        Toast.makeText(EcBottomDelegate.this, "检查更新失败", Toast.LENGTH_SHORT).show();

                    }
                })
                //注意 ：下载方法调用 PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); 此回调才有效
                .setDownloadFileListener(new DownloadFileListener() {   // 使用蒲公英提供的下载方法，这个接口才有效。
                    @Override
                    public void downloadFailed() {
                        //下载失败
                        MateLogger.e("pgyer", "download apk failed");
                    }

                    @Override
                    public void downloadSuccessful(Uri uri) {
                        PgyUpdateManager.installApk(uri);  // 使用蒲公英提供的安装方法提示用户 安装apk
                    }

                    @Override
                    public void onProgressUpdate(Integer... integers) {
                        MateLogger.e("pgyer", "update download apk progress : " + integers[0]);
                        dialog.show();
                        dialog.setProgress(integers[0]);
                    }
                })
                .register();
    }

    private void upDateDialog(String msg, String downloadUrl) {
        new AlertDialog.Builder(this)
                .setTitle("更新新版本")
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.cancel();
                })
                .setPositiveButton("下载", (dialog, which) -> {
                    //调用以下方法，DownloadFileListener 才有效；如果完全使用自己的下载方法，不需要设置DownloadFileListener
                    PgyUpdateManager.downLoadApk(downloadUrl);
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    private void initProgressDialog() {
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setTitle("更新进度");
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setMax(100);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mTabLayout = $(R.id.tab_layout_bottom);
        //初始化菜单
        initTab();
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        mTabLayout.setTabData(mTabEntities);
        //点击监听
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            mIndexDelegate = (IndexDelegate) getSupportFragmentManager().findFragmentByTag("indexDelegate");
            mCommuinityDelegate = (CommuinityDelegate) getSupportFragmentManager().findFragmentByTag("commuinityDelegate");
            mOrderListDelegate = (OrderListDelegate) getSupportFragmentManager().findFragmentByTag("orderListDelegate");
            mPersonalDelegate = (PersonalDelegate) getSupportFragmentManager().findFragmentByTag("personalDelegate");
            currentTabPosition = savedInstanceState.getInt(AppConfig.HOME_CURRENT_TAB_POSITION);
        } else {
            mIndexDelegate = new IndexDelegate();
            mCommuinityDelegate = new CommuinityDelegate();
            mOrderListDelegate = new OrderListDelegate();
            mPersonalDelegate = new PersonalDelegate();

            Bundle bundle = new Bundle();
            bundle.putInt("bar", 1);
            mPersonalDelegate.setArguments(bundle);

            transaction.add(R.id.bottom_bar_delegate_container, mIndexDelegate, "indexDelegate");
            transaction.add(R.id.bottom_bar_delegate_container, mCommuinityDelegate, "commuinityDelegate");
            transaction.add(R.id.bottom_bar_delegate_container, mOrderListDelegate, "orderListDelegate");
            transaction.add(R.id.bottom_bar_delegate_container, mPersonalDelegate, "personalDelegate");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        mTabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        MateLogger.d("主页菜单position" + position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //首页
            case 0:
                transaction.hide(mCommuinityDelegate);
                transaction.hide(mOrderListDelegate);
                transaction.hide(mPersonalDelegate);
                transaction.show(mIndexDelegate);
                transaction.commitAllowingStateLoss();
                break;
            //美女
            case 1:
                transaction.hide(mIndexDelegate);
                transaction.hide(mOrderListDelegate);
                transaction.hide(mPersonalDelegate);
                transaction.show(mCommuinityDelegate);
                transaction.commitAllowingStateLoss();
                break;
            //视频
            case 2:
                transaction.hide(mIndexDelegate);
                transaction.hide(mCommuinityDelegate);
                transaction.hide(mPersonalDelegate);
                transaction.show(mOrderListDelegate);
                transaction.commitAllowingStateLoss();
                break;
            //关注
            case 3:
                transaction.hide(mIndexDelegate);
                transaction.hide(mCommuinityDelegate);
                transaction.hide(mOrderListDelegate);
                transaction.show(mPersonalDelegate);
                transaction.commitAllowingStateLoss();


                break;
            default:
                break;
        }
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                ToastUtils.showShort("再按一次退出应用");
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                AppManager.getInstance().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
