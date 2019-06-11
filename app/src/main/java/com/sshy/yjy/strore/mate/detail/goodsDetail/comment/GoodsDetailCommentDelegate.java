package com.sshy.yjy.strore.mate.detail.goodsDetail.comment;

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
import strore.yjy.sshy.com.mate.delegates.MateDelegate;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

/**
 * create date：2018/9/8
 * create by：周正尧
 */
public class GoodsDetailCommentDelegate extends MateDelegate {

    private RecyclerView mRvGoodsDetailComment = null;
    private String mGoodsId = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_goodsdetail_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRvGoodsDetailComment = $(R.id.rv_goods_detail_comment);
        mGoodsId = MatePreference.getCustomAppProfile("productId");
        MateLogger.d("productId", mGoodsId);
        initRecyclerView();
        initData();
    }

    private void initData() {
        RxRestClient.builder()
                .url("api/getProductDetail")
                .header(GetDataBaseUserProfile.getCustomId())
                .params("productId", mGoodsId)
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
                        MateLogger.d("goodsComment",s);
                        final List<MultipleItemEntity> data =
                                new GoodsDetailCommentConverter().setJsonData(s).convert();
                        final GoodsDetailCommentAdapter mAdapter = new GoodsDetailCommentAdapter(data);
                        mRvGoodsDetailComment.setAdapter(mAdapter);
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
        mRvGoodsDetailComment.setLayoutManager(manager);
        final MateDelegate delegate = (MateDelegate) getParentFragment();
        mRvGoodsDetailComment.addOnItemTouchListener(GoodsDetailCommentItemClickListener.create(delegate));
    }
}
