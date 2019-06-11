package com.sshy.yjy.strore.mate.detail.shopDetail.comment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sshy.yjy.ui.recycler.DataConverter;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * create date：2018/9/7
 * create by：周正尧
 */
public class ShopCommentConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray tabArray = JSON.parseObject(getJsonData()).getJSONArray("tagList");
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final ArrayList<TabComment> tabsList = new ArrayList<>();
        ArrayList<CommentImage> imageList = null;
        int type = 0;
        if (tabArray != null) {
            type = ItemType.TEB;
            for (int i = 0; i < tabArray.size(); i++) {
                final JSONObject tabs = tabArray.getJSONObject(i);
                final String id = tabs.getString("id");
                final String commentId = tabs.getString("commentId");
                final String tagId = tabs.getString("tagId");
                final String tag = tabs.getString("tag");
                final String merchantId = tabs.getString("merchantId");

                TabComment tabComment = new TabComment.Builder()
                        .setId(id)
                        .setCid(commentId)
                        .setTid(tagId)
                        .setTag(tag)
                        .setMid(merchantId)
                        .build();

                tabsList.add(tabComment);
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(CommentFields.TAB, tabsList)
                    .build();

            ENTITIES.add(entity);
        }

        if (dataArray != null) {
            for (int i = 0; i < dataArray.size(); i++) {
                final JSONObject data = dataArray.getJSONObject(i);
                final JSONArray dataImage = data.getJSONArray("imgList");
                imageList = new ArrayList<>();
                if (dataImage.size() > 0) {
                    type = ItemType.TEXT_IMAGE;
                    for (int j = 0; j < dataImage.size(); j++) {
                        final JSONObject images = dataImage.getJSONObject(j);
                        final String id = images.getString("id");
                        final String img = images.getString("img");
                        final String commentId = images.getString("commentId");
                        final String isDel = images.getString("isDel");

                        final CommentImage commentImage = new CommentImage.Builder()
                                .setItemType(type)
                                .setId(id)
                                .setImg(img)
                                .setCommentId(commentId)
                                .setIsDel(isDel)
                                .build();

                        imageList.add(commentImage);
                    }
                } else {
                    type = ItemType.TEXT;
                }

                final String pos = data.getString("pos");
                final String nickname = data.getString("nickname");
                final String avatar = data.getString("avatar");
                final String commentId = data.getString("commentId");
                final int serviceQualityStar = data.getIntValue("serviceQualityStar");
                final int serviceAttitudeStar = data.getIntValue("serviceAttitudeStar");
                final int punctualServiceStar = data.getIntValue("punctualServiceStar");
                final String orderId = data.getString("orderId");
                final String content = data.getString("content");
                final String isDel = data.getString("isDel");
                final String createDate = data.getString("createDate");
                final String merchantId = data.getString("merchantId");
                final String customerId = data.getString("customerId");

                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, type)
                        .setField(CommentFields.POS, pos)
                        .setField(CommentFields.NAME, nickname)
                        .setField(CommentFields.HEAD, avatar)
                        .setField(CommentFields.COMMENT_ID, commentId)
                        .setField(CommentFields.QSTAR, serviceQualityStar)
                        .setField(CommentFields.ASTAR, serviceAttitudeStar)
                        .setField(CommentFields.PSTAR, punctualServiceStar)
                        .setField(CommentFields.NUM, orderId)
                        .setField(CommentFields.TITLE, content)
                        .setField(CommentFields.TIME, createDate)
                        .setField(CommentFields.MID, merchantId)
                        .setField(CommentFields.CID, customerId)
                        .setField(CommentFields.DEL, isDel)
                        .setField(CommentFields.PICS, imageList)
                        .build();

                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
