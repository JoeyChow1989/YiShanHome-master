package com.sshy.yjy.strore.mate.detail.shopDetail.shopMsg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.sshy.yjy.strore.R;

import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.delegates.MateDelegate;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_ADDR;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_IMG;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_LINK;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_NAME;
import static strore.yjy.sshy.com.mate.app.AppConfig.RECYCLER_OPTIONS;

/**
 * create date：2018/9/8
 * create by：周正尧
 */
public class ShopDetailShopMsg extends MateDelegate {

    private AppCompatImageView pic1;
    private AppCompatImageView pic2;
    private AppCompatImageView pic3;

    private AppCompatTextView name;
    private AppCompatTextView cart;
    private AppCompatTextView addr;
    private AppCompatTextView phone;
    private AppCompatTextView time;


    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_detail_shopmsg;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        pic1 = $(R.id.id_shop_detail_msg_pic1);
        pic2 = $(R.id.id_shop_detail_msg_pic2);
        pic3 = $(R.id.id_shop_detail_msg_pic3);

        name = $(R.id.id_shop_detail_msg_name);
        cart = $(R.id.id_shop_detail_msg_cart);
        addr = $(R.id.id_shop_detail_msg_addr);
        phone = $(R.id.id_shop_detail_msg_phone);
        time = $(R.id.id_shop_detail_msg_time);

        Glide.with(this)
                .load(AppConfig.API_HOST + MatePreference.getCustomAppProfile(ARG_MERCHANT_IMG))
                .apply(RECYCLER_OPTIONS)
                .into(pic1);

        name.setText(MatePreference.getCustomAppProfile(ARG_MERCHANT_NAME));
        cart.setText("零售");
        addr.setText(MatePreference.getCustomAppProfile(ARG_MERCHANT_ADDR));
        phone.setText(MatePreference.getCustomAppProfile(ARG_MERCHANT_LINK));

        time.setText("8:00 - 21:00");
    }
}
