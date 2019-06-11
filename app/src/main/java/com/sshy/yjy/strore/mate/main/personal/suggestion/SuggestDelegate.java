package com.sshy.yjy.strore.mate.main.personal.suggestion;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.strore.mate.main.personal.order.adapter.GridImageAdapter;
import com.sshy.yjy.strore.mate.main.personal.order.pickphoto.FullyGridLayoutManager;
import com.sshy.yjy.ui.loading.ViewLoading;
import com.zzy.mate.picture.PictureSelector;
import com.zzy.mate.picture.config.PictureConfig;
import com.zzy.mate.picture.config.PictureMimeType;
import com.zzy.mate.picture.entity.LocalMedia;
import com.zzy.mate.picture.permissions.RxPermissions;
import com.zzy.mate.picture.tools.PictureFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.activities.BaseActivity;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.net.RestClient;
import strore.yjy.sshy.com.mate.net.rx.RxRestClient;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;

/**
 * create date：2018/9/10
 * create by：周正尧
 */
public class SuggestDelegate extends BaseActivity {

    private RecyclerView mRvSelectPhoto = null;
    private AppCompatButton mCommit = null;

    private List<LocalMedia> selectList = new ArrayList<>();
    private ArrayList<String> picPath = new ArrayList<>();
    //private ArrayList<String> callBackPicPath = new ArrayList<>();
    private GridImageAdapter mAdapter;
    private int themeId;
    private int chooseMode = PictureMimeType.ofAll();

    private AppCompatEditText mContent;
    private AppCompatTextView mPhone;
    private AppCompatImageView mBack = null;

    private String img1 = "";
    private String img2 = "";
    private String img3 = "";

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SuggestDelegate.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_suggest;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mRvSelectPhoto = $(R.id.rv_suggest_select_photo);
        mCommit = $(R.id.bt_suggest_commit);
        mContent = $(R.id.id_suggest_content);
        mPhone = $(R.id.id_suggest_phone);
        mBack = $(R.id.id_suggest_back);

        mBack.setOnClickListener(v -> {
            AppManager.getInstance().finishActivity();
        });

        themeId = R.style.picture_Sina_style;
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        mRvSelectPhoto.setLayoutManager(manager);
        mAdapter = new GridImageAdapter(this, onAddPicClickListener);
        mAdapter.setList(selectList);
        //adapter.setSelectMax(9);
        mRvSelectPhoto.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position, v) -> {
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片 可自定长按保存路径
                        PictureSelector.create(SuggestDelegate.this).themeStyle(themeId).openExternalPreview(position, selectList);
                        break;
                    case 2:
                        // 预览视频
                        PictureSelector.create(SuggestDelegate.this).externalPictureVideo(media.getPath());
                        break;
                    case 3:
                        // 预览音频
                        PictureSelector.create(SuggestDelegate.this).externalPictureAudio(media.getPath());
                        break;
                    default:
                        break;
                }
            }
        });

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(SuggestDelegate.this);
                } else {
                    Toast.makeText(SuggestDelegate.this, getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        mCommit.setOnClickListener(v -> {
            ViewLoading.show(SuggestDelegate.this, "正在上传...");
            if (picPath.size() > 0) {
                RestClient.builder()
                        .url("api/uploadFile")
                        .header(GetDataBaseUserProfile.getCustomId())
                        .file(picPath.get(0))
                        .success(response -> {
                            final int code = JSONObject.parseObject(response).getIntValue("code");
                            if (code == 200) {
                                MateLogger.d("uploadpic", response);
                                img1 = JSON.parseObject(response).getJSONObject("data").getString("src");
                                //callBackPicPath.add(img1);
                                if (picPath.size() > 1) {
                                    RestClient.builder()
                                            .url("api/uploadFile")
                                            .header(GetDataBaseUserProfile.getCustomId())
                                            .file(picPath.get(1))
                                            .success(response1 -> {
                                                final int code1 = JSONObject.parseObject(response1).getIntValue("code");
                                                if (code1 == 200) {
                                                    MateLogger.d("uploadpic", response1);
                                                    img2 = JSON.parseObject(response1).getJSONObject("data").getString("src");
                                                    //callBackPicPath.add(img2);
                                                    if (picPath.size() > 2) {
                                                        RestClient.builder()
                                                                .url("api/uploadFile")
                                                                .header(GetDataBaseUserProfile.getCustomId())
                                                                .file(picPath.get(2))
                                                                .success(response2 -> {
                                                                    final int code2 = JSONObject.parseObject(response2).getIntValue("code");
                                                                    if (code2 == 200) {
                                                                        MateLogger.d("uploadpic", response2);
                                                                        img3 = JSON.parseObject(response2).getJSONObject("data").getString("src");
                                                                        //callBackPicPath.add(img3);
                                                                        sendSuggest();
                                                                    }
                                                                    sendSuggest();
                                                                })
                                                                .build()
                                                                .upload();
                                                    }
                                                    else {
                                                        sendSuggest();
                                                    }
                                                }

                                            })
                                            .build()
                                            .upload();
                                }
                                else {
                                    sendSuggest();
                                }
                            }
                        })
                        .build()
                        .upload();
            }else {
                sendSuggest();
            }
        });
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }


    private void sendSuggest() {
        RxRestClient.builder()
                .url("api/sendSuggest")
                .header(GetDataBaseUserProfile.getCustomId())
                .params("content", mContent.getText().toString())
                .params("link", mPhone.getText().toString())
                .params("img0", img1)
                .params("img1", img2)
                .params("img2", img3)
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
                        MateLogger.d("uploadpic", "xxxx" + s);
                        final int code = JSON.parseObject(s).getIntValue("code");
                        if (code == 200) {
                            ViewLoading.dismiss(SuggestDelegate.this);
                            Intent intent = new Intent(SuggestDelegate.this, SuggestSuccessDelegate.class);
                            startActivity(intent);
                            AppManager.getInstance().finishActivity();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        MateLogger.d("uploadpic", "xxxx" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create(SuggestDelegate.this)
                    .openGallery(chooseMode) //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(themeId)  //主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .minSelectNum(1)  //最小选择数量
                    .maxSelectNum(3)  //最大图片选择数量
                    .imageSpanCount(3) //每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE) //多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .enablePreviewAudio(false) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .previewVideo(false) // 是否可预览视频
                    .enableCrop(true)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                    .isGif(false)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(false)// 是否圆形裁剪
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(false)// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    .minimumCompressSize(100) //小于100kb的图片不压缩
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        MateLogger.d("回调图片", media.getCutPath());
                        picPath.add(media.getCutPath());
                    }
                    mAdapter.setList(selectList);
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 自定义压缩存储地址
     *
     * @return
     */
    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/MateGoHome/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }
}
