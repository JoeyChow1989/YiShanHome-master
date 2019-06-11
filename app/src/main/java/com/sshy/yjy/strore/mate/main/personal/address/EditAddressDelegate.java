package com.sshy.yjy.strore.mate.main.personal.address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_ADDRESS_DEFAULT;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_ADDRESS_ID;

/**
 * create date：2019/4/17
 * create by：周正尧
 */
public class EditAddressDelegate extends BaseActivity {

    private AppCompatTextView mName = null;
    private AppCompatTextView mMobile = null;
    private AppCompatEditText mAddress = null;
    private SwitchCompat isDefault = null;
    private AppCompatTextView mSubmit = null;
    private AppCompatImageView mBack = null;

    private String mDefault = null;
    private String address = null;

    /**
     * 入口
     *
     * @param activity
     **/
    public static void startAction(Activity activity, String addressId) {
        Intent intent = new Intent(activity, EditAddressDelegate.class);
        intent.putExtra(ARG_ADDRESS_ID, addressId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_edit_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        initView();
        initData();
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    private void initView() {
        mName = $(R.id.id_editaddress_name);
        mMobile = $(R.id.id_editaddress_phone);
        mAddress = $(R.id.id_editaddress_address);
        isDefault = $(R.id.id_switch_edit_address);
        mSubmit = $(R.id.id_editaddress_save);

        mBack = $(R.id.id_editaddress_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        isDefault.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mDefault = "1";
            } else {
                mDefault = "0";
            }
        });

        mSubmit.setOnClickListener(v -> {
            address = mAddress.getText().toString();
            RxRestClient.builder()
                    .url("api/updateAddress")
                    .loader(this)
                    .header(GetDataBaseUserProfile.getCustomId())
                    .params("addressId", getIntent().getStringExtra(ARG_ADDRESS_ID))
                    .params("detailAddress", address)
                    .params("isDefault", mDefault)
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
                                Toast.makeText(EditAddressDelegate.this, "保存成功!", Toast.LENGTH_SHORT).show();
                                if ("1".equals(mDefault)) {
                                    MateLogger.d("address", address);
                                    EventBus.getDefault().post(new MessageEvent(address, null, null, -1, null));
                                    MatePreference.addCustomAppProfile(ARG_ADDRESS_DEFAULT, address);
                                }
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

    private void initData() {
        RxRestClient.builder()
                .url("api/loadAddress")
                .loader(this)
                .header(GetDataBaseUserProfile.getCustomId())
                .params("addressId", getIntent().getStringExtra(ARG_ADDRESS_ID))
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
                        final JSONObject data = JSON.parseObject(s).getJSONObject("data");
                        mName.setText(data.getString("truename"));
                        mMobile.setText(data.getString("mobile"));
                        mAddress.setText(data.getString("detailAddress"));
                        if (data.getInteger("isDefault") == 1) {
                            isDefault.setChecked(true);
                        } else {
                            isDefault.setChecked(false);
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
