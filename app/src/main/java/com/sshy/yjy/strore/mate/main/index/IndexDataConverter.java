package com.sshy.yjy.strore.mate.main.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sshy.yjy.strore.mate.main.index.bean.BannerBean;
import com.sshy.yjy.strore.mate.main.index.bean.ButtonBean;
import com.sshy.yjy.strore.mate.main.index.bean.ViewPagerBean;
import com.sshy.yjy.ui.recycler.DataConverter;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;


/**
 * Created by 周正尧 on 2018/3/23 0023.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */

public class IndexDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final String msg = JSON.parseObject(getJsonData()).getString("msg");
        final JSONArray tagArray = JSON.parseObject(getJsonData()).getJSONArray("tagList");
        final JSONArray bannerArray = JSON.parseObject(getJsonData()).getJSONArray("banners");
        final JSONArray buttonArray = JSON.parseObject(getJsonData()).getJSONArray("buttons");
        final JSONArray merchantArray = JSON.parseObject(getJsonData()).getJSONArray("merchantList");

        final ArrayList<BannerBean> bannerImages = new ArrayList<>();
        final ArrayList<ViewPagerBean> tagsList = new ArrayList<>();
        final ArrayList<ButtonBean> buttons = new ArrayList<>();

        int type = 0;
        if (bannerArray != null) {
            type = ItemType.BANNER;
            final int bannerSize = bannerArray.size();
            for (int i = 0; i < bannerSize; i++) {
                final JSONObject dataBanner = bannerArray.getJSONObject(i);
                final String bannerId = dataBanner.getString("bannerId");
                final String bannerImg = dataBanner.getString("img");
                final String bannerIsDel = dataBanner.getString("isDel");
                final String createDate = dataBanner.getString("createDate");
                final String clickType = dataBanner.getString("clickType");
                final String targetValue = dataBanner.getString("targetValue");

                final BannerBean bean = new BannerBean.Builder()
                        .setItemType(type)
                        .setBannerId(bannerId)
                        .setImg(bannerImg)
                        .setIsDel(bannerIsDel)
                        .setCreateDate(createDate)
                        .setClickType(clickType)
                        .setTargetValue(targetValue)
                        .build();
                bannerImages.add(bean);
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.BANNERS, bannerImages)
                    .build();

            ENTITIES.add(entity);
        }

        if (tagArray != null) {
            type = ItemType.VIEW_PAGER;
            final int tagSize = tagArray.size();
            for (int i = 0; i < tagSize; i++) {
                final JSONObject tag = tagArray.getJSONObject(i);
                final String pos = tag.getString("pos");
                final String createDateZH = tag.getString("createDateZH");
                final String tagId = tag.getString("tagId");
                final String tagName = tag.getString("tagName");
                final String isDel = tag.getString("isDel");
                final String icon = tag.getString("icon");
                final String createDate = tag.getString("createDate");

                final ViewPagerBean tagBean = new ViewPagerBean.Builder()
                        .setItemType(type)
                        .setPos(pos)
                        .setCreateDateZH(createDateZH)
                        .setTagId(tagId)
                        .setTagName(tagName)
                        .setDel(isDel)
                        .setIcon(icon)
                        .setCreateDate(createDate)
                        .build();

                tagsList.add(tagBean);
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.PAGERS, tagsList)
                    .build();

            ENTITIES.add(entity);
        }

        if (buttonArray != null) {
            type = ItemType.TEB;
            final int buttonSize = buttonArray.size();
            for (int i = 0; i < buttonSize; i++) {
                final JSONObject dataButton = buttonArray.getJSONObject(i);
                final String catId = dataButton.getString("catId");
                final String catName = dataButton.getString("catName");

                final ButtonBean bean = new ButtonBean.Builder()
                        .setItemType(type)
                        .setCatId(catId)
                        .setCatName(catName)
                        .build();
                buttons.add(bean);
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.BUTTON, buttons)
                    .build();
            ENTITIES.add(entity);
        }

        if (msg != null) {
            type = ItemType.TEXT;
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.TEXT, "附近的店")
                    .build();
            ENTITIES.add(entity);
        }

        if (merchantArray != null) {
            type = ItemType.TEXT_IMAGE;
            final int merchantSize = merchantArray.size();
            for (int i = 0; i < merchantSize; i++) {
                final JSONObject dataMerChant = merchantArray.getJSONObject(i);
                final String price = dataMerChant.getString("price");
                final String tagList = dataMerChant.getString("tagList");
                final String shopDistance = dataMerChant.getString("shopDistance");
                final String pos = dataMerChant.getString("pos");
                final String merchantId = dataMerChant.getString("merchantId");
                final String merchantName = dataMerChant.getString("merchantName");
                final String address = dataMerChant.getString("address");
                final String lat = dataMerChant.getString("lat");
                final String lng = dataMerChant.getString("lng");
                final String merchantImg = dataMerChant.getString("merchantImg");
                final String isDel = dataMerChant.getString("isDel");
                final String username = dataMerChant.getString("username");
                final String password = dataMerChant.getString("password");
                final String mobile = dataMerChant.getString("mobile");
                final String createDate = dataMerChant.getString("createDate");
                final String linkman = dataMerChant.getString("linkman");
                final String link = dataMerChant.getString("link");
                final String operatingStatus = dataMerChant.getString("operatingStatus");
                final String orderCount = dataMerChant.getString("orderCount");
                final boolean isShow = dataMerChant.getBooleanValue("isshow");
                final String businessLicense = dataMerChant.getString("businessLicense");
                final String allowedOpen = dataMerChant.getString("allowedOpen");
                final String trueName = dataMerChant.getString("truename");
                final String introduction = dataMerChant.getString("introduction");
                final String score = dataMerChant.getString("score");
                final String serviceQuality = dataMerChant.getString("serviceQuality");
                final String punctualityRate = dataMerChant.getString("punctualityRate");
                final int isCollect = dataMerChant.getInteger("isCollect");

                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, type)
                        .setField(MultipleFields.ID, merchantId)
                        .setField(MultipleFields.IMAGE_URL, merchantImg)
                        .setField(MultipleFields.NAME, merchantName)
                        .setField(MultipleFields.SCORE, score)
                        .setField(MultipleFields.STAR, serviceQuality)
                        .setField(MultipleFields.TITLE, address)
                        .setField(MultipleFields.SOLD, orderCount)
                        .setField(MultipleFields.PUNCT, punctualityRate)
                        .setField(MultipleFields.TAG, link)
                        .setField(MultipleFields.DISTANCE, shopDistance)
                        .setField(MultipleFields.PRICE, price)
                        .setField(MultipleFields.COLLECT,isCollect)
                        .build();

                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
