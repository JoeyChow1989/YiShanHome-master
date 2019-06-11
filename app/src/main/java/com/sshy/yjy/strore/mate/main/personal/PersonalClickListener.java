package com.sshy.yjy.strore.mate.main.personal;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.sshy.yjy.strore.mate.main.personal.list.GridBean;

/**
 * create date：2018/4/23
 * create by：周正尧
 */
public class PersonalClickListener extends SimpleClickListener {

    private final FragmentActivity DELEGATE;

    public PersonalClickListener(FragmentActivity delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final GridBean bean = (GridBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        Intent intent = null;
        switch (id) {
            case 1:
                intent = new Intent(DELEGATE,bean.getDelegate().getClass());
                break;
            case 2:
                intent = new Intent(DELEGATE,bean.getDelegate().getClass());
                break;
            case 3:
                intent = new Intent(DELEGATE,bean.getDelegate().getClass());
                break;
            case 4:
                intent = new Intent(DELEGATE,bean.getDelegate().getClass());
                break;
            case 5:
                intent = new Intent(DELEGATE,bean.getDelegate().getClass());
                break;
            case 6:
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
