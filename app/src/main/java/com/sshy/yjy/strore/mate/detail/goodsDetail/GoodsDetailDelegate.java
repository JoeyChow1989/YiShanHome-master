package com.sshy.yjy.strore.mate.detail.goodsDetail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RatingBar;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.strore.mate.submitorder.SubOrderDelegate;
import com.sshy.yjy.ui.banner.HolderCreator;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

/**
 * Created by 傅令杰
 */

public class GoodsDetailDelegate extends BaseActivity {

    private AppCompatImageView mBack = null;
    private AppCompatImageView mGoodsDetailCollect = null;
    private AppCompatImageView mShare = null;
    private AppCompatTextView mTitle = null;
    private AppCompatTextView mPrice = null;
    private AppCompatTextView mRealPrcie = null;
    private AppCompatTextView mCount = null;
    private AppCompatTextView mUpTitle = null;
    private AppCompatTextView mCommentCount = null;

    private AppCompatTextView mInsnContent = null;
    private LinearLayoutCompat mCommentLayout = null;

    private WebView webView;

    //评论
    private CardView mCommentLy = null;
    private CardView mCommentLyWithPic = null;
    private AppCompatTextView mSeeMoreComment = null;

    private CircleImageView mheader = null;
    private AppCompatTextView mName = null;
    private RatingBar mStar = null;
    private AppCompatTextView mTime = null;
    private AppCompatTextView mContent = null;

    private CircleImageView mheaderPic = null;
    private AppCompatTextView mNamePic = null;
    private RatingBar mStarPic = null;
    private AppCompatTextView mTimePic = null;
    private AppCompatTextView mContentPic = null;
    private AppCompatImageView mPic1 = null;
    private AppCompatImageView mPic2 = null;
    private AppCompatImageView mPic3 = null;

    //跳转数据
    private String name;
    private String content;
    private String price;
    private String realprice;
    private String logo;
    private String mMerchanId;

    private int isCollect = -1;
    private boolean isCollected = false;

    private ConvenientBanner<String> mBanner = null;
    private String mGoodsId = null;

    //底部
    private AppCompatTextView mGoToPay = null;
    private AppCompatTextView mBottomPrice = null;
    private AppCompatTextView mBottomRealPrice = null;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String goodsId) {
        Intent intent = new Intent(activity, GoodsDetailDelegate.class);
        intent.putExtra(AppConfig.ARG_GOODS_ID, goodsId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent args = getIntent();
        if (args != null) {
            mGoodsId = args.getStringExtra(AppConfig.ARG_GOODS_ID);
            initData(mGoodsId);
            MatePreference.addCustomAppProfile("productId", mGoodsId);
        }
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        initView();
        initWebView();
    }

    private void initWebView() {
        //不能横向滚动
        webView.setHorizontalScrollBarEnabled(false);
        //不能纵向滚动
        webView.setVerticalScrollBarEnabled(false);
        //允许截图
        webView.setDrawingCacheEnabled(true);
        //屏蔽长按事件

        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        //初始化WebSettings
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        final String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua + "Mate");
        // 设置可以支持缩放
        settings.setSupportZoom(true);

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        //隐藏缩放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        //扩大比例的缩放
        settings.setUseWideViewPort(true);

        //文件权限
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        //缓存相关
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    private void initView() {
        mBack = $(R.id.id_goods_detail_back);
        mGoodsDetailCollect = $(R.id.id_goods_detail_collect);
        mShare = $(R.id.id_goods_detail_share);
        mBanner = $(R.id.detail_banner);
        webView = $(R.id.webview);
        mTitle = $(R.id.id_goods_detail_name);
        mUpTitle = $(R.id.id_goods_detail_title_name);
        mPrice = $(R.id.id_goods_detail_price);
        mRealPrcie = $(R.id.id_goods_detail_realprice);
        mCount = $(R.id.id_goods_detail_count);
        mInsnContent = $(R.id.id_goods_detail_intro);

        //comment
        mCommentLayout = $(R.id.id_goods_detail_commtent_layout);
        mCommentLy = $(R.id.id_goods_detail_up_comment);
        mCommentLyWithPic = $(R.id.id_goods_detail_up_comment_witpic);
        mCommentCount = $(R.id.id_goods_detail_comment_count);
        mSeeMoreComment = $(R.id.id_goods_detail_seemore_comment);

        //content
        mheader = $(R.id.id_shop_detail_comment_normal_usericon);
        mName = $(R.id.id_shop_detail_comment_normal_name);
        mStar = $(R.id.rating_goods_detail);
        mTime = $(R.id.id_shop_detail_comment_normal_time);
        mContent = $(R.id.id_shop_detail_comment_normal_text);
        //pic
        mheaderPic = $(R.id.id_shop_detail_comment_usericon);
        mNamePic = $(R.id.id_shop_detail_comment_name);
        mStarPic = $(R.id.rating_goods_detail_pic);
        mTimePic = $(R.id.id_shop_detail_comment_time);
        mContentPic = $(R.id.id_shop_detail_comment_text);
        mPic1 = $(R.id.id_shop_detail_comment_pic1);
        mPic2 = $(R.id.id_shop_detail_comment_pic2);
        mPic3 = $(R.id.id_shop_detail_comment_pic3);

        mBottomPrice = $(R.id.id_goods_detail_bottom_price);
        mBottomRealPrice = $(R.id.id_goods_detail_bottom_realprice);

        mGoToPay = $(R.id.tv_bottom_go_pay);
        mGoToPay.setOnClickListener(v -> {
            SubOrderDelegate.startAction(this, logo, name, content, price, realprice,
                    mMerchanId, mGoodsId);
        });

        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        mGoodsDetailCollect.setOnClickListener(v -> {
            if (!isCollected) {
                saveCollect();
            } else {
                delCollect();
            }
        });

        mSeeMoreComment.setOnClickListener(v -> {
            GoodsDetailComment.startAction(GoodsDetailDelegate.this);
        });
    }

    //删除收藏
    private void delCollect() {
        RxRestClient.builder()
                .url("api/delCollect")
                .loader(this)
                .header(GetDataBaseUserProfile.getCustomId())
                .params("productId", mGoodsId)
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
                        isCollected = false;
                        Glide.with(GoodsDetailDelegate.this)
                                .load(R.drawable.ic_shoucang_appcolor)
                                .apply(AppConfig.RECYCLER_OPTIONS)
                                .into(mGoodsDetailCollect);
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

    //保存收藏
    private void saveCollect() {
        RxRestClient.builder()
                .url("api/saveCollect")
                .loader(this)
                .header(GetDataBaseUserProfile.getCustomId())
                .params("productId", mGoodsId)
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
                        MateLogger.d("goods", s);
                        isCollected = true;
                        Glide.with(GoodsDetailDelegate.this)
                                .load(R.drawable.ic_shoucang_appcolor_had)
                                .apply(AppConfig.RECYCLER_OPTIONS)
                                .into(mGoodsDetailCollect);
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

    @SuppressLint("SetTextI18n")
    private void initData(String goodsId) {
        RxRestClient.builder()
                .url("api/getProductDetail")
                .params("productId", goodsId)
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
                    public void onNext(String response) {
                        MateLogger.d("goodsdetail", response);
                        final JSONObject data =
                                JSON.parseObject(response).getJSONObject("data");
                        initBanner(data);

                        final JSONArray array = data.getJSONArray("commentList");
                        if (array.size() > 0) {
                            final JSONObject object = array.getJSONObject(0);
                            final JSONArray imgsArray = object.getJSONArray("imgList");
                            mCommentCount.setText("(" + array.size() + ")");

                            if (imgsArray.size() > 0) {
                                mCommentLyWithPic.setVisibility(View.VISIBLE);
                                mCommentLy.setVisibility(View.GONE);

                                Glide.with(GoodsDetailDelegate.this)
                                        .load(AppConfig.API_HOST + object.getString("avatar"))
                                        .apply(AppConfig.RECYCLER_OPTIONS)
                                        .into(mheaderPic);

                                mNamePic.setText(object.getString("nickname"));
                                mStarPic.setRating(object.getInteger("serviceQualityStar"));
                                mTimePic.setText(object.getString("createDate"));
                                mContentPic.setText(object.getString("content"));

                                if (imgsArray.size() > 0) {
                                    Glide.with(GoodsDetailDelegate.this)
                                            .load(AppConfig.API_HOST + imgsArray.getJSONObject(0).getString("img"))
                                            .apply(AppConfig.RECYCLER_OPTIONS)
                                            .into(mPic1);
                                }

                                if (imgsArray.size() > 1) {

                                    Glide.with(GoodsDetailDelegate.this)
                                            .load(AppConfig.API_HOST + imgsArray.getJSONObject(0).getString("img"))
                                            .apply(AppConfig.RECYCLER_OPTIONS)
                                            .into(mPic1);

                                    Glide.with(GoodsDetailDelegate.this)
                                            .load(AppConfig.API_HOST + imgsArray.getJSONObject(1).getString("img"))
                                            .apply(AppConfig.RECYCLER_OPTIONS)
                                            .into(mPic2);
                                }

                                if (imgsArray.size() > 2) {
                                    Glide.with(GoodsDetailDelegate.this)
                                            .load(AppConfig.API_HOST + imgsArray.getJSONObject(0).getString("img"))
                                            .apply(AppConfig.RECYCLER_OPTIONS)
                                            .into(mPic1);

                                    Glide.with(GoodsDetailDelegate.this)
                                            .load(AppConfig.API_HOST + imgsArray.getJSONObject(1).getString("img"))
                                            .apply(AppConfig.RECYCLER_OPTIONS)
                                            .into(mPic2);

                                    Glide.with(GoodsDetailDelegate.this)
                                            .load(AppConfig.API_HOST + imgsArray.getJSONObject(2).getString("img"))
                                            .apply(AppConfig.RECYCLER_OPTIONS)
                                            .into(mPic3);
                                }

                            } else {
                                mCommentLy.setVisibility(View.GONE);
                                mCommentLyWithPic.setVisibility(View.VISIBLE);
                                Glide.with(GoodsDetailDelegate.this)
                                        .load(AppConfig.API_HOST + object.getString("avatar"))
                                        .apply(AppConfig.RECYCLER_OPTIONS)
                                        .into(mheader);

                                mName.setText(object.getString("nickname"));
                                mStar.setRating(object.getInteger("serviceQualityStar"));
                                mTime.setText(object.getString("createDate"));
                                mContent.setText(object.getString("content"));
                            }
                        } else {
                            mCommentLayout.setVisibility(View.GONE);
                        }

                        webView.loadUrl(AppConfig.API_HOST + "api/product/h5?productId=" + goodsId);

                        isCollect = data.getInteger("isCollect");
                        name = data.getString("productName");
                        content = data.getString("introduction");
                        price = data.getString("price");
                        realprice = data.getString("retailPrice");
                        logo = data.getString("productLogo");
                        mMerchanId = data.getString("merchantId");

                        mTitle.setText(name);
                        mInsnContent.setText(content);
                        mUpTitle.setText(data.getString("productName"));
                        mPrice.setText(price);
                        mRealPrcie.setText(realprice);
                        mCount.setText(data.getString("soldCount"));

                        mBottomRealPrice.setText(Double.parseDouble(realprice) + "元");
                        mBottomPrice.setText(String.valueOf(Double.parseDouble(price)));

                        if (isCollect == 0 || isCollect == -1) {
                            Glide.with(GoodsDetailDelegate.this)
                                    .load(R.drawable.ic_shoucang_appcolor)
                                    .apply(AppConfig.RECYCLER_OPTIONS)
                                    .into(mGoodsDetailCollect);

                            isCollected = false;
                        }

                        if (isCollect == 1) {
                            Glide.with(GoodsDetailDelegate.this)
                                    .load(R.drawable.ic_shoucang_appcolor_had)
                                    .apply(AppConfig.RECYCLER_OPTIONS)
                                    .into(mGoodsDetailCollect);

                            isCollected = true;
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        MateLogger.d("failure", t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        MateLoader.stopLoading();
                    }
                });
    }

    private void initBanner(JSONObject data) {
        final JSONArray array = data.getJSONArray("bannerList");
        final List<String> images = new ArrayList<>();
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            images.add(AppConfig.API_HOST + array.getString(i));
        }

        mBanner.setPages(new HolderCreator(), images)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);
    }
}