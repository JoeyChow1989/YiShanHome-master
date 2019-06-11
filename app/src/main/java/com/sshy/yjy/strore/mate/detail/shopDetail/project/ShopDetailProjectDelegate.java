package com.sshy.yjy.strore.mate.detail.shopDetail.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.delegates.MateDelegate;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_ID;

/**
 * create date：2018/9/6
 * create by：周正尧
 */
public class ShopDetailProjectDelegate extends MateDelegate {

    private RecyclerView mShopDetailProject = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shopdetail_project;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mShopDetailProject = $(R.id.ry_shopdetail_project);
        initRecyclerView();
        initData();
    }

    private void initData() {
        RxRestClient.builder()
                .url("api/getMerchantDetail")
                .loader(_mActivity)
                .header(GetDataBaseUserProfile.getCustomId())
                .params("merchantId", MatePreference.getCustomAppProfile(AppConfig.ARG_MERCHANT_ID))
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
                        MateLogger.d("merchantDetail", response);
                        final List<MultipleItemEntity> data =
                                new ShopDetailDataConverter().setJsonData(response).convert();
                        ShopDetailProjectAdapter mAdapter = new ShopDetailProjectAdapter(data, _mActivity);
                        mShopDetailProject.setAdapter(mAdapter);
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

    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mShopDetailProject.setLayoutManager(manager);
        mShopDetailProject.addOnItemTouchListener(ShopDetailProjectItemClickListener.create(_mActivity));
    }
}