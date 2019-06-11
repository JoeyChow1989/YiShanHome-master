package com.sshy.yjy.ui.recycler;


import com.google.auto.value.AutoValue;

/**
 * Created by 周正尧 on 2018/3/27 0027.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */

@AutoValue
public abstract class RgbValue {

    public abstract int red();

    public abstract int green();

    public abstract int blue();

    public static RgbValue create(int red, int green, int blue) {
        return new AutoValue_RgbValue(red, green, blue);
    }



}
