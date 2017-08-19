package com.yqb.imitatemeipai.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

import com.yqb.imitatemeipai.util.VideoPagerUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QJZ on 2017/8/13.
 */

public class VideoSwitchPager2 extends RecyclerView {

    private VideoPagerAdapter<?> mViewPagerAdapter;
    private List<OnPageChangedListener> mOnPageChangedListeners;

    private float mLastY;
    private float mTouchSpan;
    private float minSlideDistance;
    private float mFlingFactor = 0.15f;
    private float mTriggerOffset = 0.25f;
    private float mMillisecondsPerInch = 25f;

    private int mFirstTopWhenDragging;
    private int mFisrtLeftWhenDragging;

    private int mPositionBeforeScroll = -1;
    private int mPositionOnTouchDown = -1;
    private int mSmoothScrollTargetPosition = -1;

    //当前视图
    private View mCurrentView;
    //拖拽屏幕起始点
    private PointF mTouchStartPoint;

    private boolean mNeedAdjust;
    private boolean mSinglePageFling;

    //当是线性布局的时候，设置是否需要反向布局，目前不用
    private boolean mReverseLayout = false;
    private boolean mHasCalledOnPageChanged = true;

    private int mMaxLeftWhenDragging = Integer.MIN_VALUE;
    private int mMinLeftWhenDragging = Integer.MAX_VALUE;
    private int mMaxTopWhenDragging = Integer.MIN_VALUE;
    private int mMinTopWhenDragging = Integer.MAX_VALUE;

    public VideoSwitchPager2(Context context) {
        this(context, null);
    }

    public VideoSwitchPager2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoSwitchPager2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setNestedScrollingEnabled(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        minSlideDistance = viewConfiguration.getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN && getLayoutManager() != null) {
            //得到数字方向上的将要居中显示View的位置
            mPositionOnTouchDown = VideoPagerUtils.getCenterYChildPosition(this);
            mLastY = ev.getRawY();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //做测试
  /*      if (e.getAction() == MotionEvent.ACTION_MOVE) {
            if (mCurrentView != null) {
                mMaxLeftWhenDragging = Math.max(mCurrentView.getLeft(), mMaxLeftWhenDragging);
                mMaxTopWhenDragging = Math.max(mCurrentView.getTop(), mMaxTopWhenDragging);
                mMinLeftWhenDragging = Math.min(mCurrentView.getLeft(), mMinLeftWhenDragging);
                mMinTopWhenDragging = Math.min(mCurrentView.getTop(), mMinTopWhenDragging);
            }
        }*/
        return super.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        final float x = e.getRawX();
        final float y = e.getRawY();

        if (mTouchStartPoint == null){
            mTouchStartPoint = new PointF();
        }

        switch (MotionEvent.ACTION_MASK & e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartPoint.set(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                float tempDistance = (float) Math.sqrt(x * x + y * y);
                float lastDistance = (float) Math.sqrt(mTouchStartPoint.x * mTouchStartPoint.x + mTouchStartPoint.y * mTouchStartPoint.y);

                if (Math.abs(lastDistance - tempDistance) > minSlideDistance) {
                    float k = Math.abs((mTouchStartPoint.y - y) / (mTouchStartPoint.x - x));

                    if (Math.abs(mTouchStartPoint.y - y) < 1)
                        return getLayoutManager().canScrollHorizontally();

                    if (Math.abs(mTouchStartPoint.x - x) < 1)
                        return !getLayoutManager().canScrollHorizontally();
                    return k < Math.tan(Math.toRadians(30F));
                }
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);

        if (layout instanceof LinearLayoutManager) {
            mReverseLayout = ((LinearLayoutManager) layout).getReverseLayout();
        }
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        boolean flinging = super.fling((int) (velocityX * mFlingFactor), (int) (velocityY * mFlingFactor));
        if (flinging) {
            adjustPositionY(velocityY);
        }
        return flinging;
    }

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);

        mPositionBeforeScroll = getCurrentPosition();
        mSmoothScrollTargetPosition = position;

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                if (mSmoothScrollTargetPosition >= 0 && mSmoothScrollTargetPosition < getItemCount()) {
                    //进行翻页
                    if (mOnPageChangedListeners != null) {
                        for (OnPageChangedListener onPageChangedListener : mOnPageChangedListeners) {
                            if (onPageChangedListener != null) {
                                onPageChangedListener.OnPageChanged(getChildAt(getCurrentPosition()), mPositionBeforeScroll, getCurrentPosition());
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void smoothScrollToPosition(int position) {
        if (mPositionBeforeScroll < 0) {
            mPositionBeforeScroll = getCurrentPosition();
        }
        mSmoothScrollTargetPosition = position;
        if (getLayoutManager() != null && getLayoutManager() instanceof LinearLayoutManager) {
            LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(getContext()) {
                @Override
                public PointF computeScrollVectorForPosition(int targetPosition) {
                    if (getLayoutManager() == null) {
                        return null;
                    }
                    return ((LinearLayoutManager) getLayoutManager()).computeScrollVectorForPosition(targetPosition);
                }

                @Override
                protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
                    if (getLayoutManager() == null) {
                        return;
                    }

                    int dx = calculateDxToMakeVisible(targetView, getHorizontalSnapPreference());
                    int dy = calculateDyToMakeVisible(targetView, getVerticalSnapPreference());

                    if (dx > 0) {
                        dx = dx - getLayoutManager().getLeftDecorationWidth(targetView);
                    } else {
                        dx = dx + getLayoutManager().getRightDecorationWidth(targetView);
                    }
                    if (dy > 0) {
                        dy = dy - getLayoutManager().getTopDecorationHeight(targetView);
                    } else {
                        dy = dy + getLayoutManager().getBottomDecorationHeight(targetView);
                    }

                    final int distance = (int) Math.sqrt(dx * dx + dy * dy);
                    final int time = calculateTimeForDeceleration(distance);

                    if (time > 0) {
                        action.update(-dx, -dy, time, mDecelerateInterpolator);
                    }
                }
                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return mMillisecondsPerInch / displayMetrics.densityDpi;
                }
                @Override
                protected void onStop() {
                    super.onStop();
                    if (mOnPageChangedListeners != null) {
                        for (OnPageChangedListener onPageChangedListener : mOnPageChangedListeners) {
                            if (onPageChangedListener != null) {
                                onPageChangedListener.OnPageChanged(getChildAt(mSmoothScrollTargetPosition), mPositionBeforeScroll, mSmoothScrollTargetPosition);
                            }
                        }
                    }
                    mHasCalledOnPageChanged = true;
                }
            };

            linearSmoothScroller.setTargetPosition(position);
            if (position == RecyclerView.NO_POSITION) {
                return;
            }
            getLayoutManager().startSmoothScroll(linearSmoothScroller);
        } else {
            super.smoothScrollToPosition(position);
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == SCROLL_STATE_DRAGGING) {
            mNeedAdjust = true;
            mCurrentView = getLayoutManager().canScrollHorizontally() ? VideoPagerUtils.getCenterXChild(this) :
                    VideoPagerUtils.getCenterYChild(this);
            if (mCurrentView != null) {
                if (mHasCalledOnPageChanged) {
                    // While rvp is scrolling, mPositionBeforeScroll will be previous value.
                    mPositionBeforeScroll = getChildLayoutPosition(mCurrentView);
                    mHasCalledOnPageChanged = false;
                }
                mFisrtLeftWhenDragging = mCurrentView.getLeft();
                mFirstTopWhenDragging = mCurrentView.getTop();
            } else {
                mPositionBeforeScroll = -1;
            }
            mTouchSpan = 0;
        } else if (state == SCROLL_STATE_SETTLING) {
            mNeedAdjust = false;
            if (mCurrentView != null) {
                if (getLayoutManager().canScrollHorizontally()) {
                    mTouchSpan = mCurrentView.getLeft() - mFisrtLeftWhenDragging;
                } else {
                    mTouchSpan = mCurrentView.getTop() - mFirstTopWhenDragging;
                }
            } else {
                mTouchSpan = 0;
            }
            mCurrentView = null;
        } else if (state == SCROLL_STATE_IDLE) {
            if (mNeedAdjust) {
                int targetPosition = getLayoutManager().canScrollHorizontally() ? VideoPagerUtils.getCenterXChildPosition(this) :
                        VideoPagerUtils.getCenterYChildPosition(this);
                if (mCurrentView != null) {
                    targetPosition = getChildAdapterPosition(mCurrentView);
                    if (getLayoutManager().canScrollHorizontally()) {
                        int spanX = mCurrentView.getLeft() - mFisrtLeftWhenDragging;
                        // if user is tending to cancel paging action, don't perform position changing
                        if (spanX > mCurrentView.getWidth() * mTriggerOffset && mCurrentView.getLeft() >= mMaxLeftWhenDragging) {
                            if (!mReverseLayout) targetPosition--;
                            else targetPosition++;
                        } else if (spanX < mCurrentView.getWidth() * -mTriggerOffset && mCurrentView.getLeft() <= mMinLeftWhenDragging) {
                            if (!mReverseLayout) targetPosition++;
                            else targetPosition--;
                        }
                    } else {
                        int spanY = mCurrentView.getTop() - mFirstTopWhenDragging;
                        if (spanY > mCurrentView.getHeight() * mTriggerOffset && mCurrentView.getTop() >= mMaxTopWhenDragging) {
                            if (!mReverseLayout) targetPosition--;
                            else targetPosition++;
                        } else if (spanY < mCurrentView.getHeight() * -mTriggerOffset && mCurrentView.getTop() <= mMinTopWhenDragging) {
                            if (!mReverseLayout) targetPosition++;
                            else targetPosition--;
                        }
                    }
                }
                smoothScrollToPosition(safeTargetPosition(targetPosition, getItemCount()));
                mCurrentView = null;
            } else if (mSmoothScrollTargetPosition != mPositionBeforeScroll) {
                mPositionBeforeScroll = mSmoothScrollTargetPosition;
            }
            mMaxLeftWhenDragging = Integer.MIN_VALUE;
            mMinLeftWhenDragging = Integer.MAX_VALUE;
            mMaxTopWhenDragging = Integer.MIN_VALUE;
            mMinTopWhenDragging = Integer.MAX_VALUE;
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        try {
            Field fLayoutState = state.getClass().getDeclaredField("mLayoutState");
            fLayoutState.setAccessible(true);
            Object layoutState = fLayoutState.get(state);
            Field fAnchorOffset = layoutState.getClass().getDeclaredField("mAnchorOffset");
            Field fAnchorPosition = layoutState.getClass().getDeclaredField("mAnchorPosition");
            fAnchorPosition.setAccessible(true);
            fAnchorOffset.setAccessible(true);
            if (fAnchorOffset.getInt(layoutState) > 0) {
                fAnchorPosition.set(layoutState, fAnchorPosition.getInt(layoutState) - 1);
            } else if (fAnchorOffset.getInt(layoutState) < 0) {
                fAnchorPosition.set(layoutState, fAnchorPosition.getInt(layoutState) + 1);
            }
            fAnchorOffset.setInt(layoutState, 0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        super.onRestoreInstanceState(state);
    }

    private int getItemCount() {
        return mViewPagerAdapter == null ? 0 : mViewPagerAdapter.getItemCount();
    }

    public int getCurrentPosition() {
        int curPosition;
        if (getLayoutManager().canScrollHorizontally()) {
            curPosition = VideoPagerUtils.getCenterXChildPosition(this);
        } else {
            curPosition = VideoPagerUtils.getCenterYChildPosition(this);
        }
        if (curPosition < 0) {
            curPosition = mSmoothScrollTargetPosition;
        }
        return curPosition;
    }

    private int getFlingCount(int velocity, int cellSize) {
        if (velocity == 0) {
            return 0;
        }
        int sign = velocity > 0 ? 1 : -1;
        return (int) (sign * Math.ceil((velocity * sign * mFlingFactor / cellSize) - mTriggerOffset));
    }

    private int safeTargetPosition(int position, int count) {
        if (position < 0) {
            return 0;
        }
        if (position >= count) {
            return count - 1;
        }
        return position;
    }

    protected void adjustPositionX(int velocityX) {
        if (mReverseLayout) velocityX *= -1;

        int childCount = getChildCount();
        if (childCount > 0) {
            int curPosition = VideoPagerUtils.getCenterXChildPosition(this);
            int childWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            int flingCount = getFlingCount(velocityX, childWidth);
            int targetPosition = curPosition + flingCount;
            if (mSinglePageFling) {
                flingCount = Math.max(-1, Math.min(1, flingCount));
                targetPosition = flingCount == 0 ? curPosition : mPositionOnTouchDown + flingCount;
            }
            targetPosition = Math.max(targetPosition, 0);
            targetPosition = Math.min(targetPosition, getItemCount() - 1);
            if (targetPosition == curPosition
                    && (!mSinglePageFling || mPositionOnTouchDown == curPosition)) {
                View centerXChild = VideoPagerUtils.getCenterXChild(this);
                if (centerXChild != null) {
                    if (mTouchSpan > centerXChild.getWidth() * mTriggerOffset * mTriggerOffset && targetPosition != 0) {
                        if (!mReverseLayout) targetPosition--;
                        else targetPosition++;
                    } else if (mTouchSpan < centerXChild.getWidth() * -mTriggerOffset && targetPosition != getItemCount() - 1) {
                        if (!mReverseLayout) targetPosition++;
                        else targetPosition--;
                    }
                }
            }
            smoothScrollToPosition(safeTargetPosition(targetPosition, getItemCount()));
        }
    }

    protected void adjustPositionY(int velocityY) {
        if (mReverseLayout) velocityY *= -1;

        int childCount = getChildCount();
        if (childCount > 0) {
            int curPosition = VideoPagerUtils.getCenterYChildPosition(this);
            int childHeight = getHeight() - getPaddingTop() - getPaddingBottom();
            int flingCount = getFlingCount(velocityY, childHeight);
            int targetPosition = curPosition + flingCount;
            if (mSinglePageFling) {
                flingCount = Math.max(-1, Math.min(1, flingCount));
                targetPosition = flingCount == 0 ? curPosition : mPositionOnTouchDown + flingCount;
            }

            targetPosition = Math.max(targetPosition, 0);
            targetPosition = Math.min(targetPosition, getItemCount() - 1);
            if (targetPosition == curPosition
                    && (!mSinglePageFling || mPositionOnTouchDown == curPosition)) {
                View centerYChild = VideoPagerUtils.getCenterYChild(this);
                if (centerYChild != null) {
                    if (mTouchSpan > centerYChild.getHeight() * mTriggerOffset && targetPosition != 0) {
                        if (!mReverseLayout) targetPosition--;
                        else targetPosition++;
                    } else if (mTouchSpan < centerYChild.getHeight() * -mTriggerOffset && targetPosition != getItemCount() - 1) {
                        if (!mReverseLayout) targetPosition++;
                        else targetPosition--;
                    }
                }
            }
            smoothScrollToPosition(safeTargetPosition(targetPosition, getItemCount()));
        }
    }

    public interface OnPageChangedListener {
        void OnPageChanged(View view, int oldPosition, int newPosition);
    }

    public void addOnPageChangedListener(OnPageChangedListener listener) {
        if (mOnPageChangedListeners == null) {
            mOnPageChangedListeners = new ArrayList<>();
        }
        mOnPageChangedListeners.add(listener);
    }

    public void removeOnPageChangedListener(OnPageChangedListener listener) {
        if (mOnPageChangedListeners != null) {
            mOnPageChangedListeners.remove(listener);
        }
    }

    public void clearOnPageChangedListeners() {
        if (mOnPageChangedListeners != null) {
            mOnPageChangedListeners.clear();
        }
    }

    @Override
    public Adapter getAdapter() {
        if (mViewPagerAdapter != null) {
            return mViewPagerAdapter.mAdapter;
        }
        return null;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mViewPagerAdapter = ensureRecyclerViewPagerAdapter(adapter);
        super.setAdapter(mViewPagerAdapter);
    }

    public VideoPagerAdapter getWrapperAdapter() {
        return mViewPagerAdapter;
    }

    @NonNull
    protected VideoPagerAdapter ensureRecyclerViewPagerAdapter(Adapter adapter) {
        return (adapter instanceof VideoPagerAdapter) ? (VideoPagerAdapter) adapter : new VideoPagerAdapter(this, adapter);
    }

    @Override
    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        mViewPagerAdapter = ensureRecyclerViewPagerAdapter(adapter);
        super.swapAdapter(mViewPagerAdapter, removeAndRecycleExistingViews);
    }

    public float getlLastY() {
        return mLastY;
    }

    public void setFlingFactor(float flingFactor) {
        mFlingFactor = flingFactor;
    }

    public float getFlingFactor() {
        return mFlingFactor;
    }

    public void setTriggerOffset(float triggerOffset) {
        mTriggerOffset = triggerOffset;
    }

    public float getTriggerOffset() {
        return mTriggerOffset;
    }

    public void setSinglePageFling(boolean singlePageFling) {
        mSinglePageFling = singlePageFling;
    }

    public boolean isSinglePageFling() {
        return mSinglePageFling;
    }

}
