package com.sshy.yjy.strore.mate.shoplist;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.sshy.yjy.strore.mate.detail.shopDetail.ShopDetailDelegate;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_ADDR;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_DIS;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_IMG;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_LINK;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_NAME;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_PUB;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_QUITY;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_SCORE;

/**
 * create date：2019/3/29
 * create by：周正尧
 */
public class ShopListItemClickListener extends SimpleClickListener {

    private final Activity DELEGATE;

    private ShopListItemClickListener(Activity delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(Activity delegate) {
        return new ShopListItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final String merchantId = entity.getField(MultipleFields.ID);
        final String merchantName = entity.getField(MultipleFields.NAME);
        final String address = entity.getField(MultipleFields.TITLE);
        final String mobile = entity.getField(MultipleFields.TAG);
        final String pic = entity.getField(MultipleFields.IMAGE_URL);
        final String distance = entity.getField(MultipleFields.DISTANCE);
        final String score = entity.getField(MultipleFields.SCORE);
        final String serviceQuality = entity.getField(MultipleFields.STAR);
        final String pub = entity.getField(MultipleFields.PUNCT);

        MatePreference.addCustomAppProfile(AppConfig.ARG_MERCHANT_ID, merchantId);
        MatePreference.addCustomAppProfile(ARG_MERCHANT_NAME,merchantName);
        MatePreference.addCustomAppProfile(ARG_MERCHANT_ADDR,address);
        MatePreference.addCustomAppProfile(ARG_MERCHANT_LINK,mobile);
        MatePreference.addCustomAppProfile(ARG_MERCHANT_IMG, pic);
        MatePreference.addCustomAppProfile(ARG_MERCHANT_DIS, distance);
        MatePreference.addCustomAppProfile(ARG_MERCHANT_SCORE, score);
        MatePreference.addCustomAppProfile(ARG_MERCHANT_QUITY, serviceQuality);
        MatePreference.addCustomAppProfile(ARG_MERCHANT_PUB, pub);

        ShopDetailDelegate.startAction(DELEGATE, merchantId);

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
