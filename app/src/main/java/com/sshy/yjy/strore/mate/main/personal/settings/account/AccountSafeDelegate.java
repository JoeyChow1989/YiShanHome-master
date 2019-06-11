package com.sshy.yjy.strore.mate.main.personal.settings.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.personal.list.ListAdapter;
import com.sshy.yjy.strore.mate.main.personal.list.ListBean;
import com.sshy.yjy.strore.mate.main.personal.list.ListItemType;
import com.sshy.yjy.strore.mate.main.personal.settings.ChangePwdDelegate;
import com.sshy.yjy.strore.mate.main.personal.settings.NameSettingDelegate;

import java.util.ArrayList;
import java.util.List;

import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/9/10
 * create by：周正尧
 */
public class AccountSafeDelegate extends BaseActivity {

    private RecyclerView mRvAccountSafe = null;
    private AppCompatImageView mBack = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_account_safe;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mRvAccountSafe = $(R.id.rv_account_safe);
        mBack = $(R.id.id_account_safe_back);

        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        final ListBean changePwd = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NOVAULE)
                .setId(1)
                .setText("修改密码")
                .setDelegate(new ChangePwdDelegate())
                .build();

        final ListBean name = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NOVAULE)
                .setId(2)
                .setText("修改昵称")
                .setDelegate(new NameSettingDelegate())
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(changePwd);
        data.add(name);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvAccountSafe.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRvAccountSafe.setAdapter(adapter);
        mRvAccountSafe.addOnItemTouchListener(AccountSafeClickListener.create(this));
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }
}
