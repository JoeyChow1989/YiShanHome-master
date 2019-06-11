package com.sshy.yjy.strore.mate.main.sort.content;

import android.support.v4.app.FragmentActivity;
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
 * Created by 周正尧 on 2018/3/28 0028.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */
public class SortContentItemClickListener extends SimpleClickListener {

    private final FragmentActivity DELEGATE;

    private SortContentItemClickListener(FragmentActivity delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(FragmentActivity delegate) {
        return new SortContentItemClickListener(delegate);
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
