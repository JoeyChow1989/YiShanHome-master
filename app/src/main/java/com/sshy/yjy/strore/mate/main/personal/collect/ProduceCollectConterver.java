package com.sshy.yjy.strore.mate.main.personal.collect;

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
public class ProduceCollectConterver extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        final int type = ItemType.ITEM;
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int isCollect = data.getInteger("isCollect");
            final String productId = data.getString("productId");
            final String productName = data.getString("productName");
            final String price = data.getString("price");
            final String retailPrice = data.getString("retailPrice");
            final String soldCount = data.getString("soldCount");
            final String productLogo = data.getString("productLogo");

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
