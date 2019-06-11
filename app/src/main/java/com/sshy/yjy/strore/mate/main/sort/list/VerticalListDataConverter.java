package com.sshy.yjy.strore.mate.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sshy.yjy.ui.recycler.DataConverter;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by 周正尧 on 2018/3/28 0028.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */
public final class VerticalListDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();

        final JSONArray dataArray = JSON.parseObject(getJsonData())
                .getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String catId = data.getString("catId");
            final String catName = data.getString("catName");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID, catId)
                    .setField(MultipleFields.TEXT, catName)
                    .setField(MultipleFields.TAG, false)
                    .build();

            dataList.add(entity);
            //设置第一个被选中
            dataList.get(0).setField(MultipleFields.TAG, true);
        }
        return dataList;
    }
}
