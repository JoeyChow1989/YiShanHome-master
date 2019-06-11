package com.sshy.yjy.strore.mate.main.personal.collect;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

/**
 * create date：2019/3/29
 * create by：周正尧
 */
public class ProduceCollectItemClickListener extends SimpleClickListener {

    private final FragmentActivity DELEGATE;

    private ProduceCollectItemClickListener(FragmentActivity delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(FragmentActivity delegate) {
        return new ProduceCollectItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

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
