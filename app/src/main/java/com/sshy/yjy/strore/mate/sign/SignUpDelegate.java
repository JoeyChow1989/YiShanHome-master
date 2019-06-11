package com.sshy.yjy.strore.mate.sign;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.sshy.yjy.strore.R;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
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

public class SignUpDelegate extends BaseActivity{

    TextInputEditText mPhone = null;
    TextInputEditText mYanZheng = null;
    TextInputEditText mPassword = null;

    AppCompatTextView mTxtYanZheng = null;

    AppCompatButton mSignUp = null;

    String phone = null;
    String yanzheng = null;
    String pwd = null;

    private int code = 0;
    private int NUM = 60;

    private Handler mHandler;

    private AppCompatImageView mBack = null;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SignUpDelegate.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what <= 1) {
                    mTxtYanZheng.setText("获取验证码");
                    mTxtYanZheng.setClickable(true);
                } else {
                    mTxtYanZheng.setText(msg.what + "s后重试");
                    mTxtYanZheng.setClickable(false);
                }

            }
        };
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    private boolean checkForm() {
        boolean isPass = true;

        phone = mPhone.getText().toString();
        pwd = mPassword.getText().toString();
        yanzheng = mYanZheng.getText().toString();

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length() != 11) {
            mPhone.setError("错误的手机号格式");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

        if (pwd.isEmpty() || pwd.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (yanzheng.isEmpty() || yanzheng.length() != 6 || !yanzheng.equals(String.valueOf(code))) {
            mYanZheng.setError("验证码错误");
            isPass = false;
        } else {
            mYanZheng.setError(null);
        }
        return isPass;
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mPhone = $(R.id.edit_sign_up_phone);
        mYanZheng = $(R.id.edit_sign_up_yanzheng);
        mTxtYanZheng = $(R.id.id_sign_up_txt_yanzheng);
        mPassword = $(R.id.edit_sign_up_pwd);

        mBack = $(R.id.id_sign_up_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        //验证码
        mTxtYanZheng.setOnClickListener(v -> {
            if (mPhone.getText().toString().isEmpty()
                    || !Patterns.PHONE.matcher(mPhone.getText().toString()).matches()
                    || mPhone.getText().toString().length() != 11) {
                mPhone.setError("错误的手机号格式");
            } else {
                code = (int) ((Math.random() * 9 + 1) * 100000);
                for (int i = 0; i <= NUM; i++) {
                    Message message = Message.obtain(mHandler);
                    message.what = NUM - i;
                    mHandler.sendMessageDelayed(message, 1000 * i);//通过延迟发送消息，每隔一秒发送一条消息
                }
                RxRestClient.builder()
                        .url("api/sendSms")
                        .loader(this)
                        .params("nickname", "尊敬的颐闪到家用户")
                        .params("mobile", mPhone.getText().toString())
                        .params("code", code)
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
                                if (JSON.parseObject(response).getIntValue("code") == 200) {
                                    String msg = JSON.parseObject(response).getString("msg");
                                    Toast.makeText(SignUpDelegate.this, msg, Toast.LENGTH_SHORT).show();
                                }
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
        });

        mSignUp = $(R.id.btn_sign_up);
        mSignUp.setOnClickListener(v -> {
            if (checkForm()) {
                RxRestClient.builder()
                        .url("api/reg")
                        .loader(this)
                        .params("mobile", mPhone.getText().toString())
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
                                MateLogger.json("USER_PROFILE", response);
                                final String code = JSON.parseObject(response).getString("code");
                                final String msg = JSON.parseObject(response).getString("msg");
                                if ("200".equals(code)) {
                                    Toast.makeText(SignUpDelegate.this, msg, Toast.LENGTH_SHORT).show();
                                    AppManager.getInstance().finishActivity();
                                } else {
                                    Toast.makeText(SignUpDelegate.this, msg, Toast.LENGTH_SHORT).show();
                                }
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
        });
    }
}
