package com.yqb.imitatemeipai.widget;

/**
 * Created by QJZ on 2017/8/2.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;

import static android.graphics.Bitmap.Config.ARGB_8888;

public class CircleImageView extends ImageView {

    private int mRadius;
    private Paint mPaint;

    private int mHeight;
    private int mWidth;

    private Bitmap mBitmap;


    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        mRadius = mBitmap.getWidth() < mBitmap.getHeight() ? mBitmap.getWidth() / 2 : mBitmap.getHeight() / 2;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //如果为确定大小值，则圆的半径为宽度/2
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            mRadius = heightSize / 2;
            mWidth = widthSize;
            mHeight = heightSize;
        }

        //如果为wrap_content 那么View大小为圆的半径大小*2
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            mWidth = mRadius * 2;
            mHeight = mRadius * 2;
        }

        setMeasuredDimension(mHeight, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1.0f);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mRadius * 2, mRadius * 2, false);

        Bitmap drawBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), ARGB_8888);
        Canvas bitmapCanvas = new Canvas(drawBitmap);
        bitmapCanvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        bitmapCanvas.drawBitmap(mBitmap, 0, 0, mPaint);

        canvas.drawBitmap(drawBitmap, 0, 0, null);

    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = getBitmapFromDrawable(getDrawable());
        mRadius = mBitmap.getWidth() < mBitmap.getHeight() ? mBitmap.getWidth() / 2 : mBitmap.getHeight() / 2;

        requestLayout();
        postInvalidate();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (drawable instanceof BitmapDrawable) {
            mBitmap = getBitmapFromDrawable(getDrawable());
            mRadius = mBitmap.getWidth() < mBitmap.getHeight() ? mBitmap.getWidth() / 2 : mBitmap.getHeight() / 2;
        }

        requestLayout();
        postInvalidate();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
        mRadius = mBitmap.getWidth() < mBitmap.getHeight() ? mBitmap.getWidth() / 2 : mBitmap.getHeight() / 2;

        requestLayout();
        postInvalidate();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(2, 2, ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}