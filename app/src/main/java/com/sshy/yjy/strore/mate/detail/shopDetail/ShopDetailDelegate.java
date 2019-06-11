package com.sshy.yjy.strore.mate.detail.shopDetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.detail.TabEntity;
import com.sshy.yjy.strore.mate.detail.shopDetail.comment.ShopDetailCommentDelegate;
import com.sshy.yjy.strore.mate.detail.shopDetail.project.ShopDetailProjectDelegate;
import com.sshy.yjy.strore.mate.detail.shopDetail.shopMsg.ShopDetailShopMsg;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.ui.popwindow.CustomPopWindow;

import java.util.ArrayList;

import cn.sharesdk.onekeyshare.OnekeyShare;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import qiu.niorgai.StatusBarCompat;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.appbar.AppBarStateChangeListener;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_DIS;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_ID;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_MERCHANT_LINK;

/**
 * create date：2018/9/3
 * create by：周正尧
 */
public class ShopDetailDelegate extends BaseActivity {

    private Toolbar mToolBar = null;
    private RelativeLayout mMainWidget = null;
    private CollapsingToolbarLayout mCollapsingToolbarLayout = null;
    private AppBarLayout mAppBarLayout = null;
    private CommonTabLayout mTabLayout = null;
    private CustomPopWindow mCustomPopWindow = null;
    private AppCompatImageView mCollectShopDetail = null;
    private AppCompatImageView mShareShopDetail = null;

    private AppCompatImageView mBack = null;

    private AppCompatTextView mShopDetailTitle = null;
    private AppCompatImageView mShopPhone = null;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"项目", "点评", "商家"};
    private int[] mIconUnselectIds = {
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_community,
            R.drawable.ic_bottom_order, R.drawable.ic_bottom_mine};
    private int[] mIconSelectIds = {
            R.drawable.ic_bottom_index, R.drawable.ic_bottom_community,
            R.drawable.ic_bottom_order, R.drawable.ic_bottom_mine};


    //顶部信息
    private AppCompatTextView name = null;
    private AppCompatImageView pic = null;
    private AppCompatTextView pingfen = null;
    private AppCompatTextView soldCount = null;
    private AppCompatTextView pubn = null;
    private AppCompatTextView address = null;
    private AppCompatTextView distance = null;

    private int isCollect = -1;
    private boolean isCollected = false;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String id) {
        Intent intent = new Intent(activity, ShopDetailDelegate.class);
        intent.putExtra(ARG_MERCHANT_ID, id);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = $(R.id.id_shop_detail_name);
        pic = $(R.id.id_shop_detail_pic);
        pingfen = $(R.id.id_tv_shop_detail_pingfen);
        soldCount = $(R.id.id_tv_shop_detail_yueshou);
        pubn = $(R.id.id_tv_shop_detail_zhundian);
        distance = $(R.id.id_shop_detail_distance);
        address = $(R.id.tv_shop_detail_addrs);

        distance.setText(MatePreference.getCustomAppProfile(ARG_MERCHANT_DIS) + "km");
    }

    @Override
    public void setStatusBar() {
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        initViews();
        initData();
    }

    private void initData() {
        RxRestClient.builder()
                .url("api/getMerchantDetail")
                .params("merchantId", getIntent().getStringExtra(ARG_MERCHANT_ID))
                .header(GetDataBaseUserProfile.getCustomId())
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                        MateLogger.d("index", s);
                        final JSONObject data = JSON.parseObject(s).getJSONObject("data");
                        name.setText(data.getString("merchantName"));
                        pingfen.setText("评分" + data.getString("score"));
                        soldCount.setText("月售" + data.getString("orderCount"));
                        pubn.setText("准确率" + Double.parseDouble(data.getString("punctualityRate")) * 100 + "%");
                        address.setText(data.getString("address"));
                        isCollect = data.getInteger("isCollect");

                        Glide.with(ShopDetailDelegate.this)
                                .load(AppConfig.API_HOST + data.getString("merchantImg"))
                                .apply(AppConfig.RECYCLER_OPTIONS)
                                .into(pic);

                        if (isCollect == 0 || isCollect == -1) {
                            Glide.with(ShopDetailDelegate.this)
                                    .load(R.drawable.ic_shoucang)
                                    .apply(AppConfig.RECYCLER_OPTIONS)
                                    .into(mCollectShopDetail);

                            isCollected = false;
                        }

                        if (isCollect == 1) {
                            Glide.with(ShopDetailDelegate.this)
                                    .load(R.drawable.ic_shoucang_had)
                                    .apply(AppConfig.RECYCLER_OPTIONS)
                                    .into(mCollectShopDetail);

                            isCollected = true;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initViews() {
        mToolBar = $(R.id.id_shop_detail_toolbar);
        mCollapsingToolbarLayout = $(R.id.cl_shop_detail);
        mAppBarLayout = $(R.id.al_shop_detail);
        mShopDetailTitle = $(R.id.id_shop_detail_title);
        mTabLayout = $(R.id.id_shop_detail_tab);
        mShopPhone = $(R.id.iv_shop_detail_phone);
        mMainWidget = $(R.id.id_shop_detail_main_rl);
        mBack = $(R.id.id_shop_detail_back);
        mCollectShopDetail = $(R.id.id_collect_shop_detail);
        mShareShopDetail = $(R.id.id_shop_detail_share);

        setSupportActionBar(mToolBar);
        mToolBar.setTitle("");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));

        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        mCollapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state) {
                    case EXPANDED:
                        mToolBar.setBackgroundColor(Color.TRANSPARENT);
                        mShopDetailTitle.setVisibility(View.GONE);
                        break;
                    case COLLAPSED:
                        mToolBar.setBackgroundColor(getResources().getColor(R.color.app_main));
                        mShopDetailTitle.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });

        //状态栏
        StatusBarCompat.translucentStatusBar(this, true);

        mShopPhone.setOnClickListener(v -> {
            showPopTopWithDarkBg();
        });

        mFragments.add(new ShopDetailProjectDelegate());
        mFragments.add(new ShopDetailCommentDelegate());
        mFragments.add(new ShopDetailShopMsg());

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);

        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mTabLayout.setCurrentTab(0);

        mCollectShopDetail.setOnClickListener(v -> {
            if (!isCollected) {
                saveCollect();
            } else {
                delCollect();
            }
        });

        mShareShopDetail.setOnClickListener(v -> {
            //showShare();
        });
    }

    //分享
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }

    private void saveCollect() {
        RxRestClient.builder()
                .url("api/saveCollect")
                .params("merchantId", getIntent().getStringExtra(ARG_MERCHANT_ID))
                .header(GetDataBaseUserProfile.getCustomId())
                .loader(this)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        MateLogger.d("collect", s);
                        isCollected = true;
                        Glide.with(ShopDetailDelegate.this)
                                .load(R.drawable.ic_shoucang_had)
                                .apply(AppConfig.RECYCLER_OPTIONS)
                                .into(mCollectShopDetail);
                        ToastUtils.showShort("收藏成功");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        MateLoader.stopLoading();
                    }
                });
    }

    private void delCollect() {
        RxRestClient.builder()
                .url("api/delCollect")
                .params("merchantId", getIntent().getStringExtra(ARG_MERCHANT_ID))
                .header(GetDataBaseUserProfile.getCustomId())
                .loader(this)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        MateLogger.d("collect", s);
                        isCollected = false;
                        Glide.with(ShopDetailDelegate.this)
                                .load(R.drawable.ic_shoucang)
                                .apply(AppConfig.RECYCLER_OPTIONS)
                                .into(mCollectShopDetail);
                        ToastUtils.showShort("取消收藏");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        MateLoader.stopLoading();
                    }
                });
    }


    /**
     * 显示PopupWindow 同时背景变暗
     */
    private void showPopTopWithDarkBg() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_pop_phone, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, 300)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setOnDissmissListener(() -> {
                })
                .create();
        mCustomPopWindow.showAsDropDown(mMainWidget, 0, -(mCustomPopWindow.getHeight()));
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {

        AppCompatTextView phoneCall = contentView.findViewById(R.id.tv_shop_detail_phonecall);
        final String phoneNumber = MatePreference.getCustomAppProfile(ARG_MERCHANT_LINK);
        phoneCall.setText(phoneNumber);


        View.OnClickListener listener = v -> {
            if (mCustomPopWindow != null) {
                mCustomPopWindow.dissmiss();
            }
            int id = v.getId();
            if (id == R.id.tv_shop_detail_phonecall) {
                //拨打电话
                Intent myCallIntent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + phoneNumber));
                startActivity(myCallIntent);
            } else if (id == R.id.tv_shop_detail_tel_dismiss) {
                mCustomPopWindow.dissmiss();
            }
        };
        contentView.findViewById(R.id.tv_shop_detail_phonecall).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_shop_detail_tel_dismiss).setOnClickListener(listener);
    }
}
