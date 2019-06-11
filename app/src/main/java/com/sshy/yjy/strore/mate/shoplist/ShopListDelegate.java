package com.sshy.yjy.strore.mate.shoplist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.strore.mate.main.sort.content.ContentDataConverter;
import com.sshy.yjy.strore.mate.refresh.RefreshHandler;
import com.sshy.yjy.ui.dropmenu.DropDownMenu;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.app.Mate;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_PRODUCT_CAT;

/**
 * create date：2018/7/26
 * create by：周正尧
 */
public class ShopListDelegate extends BaseActivity {

    DropDownMenu mDropDownMenu;
    private String mHeaders[] = {"综合推荐", "智能排序", "距离最近"};
    private String mFirstTab[] = {"综合推荐", "热门推荐", "新店推荐"};
    private String mSecondTab[] = {"智能排序", "好评优先", "销量最多"};
    private String mThirdTab[] = {"距离最近", "1km", "2km"};

    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter mFirstTabAdapter;
    private GirdDropDownAdapter mSecondTabAdapter;
    private GirdDropDownAdapter mThirdTabAdapter;

    private RecyclerView mRecyclerView;
    private AppCompatImageView mBack = null;

    //data
    private ArrayList<MultipleItemEntity> mData;
    private ShopListAdapter mAdapter;


    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String catId) {
        Intent intent = new Intent(activity, ShopListDelegate.class);
        intent.putExtra(ARG_PRODUCT_CAT, catId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shoplist;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        initViews();
        initData();
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.colorPrimary);
    }

    private void initData() {
        RxRestClient.builder()
                .url("api/findMerchantPage")
                .header(GetDataBaseUserProfile.getCustomId())
                .params("lng", MatePreference.getCustomAppProfile("lng"))
                .params("lat", MatePreference.getCustomAppProfile("lat"))
                .params("catId", getIntent().getStringExtra(ARG_PRODUCT_CAT))
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
                        MateLogger.d("findMerchantPage", response);
                        final int code = JSON.parseObject(response).getInteger("code");
                        if (code == 200) {
                            mData = new ContentDataConverter().setJsonData(response).convert();
                            mAdapter = new ShopListAdapter(R.layout.item_multiple_image_text,
                                    mData);
                            mRecyclerView.setAdapter(mAdapter);
                        }
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

    private void initViews() {
        mBack = $(R.id.id_shop_list_back);
        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        mDropDownMenu = $(R.id.dropDownMenu);
        final ListView mFirstTabView = new ListView(this);
        mFirstTabAdapter = new GirdDropDownAdapter(this, Arrays.asList(mFirstTab));
        mFirstTabView.setDividerHeight(0);
        mFirstTabView.setAdapter(mFirstTabAdapter);

        final ListView mSecondTabView = new ListView(this);
        mSecondTabAdapter = new GirdDropDownAdapter(this, Arrays.asList(mSecondTab));
        mSecondTabView.setDividerHeight(0);
        mSecondTabView.setAdapter(mSecondTabAdapter);

        final ListView mThirdTabView = new ListView(this);
        mThirdTabAdapter = new GirdDropDownAdapter(this, Arrays.asList(mThirdTab));
        mThirdTabView.setDividerHeight(0);
        mThirdTabView.setAdapter(mThirdTabAdapter);

        popupViews.add(mFirstTabView);
        popupViews.add(mSecondTabView);
        popupViews.add(mThirdTabView);

        mFirstTabView.setOnItemClickListener((parent, view, position, id) -> {
            mFirstTabAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? mHeaders[0] : mFirstTab[position]);
            mDropDownMenu.closeMenu();
            //刷新列表

            if (position == 1) {
                RefreshData("", "");
            }
            if (position == 2) {
                RefreshData("hot", "");
            }
            if (position == 3) {
                RefreshData("new", "");
            }

        });

        mSecondTabView.setOnItemClickListener((parent, view, position, id) -> {
            mSecondTabAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? mHeaders[1] : mSecondTab[position]);
            mDropDownMenu.closeMenu();

            if (position == 1) {
                RefreshData("", "");
            }
            if (position == 2) {
                RefreshData("score", "");
            }
            if (position == 3) {
                RefreshData("orderCount", "");
            }
        });

        mThirdTabView.setOnItemClickListener((parent, view, position, id) -> {
            mThirdTabAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? mHeaders[2] : mThirdTab[position]);
            mDropDownMenu.closeMenu();

            if (position == 1) {
                RefreshData("", "");
            }
            if (position == 2) {
                RefreshData("distance", "1");
            }
            if (position == 3) {
                RefreshData("distance", "2");
            }

        });

        mRecyclerView = new RecyclerView(this);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnItemTouchListener(ShopListItemClickListener.create(this));
        mDropDownMenu.setDropDownMenu(Arrays.asList(mHeaders), popupViews, mRecyclerView);
    }

    private void RefreshData(String sortBy, String distance) {
        RxRestClient.builder()
                .url("api/findMerchantPage")
                .header(GetDataBaseUserProfile.getCustomId())
                .loader(this)
                .params("lng", MatePreference.getCustomAppProfile("lng"))
                .params("lat", MatePreference.getCustomAppProfile("lat"))
                .params("catId", getIntent().getStringExtra(ARG_PRODUCT_CAT))
                .params("sortBy", sortBy)
                .params("distance", distance)
                .build()
                .post()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String response) {
                        MateLogger.d("shopList", response);
                        mData.clear();
                        final int code = JSON.parseObject(response).getInteger("code");
                        if (code == 200) {
                            mData = new ContentDataConverter().setJsonData(response).convert();
                            mAdapter.setNewData(mData);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        MateLogger.d("shopList", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        MateLoader.stopLoading();
                    }
                });
    }
}