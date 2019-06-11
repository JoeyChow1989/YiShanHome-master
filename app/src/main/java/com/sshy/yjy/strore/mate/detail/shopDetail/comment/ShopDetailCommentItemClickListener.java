package com.sshy.yjy.strore.mate.detail.shopDetail.comment;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

/**
 * create date：2018/9/7
 * create by：周正尧
 */
public class ShopDetailCommentItemClickListener extends SimpleClickListener {

    private final FragmentActivity DELEGATE;

    private ShopDetailCommentItemClickListener(FragmentActivity delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(FragmentActivity delegate) {
        return new ShopDetailCommentItemClickListener(delegate);
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
