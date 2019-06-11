package com.sshy.yjy.strore.mate.main.sort.content.adapter;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;
import com.sshy.yjy.ui.recycler.adapter.MultipleRecyclerAdapter;
import com.sshy.yjy.ui.recycler.adapter.MultipleViewHolder;

import java.util.ArrayList;

import strore.yjy.sshy.com.mate.app.AppConfig;

import static strore.yjy.sshy.com.mate.app.AppConfig.RECYCLER_OPTIONS;

/**
 * create date：2019/1/22
 * create by：周正尧
 */
public class ContentAdapter extends MultipleRecyclerAdapter {

    public ContentAdapter(ArrayList<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.ITEM, R.layout.item_multiple_image_text_sort);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        final String title;
        final String price;
        final String text;
        final String pos;
        final String sold;
        final String image_url;

        switch (holder.getItemViewType()) {
            case ItemType.ITEM:
                title = entity.getField(MultipleFields.NAME);
                price = entity.getField(MultipleFields.PRICE);
                text = entity.getField(MultipleFields.TEXT);
                pos = entity.getField(MultipleFields.DISTANCE);
                sold = entity.getField(MultipleFields.SOLD);
                image_url = entity.getField(MultipleFields.IMAGE_URL);

                holder.setText(R.id.tv_index_item_title, title);
                if (price != null) {
                    holder.setText(R.id.tv_index_item_price, String.valueOf(Double.parseDouble(price)));
                }
                holder.setText(R.id.tv_index_item_text, text);
                if (pos != null) {
                    holder.setText(R.id.tv_index_item_distance, Double.parseDouble(pos) + "km");
                }
                holder.setText(R.id.tv_index_item_sold, "已售:" + sold);

                Glide.with(mContext)
                        .load(AppConfig.API_HOST + image_url)
                        .apply(AppConfig.RECYCLER_OPTIONS)
                        .into((AppCompatImageView) holder.getView(R.id.img_multiple));
                break;
            default:
                break;
        }
    }
}
