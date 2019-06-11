package com.sshy.yjy.strore.mate.main.cart;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.cart.bean.GoodsInfo;
import com.sshy.yjy.strore.mate.main.cart.bean.StoreInfo;
import com.zzy.mate.picture.tools.ToastManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import strore.yjy.sshy.com.mate.delegates.BaseDelegate;
import strore.yjy.sshy.com.mate.net.RestCreator;
import strore.yjy.sshy.com.mate.net.callback.ISuccess;
import strore.yjy.sshy.com.mate.pay.IAlPayResultListener;

import static in.srain.cube.views.ptr.util.PtrLocalDisplay.dp2px;

/**
 * Created by 傅令杰
 */

public class ShopCartDelegate extends BaseDelegate implements ISuccess, IAlPayResultListener,
        ShopCartAdapter.CheckInterface, ShopCartAdapter.ModifyCountInterface,
        ShopCartAdapter.GroupEditorListener, View.OnClickListener {

    private ExpandableListView listView = null;
    private CheckBox allCheckBox = null;
    private AppCompatTextView totalPrice = null;
    private AppCompatTextView goPay = null;
    private LinearLayoutCompat orderInfo = null;
    private AppCompatTextView shareGoods = null;
    private AppCompatTextView collectGoods = null;
    private AppCompatTextView delGoods = null;
    private LinearLayoutCompat shareInfo = null;
    private LinearLayoutCompat llCart = null;
    private PtrFrameLayout mPtrFrame = null;
    private LinearLayoutCompat mEmptyShopCart = null;
    private AppCompatTextView mShoppingCatNum = null;
    private AppCompatButton actionBarEdit = null;

    private double mTotalPrice = 0.00;
    private int mTotalCount = 0;

    //false就是编辑，ture就是完成
    private boolean flag = false;
    private ShopCartAdapter adapter;

    //数据
    private List<StoreInfo> groups; //组元素的列表
    private Map<String, List<GoodsInfo>> children; //子元素的列表

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initViews();
        initPtrFrame();
        final String url = "shop_cart.php";
        final WeakHashMap<String, Object> params = new WeakHashMap<>();
        Observable<String> observable = RestCreator.getRxRestService()
                .get(url, params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String s) {

                        groups = new ArrayList<>();
                        children = new HashMap<>();

                        JSONArray array = JSON.parseObject(s).getJSONArray("data");
                        int groups_size = array.size();
                        for (int i = 0; i < groups_size; i++) {
                            String group = array.getJSONObject(i).getString("shop");
                            groups.add(new StoreInfo(String.valueOf(i), group));

                            List<GoodsInfo> goods = new ArrayList<>();
                            JSONArray childs_array = array.getJSONObject(i).getJSONArray("data");
                            int child_size = childs_array.size();
                            for (int j = 0; j < child_size; j++) {
                                String id = childs_array.getJSONObject(j).getString("id");
                                String name = childs_array.getJSONObject(j).getString("title");
                                String desc = childs_array.getJSONObject(j).getString("desc");
                                String price = childs_array.getJSONObject(j).getString("price");
                                String count = childs_array.getJSONObject(j).getString("count");
                                String thumb = childs_array.getJSONObject(j).getString("thumb");

                                goods.add(new GoodsInfo(id, name, desc, Double.valueOf(price),
                                        1555 + new Random().nextInt(3000), "第一排",
                                        "sss", thumb, Integer.parseInt(count)));
                            }
                            children.put(groups.get(i).getId(), goods);
                        }

                        initEvents(groups, children);
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
        listView = $(R.id.listView);
        allCheckBox = $(R.id.all_checkBox);
        totalPrice = $(R.id.total_price);
        goPay = $(R.id.go_pay);
        orderInfo = $(R.id.order_info);
        shareGoods = $(R.id.share_goods);
        collectGoods = $(R.id.collect_goods);
        delGoods = $(R.id.del_goods);
        shareInfo = $(R.id.share_info);
        llCart = $(R.id.ll_cart);
        mPtrFrame = $(R.id.mPtrframe);
        mEmptyShopCart = $(R.id.layout_empty_shopcart);
        mShoppingCatNum = $(R.id.shoppingcat_num);
        actionBarEdit = $(R.id.actionBar_edit);

        allCheckBox.setOnClickListener(this);
        goPay.setOnClickListener(this);
        shareGoods.setOnClickListener(this);
        collectGoods.setOnClickListener(this);
        delGoods.setOnClickListener(this);
        actionBarEdit.setOnClickListener(this);
    }

    private void initEvents(List<StoreInfo> groups, Map<String, List<GoodsInfo>> childs) {
        adapter = new ShopCartAdapter(groups, childs, _mActivity);
        adapter.setCheckInterface(this);//关键步骤1：设置复选框的接口
        adapter.setModifyCountInterface(this); //关键步骤2:设置增删减的接口
        adapter.setGroupEditorListener(this);//关键步骤3:监听组列表的编辑状态
        listView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头
        listView.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            listView.expandGroup(i); //关键步骤4:初始化，将ExpandableListView以展开的方式显示
        }
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //int mFirstVisiablePosition = view.getFirstVisiblePosition();
                int top = -1;
                View firstView = view.getChildAt(firstVisibleItem);
                if (firstView != null) {
                    top = firstView.getTop();
                }
                if (firstVisibleItem == 0 && top == 0) {
                    mPtrFrame.setEnabled(true);
                } else {
                    mPtrFrame.setEnabled(false);
                }
            }
        });
    }

    private void initPtrFrame() {
        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getContext());
        header.setPadding(PtrLocalDisplay.dp2px(20), PtrLocalDisplay.dp2px(20), 0, 0);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    @Override
    public void onSuccess(String response) {

    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }

    private void setVisiable() {
        if (flag) {
            orderInfo.setVisibility(View.GONE);
            shareInfo.setVisibility(View.VISIBLE);
            actionBarEdit.setText("完成");
        } else {
            orderInfo.setVisibility(View.VISIBLE);
            shareInfo.setVisibility(View.GONE);
            actionBarEdit.setText("编辑");
        }
    }

    /**
     * ActionBar标题上点编辑的时候，只显示每一个店铺的商品修改界面
     * ActionBar标题上点完成的时候，只显示每一个店铺的商品信息界面
     */
    private void setActionBarEditor() {
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            if (group.isActionBarEditor()) {
                group.setActionBarEditor(false);
            } else {
                group.setActionBarEditor(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * @return 判断组元素是否全选
     */
    private boolean isCheckAll() {
        for (StoreInfo group : groups) {
            if (!group.isChoosed()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo> child = children.get(group.getId());
        for (int i = 0; i < child.size(); i++) {
            child.get(i).setChoosed(isChecked);
        }
        if (isCheckAll()) {
            allCheckBox.setChecked(true);//全选
        } else {
            allCheckBox.setChecked(false);//反选
        }
        adapter.notifyDataSetChanged();
        calulate();
    }

    /**
     * 全选和反选
     * 错误标记：在这里出现过错误
     */
    private void doCheckAll() {
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            group.setChoosed(allCheckBox.isChecked());
            List<GoodsInfo> child = children.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                child.get(j).setChoosed(allCheckBox.isChecked());//这里出现过错误
            }
        }
        adapter.notifyDataSetChanged();
        calulate();
    }

    /**
     * 计算商品总价格，操作步骤
     * 1.先清空全局计价,计数
     * 2.遍历所有的子元素，只要是被选中的，就进行相关的计算操作
     * 3.给textView填充数据
     */
    @SuppressLint("SetTextI18n")
    private void calulate() {
        mTotalPrice = 0.00;
        mTotalCount = 0;
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            List<GoodsInfo> child = children.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                GoodsInfo good = child.get(j);
                if (good.isChoosed()) {
                    mTotalCount++;
                    mTotalPrice += good.getPrice() * good.getCount();
                }
            }
        }
        totalPrice.setText("￥" + mTotalPrice + "");
        goPay.setText("去支付(" + mTotalCount + ")");
        if (mTotalCount == 0) {
            setCartNum();
        } else {
            mShoppingCatNum.setText("购物车(" + mTotalCount + ")");
        }
    }

    /**
     * 设置购物车的数量
     */
    @SuppressLint("SetTextI18n")
    private void setCartNum() {
        int count = 0;
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            group.setChoosed(allCheckBox.isChecked());
            List<GoodsInfo> Children = children.get(group.getId());
            for (GoodsInfo children : Children) {
                count++;
            }
        }
        //购物车已经清空
        if (count == 0) {
            clearCart();
        } else {
            mShoppingCatNum.setText("购物车(" + count + ")");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void clearCart() {
        mShoppingCatNum.setText("购物车(0)");
        actionBarEdit.setVisibility(View.GONE);
        llCart.setVisibility(View.GONE);
        mEmptyShopCart.setVisibility(View.VISIBLE);//这里发生过错误
    }

    /**
     * 删除操作
     * 1.不要边遍历边删除,容易出现数组越界的情况
     * 2.把将要删除的对象放进相应的容器中，待遍历完，用removeAll的方式进行删除
     */
    private void doDelete() {
        List<StoreInfo> toBeDeleteGroups = new ArrayList<StoreInfo>(); //待删除的组元素
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            List<GoodsInfo> toBeDeleteChildren = new ArrayList<GoodsInfo>();//待删除的子元素
            List<GoodsInfo> child = children.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                if (child.get(j).isChoosed()) {
                    toBeDeleteChildren.add(child.get(j));
                }
            }
            child.removeAll(toBeDeleteChildren);
        }
        groups.removeAll(toBeDeleteGroups);
        //重新设置购物车
        setCartNum();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true; //判断该组下面的所有子元素是否处于同一状态
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo> child = children.get(group.getId());
        for (int i = 0; i < child.size(); i++) {
            //不选全中
            if (child.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }

        if (allChildSameState) {
            group.setChoosed(isChecked);//如果子元素状态相同，那么对应的组元素也设置成这一种的同一状态
        } else {
            group.setChoosed(false);//否则一律视为未选中
        }

        if (isCheckAll()) {
            allCheckBox.setChecked(true);//全选
        } else {
            allCheckBox.setChecked(false);//反选
        }

        adapter.notifyDataSetChanged();
        calulate();

    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        GoodsInfo good = (GoodsInfo) adapter.getChild(groupPosition, childPosition);
        int count = good.getCount();
        count++;
        good.setCount(count);
        ((TextView) showCountView).setText(String.valueOf(count));
        adapter.notifyDataSetChanged();
        calulate();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        GoodsInfo good = (GoodsInfo) adapter.getChild(groupPosition, childPosition);
        int count = good.getCount();
        if (count == 1) {
            return;
        }
        count--;
        good.setCount(count);
        ((TextView) showCountView).setText("" + count);
        adapter.notifyDataSetChanged();
        calulate();
    }

    @Override
    public void doUpdate(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        GoodsInfo good = (GoodsInfo) adapter.getChild(groupPosition, childPosition);
        int count = good.getCount();
        ((TextView) showCountView).setText(String.valueOf(count));
        adapter.notifyDataSetChanged();
        calulate();
    }

    @Override
    public void childDelete(int groupPosition, int childPosition) {
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo> child = children.get(group.getId());
        child.remove(childPosition);
        if (child.size() == 0) {
            groups.remove(groupPosition);
        }
        adapter.notifyDataSetChanged();
        calulate();

    }

    @Override
    public void groupEditor(int groupPosition) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
        mTotalPrice = 0.00;
        mTotalCount = 0;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.all_checkBox) {
            doCheckAll();
        } else if (i == R.id.go_pay) {
            AlertDialog dialog;
            if (mTotalCount == 0) {
                ToastManage.s(getContext(), "请选择要支付的商品");
                return;
            }
            dialog = new AlertDialog.Builder(getContext()).create();
            dialog.setMessage("总计：" + mTotalCount + "种商品，" + mTotalPrice + "元");
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "支付", (dialog1, which) -> {
                return;
            });

            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", (dialog1, which) -> {
                return;
            });
            dialog.show();
        } else if (i == R.id.share_goods) {
            if (mTotalCount == 0) {
                ToastManage.s(getContext(), "请选择要分享的商品");
                return;
            }
            ToastManage.s(getContext(), "分享成功");
        } else if (i == R.id.collect_goods) {
            if (mTotalCount == 0) {
                ToastManage.s(getContext(), "请选择要收藏的商品");
                return;
            }
            ToastManage.s(getContext(), "收藏成功");
        } else if (i == R.id.del_goods) {
            AlertDialog dialog;
            if (mTotalCount == 0) {
                ToastManage.s(getContext(), "请选择要删除的商品");
                return;
            }
            dialog = new AlertDialog.Builder(getContext()).create();
            dialog.setMessage("确认要删除该商品吗?");
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog1, which) -> {
                doDelete();
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", (dialog1, which) -> {
                return;
            });
            dialog.show();
        } else if (i == R.id.actionBar_edit) {
            flag = !flag;
            setActionBarEditor();
            setVisiable();
        }
    }
}
