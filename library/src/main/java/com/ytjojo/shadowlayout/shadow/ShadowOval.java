package com.ytjojo.shadowlayout.shadow;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.view.ViewGroup;


public class ShadowOval implements Shadow {

    private ShapeDrawable mTopShadow;
    private ShapeDrawable mBottomShadow;

    private RectF mRectTopShadow;
    private RectF mRectBottomShadow;
    Path mPath;
    RectF mClipRect;
    public ShadowOval() {
        mRectTopShadow = new RectF();
        mRectBottomShadow = new RectF();
        mTopShadow = new ShapeDrawable(new OvalShape());
        mBottomShadow = new ShapeDrawable(new OvalShape());
        mPath = new Path();
        mClipRect = new RectF();
    }

    @Override
    public void setParameter(int colorTopShadow, int colorBottomShadow, float offsetTopShadow, float offsetBottomShadow,
                             float blurTopShadow,
                             float blurBottomShadow,
                             Rect rect) {
        mRectTopShadow.left   = rect.left;
        mRectTopShadow.top    = rect.top  + offsetTopShadow;
        mRectTopShadow.right  = rect.right;
        mRectTopShadow.bottom = rect.bottom + offsetTopShadow;

        mRectBottomShadow.left   = rect.left;
        mRectBottomShadow.top    = rect.top  + offsetBottomShadow;
        mRectBottomShadow.right  = rect.right;
        mRectBottomShadow.bottom = rect.bottom + offsetBottomShadow;

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
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawOval(mRectBottomShadow, mBottomShadow.getPaint());
        canvas.drawOval(mRectTopShadow, mTopShadow.getPaint());
    }

    @Override
    public void onDrawOver(Canvas canvas) {

    }

    @Override
    public boolean onClipChildCanvas(Canvas canvas,View child) {
        mPath.reset();
        int width = Math.min(child.getHeight(),child.getWidth());
        int x = child.getLeft()+child.getWidth()/2;
        int y = child.getTop()+child.getHeight()/2;
        mPath.addCircle(x,y,width/2,Path.Direction.CW);
        canvas.clipPath(mPath, Region.Op.REPLACE);
        return false;
    }

    @Override
    public void onLayout(View parent, int left, int top, int right, int bottom) {
        ViewGroup viewGroup = (ViewGroup) parent;

        mPath.addRoundRect(mClipRect,mClipRect.width(),mClipRect.width(),Path.Direction.CW);
    }
}
