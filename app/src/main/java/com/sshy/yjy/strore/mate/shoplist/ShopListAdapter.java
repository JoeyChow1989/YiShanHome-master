package com.sshy.yjy.strore.mate.shoplist;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;
import com.sshy.yjy.ui.recycler.adapter.MultipleRecyclerAdapter;
import com.sshy.yjy.ui.recycler.adapter.MultipleViewHolder;

import java.util.List;

import strore.yjy.sshy.com.mate.app.AppConfig;

import static strore.yjy.sshy.com.mate.app.AppConfig.RECYCLER_OPTIONS;

/**
 * create date：2018/9/5
 * create by：周正尧
 */
public class ShopListAdapter extends BaseQuickAdapter<MultipleItemEntity, BaseViewHolder> {

    public ShopListAdapter(int layoutResId, @Nullable List<MultipleItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItemEntity item) {
        final String title;
        final String price;
        final String text;
        final String pos;
        final String sold;
        final String image_url;

        title = item.getField(MultipleFields.NAME);
        price = item.getField(MultipleFields.PRICE);
        text = item.getField(MultipleFields.TITLE);
        pos = item.getField(MultipleFields.DISTANCE);
        sold = item.getField(MultipleFields.SOLD);
        image_url = item.getField(MultipleFields.IMAGE_URL);

        helper.setText(R.id.tv_index_item_title, title);
        if (price != null) {
            helper.setText(R.id.tv_index_item_price, String.valueOf(Double.parseDouble(price)));
        }
        helper.setText(R.id.tv_index_item_text, text);
        if (pos != null) {
            helper.setText(R.id.tv_index_item_distance, Double.parseDouble(pos) + "km");
        }
        helper.setText(R.id.tv_index_item_sold, "已售:" + sold);

        Glide.with(mContext)
                .load(AppConfig.API_HOST + image_url)
                .apply(AppConfig.RECYCLER_OPTIONS)
                .into((AppCompatImageView) helper.getView(R.id.img_multiple));
    }
}
