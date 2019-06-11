package com.sshy.yjy.strore.mate.main.personal.collect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;


import com.sshy.yjy.strore.R;

import java.util.ArrayList;

import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/9/13
 * create by：周正尧
 */
public class CollectDelegate extends BaseActivity {

    private AppCompatImageView mBack = null;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, CollectDelegate.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    private ViewPager mViewPager = null;
    private TabLayout mTabLayout = null;

    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public Object setLayout() {
        return R.layout.delegate_collect;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mViewPager = $(R.id.id_collect_change);
        mTabLayout = $(R.id.id_collect_tab);
        mBack = $(R.id.id_collect_back);

        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        initViews();
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    private void initViews() {
        mTitles.add("商家");
        mTitles.add("商品");

        mFragments.add(new ShopCollectDelegate());
        mFragments.add(new ProduceCollectDelegate());

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);

    }
}
