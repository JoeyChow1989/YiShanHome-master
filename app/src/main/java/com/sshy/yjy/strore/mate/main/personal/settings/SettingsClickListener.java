package com.sshy.yjy.strore.mate.main.personal.settings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.personal.list.ListBean;

import strore.yjy.sshy.com.mate.app.Mate;
import strore.yjy.sshy.com.mate.ui.loader.MateLoader;
import strore.yjy.sshy.com.mate.util.DataCleanManager;

import static strore.yjy.sshy.com.mate.app.Mate.getApplicationContext;

/**
 * create date：2018/4/26
 * create by：周正尧
 */
public class SettingsClickListener extends SimpleClickListener {

    private final Context DELEGATE;

    private SettingsClickListener(Context delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(Context delegate) {
        return new SettingsClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        Intent intent;
        switch (id) {
            case 1:
                intent = new Intent(DELEGATE, bean.getDelegate().getClass());
                DELEGATE.startActivity(intent);
                break;
            case 2:
                intent = new Intent(DELEGATE, bean.getDelegate().getClass());
                DELEGATE.startActivity(intent);
                break;
            case 3:
                intent = new Intent(DELEGATE, bean.getDelegate().getClass());
                DELEGATE.startActivity(intent);
                break;
            case 4:
                intent = new Intent(DELEGATE, bean.getDelegate().getClass());
                DELEGATE.startActivity(intent);
                break;
            case 5:
                MateLoader.showLoading(DELEGATE);
                Mate.getHandler().postDelayed(() -> {
                    try {
                        DataCleanManager.cleanInternalCache(Mate.getApplicationContext());
                        AppCompatTextView tv = view.findViewById(R.id.arrow_item_value_value);
                        tv.setText("0kb");

                        MateLoader.stopLoading();
                        ToastUtils.showShort("清除成功！");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1000);

                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
