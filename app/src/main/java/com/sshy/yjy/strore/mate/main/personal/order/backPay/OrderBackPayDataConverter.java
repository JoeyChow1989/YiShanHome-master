package com.sshy.yjy.strore.mate.main.personal.order.backPay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sshy.yjy.strore.mate.main.personal.order.OrderItemFields;
import com.sshy.yjy.ui.recycler.DataConverter;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

public class OrderBackPayDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = array.getJSONObject(i);
            final String pos = data.getString("pos");
            final String orderId = data.getString("orderId");
            final String merchantId = data.getString("merchantId");
            final String orderNo = data.getString("orderNo");
            final String createDate = data.getString("createDate");
            final String serviceDate = data.getString("serviceDate");
            final int status = data.getIntValue("status");
            final String isDel = data.getString("isDel");
            final double money = data.getDoubleValue("money");
            final String couponId = data.getString("couponId");
            final String couponMoney = data.getString("couponMoney");
            final String title = data.getString("title");
            final String img = data.getString("img");
            final String customerId = data.getString("customerId");

            int mType = 0;
            if (status == 5) {
                mType = ItemType.ITEM_ORDER_BACK_PAY;
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, mType)
                    .setField(MultipleFields.ID, merchantId)
                    .setField(MultipleFields.IMAGE_URL, img)
                    .setField(MultipleFields.TITLE, title)
                    .setField(OrderItemFields.ORDER_ID, orderId)
                    .setField(OrderItemFields.ORDER_NUM,orderNo)
                    .setField(OrderItemFields.ORDER_TIME, createDate)
                    .setField(OrderItemFields.SERVICE_TIME, serviceDate)
                    .setField(OrderItemFields.PRICE, money)
                    //.setField(MultipleFields.STAR, star)
                    .build();

            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
