package com.sshy.yjy.strore.mate.main.sort.content.adapter;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.sort.content.SectionBean;

import java.util.List;


/**
 * Created by 周正尧 on 2018/3/29 0029.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */
public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     *
     */

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();


    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);

    }

    @Override
    protected void convertHead(BaseViewHolder holder, SectionBean item) {
        holder.setText(R.id.header, item.header);
        holder.setVisible(R.id.more, item.isIsMore());
        holder.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(BaseViewHolder holder, SectionBean item) {
        //.t返回SectionBean类型
        final String thumb = item.t.getGoodsThumb();
        final String name = item.t.getGoodsName();
       // final int id = item.t.getGoodsId();

       // final SectionContentItemEntity entity = item.t;
        holder.setText(R.id.tv,name);
        final AppCompatImageView goodsImageView = holder.getView(R.id.iv);

        Glide.with(mContext)
                .load(thumb)
                .apply(OPTIONS)
                .into(goodsImageView);

    }
}
