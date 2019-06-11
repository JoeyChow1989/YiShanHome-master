package strore.yjy.sshy.com.mate.pay;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import strore.yjy.sshy.com.mate.R;
import strore.yjy.sshy.com.mate.app.ConfigKeys;
import strore.yjy.sshy.com.mate.app.Mate;
import strore.yjy.sshy.com.mate.net.RestClient;
import strore.yjy.sshy.com.mate.net.callback.ISuccess;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.log.MateLogger;

public class FastPay implements View.OnClickListener {

    //设置支付回调监听

    private IAlPayResultListener mIAlPayResultListener = null;
    private Activity mActivity = null;


    private AlertDialog mDialog = null;
    private int mOrderId = -1;

    private FastPay(Activity delegate) {
        this.mActivity = delegate;
        this.mDialog = new AlertDialog.Builder(delegate).create();
    }

    public static FastPay create(Activity delegate) {
        return new FastPay(delegate);
    }

    public void beginPayDialog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.itv_dialog_pay_alipay).setOnClickListener(this);
            window.findViewById(R.id.itv_dialog_pay_wechat).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);

        }

    }

    public FastPay setPayResultListener(IAlPayResultListener listener) {
        this.mIAlPayResultListener = listener;
        return this;
    }

    public FastPay setOrderId(int orderId) {
        this.mOrderId = orderId;
        return this;
    }


    private final void aliPay(int orderId) {
        final String singUrl = "";
        //获取签名字符串
        RestClient.builder()
                .url(singUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String paySign = JSON.parseObject(response).getString("result");
                        MateLogger.d("PAY_SIGN", paySign);
                        //必须是异步的调用客户端支付接口
                        final PayAsyncTask payAsyncTask = new PayAsyncTask(mActivity, mIAlPayResultListener);
                        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paySign);
                    }
                })
                .build()
                .post();

    }

    private final void weChatPay(int orderId) {
        MateLoader.stopLoading();
        final String weChatPrePayUrl = "api/wx/prepay";
        MateLogger.d("WX_PAY", weChatPrePayUrl);

//        final IWXAPI iwxapi = MateWeChat.getInstance().getWXAPI();
//        final String appId = Mate.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
//        iwxapi.registerApp(appId);
//        RestClient.builder()
//                .url(weChatPrePayUrl)
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String response) {
//                        final JSONObject result = JSON.parseObject(response).getJSONObject("result");
//                        final String prepayId = result.getString("prepayid");
//                        final String partnerId = result.getString("partnerid");
//                        final String packageValue = result.getString("package");
//                        final String timestamp = result.getString("timestamp");
//                        final String nonceStr = result.getString("noncestr");
//                        final String paySign = result.getString("sign");
//
//                        final PayReq payReq = new PayReq();
//                        payReq.appId = appId;
//                        payReq.prepayId = prepayId;
//                        payReq.partnerId = partnerId;
//                        payReq.packageValue = packageValue;
//                        payReq.timeStamp = timestamp;
//                        payReq.nonceStr = nonceStr;
//                        payReq.sign = paySign;
//
//
//                        iwxapi.sendReq(payReq);
//                    }
//                })
//                .build()
//                .post();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.itv_dialog_pay_alipay) {
            aliPay(mOrderId);
            mDialog.cancel();
        } else if (id == R.id.itv_dialog_pay_wechat) {
            weChatPay(mOrderId);
            mDialog.cancel();
        } else if (id == R.id.btn_dialog_pay_cancel) {
            mDialog.cancel();

        }
    }
}
