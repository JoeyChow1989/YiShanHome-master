package com.sshy.yjy.strore.mate.main.personal.list;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sshy.yjy.strore.R;

import java.util.List;

/**
 * create date：2018/8/9
 * create by：周正尧
 */
public class GridAdapter extends BaseMultiItemQuickAdapter<GridBean, BaseViewHolder> {

    public static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerInside()
            .dontAnimate();

    public GridAdapter(List<GridBean> data) {
        super(data);
        addItemType(GridItemType.ITEM_NORMAL, R.layout.arrow_item_grid_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, GridBean item) {
        switch (helper.getItemViewType()) {
            case GridItemType.ITEM_NORMAL:
                helper.setText(R.id.tv_arrow_grid_text, item.getText());
                Glide.with(mContext)
                        .applyDefaultRequestOptions(OPTIONS)
                        .load(item.getImageId())
                        .into((ImageView) helper.getView(R.id.img_grid_avatar));
                break;
            default:
                break;
        }
    }
}
