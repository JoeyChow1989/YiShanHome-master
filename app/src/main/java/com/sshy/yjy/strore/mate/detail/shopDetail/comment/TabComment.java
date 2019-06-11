package com.sshy.yjy.strore.mate.detail.shopDetail.comment;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * create date：2019/3/29
 * create by：周正尧
 */
public class TabComment implements MultiItemEntity {

    private int mItemType = 0;
    private String id;
    private String cid;
    private String tid;
    private String tag;
    private String mid;

    public TabComment(int mItemType, String id, String cid, String tid, String tag, String mid) {
        this.mItemType = mItemType;
        this.id = id;
        this.cid = cid;
        this.tid = tid;
        this.tag = tag;
        this.mid = mid;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public String getId() {
        return id;
    }

    public String getCid() {
        return cid;
    }

    public String getTid() {
        return tid;
    }

    public String getTag() {
        return tag;
    }

    public String getMid() {
        return mid;
    }

    public static final class Builder {

        private int mItemType = 0;
        private String id;
        private String cid;
        private String tid;
        private String tag;
        private String mid;

        public Builder setmItemType(int mItemType) {
            this.mItemType = mItemType;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setCid(String cid) {
            this.cid = cid;
            return this;
        }

        public Builder setTid(String tid) {
            this.tid = tid;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder setMid(String mid) {
            this.mid = mid;
            return this;
        }

        public TabComment build() {
            return new TabComment(mItemType, id, cid, tid, tag, mid);
        }
    }
}
