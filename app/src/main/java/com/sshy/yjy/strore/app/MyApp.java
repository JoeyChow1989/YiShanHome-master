package com.sshy.yjy.strore.app;

import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.bolex.autoEx.AutoEx;
import com.mob.MobSDK;
import com.pgyersdk.Pgyer;
import com.pgyersdk.PgyerActivityManager;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.crash.PgyerCrashObservable;
import com.squareup.leakcanary.LeakCanary;
import com.sshy.yjy.strore.mate.database.DataBaseManager;

import cn.jpush.android.api.JPushInterface;
import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.app.BaseApplication;
import strore.yjy.sshy.com.mate.app.Mate;
import strore.yjy.sshy.com.mate.net.rx.AddCookieInterceptor;
import strore.yjy.sshy.com.mate.util.callback.CallBackManager;
import strore.yjy.sshy.com.mate.util.callback.CallBackType;


/**
 * create date：2019/3/9
 * create by：周正尧
 */
public class MyApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Mate.init(this)
                .withLoaderDelayed(1000)
                .withApiHost(AppConfig.API_HOST)
                .withWeChatAppId(AppConfig.WXAPP_ID)
                .withWeChatAppSecret(AppConfig.WXAPP_SECRET)
                .withJavascriptInterface("mate")
                //添加cookie同步拦截器
                .withWebHost("")
                .withInterceptor(new AddCookieInterceptor())
                .configure();

        //数据库
        DataBaseManager.getIntance().init(this);
        //百度地图
        SDKInitializer.initialize(this);
        //开启极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //极光推送回调
        CallBackManager.getInstance()
                .addCallBack(CallBackType.TAG_OPEN_PUSH, args -> {
                    if (JPushInterface.isPushStopped(Mate.getApplicationContext())) {
                        //开启极光推送
                        JPushInterface.setDebugMode(true);
                        JPushInterface.init(Mate.getApplicationContext());
                    }
                })
                .addCallBack(CallBackType.TAG_STOP_PUSH, args -> {
                    if (!JPushInterface.isPushStopped(Mate.getApplicationContext())) {
                        JPushInterface.stopPush(Mate.getApplicationContext());
                    }
                });

        AutoEx.apply();

        MobSDK.init(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        PgyCrashManager.register();
        PgyerCrashObservable.get().attach((thread, throwable) -> {

        });
        PgyerActivityManager.set(this);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        Pgyer.setAppId("07977b0790ed3ace8ea64caab1ce06b8");
    }
}
