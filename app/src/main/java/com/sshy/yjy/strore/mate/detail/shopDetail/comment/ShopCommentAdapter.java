package com.sshy.yjy.strore.mate.detail.shopDetail.comment;

import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.ui.flowlayout.FlowLayout;
import com.sshy.yjy.ui.flowlayout.TagAdapter;
import com.sshy.yjy.ui.flowlayout.TagFlowLayout;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;
import com.sshy.yjy.ui.recycler.adapter.MultipleRecyclerAdapter;
import com.sshy.yjy.ui.recycler.adapter.MultipleViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import strore.yjy.sshy.com.mate.app.AppConfig;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static strore.yjy.sshy.com.mate.app.AppConfig.RECYCLER_OPTIONS;

/**
 * create date：2018/9/7
 * create by：周正尧
 */
public class ShopCommentAdapter extends MultipleRecyclerAdapter {

    public ShopCommentAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.TEXT, R.layout.item_shop_detail_comment_normal);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_shop_detail_comment_pics);
        addItemType(ItemType.TEB, R.layout.item_shop_detail_comment_tabs);

        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {

        final int quality;
        final String name;
        final String headpic;
        final String time;
        final String title;
        final ArrayList<CommentImage> pics;
        final ArrayList<TabComment> tabComments;
        final ArrayList<String> tabs;

        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                name = entity.getField(CommentFields.NAME);
                headpic = entity.getField(CommentFields.HEAD);
                time = entity.getField(CommentFields.TIME);
                title = entity.getField(CommentFields.TITLE);

                holder.setText(R.id.id_shop_detail_comment_normal_name, name);
                if (time != null) {
                    holder.setText(R.id.id_shop_detail_comment_normal_time, time);
                }
                holder.setText(R.id.id_shop_detail_comment_normal_text, title);

                RatingBar bar = holder.getView(R.id.rating_goods_detail);
                quality = entity.getField(CommentFields.QSTAR);
                bar.setRating(quality);

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

                holder.setText(R.id.id_shop_detail_comment_name, name);
                holder.setText(R.id.id_shop_detail_comment_time, time);
                holder.setText(R.id.id_shop_detail_comment_text, title);

                //MateLogger.d("type_sss", pics.get(0).getImg());

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
//            case ItemType.STATS:
//                num = entity.getField(CommentFields.NUM);
//                quality = entity.getField(CommentFields.QSTAR);
//                ontime = entity.getField(CommentFields.ONTIME);
//
//                holder.setText(R.id.tv_shop_detail_comment_star_num, num);
//                holder.setText(R.id.tv_shop_detail_comment_star_quality, quality);
//                holder.setText(R.id.tv_shop_detail_comment_star_ontime, ontime);
//
//                break;
            case ItemType.TEB:
                tabComments = entity.getField(CommentFields.TAB);
                tabs = new ArrayList<>();
                for (int i = 0; i < tabComments.size(); i++) {
                    String tab = tabComments.get(i).getTag();
                    tabs.add(tab);
                }

                final TagFlowLayout mTag = holder.getView(R.id.id_shop_detail_comment_tagflowlayout);
                final LayoutInflater mInflater = LayoutInflater.from(mContext);
                mTag.setAdapter(new TagAdapter<String>(tabs) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        TextView tv = (TextView) mInflater.inflate(R.layout.item_shopdetail_comment_tag_tv,
                                mTag, false);
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(12);
                        tv.setText(s);
                        return tv;
                    }
                });
                break;
            default:
                break;
        }
    }
}
