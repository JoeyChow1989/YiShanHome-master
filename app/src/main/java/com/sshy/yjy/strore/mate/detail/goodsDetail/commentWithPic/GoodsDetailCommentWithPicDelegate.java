package com.sshy.yjy.strore.mate.detail.goodsDetail.commentWithPic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.strore.mate.detail.goodsDetail.comment.GoodsDetailCommentItemClickListener;
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
public class GoodsDetailCommentWithPicDelegate extends MateDelegate {

    private RecyclerView mRvGoodsDetailCommentWithPic = null;
    private String mGoodsId = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_goodsdetail_comment_withpic;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRvGoodsDetailCommentWithPic = $(R.id.rv_goods_detail_comment_withpic);
        mGoodsId = MatePreference.getCustomAppProfile("productId");
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

                        MateLogger.d("CommentWithPic", s);

                        final List<MultipleItemEntity> data =
                                new GoodsDetailCommentWithPicConverter().setJsonData(s).convert();
                        final GoodsDetailCommentWithPicAdapter mAdapter =
                                new GoodsDetailCommentWithPicAdapter(R.layout.item_shop_detail_comment_pics,
                                        data);
                        mRvGoodsDetailCommentWithPic.setAdapter(mAdapter);
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
        mRvGoodsDetailCommentWithPic.setLayoutManager(manager);
        final MateDelegate delegate = (MateDelegate) getParentFragment();
        mRvGoodsDetailCommentWithPic.addOnItemTouchListener(GoodsDetailCommentItemClickListener.create(delegate));
    }
}
