package com.sshy.yjy.strore.mate.main.personal.order.toComment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.personal.order.OrderCommentDelegate;
import com.sshy.yjy.strore.mate.main.personal.order.OrderItemFields;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;
import com.sshy.yjy.ui.recycler.adapter.MultipleRecyclerAdapter;
import com.sshy.yjy.ui.recycler.adapter.MultipleViewHolder;

import java.util.List;

import strore.yjy.sshy.com.mate.app.AppConfig;

import static strore.yjy.sshy.com.mate.app.AppConfig.RECYCLER_OPTIONS;

/**
 * create date：2018/9/12
 * create by：周正尧
 */
public class OrderToCommentAdapter extends MultipleRecyclerAdapter {

    private FragmentActivity DELEGATE = null;

    public OrderToCommentAdapter(List<MultipleItemEntity> data, FragmentActivity delegate) {
        super(data);
        this.DELEGATE = delegate;
        addItemType(ItemType.ITEM_ORDER_TO_COMMENT, R.layout.item_order_to_comment);
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, MultipleItemEntity entity) {

        final String title;
        final String thumb;
        final String ordernum;
        final String ordertime;
        final String servicetime;
        final double price;

        switch (holder.getItemViewType()) {
            case ItemType.ITEM_ORDER_TO_COMMENT:

                title = entity.getField(MultipleFields.TITLE);
                thumb = entity.getField(MultipleFields.IMAGE_URL);
                ordernum = entity.getField(OrderItemFields.ORDER_NUM);
                ordertime = entity.getField(OrderItemFields.ORDER_TIME);
                servicetime = entity.getField(OrderItemFields.SERVICE_TIME);
                price = entity.getField(OrderItemFields.PRICE);

                if (ordertime != null) {
                    holder.setText(R.id.tv_item_order_to_comment_ordertime, "下单时间:" + ordertime);
                }
                if (servicetime != null) {
                    holder.setText(R.id.tv_item_order_to_comment_servicetime, "服务时间:" + servicetime);
                }

                holder.setText(R.id.tv_item_order_to_comment_title, title);
                holder.setText(R.id.tv_item_order_to_comment_ordernum, "订单号:" + ordernum);
                holder.setText(R.id.tv_item_order_to_comment_typetxt, "待评价");
                holder.setText(R.id.tv_item_order_to_comment_price, String.valueOf(price) + "元");

                Glide.with(mContext)
                        .load(AppConfig.API_HOST + thumb)
                        .apply(AppConfig.RECYCLER_OPTIONS)
                        .into((AppCompatImageView) holder.getView(R.id.tv_item_order_to_comment_pic));

                final AppCompatButton mBtComment = holder.getView(R.id.tv_item_order_to_comment_comment);

                mBtComment.setOnClickListener(v -> {
                    OrderCommentDelegate.startAction(DELEGATE, entity.getField(OrderItemFields.ORDER_ID));
                });
                break;
            default:
                break;
        }
    }
}
