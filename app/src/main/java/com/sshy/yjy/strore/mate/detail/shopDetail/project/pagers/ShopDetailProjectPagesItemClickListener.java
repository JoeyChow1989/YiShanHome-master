package com.sshy.yjy.strore.mate.detail.shopDetail.project.pagers;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.sshy.yjy.strore.mate.detail.goodsDetail.GoodsDetailDelegate;


/**
 * create date：2018/9/7
 * create by：周正尧
 */
public class ShopDetailProjectPagesItemClickListener extends SimpleClickListener {

    private final FragmentActivity DELEGATE;

    private ShopDetailProjectPagesItemClickListener(FragmentActivity delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(FragmentActivity delegate) {
        return new ShopDetailProjectPagesItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final TopViewPager entity = (TopViewPager) baseQuickAdapter.getData().get(position);
        final String productId = entity.getId();
        GoodsDetailDelegate.startAction(DELEGATE,productId);
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
