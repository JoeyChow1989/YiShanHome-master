package com.sshy.yjy.strore.mate.main;

import com.sshy.yjy.strore.mate.database.DataBaseManager;
import com.sshy.yjy.strore.mate.database.UserProfile;

import java.util.List;


/**
 * create date：2018/12/14
 * create by：周正尧
 */
public class GetDataBaseUserProfile {

    final static List<UserProfile> mList = DataBaseManager.getIntance().queryUserList();

    public static final String getCustomId() {
        String mCustomId = mList.get(mList.size() - 1).getCustomerId();
        return mCustomId;
    }

    public static final String getCustomMobile() {
        String mCustomMobile = mList.get(mList.size() - 1).getMobile();
        return mCustomMobile;
    }
}