package com.sshy.yjy.strore.mate.main.personal.settings.cooperation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.personal.list.ListAdapter;
import com.sshy.yjy.strore.mate.main.personal.list.ListBean;
import com.sshy.yjy.strore.mate.main.personal.list.ListItemType;
import com.sshy.yjy.ui.recycler.BaseDecoration;

import java.util.ArrayList;
import java.util.List;

import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/9/13
 * create by：周正尧
 */
public class  CooperationDelegate extends BaseActivity {

    private RecyclerView mRvCooperation = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_cooperation;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mRvCooperation = $(R.id.rv_cooperation);
        initData();

        ListBean health = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_COOPER)
                .setId(1)
                .setPic(R.drawable.ic_cooper_health_pic)
                .setText("养身商家入驻")
                .setValue("加入颐闪到家，实现销售用户双增长")
                .setDelegate(new CooperationWantedDelegate())
                .build();

        ListBean foods = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_COOPER)
                .setId(2)
                .setPic(R.drawable.ic_cooper_foods_pic)
                .setText("美食商家入驻")
                .setValue("加入颐闪到家，实现销售用户双增长")
                .setDelegate(new CooperationWantedDelegate())
                .build();

        ListBean trains = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_COOPER)
                .setId(3)
                .setPic(R.drawable.ic_cooper_trains_pic)
                .setText("教育商家入驻")
                .setValue("加入颐闪到家，实现销售用户双增长")
                .setDelegate(new CooperationWantedDelegate())
                .build();

        ListBean houseKeep = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_COOPER)
                .setId(4)
                .setPic(R.drawable.ic_cooper_keep_pic)
                .setText("家政商家入驻")
                .setValue("加入颐闪到家，实现销售用户双增长")
                .setDelegate(new CooperationWantedDelegate())
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(health);
        data.add(foods);
        data.add(trains);
        data.add(houseKeep);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvCooperation.setLayoutManager(manager);
        mRvCooperation.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(this,
                R.color.app_background), 3));
        final ListAdapter adapter = new ListAdapter(data);
        mRvCooperation.setAdapter(adapter);
        mRvCooperation.addOnItemTouchListener(CooperationClickListener.create(this));
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    private void initData() {

    }
}
