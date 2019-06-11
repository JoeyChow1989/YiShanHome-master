package com.sshy.yjy.strore.mate.detail.shopDetail.project.pagers;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sshy.yjy.strore.R;

import java.util.List;

import strore.yjy.sshy.com.mate.app.AppConfig;

/**
 * create date：2018/9/7
 * create by：周正尧
 */
public class ShopDetailProPagersAdapter extends BaseQuickAdapter<TopViewPager, BaseViewHolder> {

    //设置图片加载策略
    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    public ShopDetailProPagersAdapter(int layoutResId, @Nullable List<TopViewPager> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, TopViewPager item) {
        helper.setText(R.id.tv_shop_detail_pages_title, item.getTitle());
        helper.setText(R.id.tv_shop_detail_pages_price, item.getPrice());

        Glide.with(mContext)
                .load(AppConfig.API_HOST + item.getImageUrl())
                .apply(RECYCLER_OPTIONS)
                .into((AppCompatImageView) helper.getView(R.id.tv_shop_detail_pages_pic));
    }
}
