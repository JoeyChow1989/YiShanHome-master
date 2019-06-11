package com.sshy.yjy.strore.mate.detail.goodsDetail.commentWithPic;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.detail.shopDetail.comment.CommentFields;
import com.sshy.yjy.strore.mate.detail.shopDetail.comment.CommentImage;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.util.log.MateLogger;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static strore.yjy.sshy.com.mate.app.AppConfig.RECYCLER_OPTIONS;

/**
 * create date：2019/4/16
 * create by：周正尧
 */
public class GoodsDetailCommentWithPicAdapter extends BaseQuickAdapter<MultipleItemEntity, BaseViewHolder> {


    public GoodsDetailCommentWithPicAdapter(int layoutResId, @Nullable List<MultipleItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, MultipleItemEntity item) {
        final String name;
        final String headpic;
        final String time;
        final String title;
        final ArrayList<CommentImage> pics;

        name = item.getField(CommentFields.NAME);
        headpic = item.getField(CommentFields.HEAD);
        time = item.getField(CommentFields.TIME);
        title = item.getField(CommentFields.TITLE);
        pics = item.getField(CommentFields.PICS);

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
    }
}
