package com.yqb.imitatemeipai.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Scroller;

import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.util.WindowUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QJZ on 2017/8/3.
 */

public class VideoSwitchPager extends ViewGroup {

    private int mScreenHeight;
    private int mScreenWidth;

    private int mScrollYStart;
    private int mScrollYEnd;

    private int mLastY;
    private Scroller mScroller;

    private List<ImageView> imageViewList = new ArrayList<>();

    public VideoSwitchPager(Context context) {
        super(context);
        initView(context);
    }

    public VideoSwitchPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VideoSwitchPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
        lp.height = mScreenHeight * imageViewList.size();
        setLayoutParams(lp);

       for(int i=0 ; i<imageViewList.size(); i++){
           getChildAt(i).layout(l, i*mScreenHeight, r, (i+1)*mScreenHeight);
       }
    }

    private void initView(Context context){

        ImageView temp1 = new ImageView(context);
        temp1.setBackgroundResource(R.drawable.p1);
        ImageView temp2 = new ImageView(context);
        temp2.setBackgroundResource(R.drawable.p2);
//        ImageView temp3 = new ImageView(context);
//        temp3.setBackgroundResource(R.drawable.p3);

        imageViewList.add(temp1);
        imageViewList.add(temp2);
//        imageViewList.add(temp3);

        addView(temp1);
        addView(temp2);
//        addView(temp3);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mScreenHeight = outMetrics.heightPixels - WindowUtil.getStatusBarHeight(context);
        mScreenWidth = outMetrics.widthPixels;

        mScroller = new Scroller(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mScrollYStart = getScrollY();
                Log.d("Move","DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d("Move","MOVE");
                int dy = mLastY - y;
                scrollBy(0, dy);
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:

                mScrollYEnd = getScrollY();
                int dScrollY = mScrollYEnd - mScrollYStart;

                if (mScrollYEnd > mScrollYStart)// 往上滑动
                {
                    if (mScrollYEnd - mScrollYStart > mScreenHeight / 2)
                    {
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);
                    } else
                    {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY, 1000);
                    }
                }
                if (mScrollYEnd < mScrollYStart)// 往下滑动
                {
                    if (-mScrollYEnd + mScrollYStart > mScreenHeight / 2)
                    {
                        mScroller.startScroll(0, getScrollY(), 0, -mScreenHeight - dScrollY,1000);
                    } else
                    {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY,1000);
                    }
                }
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
