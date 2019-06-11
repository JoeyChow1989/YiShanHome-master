package com.sshy.yjy.strore.mate.submitorder;

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
import com.sshy.yjy.strore.mate.main.personal.address.AddAddressDelegate;
import com.sshy.yjy.strore.mate.main.personal.address.AddressAdapter;
import com.sshy.yjy.strore.mate.main.personal.address.AddressDataConverter;
import com.sshy.yjy.strore.mate.main.personal.address.AddressDelegate;
import com.sshy.yjy.strore.mate.main.personal.address.AddressItemFields;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
 * create date：2018/9/5
 * create by：周正尧
 */
public class AddressChoiceDelegate extends BaseActivity {

    private AppCompatImageView mBack = null;
    private AppCompatTextView mAddAddress = null;

    private RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.detegate_addr_choice;
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
        mBack = $(R.id.id_left_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        mRecyclerView = $(R.id.id_addr_choice_rv);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        mAddAddress = $(R.id.id_addr_choice_add);
        mAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(AddressChoiceDelegate.this, AddAddressDelegate.class);
            startActivity(intent);
        });
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
                        final AddressChoiceAdapter addressAdapter = new AddressChoiceAdapter(data,
                                AddressChoiceDelegate.this);
                        mRecyclerView.setAdapter(addressAdapter);

                        addressAdapter.setOnItemClickListener((adapter, view, position) -> {
                            MateLogger.d("itemClick", position);

                            final MultipleItemEntity entity = (MultipleItemEntity) adapter.getData().get(position);
                            final String addressId = entity.getField(MultipleFields.ID);
                            final String address = entity.getField(AddressItemFields.ADDRESS);
                            EventBus.getDefault().post(new MessageEvent(address, addressId, null,-1, null));
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

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }
}
