package com.sshy.yjy.strore.mate.main.personal.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.personal.list.ListAdapter;
import com.sshy.yjy.strore.mate.main.personal.list.ListBean;
import com.sshy.yjy.strore.mate.main.personal.list.ListItemType;
import com.sshy.yjy.strore.mate.main.personal.settings.account.AccountSafeDelegate;
import com.sshy.yjy.strore.mate.main.personal.settings.cooperation.CooperationDelegate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.DataCleanManager;
import strore.yjy.sshy.com.mate.util.PackageUtils;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/4/26
 * create by：周正尧
 */
public class SettingsDelegate extends BaseActivity {

    private RecyclerView mRecyclerView = null;
    private String cashe = null;
    private AppCompatImageView mBack = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x01:
                    MateLogger.d("ssssssssssssssssss");
                    break;
                case 0x02:
                    MateLogger.d("xxxxxxxxxxxxxxxxx");
                    break;
            }
        }
    };

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SettingsDelegate.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mRecyclerView = $(R.id.rv_settings);
        initClearCache();

        mBack = $(R.id.id_setting_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });


//        final ListBean push = new ListBean.Builder()
//                .setItemType(ListItemType.ITEM_SWITCH)
//                .setId(1)
//                .setText("消息推送")
//                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @SuppressWarnings("unchecked")
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            CallBackManager.getInstance()
//                                    .getCallBack(CallBackType.TAG_OPEN_PUSH).executeCallback(null);
//                            Toast.makeText(getContext(), "打开推送", Toast.LENGTH_SHORT).show();
//                        } else {
//                            CallBackManager.getInstance()
//                                    .getCallBack(CallBackType.TAG_STOP_PUSH).executeCallback(null);
//                            Toast.makeText(getContext(), "关闭推送", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .build();

        final ListBean account = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NOVAULE)
                .setId(1)
                .setText("账号和安全")
                .setDelegate(new AccountSafeDelegate())
                .build();

        final ListBean suggest = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NOVAULE)
                .setId(2)
                .setDelegate(new CooperationDelegate())
                .setText("我要合作")
                .build();

        final ListBean customSer = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_PIC)
                .setId(3)
                .setDelegate(new ContectCusServiceDelegate())
                .setPic(R.drawable.ic_phone)
                .setText("联系客服")
                .build();

        final ListBean update = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_VALUE)
                .setId(4)
                .setText("版本更新")
                .setValue(PackageUtils.getVersionName(this))
                .setDelegate(new UpDataDelegate())
                .build();

        final ListBean clearCash = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_VALUE)
                .setId(5)
                .setText("清除缓存")
                .setValue(cashe)
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(account);
        data.add(suggest);
        data.add(customSer);
        data.add(update);
        data.add(clearCash);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(SettingsClickListener.create(this));
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    private void initClearCache() {
        File file = new File(getCacheDir().getPath());
        try {
            cashe = DataCleanManager.getCacheSize(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
