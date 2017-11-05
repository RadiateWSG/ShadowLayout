package com.ytjojo.shadowlayout.shadow;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;


public class ShadowRect implements Shadow {

    private ShapeDrawable mTopShadow;
    private ShapeDrawable mBottomShadow;

    private Rect mRectTopShadow;
    private Rect mRectBottomShadow;
    Path mPath;
    int mRoundRectRadius;
    RectF mClipRect;

    public ShadowRect() {
        mRectTopShadow = new Rect();
        mRectBottomShadow = new Rect();
        mTopShadow = new ShapeDrawable(new RectShape());
        mBottomShadow = new ShapeDrawable(new RectShape());
        mPath = new Path();
        mClipRect = new RectF();
    }

    float blurTopShadow;

    @Override
    public void setParameter(int colorTopShadow, int colorBottomShadow, float offsetTopShadow, float offsetBottomShadow,
                             float blurTopShadow,
                             float blurBottomShadow,
                             Rect rect) {
        mRectTopShadow.left = rect.left;
        mRectTopShadow.top = (int) (rect.top + offsetTopShadow);
        mRectTopShadow.right = rect.right;
        mRectTopShadow.bottom = (int) (rect.bottom + offsetTopShadow / 2);
//        mRectTopShadow.bottom = (int) (rect.bottom );

        mRectBottomShadow.left = rect.left;
        mRectBottomShadow.top = (int) (rect.top + offsetBottomShadow);
        mRectBottomShadow.right = rect.right;
        mRectBottomShadow.bottom = (int) (rect.bottom + offsetBottomShadow / 2);


        mTopShadow.getPaint().setColor(colorTopShadow);
        if (0 < blurTopShadow) {
            mTopShadow.getPaint().setMaskFilter(new BlurMaskFilter(blurTopShadow, BlurMaskFilter.Blur.NORMAL));
        } else {
            mTopShadow.getPaint().setMaskFilter(null);
        }

        mBottomShadow.getPaint().setColor(colorBottomShadow);
        if (0 < blurBottomShadow) {
            mBottomShadow.getPaint().setMaskFilter(new BlurMaskFilter(blurBottomShadow, BlurMaskFilter.Blur.NORMAL));
        } else {
            mBottomShadow.getPaint().setMaskFilter(null);
        }
        this.blurTopShadow = blurTopShadow;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mRoundRectRadius <= 0) {
            canvas.drawRect(mRectBottomShadow, mBottomShadow.getPaint());
            canvas.drawRect(mRectTopShadow, mTopShadow.getPaint());
        }else {
            canvas.drawRoundRect(new RectF(mRectBottomShadow.left, mRectBottomShadow.top, mRectBottomShadow.right, mRectBottomShadow.bottom)
                    , mRoundRectRadius, mRoundRectRadius, mBottomShadow.getPaint());
            canvas.drawRoundRect(new RectF(mRectTopShadow.left, mRectTopShadow.top, mRectTopShadow.right, mRectTopShadow.bottom)
                    , mRoundRectRadius, mRoundRectRadius, mTopShadow.getPaint());
        }



    }

    @Override
    public void onDrawOver(Canvas canvas) {

    }

    @Override
    public boolean onClipChildCanvas(Canvas canvas, View child) {
        if (mRoundRectRadius <= 0) {
            return false;
        }
        mClipRect.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
        mPath.reset();
        mPath.addRoundRect(mClipRect, mRoundRectRadius, mRoundRectRadius, Path.Direction.CW);
        canvas.clipPath(mPath, Region.Op.REPLACE);
        return false;

    }

    @Override
    public void onLayout(View parent, int left, int top, int right, int bottom) {

    }

    public void setRoundRectRadius(int roundRectRadius) {
        mRoundRectRadius = roundRectRadius;
    }
}
