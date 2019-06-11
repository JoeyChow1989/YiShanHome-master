package com.sshy.yjy.strore.mate.main.index.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * create date：2018/9/14
 * create by：周正尧
 */
public class ButtonBean implements MultiItemEntity {

    private int mItemType = 0;
    private String catId = null;
    private String catName = null;

    public ButtonBean(int mItemType, String catId, String catName) {
        this.mItemType = mItemType;
        this.catId = catId;
        this.catName = catName;
    }

    public String getCatId() {
        return catId;
    }

    public String getCatName() {
        return catName;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public static final class Builder {

        private int mItemType = 0;
        private String catId = null;
        private String catName = null;

        public Builder setItemType(int mItemType) {
            this.mItemType = mItemType;
            return this;
        }

        public Builder setCatId(String catId) {
            this.catId = catId;
            return this;
        }

        public Builder setCatName(String catName) {
            this.catName = catName;
            return this;
        }

        public ButtonBean build() {
            return new ButtonBean(mItemType, catId, catName);
        }
    }
}
