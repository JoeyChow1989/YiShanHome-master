package com.sshy.yjy.strore.mate.detail.goodsDetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.detail.goodsDetail.comment.GoodsDetailCommentDelegate;
import com.sshy.yjy.strore.mate.detail.goodsDetail.commentWithPic.GoodsDetailCommentWithPicDelegate;

import java.util.ArrayList;

import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2019-04-24
 * create by：周正尧
 */
public class GoodsDetailComment extends BaseActivity {

    private TabLayout mTabLayout = null;
    private ViewPager mViewPager = null;

    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, GoodsDetailComment.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mTabLayout = $(R.id.tab_layout);
        mViewPager = $(R.id.id_goods_detail_vp);

        mTitles.add("评价");
        mTitles.add("带图评价");

        mFragments.add(new GoodsDetailCommentDelegate());
        mFragments.add(new GoodsDetailCommentWithPicDelegate());

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

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }
}
