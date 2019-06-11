package com.sshy.yjy.strore.mate.submitorder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.app.ConfigKeys;
import strore.yjy.sshy.com.mate.app.Mate;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_ADDRESS_DEFAULT;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_ORDER_NO;

/**
 * create date：2018/8/27
 * create by：周正尧
 */
public class SubOrderDelegate extends BaseActivity implements View.OnClickListener {

    private LinearLayoutCompat mSubOrderAddAddress = null;
    private LinearLayoutCompat mSubOrderPayWay = null;
    private LinearLayoutCompat mSubToPay = null;

    private AppCompatImageView mSubOrderCut = null;
    private AppCompatImageView mSubOrderAdd = null;
    private AppCompatImageView mProductPic = null;
    private AppCompatTextView mSubOrderNum = null;
    private AppCompatTextView mOrderName = null;
    private AppCompatTextView mOrderContent = null;
    private AppCompatTextView mOrderAddress = null;
    private AppCompatImageView mBack = null;
    private int mNum = -1;

    private AppCompatImageView mOrderPayWayPic1 = null;
    private AppCompatImageView mOrderPayWayPic2 = null;
    private AppCompatImageView mOrderAddressPic = null;
    private AppCompatTextView mOrderAddressTxt = null;
    private AppCompatTextView mOrderPayWayText = null;

    private AppCompatTextView mOrderMinPrice;
    private AppCompatTextView mOrderMaxPrice;

    private AppCompatTextView mOrderPrice;
    private AppCompatTextView mOrderRealPrice;

    //订单号
    private String orderNo;

    //地址id
    private String addressId;

    //判断标示
    private boolean isAddress = false;
    //private boolean isPayWay = false;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String pic, String name,
                                   String content, String price, String realprice, String merchantId, String productId) {
        Intent intent = new Intent(activity, SubOrderDelegate.class);
        intent.putExtra("orderPic", pic);
        intent.putExtra("name", name);
        intent.putExtra("content", content);
        intent.putExtra("price", price);
        intent.putExtra("realprice", realprice);
        intent.putExtra("merchantId", merchantId);
        intent.putExtra("productId", productId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sub_order;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mSubOrderAddAddress = $(R.id.ly_suborder_add_addr);
        //mSubOrderPayWay = $(R.id.ly_suborder_add_payway);
        mOrderName = $(R.id.id_order_name);
        mSubToPay = $(R.id.id_suborder_to_pay);
        mOrderContent = $(R.id.id_order_content);
        mProductPic = $(R.id.id_order_product_pic);
        mOrderAddress = $(R.id.id_suborder_address);

        mSubOrderCut = $(R.id.iv_suborder_cut);
        mSubOrderAdd = $(R.id.iv_suborder_add);
        mSubOrderNum = $(R.id.tv_suborder_num);
        mBack = $(R.id.id_left_back);

        mOrderPayWayPic1 = $(R.id.id_suborder_payway_pic1);
        mOrderPayWayPic2 = $(R.id.id_suborder_payway_pic2);
        mOrderAddressPic = $(R.id.id_suborder_add_pic);
        mOrderAddressTxt = $(R.id.id_suborder_add_txt);
        mOrderPayWayText = $(R.id.id_suborder_payway_txt);

        mOrderMinPrice = $(R.id.id_order_min_price);
        mOrderMaxPrice = $(R.id.id_order_max_price);

        mOrderPrice = $(R.id.id_order_price);
        mOrderRealPrice = $(R.id.id_order_realprice);

        Glide.with(this)
                .load(AppConfig.API_HOST + getIntent().getStringExtra("orderPic"))
                .apply(AppConfig.RECYCLER_OPTIONS)
                .into(mProductPic);

        mOrderName.setText(getIntent().getStringExtra("name"));
        mOrderContent.setText(getIntent().getStringExtra("content"));

        mOrderPrice.setText(String.valueOf(Double.parseDouble(getIntent().getStringExtra("price"))));
        mOrderMinPrice.setText(Double.parseDouble(getIntent().getStringExtra("price")) + "元");
        mOrderRealPrice.setText("门市价:" + Double.parseDouble(getIntent().getStringExtra("realprice")) + "元");
        mOrderMaxPrice.setText(String.valueOf(Double.parseDouble(getIntent().getStringExtra("price"))));

        mOrderPayWayPic1.setVisibility(View.GONE);
        mOrderPayWayPic2.setVisibility(View.VISIBLE);
        mOrderPayWayText.setText("微信");


        mSubOrderAddAddress.setOnClickListener(this);
        //mSubOrderPayWay.setOnClickListener(this);
        mSubToPay.setOnClickListener(this);
        mSubOrderCut.setOnClickListener(this);
        mSubOrderAdd.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(MessageEvent event) {

        MateLogger.d("paycccc", event.addressId + "-----" + event.payWay);


//        if (event.payWay == 1 && addressId == null) {
//            mOrderPayWayText.setText("微信");
//            isPayWay = true;
//            mOrderPayWayPic1.setVisibility(View.GONE);
//            mOrderPayWayPic2.setVisibility(View.VISIBLE);
//        } else if (event.payWay == -1 && addressId == null) {
//            isPayWay = false;
//            mOrderPayWayText.setText("请选择支付方式");
//            mOrderPayWayPic1.setVisibility(View.VISIBLE);
//            mOrderPayWayPic2.setVisibility(View.GONE);
//        }
//
        if (event.addressId != null && event.payWay == -1) {
            mOrderAddress.setText(event.addrs);
            mOrderAddressPic.setVisibility(View.GONE);
            mOrderAddressTxt.setVisibility(View.VISIBLE);
            addressId = event.addressId;
            isAddress = true;
        } else {
            mOrderAddressPic.setVisibility(View.GONE);
            mOrderAddressTxt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        Intent intent = null;
        int id = v.getId();
        if (id == R.id.ly_suborder_add_addr) {
            intent = new Intent(SubOrderDelegate.this, AddressChoiceDelegate.class);
            startActivity(intent);
//        } else if (id == R.id.ly_suborder_add_payway) {
//            intent = new Intent(SubOrderDelegate.this, PayWayChoiceDelegate.class);
//            startActivity(intent);
        } else if (id == R.id.iv_suborder_cut) {
            if (Integer.parseInt(mSubOrderNum.getText().toString()) > 1) {
                mNum = Integer.parseInt(mSubOrderNum.getText().toString());
                mSubOrderNum.setText(String.valueOf(mNum - 1));
                mOrderMinPrice.setText(Double.parseDouble(getIntent().getStringExtra("price")) * (mNum - 1) + "元");
                mOrderMaxPrice.setText(String.valueOf(Double.parseDouble(getIntent().getStringExtra("price")) * (mNum - 1)));
            }
        } else if (id == R.id.iv_suborder_add) {
            mNum = Integer.parseInt(mSubOrderNum.getText().toString());
            mSubOrderNum.setText(String.valueOf(mNum + 1));
            mOrderMinPrice.setText(Double.parseDouble(getIntent().getStringExtra("price")) * (mNum + 1) + "元");
            mOrderMaxPrice.setText(String.valueOf(Double.parseDouble(getIntent().getStringExtra("price")) * (mNum + 1)));
        } else if (id == R.id.id_left_back) {
            AppManager.getInstance().finishActivity();
        } else if (id == R.id.id_suborder_to_pay) {
            int subOrderNum = Integer.parseInt(mSubOrderNum.getText().toString());
            if (checkForm()) {
                GoToPayRequest(getIntent().getStringExtra("merchantId"),
                        getCurrentTime(), getOrderDetail(subOrderNum), addressId);
            }
        }
    }

    private String getCurrentTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    private JSONArray getOrderDetail(int subOrderNum) {
        JSONArray imgArray = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("productId", getIntent().getStringExtra("productId"));
        object.put("productCount", subOrderNum);
        imgArray.add(object);
        return imgArray;
    }


    private boolean checkForm() {
        boolean isPass = true;
        if (!isAddress) {
            Toast.makeText(SubOrderDelegate.this, "请选择收货地址", Toast.LENGTH_SHORT).show();
            isPass = false;
        }

//        if (!isPayWay) {
//            Toast.makeText(SubOrderDelegate.this, "请选择支付方式", Toast.LENGTH_SHORT).show();
//            isPass = false;
//        }
        return isPass;
    }


    //发起订单并支付
    private void GoToPayRequest(String merchantId, String time, JSONArray array, String addressId) {

        MateLogger.d("paycccc", merchantId + "----" + array.toString() + "----" +
                GetDataBaseUserProfile.getCustomMobile() + "----" + time + "----" +
                addressId);

        RxRestClient.builder()
                .url("api/saveOrder")
                .loader(this)
                .header(GetDataBaseUserProfile.getCustomId())
                .params("mobile", GetDataBaseUserProfile.getCustomMobile())
                .params("merchantId", merchantId)
                .params("productList", array)
                .params("serviceDate", time)
                .params("addressId", addressId)
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
                        MateLogger.d("saveOrder", s);
                        final JSONObject data = JSON.parseObject(s).getJSONObject("data");
                        final String code = JSON.parseObject(s).getString("code");
                        if ("200".equals(code)) {
                            final String orderId = data.getString("orderId");
                            PayMethed(orderId);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        MateLogger.d("paycccc", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void PayMethed(String orderId) {
        final String weChatPrePayUrl = "api/wx/prepay";
        final String appId = Mate.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
        final IWXAPI iwxapi = WXAPIFactory.createWXAPI(this, appId, false);
        iwxapi.registerApp(appId);
        RxRestClient.builder()
                .url(weChatPrePayUrl)
                .loader(this)
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
                        orderNo = result.getString("orderNo");

                        final PayReq payReq = new PayReq();
                        payReq.appId = appId;
                        payReq.prepayId = prepayId;
                        payReq.partnerId = partnerId;
                        payReq.packageValue = packageValue;
                        payReq.timeStamp = timestamp;
                        payReq.nonceStr = nonceStr;
                        payReq.sign = paySign;

                        MatePreference.addCustomAppProfile(ARG_ORDER_NO, orderNo);
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
