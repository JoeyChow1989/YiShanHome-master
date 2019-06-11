package com.sshy.yjy.strore.mate.main.personal.favourable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.sshy.yjy.strore.R;
import com.sshy.yjy.ui.recycler.BaseDecoration;

import strore.yjy.sshy.com.mate.delegates.MateDelegate;

/**
 * create date：2018/9/13
 * create by：周正尧
 */
public class HealthFavourDelegate extends MateDelegate {

    private RecyclerView mRvFavourHealth = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_favour_health;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRvFavourHealth = $(R.id.rv_favour_health);
        initViews();
        initData();
    }

    private void initData() {

    }

    private void initViews() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvFavourHealth.setLayoutManager(manager);
        mRvFavourHealth.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(),
                R.color.app_background), 3));
    }
}
