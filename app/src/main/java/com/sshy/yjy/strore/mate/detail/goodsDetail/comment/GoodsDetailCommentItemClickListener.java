package com.sshy.yjy.strore.mate.detail.goodsDetail.comment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import strore.yjy.sshy.com.mate.delegates.MateDelegate;

/**
 * create date：2018/9/8
 * create by：周正尧
 */
public class GoodsDetailCommentItemClickListener extends SimpleClickListener {

    private final MateDelegate DELEGATE;

    private GoodsDetailCommentItemClickListener(MateDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(MateDelegate delegate) {
        return new GoodsDetailCommentItemClickListener(delegate);
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
