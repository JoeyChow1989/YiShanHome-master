package com.sshy.yjy.strore.mate.detail.shopDetail.project;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.sshy.yjy.strore.mate.detail.goodsDetail.GoodsDetailDelegate;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

/**
 * create date：2018/9/6
 * create by：周正尧
 */
public class ShopDetailProjectItemClickListener extends SimpleClickListener {

    private final FragmentActivity DELEGATE;

    private ShopDetailProjectItemClickListener(FragmentActivity delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(FragmentActivity delegate) {
        return new ShopDetailProjectItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        if (position > 1) {
            final String productId = entity.getField(MultipleFields.ID);
            GoodsDetailDelegate.startAction(DELEGATE, productId);
        }
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
