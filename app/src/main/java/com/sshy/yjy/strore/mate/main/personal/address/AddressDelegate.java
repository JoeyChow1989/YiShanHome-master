package com.sshy.yjy.strore.mate.main.personal.address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.strore.mate.submitorder.MessageEvent;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_ADDRESS_DEFAULT;

/**
 * create date：2018/4/23
 * create by：周正尧
 */
public class AddressDelegate extends BaseActivity {

    private RecyclerView mRecyclerView;
    private AppCompatTextView mAddAddress;

    private AppCompatImageView mBack = null;

    /**
     * 入口
     *
     * @param activity
     **/
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, AddressDelegate.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
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

    private void initData() {
        RxRestClient.builder()
                .url("api/findAddressList")
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
                        final List<MultipleItemEntity> data =
                                new AddressDataConverter().setJsonData(s).convert();
                        final AddressAdapter addressAdapter = new AddressAdapter(data,
                                AddressDelegate.this);
                        mRecyclerView.setAdapter(addressAdapter);

                        addressAdapter.setOnItemClickListener((adapter, view, position) ->  {
                            final MultipleItemEntity entity = (MultipleItemEntity) adapter.getData().get(position);
                            final String address = entity.getField(AddressItemFields.ADDRESS);
                            EventBus.getDefault().post(new MessageEvent(address, null, null,-1, null));
                            MatePreference.addCustomAppProfile(ARG_ADDRESS_DEFAULT, address);
                            AppManager.getInstance().finishActivity();
                        });
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

    private void initView() {
        mRecyclerView = $(R.id.rv_address);
        mAddAddress = $(R.id.id_address_add);

        mBack = $(R.id.id_address_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(AddressDelegate.this, AddAddressDelegate.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }
}
