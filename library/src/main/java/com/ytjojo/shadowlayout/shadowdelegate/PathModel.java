package com.ytjojo.shadowlayout.shadowdelegate;

import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.View;

import com.ytjojo.shadowlayout.R;
import com.ytjojo.shadowlayout.ShadowLayout;

/**
 * Created by Administrator on 2017/11/4 0004.
 */

public class PathModel implements ShadowDelegate {
    private final static float MIN_RADIUS = 0.1F;
    Path mPath;
    Path mClipPath;
    Path mShadowpath;
    Paint mPaint;
    ShadowLayout mParent;
    float mShadowRadius;
    private float mOffsetDy;
    private float mOffsetDx;

    private Point mControlPoint1;
    private Point mControlPoint2;
    private int mEndRightY;
    private int mStartLeftY;
    private float mCoordinatex1;
    private float mCoordinatey1;
    private float mCoordinatex2;
    private float mCoordinatey2;
    private float mRatgStartLeftY;
    private float mRateEndRightY;
    Rect mBoundsRect;

    public PathModel(ShadowLayout parent, TypedArray typedArray) {
        mParent = parent;
        mBoundsRect = new Rect();
        mParent.setWillNotDraw(false);
        mParent.setClipToPadding(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mParent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mPath = new Path();
        mClipPath = new Path();
        mShadowpath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        setShadowColor(
                typedArray.getColor(
                        R.styleable.ShadowLayout_sl_shadow_color, Color.BLACK
                )
        );

        setShadowRadius(
                typedArray.getDimension(
                        R.styleable.ShadowLayout_sl_shadow_radius, 25
                )
        );
        mOffsetDx = typedArray.getDimensionPixelSize(
                R.styleable.ShadowLayout_sl_shadow_offsetdx, 0
        );
        mOffsetDy = typedArray.getDimensionPixelSize(
                R.styleable.ShadowLayout_sl_shadow_offsetdy, 0
        );
        mCoordinatex1 = typedArray.getFloat(R.styleable.ShadowLayout_shadow_path_coordinatex1,0f);
        mCoordinatey1 = typedArray.getFloat(R.styleable.ShadowLayout_shadow_path_coordinatey1,1f);
        mCoordinatex2 = typedArray.getFloat(R.styleable.ShadowLayout_shadow_path_coordinatex2,1f);
        mCoordinatey2 = typedArray.getFloat(R.styleable.ShadowLayout_shadow_path_coordinatey2,1f);
        mRatgStartLeftY = typedArray.getFloat(R.styleable.ShadowLayout_shadow_path_startleft_y_rate,0.6f);
        mRateEndRightY = typedArray.getFloat(R.styleable.ShadowLayout_shadow_path_endright_y_rate,0.6f);
    }


    @Override
    public void onDraw(Canvas canvas) {
        mParent.superdispatchDraw(canvas);
    }

    @Override
    public void onDrawOver(Canvas canvas) {
        canvas.save();
//        if(Build.VERSION.SDK_INT>=19){
//            canvas.clipPath(mPath);
//        }else{
//            canvas.clipPath(mPath, Region.Op.REPLACE);
//            canvas.clipPath(mClipPath, Region.Op.DIFFERENCE);
//        }
        canvas.clipPath(mPath, Region.Op.REPLACE);
        canvas.clipPath(mClipPath, Region.Op.DIFFERENCE);
        mPaint.setColor(Color.BLACK);
        mShadowpath.set(mClipPath);
        mShadowpath.offset(mOffsetDx, mOffsetDy);
        canvas.drawPath(mShadowpath, mPaint);
        canvas.restore();

    }

    @Override
    public boolean onClipCanvas(Canvas canvas, View child) {

        canvas.clipPath(mClipPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            child.invalidateOutline();
        }
        return false;
    }

    @Override
    public void invalidateShadow() {
        mParent.postInvalidate();
    }

    @Override
    public void setShadowColor(@ColorInt int color) {
        mPaint.setColor(color);
        mParent.postInvalidate();
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mBoundsRect.set(left,top,right,bottom);
        mPath.reset();
        mShadowpath.reset();
        mPath.lineTo(left, top);
        mPath.lineTo(right, top);
        mPath.lineTo(right, bottom);
        mPath.lineTo(left, bottom);
        mPath.lineTo(left, top);
        if(!useCustom){
            mClipPath.reset();
            int width = right - left;
            int height = bottom - top;
            if(mControlPoint1==null){
                mControlPoint1 = new Point();
            }
            if(mControlPoint2==null){
                mControlPoint2 = new Point();
            }
            mControlPoint1.set((int)(mCoordinatex1*width+left),(int)(mCoordinatey1*height+top));
            mControlPoint2.set((int)(mCoordinatex2*width+left),(int)(mCoordinatey2*height+top));
            mStartLeftY = (int) (mRatgStartLeftY*height+top);
            mEndRightY = (int) (mRateEndRightY*height+top);

            mClipPath.moveTo(left, top);
            mClipPath.lineTo(left, mStartLeftY);
            mClipPath.cubicTo(mControlPoint1.x, mControlPoint1.y, mControlPoint2.x, mControlPoint2.y, right,mEndRightY);
            mClipPath.lineTo(right, top);
            mClipPath.lineTo(left, top);

//        if(Build.VERSION.SDK_INT>=19){
//            mPath.op(mClipPath, Path.Op.DIFFERENCE);
//        }
        }


    }
    public void changeClipPath(){
        mClipPath.reset();
        if(mBoundsRect.isEmpty()){
            return;
        }
        if(mControlPoint1==null){
            mControlPoint1 = new Point();
        }
        if(mControlPoint2==null){
            mControlPoint2 = new Point();
        }
        mControlPoint1.set((int)(mCoordinatex1*mBoundsRect.width()+mBoundsRect.left),(int)(mCoordinatey1*mBoundsRect.height()+mBoundsRect.top));
        mControlPoint2.set((int)(mCoordinatex2*mBoundsRect.width()+mBoundsRect.left),(int)(mCoordinatey2*mBoundsRect.height()+mBoundsRect.top));
        mStartLeftY = (int) (mRatgStartLeftY*mBoundsRect.height()+mBoundsRect.top);
        mEndRightY = (int) (mRateEndRightY*mBoundsRect.height()+mBoundsRect.top);

        mClipPath.moveTo(mBoundsRect.left, mBoundsRect.top);
        mClipPath.lineTo(mBoundsRect.left, mStartLeftY);
        mClipPath.cubicTo(mControlPoint1.x, mControlPoint1.y, mControlPoint2.x, mControlPoint2.y, mBoundsRect.right,mEndRightY);
        mClipPath.lineTo(mBoundsRect.right, mBoundsRect.top);
        mClipPath.lineTo(mBoundsRect.left, mBoundsRect.top);
        invalidateShadow();
    }

    @Override
    public void onAttachToWindow() {

    }

    @Override
    public void onDetachedFromWindow() {

    }

    public void setShadowRadius(final float shadowRadius) {
        mShadowRadius = Math.max(MIN_RADIUS, shadowRadius);

        if (mParent.isInEditMode()) return;
        // Set blur filter to paint
        mPaint.setMaskFilter(new BlurMaskFilter(mShadowRadius, BlurMaskFilter.Blur.NORMAL));
        invalidateShadow();
    }
    private boolean useCustom;
    public void setPath(Path path){
        useCustom = true;
        this.mClipPath = path;
        invalidateShadow();
    }
    public void setControlPoint1(float xRate,float yRate){
        this.mCoordinatex1 =xRate;
        this.mCoordinatey1 =yRate;
    }
    public void setControlPoint2(float xRate,float yRate){
        this.mCoordinatex2 =xRate;
        this.mCoordinatey2 =yRate;
    }

    public void setRatgStartLeftY(int ratgStartLeftY) {
        this.mRatgStartLeftY = ratgStartLeftY;
    }
    public void setRateEndRightY(int rateEndRightY) {
        this.mRateEndRightY = rateEndRightY;
    }
}
