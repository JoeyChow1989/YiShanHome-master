package com.sshy.yjy.strore.mate.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sshy.yjy.strore.mate.database.DataBaseManager;
import com.sshy.yjy.strore.mate.database.UserProfile;

import strore.yjy.sshy.com.mate.app.AccountManager;

/**
 * Created by 周正尧 on 2018/3/15 0015.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */

public class SignHandler {

    public static void onSignIn(String response, ISignListener signListener) {

        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final String customerId = profileJson.getString("customerId");
        final String mobile = profileJson.getString("mobile");

        if (customerId != null && mobile != null) {
            final UserProfile profile = new UserProfile(customerId, mobile);
            DataBaseManager.getIntance().getDao().insert(profile);
        }
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();
    }
}
