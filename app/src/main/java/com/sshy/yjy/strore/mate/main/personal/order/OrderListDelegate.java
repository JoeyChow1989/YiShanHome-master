package com.sshy.yjy.strore.mate.main.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.detail.TabEntity;
import com.sshy.yjy.strore.mate.main.personal.order.all.VPagersOrderAllDelegate;
import com.sshy.yjy.strore.mate.main.personal.order.backPay.VPagersOrderBackPayDelegate;
import com.sshy.yjy.strore.mate.main.personal.order.finished.VPagersOrderFinishedDelegate;
import com.sshy.yjy.strore.mate.main.personal.order.toComment.VPagersOrderToCommentDelegate;
import com.sshy.yjy.strore.mate.main.personal.order.toPay.VPagersOrderToPayDelegate;
import com.sshy.yjy.strore.mate.main.personal.order.toService.VPagersOrderToServiceDelegate;

import java.util.ArrayList;

import strore.yjy.sshy.com.mate.delegates.MateDelegate;

public class OrderListDelegate extends MateDelegate {
    private CommonTabLayout mCommonTabLayout = null;
    private ViewPager mViewPager = null;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "全部", "已下单", "待评价", "已完成", "退款/售后", "未付款"};

    private int[] mIconUnselectIds = {
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index,
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index,
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index};
    private int[] mIconSelectIds = {
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index,
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index,
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private MyPagerAdapter mAdapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mCommonTabLayout = $(R.id.tab_layout_order);
        mViewPager = $(R.id.vp_order_layout);

        mFragments.add(new VPagersOrderAllDelegate());
        mFragments.add(new VPagersOrderToServiceDelegate());
        mFragments.add(new VPagersOrderToCommentDelegate());
        mFragments.add(new VPagersOrderFinishedDelegate());
        mFragments.add(new VPagersOrderBackPayDelegate());
        mFragments.add(new VPagersOrderToPayDelegate());

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mAdapter = new MyPagerAdapter(_mActivity.getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mCommonTabLayout.setTabData(mTabEntities);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                mViewPager.setCurrentItem(position);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mCommonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageSelected(int position) {
                mCommonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
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
