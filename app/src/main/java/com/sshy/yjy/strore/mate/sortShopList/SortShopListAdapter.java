package com.sshy.yjy.strore.mate.sortShopList;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.List;

import strore.yjy.sshy.com.mate.app.AppConfig;

/**
 * create date：2019/4/12
 * create by：周正尧
 */
public class SortShopListAdapter extends BaseQuickAdapter<MultipleItemEntity, BaseViewHolder> {

    public SortShopListAdapter(int layoutResId, @Nullable List<MultipleItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItemEntity item) {
        final String title;
        final String price;
        final String pos;
        final String sold;
        final String image_url;
        final String retailPrice;

        title = item.getField(MultipleFields.TITLE);
        price = item.getField(MultipleFields.PRICE);
        pos = item.getField(MultipleFields.DISTANCE);
        sold = item.getField(MultipleFields.SOLD);
        image_url = item.getField(MultipleFields.IMAGE_URL);
        retailPrice = item.getField(MultipleFields.REALPRICE);

        helper.setText(R.id.id_product_collect_title, title);
        if (price != null) {
            helper.setText(R.id.id_product_collect_price, String.valueOf(Double.parseDouble(price)));
        }
        if (retailPrice != null) {
            AppCompatTextView real = helper.getView(R.id.id_product_collect_realprice);
            real.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            real.setText(String.valueOf(Double.parseDouble(retailPrice)));
        }
        if (pos != null) {
            helper.setText(R.id.id_shop_detail_distance, Double.parseDouble(pos) + "km");
        }

        helper.setText(R.id.id_product_collect_sold, "已售:" + sold);

        Glide.with(mContext)
                .load(AppConfig.API_HOST + image_url)
                .apply(AppConfig.RECYCLER_OPTIONS)
                .into((AppCompatImageView) helper.getView(R.id.id_product_collect_img));
    }
}
