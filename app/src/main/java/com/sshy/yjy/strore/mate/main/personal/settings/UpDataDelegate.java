package com.sshy.yjy.strore.mate.main.personal.settings;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.widget.Toast;

import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;
import com.sshy.yjy.strore.R;

import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.PackageUtils;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/9/11
 * create by：周正尧
 */
public class UpDataDelegate extends BaseActivity {

    private AppCompatTextView mMsg;
    private AppCompatTextView mCode;
    private AppCompatImageView mBack = null;

    private ProgressDialog dialog;

    @Override
    public Object setLayout() {
        return R.layout.delegate_updata;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initProgressDialog();
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
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
                        mMsg.setText("目前您拥有最新版本,无需更新");
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

                        Toast.makeText(UpDataDelegate.this, "检查更新失败", Toast.LENGTH_SHORT).show();

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
                        MateLogger.e("pgyer", "download apk failed");
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

    private void initProgressDialog() {
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setTitle("更新进度");
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setMax(100);
    }

    private void upDateDialog(String msg, String downloadUrl) {
        new AlertDialog.Builder(this)
                .setTitle("有新版本啦")
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

    @SuppressLint("SetTextI18n")
    private void initView() {
        mMsg = $(R.id.id_updata_text);
        mCode = $(R.id.id_updata_appcode);

        mCode.setText("版本号:" + PackageUtils.getVersionName(this));

        mBack = $(R.id.id_updata_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        checkUpdate();
    }
}
