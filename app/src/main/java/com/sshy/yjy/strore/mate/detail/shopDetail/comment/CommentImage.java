package com.sshy.yjy.strore.mate.detail.shopDetail.comment;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * create date：2019/4/15
 * create by：周正尧
 */
public class CommentImage implements MultiItemEntity {

    private int itemType = 0;
    private String id = null;
    private String img = null;
    private String commentId = null;
    private String isDel = null;

    public CommentImage(int itemType, String id, String img, String commentId, String isDel) {
        this.itemType = itemType;
        this.id = id;
        this.img = img;
        this.commentId = commentId;
        this.isDel = isDel;
    }

    public String getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getIsDel() {
        return isDel;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public static final class Builder {

        private int itemType = 0;
        private String id = null;
        private String img = null;
        private String commentId = null;
        private String isDel = null;

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setImg(String img) {
            this.img = img;
            return this;
        }

        public Builder setCommentId(String commentId) {
            this.commentId = commentId;
            return this;
        }

        public Builder setIsDel(String isDel) {
            this.isDel = isDel;
            return this;
        }

        public CommentImage build() {
            return new CommentImage(itemType, id, img, commentId, isDel);
        }
    }
}
