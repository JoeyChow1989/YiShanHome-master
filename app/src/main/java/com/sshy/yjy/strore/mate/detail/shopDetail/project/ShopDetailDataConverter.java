package com.sshy.yjy.strore.mate.detail.shopDetail.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sshy.yjy.strore.mate.detail.shopDetail.project.pagers.TopViewPager;
import com.sshy.yjy.ui.recycler.DataConverter;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;


/**
 * create date：2018/9/6
 * create by：周正尧
 */
public class ShopDetailDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONObject data = JSON.parseObject(getJsonData()).getJSONObject("data");
        final String msg = JSON.parseObject(getJsonData()).getString("msg");
        final int code = JSON.parseObject(getJsonData()).getIntValue("code");

        final JSONArray productArray = data.getJSONArray("productList");
        final JSONArray recommendArray = data.getJSONArray("recommendProductList");

        final ArrayList<TopViewPager> pagersList = new ArrayList<>();

        int type = 0;
        if (msg != null && code == 200) {
            type = ItemType.TEXT;
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.TEXT, "商家推荐")
                    .build();
            ENTITIES.add(entity);
        }

        if (recommendArray != null) {
            type = ItemType.VIEW_PAGER;
            final int recommendSize = recommendArray.size();
            for (int i = 0; i < recommendSize; i++) {
                final JSONObject recommendList = recommendArray.getJSONObject(i);
                final String pos = recommendList.getString("pos");
                final String catName = recommendList.getString("catName");
                final String fromTime = recommendList.getString("fromTime");
                final String toTime = recommendList.getString("toTime");
                final String productId = recommendList.getString("productId");
                final String productName = recommendList.getString("productName");
                final String price = recommendList.getString("price");
                final String retailPrice = recommendList.getString("retailPrice");
                final String isDel = recommendList.getString("isDel");
                final String soldCount = recommendList.getString("soldCount");
                final String createDate = recommendList.getString("createDate");
                final String catId = recommendList.getString("catId");
                final String merchantId = recommendList.getString("merchantId");
                final String detail = recommendList.getString("detail");
                final String productLogo = recommendList.getString("productLogo");
                final String recommend = recommendList.getString("recommend");

                final TopViewPager pager = new TopViewPager.Builder()
                        .setItemType(type)
                        .setId(productId)
                        .setTitle(productName)
                        .setPrice(price)
                        .setRealprice(retailPrice)
                        .setImageUrl(productLogo)
                        .build();

                pagersList.add(pager);
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.PAGERS, pagersList)
                    .build();

            ENTITIES.add(entity);
        }

        if (productArray != null) {
            type = ItemType.ITEM;
            final int productSize = productArray.size();
            for (int i = 0; i < productSize; i++) {
                final JSONObject productList = productArray.getJSONObject(i);
                final String pos = productList.getString("pos");
                final String catName = productList.getString("catName");
                final String fromTime = productList.getString("fromTime");
                final String toTime = productList.getString("toTime");
                final String productId = productList.getString("productId");
                final String productName = productList.getString("productName");
                final String price = productList.getString("price");
                final String retailPrice = productList.getString("retailPrice");
                final String isDel = productList.getString("isDel");
                final String soldCount = productList.getString("soldCount");
                final String createDate = productList.getString("createDate");
                final String catId = productList.getString("catId");
                final String merchantId = productList.getString("merchantId");
                final String detail = productList.getString("detail");
                final String productLogo = productList.getString("productLogo");

                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, type)
                        .setField(MultipleFields.ID, productId)
                        .setField(MultipleFields.TITLE, productName)
                        .setField(MultipleFields.PRICE, price)
                        .setField(MultipleFields.REALPRICE, retailPrice)
                        .setField(MultipleFields.IMAGE_URL, productLogo)
                        .build();

                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
