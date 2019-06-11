package com.sshy.yjy.strore.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.sshy.yjy.strore.mate.launcher.LauncherDelegate;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import strore.yjy.sshy.com.mate.util.log.MateLogger;

/**
 * create date：2018/4/25
 * create by：周正尧
 */
public class PushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();

        final Set<String> keys = bundle.keySet();
        JSONObject json = new JSONObject();

        for (String key : keys) {
            final Object val = bundle.get(key);
            json.put(key, val);
        }

        MateLogger.json("PushReceiver", json.toJSONString());

        final String pushAction = intent.getAction();
        if (pushAction.equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
            //处理接收到的信息
            onReceivedMessage(bundle);
        } else if (pushAction.equals(JPushInterface.ACTION_NOTIFICATION_OPENED)) {
            //打开相应的notification
            onOpenNotification(context, bundle);
        }
    }

    private void onReceivedMessage(Bundle bundle) {
        final String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        final String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        final int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        final String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
    }

    private void onOpenNotification(Context context, Bundle bundle) {
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final Bundle openActivityBundle = new Bundle();
        final Intent intent = new Intent(context, LauncherDelegate.class);
        intent.putExtras(openActivityBundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ContextCompat.startActivity(context, intent, null);
    }
}
