package com.yqb.imitatemeipai.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Scroller;

import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.util.WindowUtil;

import java.io.IOException;

/**
 * Created by QJZ on 2017/8/3.
 */

public class VideoSwitchPager extends ViewGroup {

    static final String TAG = "VideoSwitchPager";

    private int mScreenHeight;
    private int mScreenWidth;

    private int mScrollYStart;
    private int mScrollYEnd;

    private int mLastY;
    private Scroller mScroller;

    private int position = 0;

    private SurfaceView videoPlayView;
    private ImageView playImage;
    private MediaPlayer player = new MediaPlayer();
    private SurfaceHolder holder;

    private Adapter mAdapter;

    public VideoSwitchPager(Context context) {
        super(context);
        initView();
    }

    public VideoSwitchPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VideoSwitchPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mScreenHeight = outMetrics.heightPixels - WindowUtil.getStatusBarHeight(getContext());
        mScreenWidth = outMetrics.widthPixels;

        mScroller = new Scroller(getContext());
    }

    public void configVideoPlayer(){
        videoPlayView = (SurfaceView) getChildAt(position).findViewById(R.id.sv_video_play);
        playImage = (ImageView) getChildAt(position).findViewById(R.id.iv_video_play);

        player.reset();
        try {
            player.setDataSource(getContext(), Uri.parse(mAdapter.getVideoUrl(position)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder=videoPlayView.getHolder();
        holder.addCallback(new MyCallBack());
        try {
            player.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        playImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()){
                    player.pause();
                    playImage.setImageResource(R.mipmap.ic_video_pause);
                }else{
                    player.start();
                    playImage.setImageResource(R.mipmap.ic_video_play);
                }
            }
        });
        Log.d(TAG, "config"+position);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mAdapter != null) {
            for (int i = 0; i < mAdapter.getCount(); i++) {
                addView(mAdapter.getView(i));
            }
            configVideoPlayer();
        }

        setMeasuredDimension(mScreenWidth, mScreenHeight);

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
/*        lp.height = mScreenHeight * 3;
        setLayoutParams(lp);

        for(int i=0 ; i < 3; i++){
            getChildAt(i).layout(l, i*mScreenHeight, r, (i+1)*mScreenHeight);
        }*/
        if(mAdapter != null){
            lp.height = mScreenHeight * mAdapter.getCount();
            setLayoutParams(lp);

            for(int i=0 ; i < mAdapter.getCount(); i++){
                getChildAt(i).layout(l, i*mScreenHeight, r, (i+1)*mScreenHeight);
            }
        }
        Log.d(TAG,"layout");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mScrollYStart = getScrollY();
                Log.d(TAG, "DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "MOVE");
                int dy = mLastY - y;
                scrollBy(0, dy);
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:

                mScrollYEnd = getScrollY();
                int dScrollY = mScrollYEnd - mScrollYStart;

                if (dScrollY > 0)// 往上滑动
                {
                    if (dScrollY > mScreenHeight / 2.0 && position != mAdapter.getCount()-1)
                    {
                        mScroller.startScroll(0, mScrollYEnd, 0, mScreenHeight - dScrollY);
                        position++;
                        configVideoPlayer();
                    } else
                    {
                        mScroller.startScroll(0, mScrollYEnd, 0, -dScrollY, 1000);
                    }
                }
                if (dScrollY < 0)// 往下滑动
                {
                    if (-dScrollY > mScreenHeight / 2.0 && position != 0)
                    {
                        mScroller.startScroll(0, mScrollYEnd, 0, -mScreenHeight - dScrollY, 1000);
                        position--;
                    } else
                    {
                        mScroller.startScroll(0, mScrollYEnd, 0, -dScrollY, 1000);
                    }
                }
                invalidate();
                break;
        }
        return true;
    }

    class MyCallBack implements SurfaceHolder.Callback{

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            player.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            player.pause();
        }
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public static abstract class Adapter{

        public abstract int getCount();
        public abstract View getView(int position);
        public abstract   String getVideoUrl(int position);

    }

    public Adapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(Adapter mAdapter) {
        this.mAdapter = mAdapter;
        requestLayout();
    }

}
