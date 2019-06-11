package com.sshy.yjy.strore.mate.main.index;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.index.bean.ViewPagerBean;
import com.sshy.yjy.strore.mate.sortShopList.SortShopListDelegate;

import java.util.ArrayList;

import strore.yjy.sshy.com.mate.app.AppConfig;

import static strore.yjy.sshy.com.mate.app.AppConfig.RECYCLER_OPTIONS;


/**
 * create date：2018/6/26
 * create by：周正尧
 */
public class PagerImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private FragmentActivity context;
    private ArrayList<ViewPagerBean> pagers;
    View view;

    public PagerImageAdapter(FragmentActivity context, ArrayList<ViewPagerBean> pagers) {
        this.context = context;
        this.pagers = pagers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_pagers_image, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = new ViewHolder(view);
        Glide.with(context)
                .load(AppConfig.API_HOST + pagers.get(position).getIcon())
                .apply(AppConfig.RECYCLER_OPTIONS)
                .into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(v -> {
            SortShopListDelegate.startAction(context, pagers.get(position).getTagId(),
                    pagers.get(position).getTagName());
        });
    }

    @Override
    public int getItemCount() {
        return pagers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_page_image);
        }
    }
}
