package com.sshy.yjy.strore.mate.main.index.address;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.blankj.utilcode.util.ToastUtils;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.BdLocation.MateBdAbsLocListener;
import com.sshy.yjy.strore.mate.BdLocation.service.LocationService;
import com.sshy.yjy.strore.mate.submitorder.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static com.baidu.mapapi.BMapManager.getContext;

/**
 * create date：2018/12/6
 * create by：周正尧
 */
public class FindAddressDelegate extends BaseActivity implements AdapterView.OnItemClickListener,
        MateBdAbsLocListener.LocationInfo {

    private ListView mRvFindAddress = null;
    private AppCompatEditText mEditTextFindAddress = null;
    private AppCompatTextView mFindAddress = null;
    private AppCompatTextView mFindGetAddress = null;
    private AppCompatImageView mBack = null;

    /**
     * 列表适配器
     */
    private SearchPositionAdapter locatorAdapter;

    /**
     * 列表数据
     */
    private List<SuggestionResult.SuggestionInfo> datas;

    /**
     * 建议查询
     */
    private SuggestionSearch mSuggestionSearch;

    private LocationService locationService;

    @Override
    public Object setLayout() {
        return R.layout.delegate_findaddress;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mEditTextFindAddress = $(R.id.et_search_view_findaddr);
        mRvFindAddress = $(R.id.rv_findaddress);
        mFindAddress = $(R.id.id_find_addr_txt);
        mFindGetAddress = $(R.id.id_find_addr_get_txt);
        mBack = $(R.id.id_find_addr_back);

        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        mFindAddress.setText(MatePreference.getCustomAppProfile("addr"));

        mFindGetAddress.setOnClickListener(v -> {
            int checkPermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                return;
            } else {
                locationService.start();//开始定位.start();
            }

            ToastUtils.showShort("开始定位");
        });

        // 建议查询
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(mSuggestionResultListener);

        // 列表初始化
        datas = new ArrayList();
        locatorAdapter = new SearchPositionAdapter(this, datas);
        mRvFindAddress.setAdapter(locatorAdapter);

        // 注册监听
        mRvFindAddress.setOnItemClickListener(this);

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 这里的s表示改变之前的内容，通常start和count组合，可以在s中读取本次改变字段中被改变的内容。而after表示改变后新的内容的数量。
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 这里的s表示改变之后的内容，通常start和count组合，可以在s中读取本次改变字段中新的内容。而before表示被改变的内容的数量。
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 表示最终内容
                String mapInput = mEditTextFindAddress.getText().toString().trim();
                if (mapInput != null) {
                    //搜索关键词
                    mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                            .keyword(mapInput).city("上海")
                    );
                }
            }
        };
        mEditTextFindAddress.addTextChangedListener(tw);
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.colorPrimary);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // -----------location config ------------
        locationService = LocationService.getInstance(getApplicationContext());
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(MateBdAbsLocListener.getInstance());
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        MateBdAbsLocListener.getInstance().setInfo(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationService.unregisterListener(MateBdAbsLocListener.getInstance()); //注销掉监听
        locationService.stop(); //停止定位服务
    }

    /**
     * 获取搜索的内容
     */
    OnGetSuggestionResultListener mSuggestionResultListener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult res) {
            if (res == null || res.getAllSuggestions() == null) {
                Toast.makeText(BMapManager.getContext(), "没找到结果", Toast.LENGTH_LONG).show();
                return;
            }
            //获取在线建议检索结果
            if (datas != null) {
                datas.clear();
                for (SuggestionResult.SuggestionInfo suggestionInfos : res.getAllSuggestions()) {
                    datas.add(suggestionInfos);
                }
                locatorAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        locatorAdapter.setSelectSearchItemIndex(position);
        locatorAdapter.notifyDataSetChanged();
        EventBus.getDefault().post(new MessageEvent(datas.get(position).city, null, null, -1, datas.get(position).pt));

        AppManager.getInstance().finishActivity();
    }

    @Override
    public void getLocationInfo(String city, String addr, String lng, String lat) {
        mFindAddress.setText(addr);
    }
}
