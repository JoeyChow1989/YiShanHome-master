package com.sshy.yjy.strore.mate.main.index.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * create date：2018/9/14
 * create by：周正尧
 */
public class ViewPagerBean implements MultiItemEntity {

    private int mItemType = 0;
    private String pos;
    private String createDateZH;
    private String tagId;
    private String tagName;
    private String isDel;
    private String icon;
    private String createDate;

    public ViewPagerBean(int mItemType, String pos, String createDateZH,
                         String tagId, String tagName, String isDel,
                         String icon, String createDate) {
        this.mItemType = mItemType;
        this.pos = pos;
        this.createDateZH = createDateZH;
        this.tagId = tagId;
        this.tagName = tagName;
        this.isDel = isDel;
        this.icon = icon;
        this.createDate = createDate;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public String getPos() {
        return pos;
    }

    public String getCreateDateZH() {
        return createDateZH;
    }

    public String getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public String isDel() {
        return isDel;
    }

    public String getIcon() {
        return icon;
    }

    public String getCreateDate() {
        return createDate;
    }

    public static final class Builder {

        private int mItemType = 0;
        private String pos;
        private String createDateZH;
        private String tagId;
        private String tagName;
        private String isDel;
        private String icon;
        private String createDate;

        public Builder setItemType(int mItemType) {
            this.mItemType = mItemType;
            return this;
        }

        public Builder setPos(String pos) {
            this.pos = pos;
            return this;
        }

        public Builder setCreateDateZH(String createDateZH) {
            this.createDateZH = createDateZH;
            return this;
        }

        public Builder setTagId(String tagId) {
            this.tagId = tagId;
            return this;
        }

        public Builder setTagName(String tagName) {
            this.tagName = tagName;
            return this;
        }

        public Builder setDel(String del) {
            isDel = del;
            return this;
        }

        public Builder setIcon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder setCreateDate(String createDate) {
            this.createDate = createDate;
            return this;
        }

        public ViewPagerBean build() {
            return new ViewPagerBean(mItemType, pos, createDateZH, tagId, tagName, isDel, icon, createDate);
        }
    }
}
