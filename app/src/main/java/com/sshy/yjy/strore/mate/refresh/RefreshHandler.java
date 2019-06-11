package com.sshy.yjy.strore.mate.refresh;

import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.strore.mate.main.index.IndexAdapter;
import com.sshy.yjy.ui.recycler.DataConverter;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;
import com.sshy.yjy.ui.recycler.adapter.MultipleRecyclerAdapter;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.app.Mate;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

/**
 * Created by 周正尧 on 2018/3/22 0022.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */

public class RefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private ArrayList<MultipleItemEntity> mDataList = null;
    private final FragmentActivity DELEGATE;

//    private int pageSize = 0;
//    private int currentCount = 0;
//    private int total = 0;
//    private int index = 0;

    private RefreshHandler(SwipeRefreshLayout refreshLayout,
                           RecyclerView recyclerView,
                           DataConverter converter,
                           FragmentActivity delegate) {
        this.REFRESH_LAYOUT = refreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.DELEGATE = delegate;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout refreshLayout,
                                        RecyclerView recyclerView,
                                        DataConverter converter,
                                        FragmentActivity delegate) {
        return new RefreshHandler(refreshLayout, recyclerView, converter, delegate);
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);

        final String lng = MatePreference.getCustomAppProfile("lng");
        final String lat = MatePreference.getCustomAppProfile("lat");

        MateLogger.d("dingwei", lng + "-----"
                + lat);

        Mate.getHandler().postDelayed(() -> {
            RxRestClient.builder()
                    .url("api/getHomeData")
                    .params("lng", lng)
                    .params("lat", lat)
                    .params("currentPage", 1)
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
                            MateLogger.d("home", response);
                            mDataList.clear();
                            int code = JSON.parseObject(response).getIntValue("code");
                            int merchantCount = JSON.parseObject(response).getInteger("merchantCount");
                            if (code == 200) {
                                mDataList = CONVERTER.setJsonData(response).convert();
                                mAdapter.setNewData(mDataList);
                                if (merchantCount >= 4) {
                                    mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                                }
                                mAdapter.notifyDataSetChanged();
                                REFRESH_LAYOUT.setRefreshing(false);
                                mAdapter.setEnableLoadMore(true);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }, 2000);
    }

    @SuppressWarnings("unchecked")
    public void firstPage(String url, String lng, String lat) {
        RxRestClient.builder()
                .url(url)
                .params("lng", lng)
                .params("lat", lat)
                .params("currentPage", "1")
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
                        MateLogger.d("index", response);
                        REFRESH_LAYOUT.setRefreshing(false);
                        int code = JSON.parseObject(response).getIntValue("code");
                        int merchantCount = JSON.parseObject(response).getInteger("merchantCount");
                        if (code == 200) {
                            mDataList = CONVERTER.setJsonData(response).convert();
                            mAdapter = IndexAdapter.create(mDataList, DELEGATE);
                            if (merchantCount >= 4) {
                                mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                            }
                            RECYCLERVIEW.setAdapter(mAdapter);
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

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }

//    private void paging(final String url) {
//        pageSize = BEAN.getPageSize();
//        currentCount = BEAN.getCurrentCount();
//        total = BEAN.getTotal();
//        index = BEAN.getPageIndex();
//
//        if (mDataList.size() < pageSize || currentCount >= total) {
//            mAdapter.loadMoreEnd(true);
//        } else {
//            Mate.getHandler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    RestClient.builder()
//                            .url(url + index)
//                            .success(new ISuccess() {
//                                @Override
//                                public void onSuccess(String response) {
//                                    mAdapter.addData(mDataList);
//                                    BEAN.setCurrentCount(mAdapter.getData().size());
//                                    mAdapter.loadMoreComplete();
//                                    BEAN.addIndex();
//                                }
//                            })
//                            .build()
//                            .get();
//                }
//            }, 1000);
//        }
//    }
}
