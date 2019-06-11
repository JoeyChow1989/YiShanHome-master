package com.sshy.yjy.strore.mate.main.personal.list;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import strore.yjy.sshy.com.mate.activities.BaseActivity;

public class ListBean implements MultiItemEntity {

    private int mItemType = 0;
    private String mImageUrl = null;
    private int mPic = 0;
    private String mText = null;
    private String mValue = null;
    private int mid = 0;
    private BaseActivity mDelegate = null;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener
            = null;

    public ListBean(int mItemType,
                    String mImageUrl,
                    String mText,
                    int mPic,
                    String mValue,
                    int mid,
                    BaseActivity mDelegate,
                    CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mItemType = mItemType;
        this.mImageUrl = mImageUrl;
        this.mPic = mPic;
        this.mText = mText;
        this.mValue = mValue;
        this.mid = mid;
        this.mDelegate = mDelegate;
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public int getPic() {
        return mPic;
    }

    public String getText() {
        if (mText == null) {
            return "";
        }
        return mText;
    }

    public String getValue() {
        if (mValue == null) {
            return "";
        }
        return mValue;
    }

    public int getId() {
        return mid;
    }

    public BaseActivity getDelegate() {
        return mDelegate;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }


    public static final class Builder {
        private int id = 0;
        private int itemType = 0;
        private int pic = 0;
        private String imageUrl = null;
        private String text = null;
        private String value = null;
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = null;
        private BaseActivity mateDelegate = null;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setPic(int pic) {
            this.pic = pic;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            this.onCheckedChangeListener = onCheckedChangeListener;
            return this;
        }

        public Builder setDelegate(BaseActivity mateDelegate) {
            this.mateDelegate = mateDelegate;
            return this;
        }

        public ListBean build() {
            return new ListBean(itemType, imageUrl, text, pic, value, id,
                    mateDelegate, onCheckedChangeListener);
        }
    }
}
