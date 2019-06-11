package com.sshy.yjy.strore.mate.detail.shopDetail.project;

import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.detail.shopDetail.project.pagers.ShopDetailProPagersAdapter;
import com.sshy.yjy.strore.mate.detail.shopDetail.project.pagers.ShopDetailProjectPagesItemClickListener;
import com.sshy.yjy.strore.mate.detail.shopDetail.project.pagers.TopViewPager;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;
import com.sshy.yjy.ui.recycler.adapter.MultipleRecyclerAdapter;
import com.sshy.yjy.ui.recycler.adapter.MultipleViewHolder;

import java.util.ArrayList;
import java.util.List;

import strore.yjy.sshy.com.mate.app.AppConfig;

import static strore.yjy.sshy.com.mate.app.AppConfig.RECYCLER_OPTIONS;

/**
 * create date：2018/9/6
 * create by：周正尧
 */
public class ShopDetailProjectAdapter extends MultipleRecyclerAdapter {

    private FragmentActivity DELEGATE;

    protected ShopDetailProjectAdapter(List<MultipleItemEntity> data, FragmentActivity activity) {
        super(data);
        addItemType(ItemType.TEXT, R.layout.item_shop_detail_project_text);
        addItemType(ItemType.VIEW_PAGER, R.layout.item_shop_detail_project_rcy);
        addItemType(ItemType.ITEM, R.layout.item_shop_detail_project_normal);

        this.DELEGATE = activity;
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {

        String text;
        String imageUrl;
        String title;
        String price;
        String realprice;

        ArrayList<TopViewPager> pages;

        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                text = entity.getField(MultipleFields.TEXT);
                holder.setText(R.id.tv_shop_detail_project_top_text, text);
                break;
            case ItemType.VIEW_PAGER:
                pages = entity.getField(MultipleFields.PAGERS);
                final RecyclerView recyclerView = holder.getView(R.id.rv_shop_detail_project_recy);
                final LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,
                        false);
                recyclerView.setLayoutManager(manager);
                recyclerView.addOnItemTouchListener(ShopDetailProjectPagesItemClickListener.create(DELEGATE));
                ShopDetailProPagersAdapter adapter =
                        new ShopDetailProPagersAdapter(R.layout.item_shop_detail_project_viewpager, pages);
                recyclerView.setAdapter(adapter);
                break;
            case ItemType.ITEM:
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                text = entity.getField(MultipleFields.TEXT);
                price = entity.getField(MultipleFields.PRICE);
                title = entity.getField(MultipleFields.TITLE);
                realprice = entity.getField(MultipleFields.REALPRICE);

                Glide.with(mContext)
                        .load(AppConfig.API_HOST + imageUrl)
                        .apply(AppConfig.RECYCLER_OPTIONS)
                        .into((AppCompatImageView) holder.getView(R.id.tv_item_shopdetail_project_normal_pic));

                if (price != null && realprice != null) {
                    AppCompatTextView real = holder.getView(R.id.tv_item_shopdetail_project_normal_realprice);
                    real.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    real.setText("¥" + Double.parseDouble(realprice));

                    holder.setText(R.id.tv_item_shopdetail_project_normal_price, String.valueOf(Double.parseDouble(price)));
                }
                holder.setText(R.id.tv_item_shopdetail_project_normal_title, title);
                holder.setText(R.id.tv_item_shopdetail_project_normal_text, text);
                break;
            default:
                break;
        }
    }
}
