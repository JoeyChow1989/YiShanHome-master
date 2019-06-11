package com.sshy.yjy.strore.mate.main.personal.order.finished;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import strore.yjy.sshy.com.mate.delegates.BaseDelegate;
import strore.yjy.sshy.com.mate.delegates.MateDelegate;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;

/**
 * create date：2019/2/14
 * create by：周正尧
 */
public class VPagersOrderFinishedDelegate extends BaseDelegate implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRvOrderFinish = null;
    private SwipeRefreshLayout mSwOrderFinish = null;
    private List<MultipleItemEntity> data = null;
    private OrderFinishedAdapter adapter = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_finish;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRvOrderFinish = $(R.id.rv_finish_order);
        mSwOrderFinish = $(R.id.id_sw_order_finish);
        initViews();
        initData();
    }

    private void initData() {
        RxRestClient.builder()
                .url("api/findOrderPage")
                .params("status", 4)
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
                        data = new OrderFinishedDataConverter().setJsonData(s)
                                .convert();
                        final MateDelegate delegate = (MateDelegate) getParentFragment();
                        adapter = new OrderFinishedAdapter(data, delegate);
                        mRvOrderFinish.setAdapter(adapter);
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
        mRvOrderFinish.setLayoutManager(manager);
        mSwOrderFinish.setOnRefreshListener(this);
        mSwOrderFinish.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mSwOrderFinish.setProgressViewOffset(true, -20, 100);
    }

    @Override
    public void onRefresh() {
        mSwOrderFinish.setRefreshing(true);
        Mate.getHandler().postDelayed(() -> {
            //一些网络请求
            RxRestClient.builder()
                    .url("api/findOrderPage")
                    .params("status", 3)
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
                            data = new OrderListDataConverter().setJsonData(s).convert();
                            adapter.setNewData(data);
                            adapter.notifyDataSetChanged();
                            mSwOrderFinish.setRefreshing(false);
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
