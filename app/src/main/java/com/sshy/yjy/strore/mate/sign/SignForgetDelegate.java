package com.sshy.yjy.strore.mate.sign;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
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
 * create date：2018/8/7
 * create by：周正尧
 */
public class SignForgetDelegate extends BaseActivity {

    private AppCompatImageView mBack = null;
    private AppCompatEditText mForgetMobile = null;
    private AppCompatEditText mYanZheng = null;
    private AppCompatTextView mYanZhengTv = null;
    private AppCompatEditText mForgetPassWord = null;
    private AppCompatEditText mForgetPassWordAgain = null;
    private AppCompatButton mSubmit = null;

    private int code = 0;
    private int NUM = 60;

    String phone = null;
    String yanzheng = null;
    String pwd = null;
    String pwd_again = null;

    private Handler mHandler;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SignForgetDelegate.class);
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
                    mYanZhengTv.setText("获取验证码");
                    mYanZhengTv.setClickable(true);
                } else {
                    mYanZhengTv.setText(msg.what + "s后重试");
                    mYanZhengTv.setClickable(false);
                }

            }
        };

    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_forget;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mBack = $(R.id.id_sign_forget_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        mForgetMobile = $(R.id.edit_sign_up_forget_phone);
        mForgetPassWord = $(R.id.edit_sign_up_forget_pwd);
        mForgetPassWordAgain = $(R.id.edit_sign_up_forget_pwd_agin);
        mYanZheng = $(R.id.edit_sign_up_forget_yanzheng);
        mYanZhengTv = $(R.id.tv_sign_up_forget_yanzheng);
        mSubmit = $(R.id.btn_sign_up_forget);

        mYanZhengTv.setOnClickListener(v -> {
            if (mForgetMobile.getText().toString().isEmpty()
                    || !Patterns.PHONE.matcher(mForgetMobile.getText().toString()).matches()
                    || mForgetMobile.getText().toString().length() != 11) {
                mForgetMobile.setError("错误的手机号格式");
            } else {
                code = (int) ((Math.random() * 9 + 1) * 100000);
                for (int i = 0; i <= NUM; i++) {
                    Message message = Message.obtain(mHandler);
                    message.what = NUM - i;
                    mHandler.sendMessageDelayed(message, 1000 * i);//通过延迟发送消息，每隔一秒发送一条消息
                }
            }

            RxRestClient.builder()
                    .url("api/sendSms")
                    .loader(this)
                    .params("nickname", "尊敬的颐闪到家用户")
                    .params("mobile", mForgetMobile.getText().toString())
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
                                Toast.makeText(SignForgetDelegate.this, msg, Toast.LENGTH_SHORT).show();
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
        });


        mSubmit.setOnClickListener(v -> {
            if (checkForm()) {
                RxRestClient.builder()
                        .url("api/updateFogetPassword")
                        .loader(this)
                        .params("mobile", mForgetMobile.getText().toString())
                        .params("newPassword", mForgetPassWord.getText().toString())
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
                                MateLogger.d("forget", response);
                                final String code = JSON.parseObject(response).getString("code");
                                final String msg = JSON.parseObject(response).getString("msg");
                                if ("200".equals(code)) {
                                    Toast.makeText(SignForgetDelegate.this, msg, Toast.LENGTH_SHORT).show();
                                    AppManager.getInstance().finishActivity();
                                } else {
                                    Toast.makeText(SignForgetDelegate.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                MateLogger.d("forget", e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                MateLoader.stopLoading();
                            }
                        });
            }


        });


    }

    private boolean checkForm() {
        boolean isPass = true;
        phone = mForgetMobile.getText().toString();
        pwd = mForgetPassWord.getText().toString();
        pwd_again = mForgetPassWordAgain.getText().toString();
        yanzheng = mYanZheng.getText().toString();

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length() != 11) {
            mForgetMobile.setError("错误的手机号格式");
            isPass = false;
        } else {
            mForgetMobile.setError(null);
        }

        if (pwd.isEmpty() || pwd.length() < 6) {
            mForgetPassWord.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mForgetPassWord.setError(null);
        }

        if (pwd_again.isEmpty() || pwd_again.length() < 6) {
            mForgetPassWordAgain.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mForgetPassWordAgain.setError(null);
        }

        if (!pwd.equals(pwd_again)) {
            mForgetPassWordAgain.setError("输入的密码不一致");
            isPass = false;
        } else {
            mForgetPassWordAgain.setError(null);
        }

        if (yanzheng.isEmpty() || yanzheng.length() != 6 || !yanzheng.equals(String.valueOf(code))) {
            mYanZheng.setError("验证码错误");
            isPass = false;
        } else {
            mYanZheng.setError(null);
        }

        return isPass;
    }
}
