package com.sshy.yjy.strore.mate.main.personal.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.personal.order.all.VsPagersOrderAllDelegate;
import com.sshy.yjy.strore.mate.detail.TabEntity;
import com.sshy.yjy.strore.mate.main.personal.order.backPay.VsPagersOrderBackPayDelegate;
import com.sshy.yjy.strore.mate.main.personal.order.finished.VsPagersOrderFinishedDelegate;
import com.sshy.yjy.strore.mate.main.personal.order.toComment.VsPagersOrderToCommentDelegate;
import com.sshy.yjy.strore.mate.main.personal.order.toPay.VsPagersOrderToPayDelegate;
import com.sshy.yjy.strore.mate.main.personal.order.toService.VsPagersOrderToServiceDelegate;

import java.util.ArrayList;

import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

public class OrderPersonalListDelegate extends BaseActivity {
    private CommonTabLayout mCommonTabLayout = null;
    private ViewPager mViewPager = null;
    private AppCompatImageView mBack = null;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "全部", "待服务", "待评价", "已完成", "退款/售后", "待付款"};

    private int[] mIconUnselectIds = {
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index, R.drawable.ic_bottom_index,
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index, R.drawable.ic_bottom_index};
    private int[] mIconSelectIds = {
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index, R.drawable.ic_bottom_index,
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_index, R.drawable.ic_bottom_index};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private MyPagerAdapter mAdapter;
    public static final String ORDER_TYPE = "ORDER_TYPE";
    private static final String ARG_ID = "ARG_ID";
    private int mGoodsId = -1;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, OrderPersonalListDelegate.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_person_order_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mCommonTabLayout = $(R.id.tab_layout_order);
        mViewPager = $(R.id.vp_order_layout);
        mBack = $(R.id.id_sw_order_back);

        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        mFragments.add(new VsPagersOrderAllDelegate());
        mFragments.add(new VsPagersOrderToServiceDelegate());
        mFragments.add(new VsPagersOrderToCommentDelegate());
        mFragments.add(new VsPagersOrderFinishedDelegate());
        mFragments.add(new VsPagersOrderBackPayDelegate());
        mFragments.add(new VsPagersOrderToPayDelegate());

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mCommonTabLayout.setTabData(mTabEntities);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
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
                mCommonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.colorPrimary);
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
