package com.sshy.yjy.strore.mate.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.sort.list.VerticalListDataConverter;
import com.sshy.yjy.strore.mate.main.sort.list.adapter.SortRecyclerAdapter;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * Created by 周正尧 on 2018/3/20 0020.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */

public class SortDelegate extends BaseActivity {

    private AppCompatImageView mBack;
    private RecyclerView mRecylerViewVertiacalList;

    public static SortDelegate create() {
        final SortDelegate delegate = new SortDelegate();
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mBack = $(R.id.id_left_back_sort);
        mRecylerViewVertiacalList = $(R.id.rv_vertical_menu_list);
        initRecyclerView();

        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        RxRestClient.builder()
                .url("api/findAllCategoryList")
                .header(GetDataBaseUserProfile.getCustomId())
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
                    public void onNext(String s) {
                        final ArrayList<MultipleItemEntity> data = new VerticalListDataConverter()
                                .setJsonData(s).convert();
                        final SortRecyclerAdapter adapter = new SortRecyclerAdapter(
                                data, SortDelegate.this);
                        mRecylerViewVertiacalList.setAdapter(adapter);
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
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.colorPrimary);
    }

    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecylerViewVertiacalList.setLayoutManager(manager);
        //屏蔽动画效果
        mRecylerViewVertiacalList.setItemAnimator(null);
    }
}
