package com.sshy.yjy.strore.mate.main.personal.order.toService;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.strore.mate.main.personal.order.OrderListDataConverter;
import com.sshy.yjy.ui.recycler.BaseDecoration;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.app.Mate;
import strore.yjy.sshy.com.mate.delegates.MateDelegate;
import strore.yjy.sshy.com.mate.net.callback.ISuccess;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;

/**
 * create date：2019/2/14
 * create by：周正尧
 */
public class VsPagersOrderToServiceDelegate extends MateDelegate implements ISuccess, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRvOrderToService = null;
    private SwipeRefreshLayout mSwOrderToService = null;
    private List<MultipleItemEntity> data = null;
    private OrderToServiceAdapter adapter = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_to_service;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRvOrderToService = $(R.id.rv_order_to_service);
        mSwOrderToService = $(R.id.id_sw_order_to_service);
        initViews();
        initData();
    }

    private void initData() {
        RxRestClient.builder()
                .url("api/findOrderPage")
                .params("status", 2)
                .header(GetDataBaseUserProfile.getCustomId())
                .loader(_mActivity)
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
                        data = new OrderToServiceDataConverter().setJsonData(s)
                                .convert();
                        final MateDelegate delegate = (MateDelegate) getParentFragment();
                        adapter = new OrderToServiceAdapter(data, delegate);
                        mRvOrderToService.setAdapter(adapter);
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

    private void initViews() {
        final LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRvOrderToService.setLayoutManager(manager);
        mSwOrderToService.setOnRefreshListener(this);
        mSwOrderToService.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mSwOrderToService.setProgressViewOffset(true, -20, 100);
    }

    @Override
    public void onSuccess(String response) {
        data = new OrderToServiceDataConverter().setJsonData(response)
                .convert();
        final MateDelegate delegate = (MateDelegate) getParentFragment();
        adapter = new OrderToServiceAdapter(data, delegate);
        mRvOrderToService.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        mSwOrderToService.setRefreshing(true);
        Mate.getHandler().postDelayed(() -> {
            //一些网络请求
            //一些网络请求
            RxRestClient.builder()
                    .url("api/findOrderPage")
                    .params("status", 1)
                    .header(GetDataBaseUserProfile.getCustomId())
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
                            data.clear();
                            int code = JSON.parseObject(s).getIntValue("code");
                            if (code == 200) {
                                data = new OrderListDataConverter().setJsonData(s).convert();
                                adapter.setNewData(data);
                                adapter.notifyDataSetChanged();
                                mSwOrderToService.setRefreshing(false);
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
        }, 1000);
    }
}
