package com.sshy.yjy.strore.mate.main.index.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.strore.mate.sortShopList.SortShopListAdapter;
import com.sshy.yjy.strore.mate.sortShopList.SortShopListConverter;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

/**
 * create date：2018/5/8
 * create by：周正尧
 */
public class SearchDelegate extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView = null;
    private AppCompatEditText mSearchEdit = null;
    private AppCompatImageView mBack = null;
    private AppCompatImageView mTopSearchIcon = null;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SearchDelegate.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_search;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mRecyclerView = $(R.id.rv_search);
        mTopSearchIcon = $(R.id.tv_top_search);
        mSearchEdit = $(R.id.et_search_view);
        mTopSearchIcon.setOnClickListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnItemTouchListener(SearchItemClickListener.create(this));

        mBack = $(R.id.icon_top_search_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        RxRestClient.builder()
                .url("api/searchProduct")
                .header(GetDataBaseUserProfile.getCustomId())
                .params("lng", MatePreference.getCustomAppProfile("lng"))
                .params("lat", MatePreference.getCustomAppProfile("lat"))
                .params("keyword", mSearchEdit.getText().toString())
                .loader(this)
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
                        final int code = JSON.parseObject(response).getInteger("code");
                        if (code == 0) {
                            final ArrayList<MultipleItemEntity> mData = new SortShopListConverter().setJsonData(response).convert();
                            final SortShopListAdapter mAdapter = new SortShopListAdapter(R.layout.item_product_collect,
                                    mData);
                            mRecyclerView.setAdapter(mAdapter);
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
}
