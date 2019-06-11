package com.sshy.yjy.strore.mate.detail.goodsDetail;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;
import com.sshy.yjy.ui.recycler.adapter.MultipleRecyclerAdapter;

import java.util.List;

/**
 * create date：2019/3/12
 * create by：周正尧
 */
public class GoodsDetailAdapter extends MultipleRecyclerAdapter {

    //设置图片加载策略
    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    protected GoodsDetailAdapter(List<MultipleItemEntity> data) {
        super(data);
    }
}
