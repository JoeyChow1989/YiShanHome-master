package com.sshy.yjy.strore.mate.sortShopList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_PRODUCT_TAG;

/**
 * create date：2019/4/3
 * create by：周正尧
 */
public class SortShopListDelegate extends BaseActivity {

    private RecyclerView mRecyclerView;
    private String mTitle;
    private AppCompatTextView title;
    private AppCompatImageView mBack = null;


    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String tagId, String tagName) {
        Intent intent = new Intent(activity, SortShopListDelegate.class);
        intent.putExtra(AppConfig.ARG_PRODUCT_TAG, tagId);
        intent.putExtra("title", tagName);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_sort_shoplist;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mRecyclerView = $(R.id.id_rcy_sort_shoplist);
        initViews();
        initData();
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.colorPrimary);
    }

    private void initData() {
        RxRestClient.builder()
                .url("api/findProductListByTag")
                .loader(this)
                .header(GetDataBaseUserProfile.getCustomId())
                .params("tag", getIntent().getStringExtra(ARG_PRODUCT_TAG))
                .params("lng", MatePreference.getCustomAppProfile("lng"))
                .params("lat", MatePreference.getCustomAppProfile("lat"))
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
                            final List<MultipleItemEntity> data = new SortShopListConverter().setJsonData(response)
                                    .convert();
                            final SortShopListAdapter adapter = new SortShopListAdapter(R.layout.item_product_collect,
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

    private void initViews() {
        mTitle = getIntent().getStringExtra("title");
        title = $(R.id.id_sort_shoplist_title);
        title.setText(mTitle);

        mBack = $(R.id.id_sort_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnItemTouchListener(SortShopListItemClickListener.create(this));
    }
}