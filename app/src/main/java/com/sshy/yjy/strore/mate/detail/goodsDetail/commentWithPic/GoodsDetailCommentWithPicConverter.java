package com.sshy.yjy.strore.mate.detail.goodsDetail.commentWithPic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sshy.yjy.strore.mate.detail.shopDetail.comment.CommentFields;
import com.sshy.yjy.strore.mate.detail.shopDetail.comment.CommentImage;
import com.sshy.yjy.ui.recycler.DataConverter;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import strore.yjy.sshy.com.mate.util.log.MateLogger;

/**
 * create date：2019/4/16
 * create by：周正尧
 */
public class GoodsDetailCommentWithPicConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONObject dataObject = JSON.parseObject(getJsonData()).getJSONObject("data");
        final JSONArray dataArray = dataObject.getJSONArray("commentList");
        final ArrayList<CommentImage> imageList = new ArrayList<>();
        if (dataArray != null) {
            for (int i = 0; i < dataArray.size(); i++) {
                final JSONObject data = dataArray.getJSONObject(i);
                final JSONArray dataImage = data.getJSONArray("imgList");
                if (dataImage.size() > 0) {

                    MateLogger.d("dataImage","sssssssssssssss");

                    for (int j = 0; j < dataImage.size(); j++) {
                        final JSONObject images = dataImage.getJSONObject(j);
                        final String id = images.getString("id");
                        final String img = images.getString("img");
                        final String commentId = images.getString("commentId");
                        final String isDel = images.getString("isDel");

                        final CommentImage commentImage = new CommentImage.Builder()
                                .setId(id)
                                .setImg(img)
                                .setCommentId(commentId)
                                .setIsDel(isDel)
                                .build();

                        imageList.add(commentImage);
                    }
                }

                final String pos = data.getString("pos");
                final String nickname = data.getString("nickname");
                final String avatar = data.getString("avatar");
                final String commentId = data.getString("commentId");
                final String serviceQualityStar = data.getString("serviceQualityStar");
                final String serviceAttitudeStar = data.getString("serviceAttitudeStar");
                final String punctualServiceStar = data.getString("punctualServiceStar");
                final String orderId = data.getString("orderId");
                final String content = data.getString("content");
                final String isDel = data.getString("isDel");
                final String createDate = data.getString("createDate");
                final String merchantId = data.getString("merchantId");
                final String customerId = data.getString("customerId");

                final MultipleItemEntity entity = MultipleItemEntity.builder()
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
