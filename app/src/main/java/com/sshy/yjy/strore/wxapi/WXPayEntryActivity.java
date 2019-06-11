// 微信支付入口文件
package com.sshy.yjy.strore.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sshy.yjy.strore.mate.pay.PaySuccessDelegate;
import com.sshy.yjy.strore.mate.submitorder.SubOrderDelegate;
import com.sshy.yjy.ui.loading.ViewLoading;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.app.ConfigKeys;
import strore.yjy.sshy.com.mate.app.Mate;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_ORDER_NO;

public final class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Mate.getConfiguration(ConfigKeys.WE_CHAT_APP_ID));
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        MateLogger.d("pay", "onPayFinish, errCode = " + resp.errCode + "," + resp.openId);
        ViewLoading.show(this,"正在处理",false);
        Mate.getHandler().postDelayed(() -> {
            if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                if (resp.errCode == 0) {
                    ViewLoading.dismiss(this);
                    Toast.makeText(this, "支付成功!", Toast.LENGTH_SHORT).show();
                    PaySuccessDelegate.startAction(this, MatePreference.getCustomAppProfile(ARG_ORDER_NO));
                    AppManager.getInstance().finishActivity(this);
                }

                if (resp.errCode == -1) {
                    ViewLoading.dismiss(this);
                    Toast.makeText(this, "支付失败!", Toast.LENGTH_SHORT).show();
                    AppManager.getInstance().finishActivity(SubOrderDelegate.class);
                    AppManager.getInstance().finishActivity(this);
                }

                if (resp.errCode == -2) {
                    ViewLoading.dismiss(this);
                    Toast.makeText(this, "支付取消!", Toast.LENGTH_SHORT).show();
                    AppManager.getInstance().finishActivity(SubOrderDelegate.class);
                    AppManager.getInstance().finishActivity(this);
                }
            }
        },2000);
    }
}
