package com.sshy.yjy.strore.mate.detail.goodsDetail.comment;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.detail.shopDetail.comment.CommentFields;
import com.sshy.yjy.strore.mate.detail.shopDetail.comment.CommentImage;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;
import com.sshy.yjy.ui.recycler.adapter.MultipleRecyclerAdapter;
import com.sshy.yjy.ui.recycler.adapter.MultipleViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.util.log.MateLogger;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static strore.yjy.sshy.com.mate.app.AppConfig.RECYCLER_OPTIONS;

/**
 * create date：2019/3/20
 * create by：周正尧
 */
public class GoodsDetailCommentAdapter extends MultipleRecyclerAdapter {

    protected GoodsDetailCommentAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.TEXT, R.layout.item_shop_detail_comment_normal);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_shop_detail_comment_pics);

        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {

        final String name;
        final String headpic;
        final String time;
        final String title;
        final ArrayList<CommentImage> pics;

        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                name = entity.getField(CommentFields.NAME);
                headpic = entity.getField(CommentFields.HEAD);
                time = entity.getField(CommentFields.TIME);
                title = entity.getField(CommentFields.TITLE);

                MateLogger.d("time", time);

                if (time != null) {
                    holder.setText(R.id.id_shop_detail_comment_normal_time, time);
                }

                holder.setText(R.id.id_shop_detail_comment_normal_name, name);
                holder.setText(R.id.id_shop_detail_comment_normal_text, title);

                Glide.with(mContext)
                        .load(AppConfig.API_HOST + headpic)
                        .apply(AppConfig.RECYCLER_OPTIONS)
                        .into((CircleImageView) holder.getView(R.id.id_shop_detail_comment_normal_usericon));

                break;
            case ItemType.TEXT_IMAGE:
                name = entity.getField(CommentFields.NAME);
                headpic = entity.getField(CommentFields.HEAD);
                time = entity.getField(CommentFields.TIME);
                title = entity.getField(CommentFields.TITLE);
                pics = entity.getField(CommentFields.PICS);

                MateLogger.d("goodsdetail_pic", pics.size());

                holder.setText(R.id.id_shop_detail_comment_name, name);
                if (time != null) {
                    holder.setText(R.id.id_shop_detail_comment_time, time);
                }


                holder.setText(R.id.id_shop_detail_comment_text, title);

                Glide.with(mContext)
                        .load(AppConfig.API_HOST + headpic)
                        .apply(AppConfig.RECYCLER_OPTIONS)
                        .into((CircleImageView) holder.getView(R.id.id_shop_detail_comment_usericon));


                if (pics.size() > 0) {
                    Glide.with(mContext)
                            .load(AppConfig.API_HOST + pics.get(0).getImg())
                            .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(30, 0,
                                    RoundedCornersTransformation.CornerType.ALL)))
                            .into((AppCompatImageView) holder.getView(R.id.id_shop_detail_comment_pic1));
                }

                if (pics.size() > 1) {
                    Glide.with(mContext)
                            .load(AppConfig.API_HOST + pics.get(0).getImg())
                            .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(30, 0,
                                    RoundedCornersTransformation.CornerType.ALL)))
                            .into((AppCompatImageView) holder.getView(R.id.id_shop_detail_comment_pic1));

                    Glide.with(mContext)
                            .load(AppConfig.API_HOST + pics.get(1).getImg())
                            .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(30, 0,
                                    RoundedCornersTransformation.CornerType.ALL)))
                            .into((AppCompatImageView) holder.getView(R.id.id_shop_detail_comment_pic2));
                }

                if (pics.size() > 2) {
                    Glide.with(mContext)
                            .load(AppConfig.API_HOST + pics.get(0).getImg())
                            .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(30, 0,
                                    RoundedCornersTransformation.CornerType.ALL)))
                            .into((AppCompatImageView) holder.getView(R.id.id_shop_detail_comment_pic1));

                    Glide.with(mContext)
                            .load(AppConfig.API_HOST + pics.get(1).getImg())
                            .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(30, 0,
                                    RoundedCornersTransformation.CornerType.ALL)))
                            .into((AppCompatImageView) holder.getView(R.id.id_shop_detail_comment_pic2));

                    Glide.with(mContext)
                            .load(AppConfig.API_HOST + pics.get(2).getImg())
                            .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(30, 0,
                                    RoundedCornersTransformation.CornerType.ALL)))
                            .into((AppCompatImageView) holder.getView(R.id.id_shop_detail_comment_pic3));
                }

                break;
            default:
                break;
        }
    }
}
