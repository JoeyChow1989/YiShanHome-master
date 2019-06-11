package com.sshy.yjy.ui.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * Created by 周正尧 on 2018/3/27 0027.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */
public class BaseDecoration extends DividerItemDecoration {


    private BaseDecoration(@ColorInt int color, int size) {
        setDividerLookup(new DividerLookuoImpl(color, size));
    }


    public static BaseDecoration create(@ColorInt int color, int size){
        return new BaseDecoration(color, size);
    }
}
