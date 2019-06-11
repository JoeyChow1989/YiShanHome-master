package com.sshy.yjy.strore.mate.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.strore.mate.submitorder.MessageEvent;

import org.greenrobot.eventbus.EventBus;

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
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_AUTHOR_NICKNAME;

public class NameSettingDelegate extends BaseActivity {

    private String mNickname;
    private AppCompatTextView mOldNickname = null;
    private TextInputEditText mNewNickname = null;
    private AppCompatButton mButton = null;
    private AppCompatImageView mBack = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_name;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mOldNickname = $(R.id.id_old_nickname);
        mNewNickname = $(R.id.id_new_nickname);
        mOldNickname.setText(mNickname);
        mButton = $(R.id.tv_name_finish);
        mBack = $(R.id.id_name_back);

        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        mOldNickname.setText(MatePreference.getCustomAppProfile(ARG_AUTHOR_NICKNAME));

        mButton.setOnClickListener(v -> {
            RxRestClient.builder()
                    .url("api/updateNickname")
                    .header(GetDataBaseUserProfile.getCustomId())
                    .params("nickname", mNewNickname.getText().toString())
                    .loader(NameSettingDelegate.this)
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
                            MateLogger.d("nickname", s);
                            final int code = JSON.parseObject(s).getInteger("code");
                            if (code == 200) {
                                Toast.makeText(NameSettingDelegate.this, "修改昵称成功！",
                                        Toast.LENGTH_SHORT).show();

                                mNickname = mNewNickname.getText().toString();
                                MatePreference.addCustomAppProfile(ARG_AUTHOR_NICKNAME, mNickname);
                                EventBus.getDefault().post(new MessageEvent(null, null, mNickname, -1, null));
                                AppManager.getInstance().finishActivity();
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
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }
}
