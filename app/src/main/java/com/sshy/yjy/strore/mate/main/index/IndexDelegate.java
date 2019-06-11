package com.sshy.yjy.strore.mate.main.index;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.BdLocation.MateBdAbsLocListener;
import com.sshy.yjy.strore.mate.BdLocation.service.LocationService;
import com.sshy.yjy.strore.mate.main.index.address.FindAddressDelegate;
import com.sshy.yjy.strore.mate.main.index.search.SearchDelegate;
import com.sshy.yjy.strore.mate.refresh.RefreshHandler;
import com.sshy.yjy.strore.mate.submitorder.MessageEvent;
import com.sshy.yjy.ui.recycler.BaseDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import strore.yjy.sshy.com.mate.app.Mate;
import strore.yjy.sshy.com.mate.delegates.BaseDelegate;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static strore.yjy.sshy.com.mate.app.Mate.getApplicationContext;

/**
 * Created by 周正尧 on 2018/3/20 0020.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */

public class IndexDelegate extends BaseDelegate implements View.OnClickListener, MateBdAbsLocListener.LocationInfo {

    private RecyclerView mRecyclerView;
    private LinearLayoutCompat mSearchView = null;
    private LinearLayoutCompat mAddressClick = null;
    private AppCompatTextView mTvLoc = null;
    private SwipeRefreshLayout mRefreshLayout = null;
    private RefreshHandler mRefreshHandler = null;

    //定位
    private LocationService locationService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView = $(R.id.rv_index);
        mRefreshLayout = $(R.id.srl_index);
        mSearchView = $(R.id.et_search_view);
        mTvLoc = $(R.id.tv_loc);
        mAddressClick = $(R.id.ly_address_click);

        mSearchView.setOnClickListener(this);
        mAddressClick.setOnClickListener(this);
        initRecyclerView();
        initRefreshLayout();

        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new IndexDataConverter(),
                _mActivity);

        //开始定位
        startLocationService();

        //首页数据
        mRefreshLayout.setRefreshing(true);
        Mate.getHandler().postDelayed(() -> {
            mRefreshHandler.firstPage("api/getHomeData", MatePreference.getCustomAppProfile("lng"),
                    MatePreference.getCustomAppProfile("lat"));
        }, 2000);
    }

    private void startLocationService() {
        // -----------location config ------------
        locationService = LocationService.getInstance(getApplicationContext());
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(MateBdAbsLocListener.getInstance());
        //注册监听
        int type = _mActivity.getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }

        int checkPermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(_mActivity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        } else {
            locationService.start();//开始定位.start();
        }

        MateBdAbsLocListener.getInstance().setInfo(this);
    }

    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(_mActivity));
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, -20, 100);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.et_search_view) {
            int location[] = new int[2];
            mSearchView.getLocationOnScreen(location);
            SearchDelegate.startAction(_mActivity);
        } else if (id == R.id.ly_address_click) {
            Intent intent = new Intent(_mActivity, FindAddressDelegate.class);
            _mActivity.startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(MessageEvent event) {
        if (event.latLng != null && event.addrs != null) {

            MateLogger.d("dingwei",event.addrs);

            mTvLoc.setText(event.addrs.replace("市", ""));
            MatePreference.addCustomAppProfile("lng", String.valueOf(event.latLng.longitude));
            MatePreference.addCustomAppProfile("lat", String.valueOf(event.latLng.latitude));
            mRefreshHandler.onRefresh();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        locationService.unregisterListener(MateBdAbsLocListener.getInstance()); //注销掉监听
        locationService.stop(); //停止定位服务
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getLocationInfo(String city, String addr, String lng, String lat) {
        MatePreference.addCustomAppProfile("city", city);
        MatePreference.addCustomAppProfile("addr", addr);
        MatePreference.addCustomAppProfile("lng", lng);
        MatePreference.addCustomAppProfile("lat", lat);

        mTvLoc.setText(city.replace("市", ""));
    }
}