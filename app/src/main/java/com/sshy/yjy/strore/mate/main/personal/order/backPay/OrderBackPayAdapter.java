package com.sshy.yjy.strore.mate.main.personal.order.backPay;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.personal.order.OrderItemFields;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;
import com.sshy.yjy.ui.recycler.adapter.MultipleRecyclerAdapter;
import com.sshy.yjy.ui.recycler.adapter.MultipleViewHolder;

import java.util.List;

import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.delegates.MateDelegate;

import static strore.yjy.sshy.com.mate.app.AppConfig.RECYCLER_OPTIONS;

/**
 * create date：2018/9/12
 * create by：周正尧
 */
public class OrderBackPayAdapter extends MultipleRecyclerAdapter {

    private MateDelegate DELEGATE = null;

    public OrderBackPayAdapter(List<MultipleItemEntity> data, MateDelegate delegate) {
        super(data);
        this.DELEGATE = delegate;
        addItemType(ItemType.ITEM_ORDER_BACK_PAY, R.layout.item_order_back_pay);
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
            case ItemType.ITEM_ORDER_BACK_PAY:

                title = entity.getField(MultipleFields.TITLE);
                thumb = entity.getField(MultipleFields.IMAGE_URL);
                ordernum = entity.getField(OrderItemFields.ORDER_NUM);
                ordertime = entity.getField(OrderItemFields.ORDER_TIME);
                servicetime = entity.getField(OrderItemFields.SERVICE_TIME);
                price = entity.getField(OrderItemFields.PRICE);

                if (ordertime != null) {
                    holder.setText(R.id.tv_item_order_back_pay_ordertime, "下单时间:" + ordertime);
                }
                if (servicetime != null) {
                    holder.setText(R.id.tv_item_order_back_pay_servicetime, "服务时间:" + servicetime);
                }

                holder.setText(R.id.tv_item_order_back_pay_title, title);
                holder.setText(R.id.tv_item_order_back_pay_ordernum, "订单号:" + ordernum);
                holder.setText(R.id.tv_item_order_back_pay_typetxt, "退款/售后");
                holder.setText(R.id.tv_item_order_back_pay_price, String.valueOf(price) + "元");

                Glide.with(mContext)
                        .load(AppConfig.API_HOST + thumb)
                        .apply(AppConfig.RECYCLER_OPTIONS)
                        .into((AppCompatImageView) holder.getView(R.id.tv_item_order_back_pay_pic));
                break;
            default:
                break;
        }
    }
}
