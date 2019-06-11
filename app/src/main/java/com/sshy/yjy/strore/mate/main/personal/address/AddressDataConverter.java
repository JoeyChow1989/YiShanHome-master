package com.sshy.yjy.strore.mate.main.personal.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sshy.yjy.ui.recycler.DataConverter;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * create date：2018/4/23
 * create by：周正尧
 */
public class AddressDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = array.getJSONObject(i);
            final String addressId = data.getString("addressId");
            final String truename = data.getString("truename");
            final String mobile = data.getString("mobile");
            final String detailAddress = data.getString("detailAddress");
            final String userId = data.getString("userId");
            final String addTime = data.getString("addTime");
            final int isdefault = data.getInteger("isDefault");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(AddressItemType.ITEM_ADDRESS)
                    .setField(MultipleFields.ID, addressId)
                    .setField(MultipleFields.NAME, truename)
                    .setField(MultipleFields.TAG, userId)
                    .setField(AddressItemFields.PHONE, mobile)
                    .setField(AddressItemFields.ADDRESS, detailAddress)
                    .setField(AddressItemFields.TIME, addTime)
                    .setField(AddressItemFields.DEFAULT,isdefault)
                    .build();

            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
