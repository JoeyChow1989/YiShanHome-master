package com.sshy.yjy.strore.mate.main.personal.order.toPay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.strore.mate.main.personal.order.OrderListDataConverter;
import com.sshy.yjy.strore.mate.main.personal.order.toService.OrderToServiceAdapter;
import com.sshy.yjy.strore.mate.main.personal.order.toService.OrderToServiceDataConverter;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.app.Mate;
import strore.yjy.sshy.com.mate.delegates.BaseDelegate;
import strore.yjy.sshy.com.mate.delegates.MateDelegate;
import strore.yjy.sshy.com.mate.net.callback.ISuccess;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;

/**
 * create date：2019-05-09
 * create by：周正尧
 */
public class VPagersOrderToPayDelegate extends BaseDelegate implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRvOrderToPay = null;
    private SwipeRefreshLayout mSwOrderToPay = null;
    private List<MultipleItemEntity> data = null;
    private OrderToPayAdapter adapter = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_to_pay;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRvOrderToPay = $(R.id.rv_order_to_pay);
        mSwOrderToPay = $(R.id.id_sw_order_to_pay);
        initViews();
        initData();
    }

    private void initData() {
        RxRestClient.builder()
                .url("api/findOrderPage")
                .params("status", 0)
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
                        data = new OrderToPayDataConverter().setJsonData(s)
                                .convert();
                        adapter = new OrderToPayAdapter(data, _mActivity);
                        mRvOrderToPay.setAdapter(adapter);
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
        mRvOrderToPay.setLayoutManager(manager);
        mSwOrderToPay.setOnRefreshListener(this);
        mSwOrderToPay.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mSwOrderToPay.setProgressViewOffset(true, -20, 100);
    }

    @Override
    public void onRefresh() {
        mSwOrderToPay.setRefreshing(true);
        Mate.getHandler().postDelayed(() -> {
            //一些网络请求
            RxRestClient.builder()
                    .url("api/findOrderPage")
                    .params("status", 2)
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
                                mSwOrderToPay.setRefreshing(false);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }, 1000);
    }
}
