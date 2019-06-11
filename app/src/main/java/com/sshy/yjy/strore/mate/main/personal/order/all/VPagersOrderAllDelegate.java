package com.sshy.yjy.strore.mate.main.personal.order.all;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.strore.mate.main.personal.order.OrderListAdapter;
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
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.log.MateLogger;

/**
 * create date：2018/9/12
 * create by：周正尧
 */
public class VPagersOrderAllDelegate extends MateDelegate implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRvOrderAll = null;
    private SwipeRefreshLayout mSwOrderAll = null;
    private OrderListAdapter adapter = null;
    private List<MultipleItemEntity> data = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_pagers_order_all;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRvOrderAll = $(R.id.rv_pagers_order_all);
        mSwOrderAll = $(R.id.id_sw_order_all);
        initViews();
        initData();
    }

    private void initData() {
        RxRestClient.builder()
                .url("api/findOrderPage")
                .params("status", "")
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
                        MateLogger.d("orders", s);
                        data = new OrderListDataConverter().setJsonData(s)
                                .convert();
                        adapter = new OrderListAdapter(data, getActivity());
                        mRvOrderAll.setAdapter(adapter);
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
        mRvOrderAll.setLayoutManager(manager);
        mSwOrderAll.setOnRefreshListener(this);
        mSwOrderAll.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mSwOrderAll.setProgressViewOffset(true, -20, 100);
    }

    @Override
    public void onRefresh() {
        mSwOrderAll.setRefreshing(true);
        Mate.getHandler().postDelayed(() -> {
            //一些网络请求
            RxRestClient.builder()
                    .url("api/findOrderPage")
                    .params("status", "")
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
                        public void onNext(String response) {
                            data.clear();
                            int code = JSON.parseObject(response).getIntValue("code");
                            if (code == 200) {
                                data = new OrderListDataConverter().setJsonData(response).convert();
                                adapter.setNewData(data);
                                adapter.notifyDataSetChanged();
                                mSwOrderAll.setRefreshing(false);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showShort(e.toString());
                        }

                        @Override
                        public void onComplete() {
                            MateLoader.stopLoading();
                        }
                    });
        }, 1000);
    }
}
