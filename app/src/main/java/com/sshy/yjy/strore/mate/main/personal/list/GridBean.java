package com.sshy.yjy.strore.mate.main.personal.list;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import strore.yjy.sshy.com.mate.activities.BaseActivity;

/**
 * create date：2018/8/9
 * create by：周正尧
 */
public class GridBean implements MultiItemEntity {

    private int mItemType = 0;
    private int mImageId = 0;
    private String mText = null;
    private int mid = 0;
    private BaseActivity mDelegate = null;


    public GridBean(int mItemType, int mImageId, String mText, int mid, BaseActivity mDelegate) {
        this.mItemType = mItemType;
        this.mImageId = mImageId;
        this.mText = mText;
        this.mid = mid;
        this.mDelegate = mDelegate;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public int getImageId() {
        return mImageId;
    }

    public String getText() {
        if (mText == null) {
            return "";
        }
        return mText;
    }

    public int getId() {
        return mid;
    }

    public BaseActivity getDelegate() {
        return mDelegate;
    }

    public static final class Builder {
        private int id = 0;
        private int itemType = 0;
        private int imageId = 0;
        private String text = null;
        private BaseActivity mateDelegate = null;


        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setImageId(int imageId) {
            this.imageId = imageId;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setDelegate(BaseActivity mateDelegate) {
            this.mateDelegate = mateDelegate;
            return this;
        }

        public GridBean build() {
            return new GridBean(itemType, imageId, text, id, mateDelegate);
        }
    }
}
