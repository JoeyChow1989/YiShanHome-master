package com.sshy.yjy.strore.mate.main.personal.order.toComment;

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
import strore.yjy.sshy.com.mate.delegates.BaseDelegate;
import strore.yjy.sshy.com.mate.delegates.MateDelegate;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;

/**
 * create date：2019/2/14
 * create by：周正尧
 */
public class VPagersOrderToCommentDelegate extends BaseDelegate implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRvOrderToComment = null;
    private SwipeRefreshLayout mSwOrderToComment = null;
    private List<MultipleItemEntity> data = null;
    private OrderToCommentAdapter adapter = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_to_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRvOrderToComment = $(R.id.rv_order_to_comment);
        mSwOrderToComment = $(R.id.id_sw_order_to_comment);
        initViews();
        initData();
    }

    private void initData() {
        RxRestClient.builder()
                .url("api/findOrderPage")
                .params("status", 3)
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
                        data = new OrderToCommentDataConverter().setJsonData(s)
                                .convert();
                        adapter = new OrderToCommentAdapter(data, _mActivity);
                        mRvOrderToComment.setAdapter(adapter);
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
        mRvOrderToComment.setLayoutManager(manager);
        mSwOrderToComment.setOnRefreshListener(this);
        mSwOrderToComment.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mSwOrderToComment.setProgressViewOffset(true, -20, 100);
    }

    @Override
    public void onRefresh() {
        mSwOrderToComment.setRefreshing(true);
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
                                mSwOrderToComment.setRefreshing(false);
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
