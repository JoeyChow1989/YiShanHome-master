package com.sshy.yjy.strore.mate.sortShopList;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.sshy.yjy.strore.mate.detail.goodsDetail.GoodsDetailDelegate;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

/**
 * create date：2019/4/3
 * create by：周正尧
 */
public class SortShopListItemClickListener extends SimpleClickListener {

    private final Activity DELEGATE;

    private SortShopListItemClickListener(Activity delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(Activity delegate) {
        return new SortShopListItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final String goodId = entity.getField(MultipleFields.ID);
        GoodsDetailDelegate.startAction(DELEGATE, goodId);
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
