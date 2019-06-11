package com.sshy.yjy.strore.mate.main.personal.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
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
import strore.yjy.sshy.com.mate.ActivityManager.AppManagerDelegate;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_ADDRESS_DEFAULT;

/**
 * create date：2018/9/5
 * create by：周正尧
 */
public class AddAddressDelegate extends BaseActivity implements View.OnClickListener {

    private AppCompatEditText mTrueName = null;
    private AppCompatEditText mMobile = null;
    private AppCompatEditText mDetailAddress = null;
    private AppCompatTextView mSubmit = null;
    private SwitchCompat mSwitchCompat = null;
    private AppCompatImageView mBack = null;

    private String name = null;
    private String mobile = null;
    private String address = null;
    private String isCheckedAddr = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_add_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mTrueName = $(R.id.id_addaddress_name);
        mMobile = $(R.id.id_addaddress_phone);
        mDetailAddress = $(R.id.id_addaddress_address);
        mSwitchCompat = $(R.id.id_switch_address);
        mSubmit = $(R.id.id_addaddress_save);
        mSubmit.setOnClickListener(this);

        mBack = $(R.id.id_addaddress_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        mSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isCheckedAddr = "1";
            } else {
                isCheckedAddr = "0";
            }
        });
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    @Override
    public void onClick(View v) {
        name = mTrueName.getText().toString();
        mobile = mMobile.getText().toString();
        address = mDetailAddress.getText().toString();

        RxRestClient.builder()
                .loader(this)
                .url("api/saveAddress")
                .header(GetDataBaseUserProfile.getCustomId())
                .params("truename", name)
                .params("mobile", mobile)
                .params("detailAddress", address)
                .params("isDefault", isCheckedAddr)
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
                        final int code = JSON.parseObject(s).getInteger("code");
                        if (code == 200) {
                            if ("1".equals(isCheckedAddr)) {
                                EventBus.getDefault().post(new MessageEvent(address, null, null, -1, null));
                                MatePreference.addCustomAppProfile(ARG_ADDRESS_DEFAULT, address);
                            }
                            Toast.makeText(AddAddressDelegate.this, "添加地址成功", Toast.LENGTH_SHORT).show();
                            AppManagerDelegate.getInstance().finishActivity();
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
}
