package com.sshy.yjy.strore.mate.main.personal.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.strore.mate.sign.SignForgetDelegate;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/9/10
 * create by：周正尧
 */
public class ChangePwdDelegate extends BaseActivity {

    private AppCompatButton mChangePwdFinish = null;
    private AppCompatTextView mChangePwdForget = null;

    private TextInputEditText mChangeOld = null;
    private TextInputEditText mChangeNew = null;
    private TextInputEditText mChangeNewAgine = null;

    private AppCompatImageView mBack = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_change_password;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mChangePwdFinish = $(R.id.tv_changepwd_finish);
        mChangePwdForget = $(R.id.tv_changepwd_forget);

        mChangeOld = $(R.id.id_change_oldpassword);
        mChangeNew = $(R.id.id_change_newpassword);
        mChangeNewAgine = $(R.id.id_change_newpassword_ag);

        mBack = $(R.id.id_change_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        mChangePwdFinish.setOnClickListener(v -> {
            Intent intent = new Intent(ChangePwdDelegate.this, ChangePwdSuccessDelegate.class);
            startActivity(intent);
            AppManager.getInstance().finishActivity();
        });

        mChangePwdForget.setOnClickListener(v -> {
            if (mChangeNew.equals(mChangeNewAgine)) {
                updatePassword();
            } else {
                Toast.makeText(ChangePwdDelegate.this, "新密码不一致！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    private void updatePassword() {
        RxRestClient.builder()
                .url("api/updatePassword")
                .params("oldPassword", mChangeOld.getText().toString())
                .params("newPassword", mChangeNew.getText().toString())
                .header(GetDataBaseUserProfile.getCustomId())
                .loader(this)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Intent intent = new Intent(ChangePwdDelegate.this, SignForgetDelegate.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        MateLoader.stopLoading();
                    }
                });
    }
}
