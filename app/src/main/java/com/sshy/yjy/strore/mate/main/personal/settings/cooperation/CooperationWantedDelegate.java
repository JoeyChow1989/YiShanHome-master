package com.sshy.yjy.strore.mate.main.personal.settings.cooperation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.detail.TabEntity;

import java.util.ArrayList;

import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/9/13
 * create by：周正尧
 */
public class CooperationWantedDelegate extends BaseActivity {

    private CommonTabLayout mTabLayoutCooperWanted = null;
    private ViewPager mViewPager = null;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "开店流程", "开店要求", "资费查询", "常见问题"};

    private int[] mIconUnselectIds = {
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index,
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index};
    private int[] mIconSelectIds = {
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index,
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private MyPagerAdapter mAdapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_cooperation_wanted;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mTabLayoutCooperWanted = $(R.id.id_cooper_tab);
        mViewPager = $(R.id.vp_coopear_layout);

        mFragments.add(new FlowCooperationDelegate());
//        mFragments.add(SimpleCardFragment.getInstance(mTitles[1]));
//        mFragments.add(SimpleCardFragment.getInstance(mTitles[2]));
//        mFragments.add(SimpleCardFragment.getInstance(mTitles[3]));

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mTabLayoutCooperWanted.setTabData(mTabEntities);
        mTabLayoutCooperWanted.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayoutCooperWanted.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
