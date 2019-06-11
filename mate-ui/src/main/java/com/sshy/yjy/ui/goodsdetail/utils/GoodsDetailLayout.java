package com.sshy.yjy.ui.goodsdetail.utils;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * create date：2019-04-23
 * create by：周正尧
 */
public class GoodsDetailLayout extends ViewGroup {

    //滑动敏感值
    private int mTouchSlop;
    private int mDownY;
    private int mMoveY;
    private int mLastMoveY;
    private Scroller mScroller;
    private int mCurrentIndex = UP;
    public static final int UP = 1;
    public static final int DOWN = 2;


    public GoodsDetailLayout(Context context) {
        this(context, null);
    }

    public GoodsDetailLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(getChildAt(0), widthMeasureSpec, heightMeasureSpec);
        measureChild(getChildAt(1), widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child1 = getChildAt(0);
        View child2 = getChildAt(1);

        child1.layout(0, 0, child1.getMeasuredWidth(), child1.getMeasuredHeight());
        child2.layout(0, child1.getMeasuredHeight(), child1.getMeasuredWidth(), child1.getMeasuredHeight() +
                child2.getMeasuredHeight());
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = y;
                mLastMoveY = mMoveY;
                //第二个参数,当direction>0时，判断是否可以上滑
                boolean b1 = ViewCompat.canScrollVertically(getChildAt(0), 1);
                //第二个参数,当direction<0时，判断是否可以下滑
                boolean b4 = ViewCompat.canScrollVertically(getChildAt(1), -1);
//
//                if ((Math.abs(mMoveY - mDownY)) > mTouchSlop) {
//                    return true;
//                }
                //dy>0表示下滑
                int dy = mMoveY - mDownY;
                if (mCurrentIndex == UP && !b1 && dy < 0) {
                    return true;
                }

                if (mCurrentIndex == DOWN && !b4 && dy > 0) {
                    return true;
                }

        }
        return false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int height = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int y = (int) event.getY();
                mMoveY = y;

                scrollBy(0, mLastMoveY - mMoveY);
                if (getScrollY() <= 0) {
                    scrollTo(0, 0);
                }
                if (getScrollY() >= getHeight()) {
                    scrollTo(0, getHeight());
                }
                height = mCurrentIndex == UP ? getHeight() / 3 : getHeight() - getHeight() / 3;
                if (getScrollY() < height) {
                    if (mOnViewChangeListener != null)
                        mOnViewChangeListener.onDownAnim(mCurrentIndex);
                }
                if (getScrollY() >= height) {
                    if (mOnViewChangeListener != null)
                        mOnViewChangeListener.onPullAnim(mCurrentIndex);
                }
                mLastMoveY = mMoveY;
                break;
            case MotionEvent.ACTION_UP:

                int dy = 0;
                height = mCurrentIndex == UP ? getHeight() / 3 : getHeight() - getHeight() / 3;

                if (getScrollY() < height) {
                    dy = 0 - getScrollY();
                    mCurrentIndex = UP;
                    if (mOnViewChangeListener != null)
                        mOnViewChangeListener.onUp();
                }
                if (getScrollY() >= height) {
                    dy = getHeight() - getScrollY();
                    mCurrentIndex = DOWN;

                }

                mScroller.startScroll(0, getScrollY(), 0, dy);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public interface onViewChangeListener {
        void onPullAnim(int index);

        void onDownAnim(int index);

        void onUp();

    }

    private onViewChangeListener mOnViewChangeListener;

    public void setOnViewChangeListener(onViewChangeListener onViewChangeListener) {
        mOnViewChangeListener = onViewChangeListener;
    }
}
