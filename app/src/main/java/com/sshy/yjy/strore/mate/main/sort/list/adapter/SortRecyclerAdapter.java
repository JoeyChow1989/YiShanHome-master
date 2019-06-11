package com.sshy.yjy.strore.mate.main.sort.list.adapter;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.sort.content.ContentDelegate;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;
import com.sshy.yjy.ui.recycler.adapter.MultipleRecyclerAdapter;
import com.sshy.yjy.ui.recycler.adapter.MultipleViewHolder;

import java.util.List;

import strore.yjy.sshy.com.mate.util.log.MateLogger;

/**
 * Created by 周正尧 on 2018/3/28 0028.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */
public class SortRecyclerAdapter extends MultipleRecyclerAdapter {

    private final FragmentActivity DELEGATE;
    private int mPrePosition = 0;

    //设置图片加载策略
    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    public SortRecyclerAdapter(List<MultipleItemEntity> data, FragmentActivity delegate) {
        super(data);
        this.DELEGATE = delegate;
        //添加垂直菜单布局
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        switch (holder.getItemViewType()) {
            case ItemType.VERTICAL_MENU_LIST:
                final AppCompatTextView name = holder.getView(R.id.tv_vertical_item_name);
                final AppCompatImageView pic = holder.getView(R.id.tv_vertical_item_pic);
                final View line = holder.getView(R.id.view_line);
                final View itemView = holder.itemView;
                final String text = entity.getField(MultipleFields.TEXT);
                final boolean isClicked = entity.getField(MultipleFields.TAG);

                itemView.setOnClickListener(v ->  {
                        MateLogger.d("currentPosition",mPrePosition);

                        final int currentPosition = holder.getAdapterPosition();
                        if (mPrePosition != currentPosition) {
                            //还原上一个position
                            getData().get(mPrePosition).setField(MultipleFields.TAG, false);
                            notifyItemChanged(mPrePosition);

                            //更新选中的item
                            entity.setField(MultipleFields.TAG, true);
                            notifyItemChanged(currentPosition);
                            mPrePosition = currentPosition;
                            final String contentId = getData().get(mPrePosition).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                });

                if (holder.getAdapterPosition() == 1) {
                    pic.setVisibility(View.VISIBLE);
                    pic.setImageResource(R.drawable.ic_hot_goods);

                } else if (holder.getAdapterPosition() == 2) {
                    pic.setVisibility(View.VISIBLE);
                    pic.setImageResource(R.drawable.ic_chip_goods);
                }

                if (!isClicked) {
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                } else {
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_vertical_item_name, text);

                break;
            default:
                break;
        }
    }

    private void showContent(String contentId) {
        final ContentDelegate delegate = ContentDelegate.newInstance(contentId);
        switchContent(delegate);
    }

    private void switchContent(ContentDelegate delegate) {
        FragmentTransaction fragmentTransaction = DELEGATE.getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.sort_content_container, delegate);
        fragmentTransaction.commit();
    }
}
