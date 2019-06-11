package com.sshy.yjy.ui.recycler;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

/**
 * Created by 周正尧 on 2018/3/27 0027.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */
public class DividerLookuoImpl implements DividerItemDecoration.DividerLookup{

    private final int COLOR;
    private final int SIZE;

    public DividerLookuoImpl(int color, int size) {
        this.COLOR = color;
        this.SIZE = size;
    }

    @Override
    public Divider getVerticalDivider(int position) {
        return new Divider.Builder()
                .size(SIZE)
                .color(COLOR)
                .build();
    }

    @Override
    public Divider getHorizontalDivider(int position) {
        return new Divider.Builder()
                .size(SIZE)
                .color(COLOR)
                .build();
    }
}
