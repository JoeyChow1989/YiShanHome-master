package com.sshy.yjy.strore.mate.sign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.EcBottomDelegate;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * Created by 周正尧 on 2018/3/15 0015.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */

public class SignInDelegate extends BaseActivity implements ISignListener {


    private TextInputEditText mEmail = null;
    private TextInputEditText mPassword = null;

    private AppCompatButton mBtnSignIn = null;
    private AppCompatImageView mChatSignIn = null;
    private AppCompatTextView mForget = null;
    private AppCompatTextView mSignUp = null;
    private ISignListener mISignListener;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SignInDelegate.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mISignListener = this;
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    private boolean checkForm() {
        final String email = mEmail.getText().toString();
        final String pwd = mPassword.getText().toString();

        boolean isPass = true;
        if (email.isEmpty() || !Patterns.PHONE.matcher(email).matches()) {
            mEmail.setError("错误的手机号");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (pwd.isEmpty() || pwd.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mEmail = $(R.id.edit_sign_in_email);
        mPassword = $(R.id.edit_sign_in_pwd);
        mBtnSignIn = $(R.id.btn_sign_in);
        mBtnSignIn.setOnClickListener(v -> {
            if (checkForm()) {
                RxRestClient.builder()
                        .url("api/login")
                        .loader(this)
                        .params("mobile", mEmail.getText().toString())
                        .params("password", mPassword.getText().toString())
                        .build()
                        .post()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String response) {
                                MateLogger.d("login", response);
                                if (response != null) {
                                    String code = JSON.parseObject(response).getString("code");
                                    if ("200".equals(code)) {
                                        SignHandler.onSignIn(response, mISignListener);
                                    } else if ("-1".equals(code)) {
                                        Toast.makeText(SignInDelegate.this, JSON.parseObject(response).getString("msg"),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                MateLogger.d(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                MateLoader.stopLoading();
                            }
                        });
            }
        });

        mChatSignIn = $(R.id.icon_sign_in_wechat);
        mChatSignIn.setOnClickListener(v -> {
//            MateWeChat.getInstance().onSignInSuccess(userInfo -> {
//                Toast.makeText(SignInDelegate.this, userInfo, Toast.LENGTH_LONG).show();
//            }).signIn();
        });

        mForget = $(R.id.tv_link_forget_pwd);
        mForget.setOnClickListener(v -> {
            SignForgetDelegate.startAction(this);
        });

        mSignUp = $(R.id.tv_link_sign_up);
        mSignUp.setOnClickListener(v -> {
            SignUpDelegate.startAction(this);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功！", Toast.LENGTH_LONG).show();
        EcBottomDelegate.startAction(this);
        AppManager.getInstance().finishActivity();
    }
}
