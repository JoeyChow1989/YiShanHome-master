package com.sshy.yjy.strore.mate.detail.shopDetail.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import com.sshy.yjy.strore.R;
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
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_PUB;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_QUITY;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_SCORE;

/**
 * create date：2018/9/7
 * create by：周正尧
 */
public class ShopDetailCommentDelegate extends MateDelegate {

    private RecyclerView mRvComment = null;
    private LinearLayoutCompat mNoCommentLayout = null;

    private AppCompatTextView score;
    private AppCompatTextView serviceQuality;
    private AppCompatTextView pub;
    private RatingBar star;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shopdetail_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRvComment = $(R.id.ry_shopdetail_comment);
        mNoCommentLayout = $(R.id.ly_no_comment);
        initRecyclerView();
        iniData();
    }

    private void iniData() {
        RxRestClient.builder()
                .url("api/findCommentPage")
                .loader(_mActivity)
                .params("currentPage", "1")
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
                    public void onNext(String s) {
                        MateLogger.d("comment", s);
                        final List<MultipleItemEntity> data =
                                new ShopCommentConverter().setJsonData(s).convert();
                        ShopCommentAdapter mAdapter = new ShopCommentAdapter(data);
                        View starView = LayoutInflater.from(_mActivity).inflate(R.layout.item_shop_detail_comment_star, null,
                                false);

                        score = starView.findViewById(R.id.tv_shop_detail_comment_star_num);
                        serviceQuality = starView.findViewById(R.id.tv_shop_detail_comment_star_quality);
                        pub = starView.findViewById(R.id.tv_shop_detail_comment_star_ontime);
                        star = starView.findViewById(R.id.rating_goods_detail);

                        String scoreNum = MatePreference.getCustomAppProfile(AppConfig.ARG_MERCHANT_SCORE);

                        if (scoreNum != null || !scoreNum.isEmpty()) {
                            score.setText(scoreNum);
                            float starNum = Float.parseFloat(scoreNum);
                            star.setRating(starNum);
                        }

                        String servQua = MatePreference.getCustomAppProfile(AppConfig.ARG_MERCHANT_QUITY);

                        if (servQua !=null || !servQua.isEmpty()){
                            serviceQuality.setText(servQua);
                        }

                        String pubn = MatePreference.getCustomAppProfile(AppConfig.ARG_MERCHANT_PUB);
                        if (pubn != null || !pubn.isEmpty()) {
                            pub.setText(Double.parseDouble(pubn) * 100 + "%");
                        }

                        mAdapter.addHeaderView(starView);
                        mRvComment.setAdapter(mAdapter);
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
        mRvComment.setLayoutManager(manager);
        mRvComment.addOnItemTouchListener(ShopDetailCommentItemClickListener.create(_mActivity));
    }
}