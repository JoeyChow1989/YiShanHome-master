package com.sshy.yjy.strore.mate.main.personal.order.toPay;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.personal.order.OrderCommentDelegate;
import com.sshy.yjy.strore.mate.main.personal.order.OrderItemFields;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;
import com.sshy.yjy.ui.recycler.adapter.MultipleRecyclerAdapter;
import com.sshy.yjy.ui.recycler.adapter.MultipleViewHolder;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.app.ConfigKeys;
import strore.yjy.sshy.com.mate.app.Mate;
import strore.yjy.sshy.com.mate.delegates.MateDelegate;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.log.MateLogger;

/**
 * create date：2019-05-09
 * create by：周正尧
 */
public class OrderToPayAdapter extends MultipleRecyclerAdapter {

    private FragmentActivity DELEGATE = null;

    protected OrderToPayAdapter(List<MultipleItemEntity> data ,FragmentActivity delegate) {
        super(data);
        this.DELEGATE = delegate;
        addItemType(ItemType.ITEM_ORDER_TO_PAY, R.layout.item_order_to_pay);
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {

        final String title;
        final String thumb;
        final String ordernum;
        final String ordertime;
        final String servicetime;
        final double price;
        final String orderId;

        switch (holder.getItemViewType()) {
            case ItemType.ITEM_ORDER_TO_PAY:

                title = entity.getField(MultipleFields.TITLE);
                thumb = entity.getField(MultipleFields.IMAGE_URL);
                ordernum = entity.getField(OrderItemFields.ORDER_NUM);
                ordertime = entity.getField(OrderItemFields.ORDER_TIME);
                servicetime = entity.getField(OrderItemFields.SERVICE_TIME);
                price = entity.getField(OrderItemFields.PRICE);
                orderId = entity.getField(OrderItemFields.ORDER_ID);

                if (ordertime != null) {
                    holder.setText(R.id.tv_item_order_to_pay_ordertime, "下单时间:" + ordertime);
                }
                if (servicetime != null) {
                    holder.setText(R.id.tv_item_order_to_pay_servicetime, "服务时间:" + servicetime);
                }

                holder.setText(R.id.tv_item_order_to_pay_title, title);
                holder.setText(R.id.tv_item_order_to_pay_ordernum, "订单号:" + ordernum);
                holder.setText(R.id.tv_item_order_to_pay_typetxt, "待付款");
                holder.setText(R.id.tv_item_order_to_pay_price, price + "元");

                Glide.with(mContext)
                        .load(AppConfig.API_HOST + thumb)
                        .apply(AppConfig.RECYCLER_OPTIONS)
                        .into((AppCompatImageView) holder.getView(R.id.tv_item_order_to_pay_pic));

                final AppCompatButton mBtComment = holder.getView(R.id.tv_item_order_to_pay_comment);

                mBtComment.setOnClickListener(v -> {
                    PayMethed(orderId);
                });
                break;
            default:
                break;
        }
    }

    private void PayMethed(String orderId) {
        final String weChatPrePayUrl = "api/wx/prepay";
        final String appId = Mate.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
        final IWXAPI iwxapi = WXAPIFactory.createWXAPI(DELEGATE, appId, false);
        iwxapi.registerApp(appId);
        RxRestClient.builder()
                .url(weChatPrePayUrl)
                .loader(DELEGATE)
                .params("orderId", orderId)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        final JSONObject result = JSON.parseObject(s);
                        final String prepayId = result.getString("prepayid");
                        final String partnerId = result.getString("partnerid");
                        final String packageValue = result.getString("package");
                        final String timestamp = result.getString("timestamp");
                        final String nonceStr = result.getString("noncestr");
                        final String paySign = result.getString("sign");

                        final PayReq payReq = new PayReq();
                        payReq.appId = appId;
                        payReq.prepayId = prepayId;
                        payReq.partnerId = partnerId;
                        payReq.packageValue = packageValue;
                        payReq.timeStamp = timestamp;
                        payReq.nonceStr = nonceStr;
                        payReq.sign = paySign;

                        iwxapi.sendReq(payReq);
                    }

                    @Override
                    public void onError(Throwable e) {
                        MateLogger.d("pay", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        MateLoader.stopLoading();
                    }
                });
    }
}
