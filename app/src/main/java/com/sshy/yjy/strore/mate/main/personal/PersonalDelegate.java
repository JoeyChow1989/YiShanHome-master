package com.sshy.yjy.strore.mate.main.personal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.database.DataBaseManager;
import com.sshy.yjy.strore.mate.main.GetDataBaseUserProfile;
import com.sshy.yjy.strore.mate.main.personal.about.AboutDelegate;
import com.sshy.yjy.strore.mate.main.personal.address.AddressDelegate;
import com.sshy.yjy.strore.mate.main.personal.collect.CollectDelegate;
import com.sshy.yjy.strore.mate.main.personal.list.GridAdapter;
import com.sshy.yjy.strore.mate.main.personal.list.GridBean;
import com.sshy.yjy.strore.mate.main.personal.list.GridItemType;
import com.sshy.yjy.strore.mate.main.personal.message.MessageDelegate;
import com.sshy.yjy.strore.mate.main.personal.order.OrderPersonalListDelegate;
import com.sshy.yjy.strore.mate.main.personal.settings.SettingsDelegate;
import com.sshy.yjy.strore.mate.main.personal.suggestion.SuggestDelegate;
import com.sshy.yjy.strore.mate.main.personal.favourable.FavourDelegate;
import com.sshy.yjy.strore.mate.submitorder.MessageEvent;
import com.sshy.yjy.ui.recycler.BaseDecoration;
import com.zzy.mate.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import qiu.niorgai.StatusBarCompat;
import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.delegates.BaseDelegate;
import strore.yjy.sshy.com.mate.delegates.PermissionCheckerDelegate;
import strore.yjy.sshy.com.mate.ui.camera.CameraImageBean;
import strore.yjy.sshy.com.mate.ui.camera.MateCamera;
import strore.yjy.sshy.com.mate.ui.camera.RequestCodes;
import strore.yjy.sshy.com.mate.util.callback.CallBackManager;
import strore.yjy.sshy.com.mate.util.callback.CallBackType;
import strore.yjy.sshy.com.mate.util.callback.IGlobalCallBack;
import strore.yjy.sshy.com.mate.util.log.MateLogger;
import strore.yjy.sshy.com.mate.util.statusBar.StatusBarUtil;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

import static android.app.Activity.RESULT_OK;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_ADDRESS_ADDRESS;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_ADDRESS_DEFAULT;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_AUTHOR_NICKNAME;
import static strore.yjy.sshy.com.mate.app.AppConfig.ARG_AUTHOR_PIC;
import static strore.yjy.sshy.com.mate.app.AppConfig.RECYCLER_OPTIONS;

public class PersonalDelegate extends BaseDelegate {

    private RecyclerView mRvSettings = null;
    private AppCompatTextView mAllOrder = null;
    private CircleImageView mAvatar = null;
    private LinearLayoutCompat mUserAddress = null;
    private AppCompatTextView mAddressDefult = null;
    private AppCompatTextView mNickName = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    private void startOrderListByType() {
        OrderPersonalListDelegate.startAction(getActivity());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(MessageEvent event) {
        if (event.addrs != null && event.addrs.length() > 3) {
            mAddressDefult.setText(event.addrs);
        }

        if (event.niceName != null) {
            mNickName.setText(event.niceName);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mAllOrder = $(R.id.tv_all_order);
        mAvatar = $(R.id.img_user_avatar);
        mUserAddress = $(R.id.id_ly_users_address);
        mAddressDefult = $(R.id.tv_my_address);
        mNickName = $(R.id.tv_author_nicename);

        mAddressDefult.setText(MatePreference.getCustomAppProfile(ARG_ADDRESS_DEFAULT));
        mNickName.setText(MatePreference.getCustomAppProfile(ARG_AUTHOR_NICKNAME));

        //设置头像
        Glide.with(_mActivity)
                .load(MatePreference.getCustomAppProfile(ARG_AUTHOR_PIC))
                .into(mAvatar);

        mAllOrder.setOnClickListener(v -> {
            startOrderListByType();
        });

        mAvatar.setOnClickListener(v -> {
            CallBackManager.getInstance()
                    .addCallBack(CallBackType.ON_CROP, args -> {
                        final ImageView avatar = $(R.id.img_user_avatar);
                        Glide.with(_mActivity)
                                .load(args)
                                .into(avatar);

                        MatePreference.addCustomAppProfile(ARG_AUTHOR_PIC, args.toString());

                    });

            MateCamera.start(this);
        });

        mUserAddress.setOnClickListener(v -> {
            AddressDelegate.startAction(_mActivity);
        });

        final GridBean s1 = new GridBean.Builder()
                .setItemType(GridItemType.ITEM_NORMAL)
                .setId(1)
                .setImageId(R.drawable.ic_mine_collect)
                .setDelegate(new CollectDelegate())
                .setText("我的收藏")
                .build();

        final GridBean s2 = new GridBean.Builder()
                .setItemType(GridItemType.ITEM_NORMAL)
                .setId(2)
                .setDelegate(new MessageDelegate())
                .setImageId(R.drawable.ic_mine_msg)
                .setText("消息")
                .build();

        final GridBean s3 = new GridBean.Builder()
                .setItemType(GridItemType.ITEM_NORMAL)
                .setId(3)
                .setImageId(R.drawable.ic_mine_favourable)
                .setDelegate(new FavourDelegate())
                .setText("优惠")
                .build();

        final GridBean s4 = new GridBean.Builder()
                .setItemType(GridItemType.ITEM_NORMAL)
                .setId(4)
                .setDelegate(new AboutDelegate())
                .setImageId(R.drawable.ic_mine_about)
                .setText("关于")
                .build();

        final GridBean s5 = new GridBean.Builder()
                .setItemType(GridItemType.ITEM_NORMAL)
                .setId(5)
                .setImageId(R.drawable.ic_mine_suggest)
                .setText("意见反馈")
                .setDelegate(new SuggestDelegate())
                .build();

        final GridBean s6 = new GridBean.Builder()
                .setItemType(GridItemType.ITEM_NORMAL)
                .setId(6)
                .setImageId(R.drawable.ic_mine_setting)
                .setDelegate(new SettingsDelegate())
                .setText("设置")
                .build();


        final List<GridBean> data = new ArrayList<>();
        data.add(s1);
        data.add(s2);
        data.add(s3);
        data.add(s4);
        data.add(s5);
        data.add(s6);

        //设置RecyclerView
        final GridLayoutManager manager = new GridLayoutManager(_mActivity, 3);
        mRvSettings = $(R.id.rv_personal_setting);
        mRvSettings.setLayoutManager(manager);
        mRvSettings.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(_mActivity,
                R.color.app_background), 2));
        final GridAdapter adapter = new GridAdapter(data);
        mRvSettings.setAdapter(adapter);
        mRvSettings.addOnItemTouchListener(new PersonalClickListener(_mActivity));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    UCrop.of(resultUri, resultUri)
                            .withMaxResultSize(400, 400)
                            .start(_mActivity, this);
                    break;
                case RequestCodes.PICK_PHOTO:
                    if (data != null) {
                        final Uri pickPath = data.getData();
                        //从相册选择后需要有个路径存放剪裁过的图片
                        final String pickCropPath = MateCamera.createCropFile().getPath();
                        if (pickPath != null) {
                            UCrop.of(pickPath, Uri.parse(pickCropPath))
                                    .withMaxResultSize(400, 400)
                                    .start(_mActivity, this);
                        }
                    }
                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri = UCrop.getOutput(data);
                    //拿到剪裁后的数据进行处理
                    @SuppressWarnings("unchecked") final IGlobalCallBack<Uri> callBack = CallBackManager
                            .getInstance()
                            .getCallBack(CallBackType.ON_CROP);

                    if (callBack != null) {
                        callBack.executeCallback(cropUri);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
