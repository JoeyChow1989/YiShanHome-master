package com.sshy.yjy.strore.mate.main.index.search;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.sshy.yjy.strore.mate.detail.goodsDetail.GoodsDetailDelegate;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

/**
 * create date：2019/3/29
 * create by：周正尧
 */
public class SearchItemClickListener extends SimpleClickListener {

    private final Activity DELEGATE;

    private SearchItemClickListener(Activity delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(Activity delegate) {
        return new SearchItemClickListener(delegate);
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
