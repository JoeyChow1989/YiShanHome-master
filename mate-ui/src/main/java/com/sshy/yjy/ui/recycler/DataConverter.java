package com.sshy.yjy.ui.recycler;

import java.util.ArrayList;

/**
 * Created by 周正尧 on 2018/3/23 0023.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json){
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData(){
        if (mJsonData == null){
            throw new NullPointerException("DATA IS NULL!");
        }
        return mJsonData;
    }
}
