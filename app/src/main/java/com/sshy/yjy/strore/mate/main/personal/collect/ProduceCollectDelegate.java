package com.sshy.yjy.strore.mate.main.personal.collect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.delegates.MateDelegate;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;

/**
 * create date：2019/3/29
 * create by：周正尧
 */
public class ProduceCollectDelegate extends MateDelegate {

    private RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_collect_produce;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView = $(R.id.id_collect_produce_rv);
        initView();
        initData();
    }

    private void initData() {
        RxRestClient.builder()
                .url("api/findMyProductCollectList")
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
                        final int code = JSON.parseObject(s).getInteger("code");
                        if (code == 200) {
                            final List<MultipleItemEntity> data = new ProduceCollectConterver().setJsonData(s)
                                    .convert();
                            final ProduceCollectAdapter adapter = new ProduceCollectAdapter(R.layout.item_product_collect,
                                    data);
                            mRecyclerView.setAdapter(adapter);
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

    private void initView() {
        final LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnItemTouchListener(ProduceCollectItemClickListener.create(_mActivity));
    }
}
