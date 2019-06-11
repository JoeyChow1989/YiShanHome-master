package com.sshy.yjy.strore.mate.main.index;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.detail.goodsDetail.GoodsDetailDelegate;
import com.sshy.yjy.strore.mate.detail.shopDetail.ShopDetailDelegate;
import com.sshy.yjy.strore.mate.main.index.bean.BannerBean;
import com.sshy.yjy.strore.mate.main.index.bean.ButtonBean;
import com.sshy.yjy.strore.mate.main.index.bean.ViewPagerBean;
import com.sshy.yjy.strore.mate.main.sort.SortDelegate;
import com.sshy.yjy.strore.mate.main.webview.WebViewDelegate;
import com.sshy.yjy.strore.mate.shoplist.ShopListDelegate;
import com.sshy.yjy.ui.banner.mzbanner.MZBannerView;
import com.sshy.yjy.ui.banner.mzbanner.holder.MZHolderCreator;
import com.sshy.yjy.ui.banner.mzbanner.holder.MZViewHolder;
import com.sshy.yjy.ui.recycler.DataConverter;
import com.sshy.yjy.ui.recycler.ItemType;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;
import com.sshy.yjy.ui.recycler.adapter.MultipleRecyclerAdapter;
import com.sshy.yjy.ui.recycler.adapter.MultipleViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import strore.yjy.sshy.com.mate.app.AppConfig;
import strore.yjy.sshy.com.mate.util.log.MateLogger;

import static strore.yjy.sshy.com.mate.app.AppConfig.RECYCLER_OPTIONS;

/**
 * create date：2018/9/17
 * create by：周正尧
 */
public class IndexAdapter extends MultipleRecyclerAdapter {

    //设置图片加载策略
    public static final RequestOptions BUTTON_OPTIONS =
            new RequestOptions()
                    .optionalFitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    //确保初始化一次banner，防止重复item加载
    private boolean mIsInitBanner = false;

    private FragmentActivity DELEGATE;
    private List<MultipleItemEntity> DATA;

    protected IndexAdapter(List<MultipleItemEntity> data, FragmentActivity delegate) {
        super(data);
        this.DELEGATE = delegate;
        this.DATA = data;
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_image_text);
        addItemType(ItemType.BANNER, R.layout.item_multiple_mzbanner);
        addItemType(ItemType.VIEW_PAGER, R.layout.item_multiple_viewpager);
        addItemType(ItemType.TEB, R.layout.item_multiple_button);
        //设置宽度的监听
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    public static IndexAdapter create(List<MultipleItemEntity> data, FragmentActivity delegate) {
        return new IndexAdapter(data, delegate);
    }

    public static IndexAdapter create(DataConverter converter, FragmentActivity delegate) {
        return new IndexAdapter(converter.convert(), delegate);
    }

    @Override
    public int getItemCount() {
        return DATA.size();
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        final String text;
        final ArrayList<BannerBean> bannerImages;
        final ArrayList<String> banners;
        final ArrayList<ViewPagerBean> tags;
        final ArrayList<ButtonBean> buttons;

        final String image_url;
        final String name;
        final String pos;
        final String sold;
        final String price;

        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                text = entity.getField(MultipleFields.TEXT);
                holder.setText(R.id.text_single, text);
                break;
            case ItemType.TEXT_IMAGE:
                image_url = entity.getField(MultipleFields.IMAGE_URL);
                name = entity.getField(MultipleFields.NAME);
                text = entity.getField(MultipleFields.TITLE);
                pos = entity.getField(MultipleFields.DISTANCE);
                sold = entity.getField(MultipleFields.SOLD);
                price = entity.getField(MultipleFields.PRICE);

                if (price != null) {
                    holder.setText(R.id.tv_index_item_price, String.valueOf(Double.parseDouble(price)));
                }
                if (pos != null) {
                    holder.setText(R.id.tv_index_item_distance, Double.parseDouble(pos) + "km");
                }
                holder.setText(R.id.tv_index_item_title, name);
                holder.setText(R.id.tv_index_item_text, text);
                holder.setText(R.id.tv_index_item_sold, "已售:" + sold);

                MateLogger.d("image", image_url);

                Glide.with(mContext)
                        .load(AppConfig.API_HOST + image_url)
                        .apply(RECYCLER_OPTIONS)
                        .into((AppCompatImageView) holder.getView(R.id.img_multiple));
                break;
            case ItemType.BANNER:
                if (!mIsInitBanner) {
                    bannerImages = entity.getField(MultipleFields.BANNERS);
                    banners = new ArrayList<>();
                    for (int i = 0; i < bannerImages.size(); i++) {
                        String banner = bannerImages.get(i).getImg();
                        banners.add(banner);
                    }
//                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
//                    BannerCreator.setDefault(convenientBanner, bannerImages, this);

                    final MZBannerView mzBannerView = holder.getView(R.id.banner);
                    //点击事件
                    mzBannerView.setBannerPageClickListener((view, position) -> {
                        String clickType = bannerImages.get(position).getClickType();
                        String targetVelue = bannerImages.get(position).getTargetValue();

                        if (clickType != null) {
                            if ("商品".equals(clickType)) {
                                GoodsDetailDelegate.startAction(DELEGATE, targetVelue);
                            } else if ("商铺".equals(clickType)) {

                                ShopDetailDelegate.startAction(DELEGATE, targetVelue);

                            } else if ("H5".equals(clickType)) {
                                WebViewDelegate.startAction(DELEGATE, targetVelue);
                            }
                        }
                    });
                    mzBannerView.addPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                    mzBannerView.setPages(banners, (MZHolderCreator<BannerViewHolder>) BannerViewHolder::new);
                    mzBannerView.start();
                    mIsInitBanner = true;
                }
                break;
            case ItemType.VIEW_PAGER:
                tags = entity.getField(MultipleFields.PAGERS);
                final RecyclerView recyclerView = holder.getView(R.id.rv_index_page);
                final LinearLayoutManager manager = new LinearLayoutManager(mContext);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                PagerImageAdapter imageAdapter = new PagerImageAdapter(DELEGATE, tags);
                recyclerView.setAdapter(imageAdapter);
                break;
            case ItemType.TEB:
                buttons = entity.getField(MultipleFields.BUTTON);
                holder.setText(R.id.tv_button_text1, buttons.get(0).getCatName());
                holder.setText(R.id.tv_button_text2, buttons.get(1).getCatName());
                holder.setText(R.id.tv_button_text3, buttons.get(2).getCatName());
                holder.setText(R.id.tv_button_text4, buttons.get(3).getCatName());
                holder.setText(R.id.tv_button_text5, "全部分类");

                Glide.with(mContext)
                        .load(R.drawable.ic_cooper_health_pic)
                        .apply(BUTTON_OPTIONS)
                        .into((CircleImageView) holder.getView(R.id.civ_index_button1));

                Glide.with(mContext)
                        .load(R.drawable.ic_cooper_food_pic)
                        .apply(BUTTON_OPTIONS)
                        .into((CircleImageView) holder.getView(R.id.civ_index_button2));

                Glide.with(mContext)
                        .load(R.drawable.ic_cooper_trains_pic)
                        .apply(BUTTON_OPTIONS)
                        .into((CircleImageView) holder.getView(R.id.civ_index_button3));

                Glide.with(mContext)
                        .load(R.drawable.ic_cooper_keep_pic)
                        .apply(BUTTON_OPTIONS)
                        .into((CircleImageView) holder.getView(R.id.civ_index_button4));

                Glide.with(mContext)
                        .load(R.drawable.ic_cooper_all_pic)
                        .apply(BUTTON_OPTIONS)
                        .into((CircleImageView) holder.getView(R.id.civ_index_button5));

                holder.getView(R.id.ly_index_button_1).setOnClickListener(v -> {
                    ShopListDelegate.startAction(DELEGATE, buttons.get(0).getCatId());
                });

                holder.getView(R.id.ly_index_button_2).setOnClickListener(v -> {
                    ShopListDelegate.startAction(DELEGATE, buttons.get(1).getCatId());
                });

                holder.getView(R.id.ly_index_button_3).setOnClickListener(v -> {
                    ShopListDelegate.startAction(DELEGATE, buttons.get(2).getCatId());
                });

                holder.getView(R.id.ly_index_button_4).setOnClickListener(v -> {
                    ShopListDelegate.startAction(DELEGATE, buttons.get(3).getCatId());
                });

                holder.getView(R.id.ly_index_button_5).setOnClickListener(v -> {
                    Intent intent = new Intent(DELEGATE, SortDelegate.create().getClass());
                    DELEGATE.startActivity(intent);
                });
                break;
            default:
                break;
        }
    }

    public static class BannerViewHolder implements MZViewHolder<String> {

        private AppCompatImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.remote_banner_item, null);
            imageView = view.findViewById(R.id.remote_item_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            Glide.with(context).load(AppConfig.API_HOST + data).apply(RECYCLER_OPTIONS).into(imageView);
        }
    }
}
