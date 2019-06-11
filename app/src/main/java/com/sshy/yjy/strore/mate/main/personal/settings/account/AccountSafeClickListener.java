package com.sshy.yjy.strore.mate.main.personal.settings.account;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.sshy.yjy.strore.mate.main.personal.list.ListBean;

/**
 * create date：2018/9/10
 * create by：周正尧
 */
public class AccountSafeClickListener extends SimpleClickListener {

    private final Context DELEGATE;

    private AccountSafeClickListener(Context delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(Context delegate) {
        return new AccountSafeClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        Intent intent = null;
        switch (id) {
            case 1:
                intent = new Intent(DELEGATE,bean.getDelegate().getClass());
                break;
            case 2:
                intent = new Intent(DELEGATE,bean.getDelegate().getClass());
                break;
            default:
                break;
        }

        DELEGATE.startActivity(intent);


    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
