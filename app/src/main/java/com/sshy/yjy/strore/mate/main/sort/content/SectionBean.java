package com.sshy.yjy.strore.mate.main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by 周正尧 on 2018/3/29 0029.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */
public class SectionBean extends SectionEntity<SectionContentItemEntity> {

    private boolean mIsMore = false;
    private int mId = -1;

    public boolean isIsMore() {
        return mIsMore;
    }

    public void setIsMore(boolean mIsMore) {
        this.mIsMore = mIsMore;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public SectionBean(SectionContentItemEntity sectionContentItemEntity) {
        super(sectionContentItemEntity);
    }

    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

}
