package com.sshy.yjy.ui.recycler.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by 周正尧 on 2018/3/23 0023.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */

public class MultipleViewHolder extends BaseViewHolder {

    private MultipleViewHolder(View view){
        super(view);
    }

    public static MultipleViewHolder create(View view){
        return new MultipleViewHolder(view);
    }
}
