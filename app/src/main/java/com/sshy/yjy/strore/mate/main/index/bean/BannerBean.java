package com.sshy.yjy.strore.mate.main.index.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * create date：2019/1/8
 * create by：周正尧
 */
public class BannerBean implements MultiItemEntity {

    private int mItemType = 0;
    private String bannerId = null;
    private String img = null;
    private String isDel = null;
    private String createDate = null;
    private String clickType = null;
    private String targetValue = null;

    public BannerBean(int mItemType, String bannerId, String img, String isDel,
                      String createDate, String clickType, String targetValue) {
        this.mItemType = mItemType;
        this.bannerId = bannerId;
        this.img = img;
        this.isDel = isDel;
        this.createDate = createDate;
        this.clickType = clickType;
        this.targetValue = targetValue;
    }

    public String getBannerId() {
        return bannerId;
    }

    public String getImg() {
        return img;
    }

    public String getIsDel() {
        return isDel;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getClickType() {
        return clickType;
    }

    public String getTargetValue() {
        return targetValue;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static final class Builder {

        private int mItemType = 0;
        private String bannerId = null;
        private String img = null;
        private String isDel = null;
        private String createDate = null;
        private String clickType = null;
        private String targetValue = null;

        public Builder setItemType(int mItemType) {
            this.mItemType = mItemType;
            return this;
        }

        public Builder setBannerId(String bannerId) {
            this.bannerId = bannerId;
            return this;
        }

        public Builder setImg(String img) {
            this.img = img;
            return this;
        }

        public Builder setIsDel(String isDel) {
            this.isDel = isDel;
            return this;
        }

        public Builder setCreateDate(String createDate) {
            this.createDate = createDate;
            return this;
        }

        public Builder setClickType(String clickType) {
            this.clickType = clickType;
            return this;
        }

        public Builder setTargetValue(String targetValue) {
            this.targetValue = targetValue;
            return this;
        }

        public BannerBean build() {
            return new BannerBean(mItemType, bannerId, img, isDel, createDate, clickType, targetValue);
        }

    }
}
