package com.sshy.yjy.strore.mate.sortShopList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sshy.yjy.ui.recycler.DataConverter;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * create date：2019/4/12
 * create by：周正尧
 */
public class SortShopListConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        final int type = ItemType.ITEM;
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String introduction = data.getString("introduction");
            final String tag = data.getString("tag");
            final String otherColumn = data.getString("otherColumn");
            final int isCollect = data.getInteger("isCollect");
            final String pos = data.getString("pos");
            final String catName = data.getString("catName");
            final String merchantName = data.getString("merchantName");
            final String fromTime = data.getString("fromTime");
            final String toTime = data.getString("toTime");
            final String productId = data.getString("productId");
            final String productName = data.getString("productName");
            final String price = data.getString("price");
            final String retailPrice = data.getString("retailPrice");
            final String isDel = data.getString("isDel");
            final String soldCount = data.getString("soldCount");
            final String createDate = data.getString("createDate");
            final String catId = data.getString("catId");
            final String merchantId = data.getString("merchantId");
            final String detail = data.getString("detail");
            final String productLogo = data.getString("productLogo");
            final String recommend = data.getString("recommend");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.COLLECT, isCollect)
                    .setField(MultipleFields.ID, productId)
                    .setField(MultipleFields.TITLE, productName)
                    .setField(MultipleFields.PRICE, price)
                    .setField(MultipleFields.REALPRICE, retailPrice)
                    .setField(MultipleFields.SOLD, soldCount)
                    .setField(MultipleFields.IMAGE_URL, productLogo)
                    .build();

            ENTITIES.add(entity);

        }
        return ENTITIES;
    }
}
