package com.ytjojo.shadowlayout.shadowdelegate;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.annotation.FloatRange;
import android.view.View;

import com.ytjojo.shadowlayout.R;
import com.ytjojo.shadowlayout.ShadowLayout;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public class AutoModel implements ShadowDelegate {


    // Default shadow values
    private final static float DEFAULT_SHADOW_RADIUS = 30.0F;
    private final static float DEFAULT_SHADOW_DISTANCE = 15.0F;
    private final static float DEFAULT_SHADOW_ANGLE = 45.0F;
    private final static int DEFAULT_SHADOW_COLOR = Color.DKGRAY;

    // Shadow bounds values
    private final static int MAX_ALPHA = 255;
    private final static float MAX_ANGLE = 360.0F;
    private final static float MIN_RADIUS = 0.1F;
    private final static float MIN_ANGLE = 0.0F;
    // Shadow paint
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG) {
        {
            setDither(true);
            setFilterBitmap(true);
        }
    };
    // Shadow bitmap and canvas
    private Bitmap mBitmap;
    private final Canvas mCanvas = new Canvas();
    // View bounds
    private final Rect mBounds = new Rect();
    // Check whether need to redraw shadow
    private boolean mInvalidateShadow = true;

    // Detect if shadow is visible
    private boolean mIsShadowed;

    // Shadow variables
    private int mShadowColor;
    private int mShadowAlpha;
    private float mShadowRadius;
    private float mShadowDistance;
    private float mShadowAngle;
    private float mOffsetDy;
    private float mOffsetDx;
    private float mZoomDy;
    private boolean mDrawCenter = true;
    ShadowLayout mParent;

    public AutoModel(ShadowLayout parent,TypedArray typedArray) {
        mParent = parent;
        mParent.setWillNotDraw(false);
        mParent.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);

        setIsShadowed(typedArray.getBoolean(R.styleable.ShadowLayout_sl_shadowed, true));
        setShadowRadius(
                typedArray.getDimension(
                        R.styleable.ShadowLayout_sl_shadow_radius, DEFAULT_SHADOW_RADIUS
                )
        );
        mOffsetDx=typedArray.getDimensionPixelSize(
                R.styleable.ShadowLayout_sl_shadow_offsetdx, Integer.MAX_VALUE
        );
        mOffsetDy=typedArray.getDimensionPixelSize(
                R.styleable.ShadowLayout_sl_shadow_offsetdy, Integer.MAX_VALUE
        );
        mShadowDistance= typedArray.getDimension(
                R.styleable.ShadowLayout_sl_shadow_distance, 0
        );
        setShadowAngle(
                typedArray.getInteger(
                        R.styleable.ShadowLayout_sl_shadow_angle, (int) DEFAULT_SHADOW_ANGLE
                )
        );
        setShadowColor(
                typedArray.getColor(
                        R.styleable.ShadowLayout_sl_shadow_color, DEFAULT_SHADOW_COLOR
                )
        );

        mZoomDy = typedArray.getDimensionPixelSize( R.styleable.ShadowLayout_sl_shadow_zoomdy, 0);

        // Set padding for shadow bitmap
        final int padding = (int) (mShadowRadius+Math.max(mOffsetDx,mOffsetDy));
        mParent.setPadding(padding, padding, padding, padding);
    }

    @Override
    public void onDetachedFromWindow() {
        // Clear shadow bitmap
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

    public boolean isShadowed() {
        return mIsShadowed;
    }

    public void setIsShadowed(final boolean isShadowed) {
        mIsShadowed = isShadowed;
        if(mParent.isLayoutRequested() &&mParent.getParent()!=null){
            mParent.postInvalidate();
        }
    }

    public float getShadowDistance() {
        return mShadowDistance;
    }

    public void setShadowDistance(final float shadowDistance) {
        mShadowDistance = shadowDistance;
        resetShadow();
    }

    public float getShadowAngle() {
        return mShadowAngle;
    }

    public void setShadowAngle(@FloatRange(from = MIN_ANGLE, to = MAX_ANGLE) final float shadowAngle) {
        mShadowAngle = Math.max(MIN_ANGLE, Math.min(shadowAngle, MAX_ANGLE));
        resetShadow();
    }

    public float getShadowRadius() {
        return mShadowRadius;
    }

    public void setShadowRadius(final float shadowRadius) {
        mShadowRadius = Math.max(MIN_RADIUS, shadowRadius);

        if ( mParent.isInEditMode()) return;
        // Set blur filter to paint
        mPaint.setMaskFilter(new BlurMaskFilter(mShadowRadius, BlurMaskFilter.Blur.NORMAL));
        invalidateShadow();
    }

    public void setRadius(final float shadowRadius) {
        mShadowRadius = Math.max(MIN_RADIUS, shadowRadius);
        if ( mParent.isInEditMode()) return;
        // Set blur filter to paint
        mPaint.setMaskFilter(new BlurMaskFilter(mShadowRadius, BlurMaskFilter.Blur.NORMAL));
        mInvalidateShadow = true;
        mParent.postInvalidate();
    }

    public int getShadowColor() {
        return mShadowColor;
    }

    public void setShadowColor(final int shadowColor) {
        mShadowColor = shadowColor;
        mShadowAlpha = Color.alpha(shadowColor);
        invalidateShadow();
    }
    public void invalidateShadow(){
        mInvalidateShadow = true;
        mParent.postInvalidate();
    }

    public float getOffsetDx() {
        return mOffsetDx;
    }

    public float getOffsetDy() {
        return mOffsetDy;
    }

    // Reset shadow layer
    private void resetShadow() {
        // Detect shadow axis offset
        if(mShadowDistance >0){
            mOffsetDx = (float) ((mShadowDistance) * Math.cos(mShadowAngle / 180.0F * Math.PI));
            mOffsetDy = (float) ((mShadowDistance) * Math.sin(mShadowAngle / 180.0F * Math.PI));
        }

        mInvalidateShadow = true;
        mParent.postInvalidate();
    }

    private int adjustShadowAlpha(final boolean adjust) {
        return Color.argb(
                adjust ? MAX_ALPHA : mShadowAlpha,
                Color.red(mShadowColor),
                Color.green(mShadowColor),
                Color.blue(mShadowColor)
        );
    }

    public void onLayout(boolean changed, int left, int top, int right, int bottom) {

        mBounds.set(
                0, 0, mParent.getMeasuredWidth(),  mParent.getMeasuredHeight()
        );
    }

    @Override
    public void onAttachToWindow() {

    }


    @Override
    public void onDraw(final Canvas canvas) {
        // If is not shadowed, skip
        if (mIsShadowed) {
            // If need to redraw shadow
            if (mInvalidateShadow) {
                // If bounds is zero
                if (mBounds.width() != 0 && mBounds.height() != 0) {
                    // Reset bitmap to bounds
                    mBitmap = Bitmap.createBitmap(
                            mBounds.width(), mBounds.height(), Bitmap.Config.ARGB_8888
                    );
                    // Canvas reset
                    mCanvas.setBitmap(mBitmap);

                    // We just redraw
                    mInvalidateShadow = false;
                    // Main feature of this lib. We create the local copy of all content, so now
                    // we can draw bitmap as a bottom layer of natural canvas.
                    // We draw shadow like blur effect on bitmap, cause of setShadowLayer() method of
                    // paint does`t draw shadow, it draw another copy of bitmap
                    mParent.superdispatchDraw(mCanvas);

                    // Get the alpha bounds of bitmap
                    Bitmap extractedAlpha = mBitmap.extractAlpha();
                    // Clear past content content to draw shadow
                    mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);

                    // Draw extracted alpha bounds of our local canvas
                    mPaint.setColor(adjustShadowAlpha(false));
                    if(mZoomDy !=0f && mZoomDy != Integer.MAX_VALUE){
                        extractedAlpha = getScaleBitmap(extractedAlpha,mZoomDy);
                    }
                    if(mDrawCenter){
                        final int w=  extractedAlpha.getWidth();
                        final int h =  extractedAlpha.getHeight();
                        float l = (mCanvas.getWidth()-w)/2+(mOffsetDx==Integer.MAX_VALUE?0:mOffsetDx);
                        float t = (mCanvas.getHeight()-h)/2 +(mOffsetDy==Integer.MAX_VALUE?0:mOffsetDy);
                        mCanvas.drawBitmap(extractedAlpha, l, t, mPaint);
                    }else{
                        mCanvas.drawBitmap(extractedAlpha, mOffsetDx==Integer.MAX_VALUE?0:mOffsetDx, mOffsetDy==Integer.MAX_VALUE?0:mOffsetDy, mPaint);

                    }

                    // Recycle and clear extracted alpha
                    extractedAlpha.recycle();
                } else {
                    // Create placeholder bitmap when size is zero and wait until new size coming up
                    mBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
                }
            }

            // Reset alpha to draw child with full alpha
            mPaint.setColor(adjustShadowAlpha(true));
            // Draw shadow bitmap
            if (mCanvas != null && mBitmap != null && !mBitmap.isRecycled())
                canvas.drawBitmap(mBitmap, 0.0F, 0.0F, mPaint);
        }

        // Draw child`s
        mParent.superdispatchDraw(mCanvas);
    }

    @Override
    public void onDrawOver(Canvas canvas) {

    }

    @Override
    public boolean onClipCanvas(Canvas canvas,View child) {
        if(mClipPath !=null && !mClipPath.isEmpty()){
            canvas.clipPath(mClipPath);
        }
        return false;
    }
    Path mClipPath;
    public void setClipPath(Path clipPath){
        mClipPath = clipPath;
        invalidateShadow();
    }


    public void setZoomDy(float dy){
        mInvalidateShadow = true;
        mZoomDy = dy;
        mParent.postInvalidate();
    }
    public void setOffsetDx(float dx){
        mInvalidateShadow = true;
        mOffsetDx = dx;
        mParent.postInvalidate();
    }
    public void setOffsetDy(float dy){
        mInvalidateShadow = true;
        mOffsetDy = dy;
        mParent.postInvalidate();
    }

    public Bitmap getScaleBitmap(Bitmap mBitmap,float dy) {
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float h = height+dy;
        if(h<=1){
            h=1;
        }
        float scale = h/height;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap mScaleBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);
        return mScaleBitmap;
    }

    Runnable mRunnable= new Runnable() {
        @Override
        public void run() {
            mInvalidateShadow = true;
            mParent.postInvalidate();
        }
    };
    public void setDrawCenter(boolean drawCenter){
        this.mDrawCenter = drawCenter;
        mInvalidateShadow = true;
        mParent.postInvalidate();
    }

}
