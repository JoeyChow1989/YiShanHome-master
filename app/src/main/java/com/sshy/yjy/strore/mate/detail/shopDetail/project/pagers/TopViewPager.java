package com.sshy.yjy.strore.mate.detail.shopDetail.project.pagers;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * create date：2018/9/6
 * create by：周正尧
 */
public class TopViewPager implements MultiItemEntity {

    private int itemType = 0;
    private String title = null;
    private String id = null;
    private String price = null;
    private String realprice = null;
    private String imageUrl = null;

    public TopViewPager(String id, int itemType, String title, String price, String realprice, String imageUrl) {
        this.id = id;
        this.itemType = itemType;
        this.title = title;
        this.price = price;
        this.realprice = realprice;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getRealprice() {
        return realprice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public static final class Builder {

        private int itemType = 0;
        private String id = null;
        private String title = null;
        private String price = null;
        private String realprice = null;
        private String imageUrl = null;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPrice(String price) {
            this.price = price;
            return this;
        }

        public Builder setRealprice(String realprice) {
            this.realprice = realprice;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public TopViewPager build() {
            return new TopViewPager(id, itemType, title, price, realprice, imageUrl);
        }
    }
}
