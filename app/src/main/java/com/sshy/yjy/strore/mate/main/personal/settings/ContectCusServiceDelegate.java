package com.sshy.yjy.strore.mate.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.personal.list.ListAdapter;
import com.sshy.yjy.strore.mate.main.personal.list.ListBean;
import com.sshy.yjy.strore.mate.main.personal.list.ListItemType;

import java.util.ArrayList;
import java.util.List;

import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/9/11
 * create by：周正尧
 */
public class ContectCusServiceDelegate extends BaseActivity {

    private RecyclerView mRvContectService = null;
    private AppCompatImageView mBack = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_contect_cus_service;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mRvContectService = $(R.id.rv_contect_service);

        mBack = $(R.id.id_contect_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        ListBean servicePhone = new ListBean.Builder()
                .setId(1)
                .setItemType(ListItemType.ITEM_NORMAL)
                .setText("固定电话")
                .setValue("31785888")
                .build();


        final List<ListBean> data = new ArrayList<>();
        data.add(servicePhone);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvContectService.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRvContectService.setAdapter(adapter);
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }
}
