package com.sshy.yjy.strore.mate.main.personal.list;

import android.support.v7.widget.SwitchCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sshy.yjy.strore.R;

import java.util.List;

public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {

    public static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(ListItemType.ITEM_NORMAL, R.layout.arrow_item_layout);
        addItemType(ListItemType.ITEM_AVATAR, R.layout.arrow_item_avatar);
        addItemType(ListItemType.ITEM_SWITCH, R.layout.arrow_switch_layout);

        addItemType(ListItemType.ITEM_PIC, R.layout.arrow_item_pic_layout);
        addItemType(ListItemType.ITEM_VALUE, R.layout.arrow_item_vaule_layout);
        addItemType(ListItemType.ITEM_NOVAULE, R.layout.arrow_item_novaule_layout);
        addItemType(ListItemType.ITEM_COOPER, R.layout.arrow_item_coopearation);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        switch (helper.getItemViewType()) {
            case ListItemType.ITEM_NORMAL:
                helper.setText(R.id.tv_arrow_text, item.getText());
                helper.setText(R.id.tv_arrow_value, item.getValue());
                break;
            case ListItemType.ITEM_AVATAR:
                Glide.with(mContext)
                        .load(item.getImageUrl())
                        .apply(OPTIONS)
                        .into((ImageView) helper.getView(R.id.img_arrow_avatar));
                break;
            case ListItemType.ITEM_SWITCH:
                helper.setText(R.id.tv_arrow_switch_text, item.getText());
                final SwitchCompat switchCompat = helper.getView(R.id.list_item_switch);
                switchCompat.setChecked(true);
                switchCompat.setOnCheckedChangeListener(item.getOnCheckedChangeListener());
                break;
            case ListItemType.ITEM_PIC:
                helper.setText(R.id.arrow_item_pic_text, item.getText());
                helper.setImageResource(R.id.arrow_item_pic_img, item.getPic());
                break;
            case ListItemType.ITEM_VALUE:
                helper.setText(R.id.arrow_item_value_text, item.getText());
                helper.setText(R.id.arrow_item_value_value, item.getValue());
                break;
            case ListItemType.ITEM_NOVAULE:
                helper.setText(R.id.arrow_item_novalue_text, item.getText());
                break;

            case ListItemType.ITEM_COOPER:
                helper.setText(R.id.iv_cooper_item_title, item.getText());
                helper.setText(R.id.iv_cooper_item_text, item.getValue());

                Glide.with(mContext)
                        .load(item.getPic())
                        .apply(OPTIONS)
                        .into((ImageView) helper.getView(R.id.iv_cooper_item_pic));

                break;
            default:
                break;
        }
    }
}
