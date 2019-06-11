package com.sshy.yjy.strore.mate.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.sort.content.adapter.ContentAdapter;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.delegates.MateDelegate;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

/**
 * Created by 周正尧 on 2018/3/28 0028.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */
public class ContentDelegate extends MateDelegate {

    private static final String ARG_CONTENT_ID = "content_id";
    private String mContentId;
    private ArrayList<MultipleItemEntity> mData = null;
    private RecyclerView mRecyclerView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = args.getString(ARG_CONTENT_ID);
        }
    }

    public static ContentDelegate newInstance(String contentId) {
        final Bundle args = new Bundle();
        args.putString(ARG_CONTENT_ID, contentId);
        final ContentDelegate contentDelegate = new ContentDelegate();
        contentDelegate.setArguments(args);
        return contentDelegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    private void init() {
        RxRestClient.builder()
                .url("api/findMerchantPage")
                .header(GetDataBaseUserProfile.getCustomId())
                .params("lng", MatePreference.getCustomAppProfile("lng"))
                .params("lat", MatePreference.getCustomAppProfile("lat"))
                .params("catId", mContentId)
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
                        MateLogger.d("content", response);
                        final int code = JSON.parseObject(response).getInteger("code");
                        if (code == 200) {
                            mData = new ContentDataConverter().setJsonData(response).convert();
//                            final SectionAdapter sectionAdapter =
//                                    new SectionAdapter(R.layout.item_section_content, R.layout.item_section_header,
//                                            mData);
                            final ContentAdapter adapter = new ContentAdapter(mData);
                            mRecyclerView.setAdapter(adapter);
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

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView = $(R.id.rv_list_content);
        final LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnItemTouchListener(SortContentItemClickListener.create(getActivity()));
        init();
    }
}
