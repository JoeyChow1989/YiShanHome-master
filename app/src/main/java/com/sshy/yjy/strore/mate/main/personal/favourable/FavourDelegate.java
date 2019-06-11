package com.sshy.yjy.strore.mate.main.personal.favourable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.detail.TabEntity;

import java.util.ArrayList;

import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/9/13
 * create by：周正尧
 */
public class FavourDelegate extends BaseActivity {

    private CommonTabLayout mTabLayout = null;
    private AppCompatImageView mBack = null;

    private String[] mTitles = {"健康养生", "人气美食", "教育培训", "家政服务"};
    private int[] mIconUnselectIds = {
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index,
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index};
    private int[] mIconSelectIds = {
            R.drawable.ic_bottom_index_checked, R.drawable.ic_bottom_index_checked,
            R.drawable.ic_bottom_index_checked, R.drawable.ic_bottom_index_checked};

    private ArrayList<CustomTabEntity> mTabEntities;
    private ArrayList<Fragment> mFragments;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, FavourDelegate.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_favour;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        initViews();
        initTabLayout();
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    private void initViews() {
        mTabLayout = $(R.id.id_favour_tab);
        mBack = $(R.id.id_favour_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        mTabEntities = new ArrayList<>();
        mFragments = new ArrayList<>();

        mFragments.add(new HealthFavourDelegate());
        mFragments.add(new GoodFoodsFavourDelegate());
        mFragments.add(new TrainFavourDelegate());
        mFragments.add(new HousekeepingFavourDelegate());

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
    }

    private void initTabLayout() {
        mTabLayout.setTabData(mTabEntities, this, R.id.fl_favour, mFragments);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragments.clear();
    }
}
