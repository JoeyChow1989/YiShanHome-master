package com.sshy.yjy.strore.mate.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sshy.yjy.ui.recycler.DataConverter;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * create date：2019/1/22
 * create by：周正尧
 */
public class ContentDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray data = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int type = ItemType.ITEM;
        final int dataSize = data.size();
        for (int i = 0; i < dataSize; i++) {
            final JSONObject dataMerChant = data.getJSONObject(i);
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
                    .setField(MultipleFields.COLLECT, isCollect)
                    .build();

            ENTITIES.add(entity);
        }

        return ENTITIES;
    }

}
