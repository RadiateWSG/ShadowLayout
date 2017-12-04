package com.ytjojo.shadowlayout.shadowdelegate;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.ytjojo.shadowlayout.R;
import com.ytjojo.shadowlayout.ShadowLayout;
import com.ytjojo.shadowlayout.ZDepth;
import com.ytjojo.shadowlayout.shadow.Shadow;
import com.ytjojo.shadowlayout.shadow.ShadowOval;
import com.ytjojo.shadowlayout.shadow.ShadowRect;
import com.ytjojo.shadowlayout.utils.ColorUtil;


public class ExactlyModel implements ShadowDelegate {

    protected static final String TAG = "ShadowView";


    protected static final int DEFAULT_ATTR_SHAPE = 0;
    protected static final int DEFAULT_ATTR_ZDEPTH = 1;
    protected static final int DEFAULT_ATTR_ZDEPTH_PADDING = 5;
    protected static final int DEFAULT_ATTR_ZDEPTH_ANIM_DURATION = 150;
    protected static final boolean DEFAULT_ATTR_ZDEPTH_DO_ANIMATION = true;
    private final static int DEFAULT_SHADOW_COLOR = 0xFF222222;

    protected static final int SHAPE_RECT = 0;
    protected static final int SHAPE_OVAL = 1;

    protected static final String ANIM_PROPERTY_ALPHA_TOP_SHADOW = "alphaTopShadow";
    protected static final String ANIM_PROPERTY_ALPHA_BOTTOM_SHADOW = "alphaBottomShadow";
    protected static final String ANIM_PROPERTY_OFFSET_TOP_SHADOW = "offsetTopShadow";
    protected static final String ANIM_PROPERTY_OFFSET_BOTTOM_SHADOW = "offsetBottomShadow";
    protected static final String ANIM_PROPERTY_BLUR_TOP_SHADOW = "blurTopShadow";
    protected static final String ANIM_PROPERTY_BLUR_BOTTOM_SHADOW = "blurBottomShadow";

    protected Shadow mShadow;
    protected ZDepth mZDepthParam;
    protected int mZDepthPaddingLeft;
    protected int mZDepthPaddingTop;
    protected int mZDepthPaddingRight;
    protected int mZDepthPaddingBottom;
    protected long mZDepthAnimDuration;
    protected boolean mZDepthDoAnimation;
    private ShadowLayout mParent;
    boolean mClipCanvas;
    private int mShadowColor = DEFAULT_SHADOW_COLOR;

    public ExactlyModel(ShadowLayout parent, TypedArray typedArray) {
        mParent = parent;
        mParent.setClipToPadding(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mParent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        init(typedArray);
    }

    protected void init(TypedArray typedArray) {

        int attrShape = typedArray.getInt(R.styleable.ShadowLayout_z_depth_shape, DEFAULT_ATTR_SHAPE);
        int attrZDepth = typedArray.getInt(R.styleable.ShadowLayout_z_depth, DEFAULT_ATTR_ZDEPTH);
        int attrZDepthAnimDuration = typedArray.getInt(R.styleable.ShadowLayout_z_depth_animDuration, DEFAULT_ATTR_ZDEPTH_ANIM_DURATION);
        boolean attrZDepthDoAnimation = typedArray.getBoolean(R.styleable.ShadowLayout_z_depth_doAnim, DEFAULT_ATTR_ZDEPTH_DO_ANIMATION);

        int attrZDepthPadding = typedArray.getInt(R.styleable.ShadowLayout_z_depth_padding, -1);
        int attrZDepthPaddingLeft = typedArray.getInt(R.styleable.ShadowLayout_z_depth_paddingLeft, -1);
        int attrZDepthPaddingTop = typedArray.getInt(R.styleable.ShadowLayout_z_depth_paddingTop, -1);
        int attrZDepthPaddingRight = typedArray.getInt(R.styleable.ShadowLayout_z_depth_paddingRight, -1);
        int attrZDepthPaddingBottom = typedArray.getInt(R.styleable.ShadowLayout_z_depth_paddingBottom, -1);
        mClipCanvas = typedArray.getBoolean(R.styleable.ShadowLayout_z_depth_clipcanvas, false);
        mShadowColor = typedArray.getColor(R.styleable.ShadowLayout_sl_shadow_color, DEFAULT_SHADOW_COLOR);
        if (attrZDepthPadding > -1) {
            attrZDepthPaddingLeft = attrZDepthPadding;
            attrZDepthPaddingTop = attrZDepthPadding;
            attrZDepthPaddingRight = attrZDepthPadding;
            attrZDepthPaddingBottom = attrZDepthPadding;
        } else {
            attrZDepthPaddingLeft = attrZDepthPaddingLeft > -1 ? attrZDepthPaddingLeft : DEFAULT_ATTR_ZDEPTH_PADDING;
            attrZDepthPaddingTop = attrZDepthPaddingTop > -1 ? attrZDepthPaddingTop : DEFAULT_ATTR_ZDEPTH_PADDING;
            attrZDepthPaddingRight = attrZDepthPaddingRight > -1 ? attrZDepthPaddingRight : DEFAULT_ATTR_ZDEPTH_PADDING;
            attrZDepthPaddingBottom = attrZDepthPaddingBottom > -1 ? attrZDepthPaddingBottom : DEFAULT_ATTR_ZDEPTH_PADDING;
        }
        int roundRectRadius = typedArray.getDimensionPixelOffset(R.styleable.ShadowLayout_sl_shadow_rectroundradius, 0);
        this.setShape(attrShape);
        if (attrShape == SHAPE_RECT) {
            ((ShadowRect) mShadow).setRoundRectRadius(roundRectRadius);
        }
        this.setZDepth(attrZDepth);
        this.setZDepthPaddingLeft(attrZDepthPaddingLeft);
        this.setZDepthPaddingTop(attrZDepthPaddingTop);
        this.setZDepthPaddingRight(attrZDepthPaddingRight);
        this.setZDepthPaddingBottom(attrZDepthPaddingBottom);
        this.setZDepthAnimDuration(attrZDepthAnimDuration);
        this.setZDepthDoAnimation(attrZDepthDoAnimation);
    }

    public Context getContext() {
        return mParent.getContext();
    }

    protected void setZDepthDoAnimation(boolean doAnimation) {
        mZDepthDoAnimation = doAnimation;
    }

    protected void setZDepthAnimDuration(long duration) {
        mZDepthAnimDuration = duration;
    }

    protected void setZDepthPaddingLeft(int zDepthPaddingLeftValue) {
        ZDepth zDepth = getZDepthWithAttributeValue(zDepthPaddingLeftValue);
        zDepth.initZDepth(getContext());
        mZDepthPaddingLeft = measureZDepthPadding(zDepth);
    }

    protected void setZDepthPaddingTop(int zDepthPaddingTopValue) {
        ZDepth zDepth = getZDepthWithAttributeValue(zDepthPaddingTopValue);
        zDepth.initZDepth(getContext());
        mZDepthPaddingTop = measureZDepthPadding(zDepth);
    }

    protected void setZDepthPaddingRight(int zDepthPaddingRightValue) {
        ZDepth zDepth = getZDepthWithAttributeValue(zDepthPaddingRightValue);
        zDepth.initZDepth(getContext());
        mZDepthPaddingRight = measureZDepthPadding(zDepth);
    }

    protected void setZDepthPaddingBottom(int zDepthPaddingBottomValue) {
        ZDepth zDepth = getZDepthWithAttributeValue(zDepthPaddingBottomValue);
        zDepth.initZDepth(getContext());
        mZDepthPaddingBottom = measureZDepthPadding(zDepth);
    }

    protected int measureZDepthPadding(ZDepth zDepth) {
        float maxAboveBlurRadius = zDepth.mBlurTopShadowPx;
        float maxAboveOffset = zDepth.mOffsetYTopShadowPx;
        float maxBelowBlurRadius = zDepth.mBlurBottomShadowPx;
        float maxBelowOffset = zDepth.mOffsetYBottomShadowPx;

        float maxAboveSize = maxAboveBlurRadius + maxAboveOffset;
        float maxBelowSize = maxBelowBlurRadius + maxBelowOffset;

        return (int) Math.max(maxAboveSize, maxBelowSize);
    }

    protected int getZDepthPaddingLeft() {
        return mZDepthPaddingLeft;
    }

    protected int getZDepthPaddingTop() {
        return mZDepthPaddingTop;
    }

    protected int getZDepthPaddingRight() {
        return mZDepthPaddingRight;
    }

    protected int getZDepthPaddingBottom() {
        return mZDepthPaddingBottom;
    }

    protected void setShape(int shape) {
        switch (shape) {
            case SHAPE_RECT:
                mShadow = new ShadowRect();
                break;

            case SHAPE_OVAL:
                mShadow = new ShadowOval();
                break;

            default:
                throw new IllegalArgumentException("unknown shape value.");
        }
    }

    protected void setZDepth(int zDepthValue) {
        ZDepth zDepth = getZDepthWithAttributeValue(zDepthValue);
        setZDepth(zDepth);
    }

    protected void setZDepth(ZDepth zDepth) {
        mZDepthParam = zDepth;
        mZDepthParam.initZDepth(getContext());
    }

    private ZDepth getZDepthWithAttributeValue(int zDepthValue) {
        switch (zDepthValue) {
            case 0:
                return ZDepth.Depth0;
            case 1:
                return ZDepth.Depth1;
            case 2:
                return ZDepth.Depth2;
            case 3:
                return ZDepth.Depth3;
            case 4:
                return ZDepth.Depth4;
            case 5:
                return ZDepth.Depth5;
            default:
                throw new IllegalArgumentException("unknown zDepth value.");
        }
    }

    Rect mShadowDrawingRect = new Rect();

    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        int parentWidth = (right - left);
//        int parentHeight = (bottom - top);
//        mShadowDrawingRect.set(mZDepthPaddingLeft,
//                mZDepthPaddingTop,
//                parentWidth - mZDepthPaddingRight,
//                parentHeight - mZDepthPaddingBottom);

        mShadowDrawingRect.setEmpty();
        if(mParent.getChildCount()>0){
            int childCount = mParent.getChildCount();
            for(int i=0;i<childCount ;i++){
                View child = mParent.getChildAt(i);
                if(i==0){
                    mShadowDrawingRect.set(child.getLeft(),child.getTop(),child.getRight(),child.getBottom());
                }else {
                    mShadowDrawingRect.union(child.getLeft(),child.getTop(),child.getRight(),child.getBottom());
                }
            }
        }
        setParameterToShadow();
        mShadow.onLayout(mParent, left, top, right, bottom);
    }

    public void onAttachToWindow() {

        int paddingLeft = this.getZDepthPaddingLeft();
        int paddingTop = this.getZDepthPaddingTop();
        int paddingRight = this.getZDepthPaddingRight();
        int paddingBottom = this.getZDepthPaddingBottom();
        mParent.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    @Override
    public void onDetachedFromWindow() {

    }


    public void onDraw(Canvas canvas) {
        mShadow.onDraw(canvas);
        mParent.superdispatchDraw(canvas);

    }

    @Override
    public void onDrawOver(Canvas canvas) {
        mShadow.onDrawOver(canvas);
    }

    @Override
    public boolean onClipCanvas(Canvas canvas, View child) {
        if (mClipCanvas) {
            boolean result = mShadow.onClipChildCanvas(canvas, child);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                child.invalidateOutline();
            }
            return result;
        }
        return false;

    }

    @Override
    public void invalidateShadow() {
        mParent.postInvalidate();
    }

    @Override
    public void setShadowColor(@ColorInt int color) {
        this.mShadowColor = color;
        setParameterToShadow();
        invalidateShadow();
    }

    private int getWidth() {
        return mParent.getMeasuredWidth();
    }

    private int getHeight() {
        return mParent.getMeasuredHeight();
    }

    public int getWidthExceptShadow() {
        return getWidth() - mParent.getPaddingLeft() - mParent.getPaddingRight();
    }

    public int getHeightExceptShadow() {
        return getHeight() - mParent.getPaddingTop() - mParent.getPaddingBottom();
    }

    private void setParameterToShadow() {

        int colorTop =  ColorUtil.adjustAlpha(mZDepthParam.mAlphaTopShadow, mShadowColor);
        int colorBottom =  ColorUtil.adjustAlpha(mZDepthParam.mAlphaBottomShadow,mShadowColor);
        mShadow.setParameter(colorTop, colorBottom, mZDepthParam.mOffsetYTopShadowPx, mZDepthParam.mOffsetYBottomShadowPx, mZDepthParam.mBlurTopShadowPx, mZDepthParam.mBlurBottomShadowPx, mShadowDrawingRect);
    }

    public void changeZDepth(ZDepth zDepth) {
        zDepth.initZDepth(getContext());
        if (!mZDepthDoAnimation) {
            mZDepthParam = zDepth;
            setParameterToShadow();
            mParent.invalidate();
            return;
        }

        PropertyValuesHolder alphaTopShadowHolder = PropertyValuesHolder.ofInt(ANIM_PROPERTY_ALPHA_TOP_SHADOW,
                mZDepthParam.mAlphaTopShadow,
                zDepth.mAlphaTopShadow);
        PropertyValuesHolder alphaBottomShadowHolder = PropertyValuesHolder.ofInt(ANIM_PROPERTY_ALPHA_BOTTOM_SHADOW,
                mZDepthParam.mAlphaBottomShadow,
                zDepth.mAlphaBottomShadow);
        PropertyValuesHolder offsetTopShadowHolder = PropertyValuesHolder.ofFloat(ANIM_PROPERTY_OFFSET_TOP_SHADOW,
                mZDepthParam.mOffsetYTopShadow,
                zDepth.mOffsetYTopShadow);
        PropertyValuesHolder offsetBottomShadowHolder = PropertyValuesHolder.ofFloat(ANIM_PROPERTY_OFFSET_BOTTOM_SHADOW,
                mZDepthParam.mOffsetYBottomShadow,
                zDepth.mOffsetYBottomShadow);
        PropertyValuesHolder blurTopShadowHolder = PropertyValuesHolder.ofFloat(ANIM_PROPERTY_BLUR_TOP_SHADOW,
                mZDepthParam.mBlurTopShadow,
                zDepth.mBlurTopShadow);
        PropertyValuesHolder blurBottomShadowHolder = PropertyValuesHolder.ofFloat(ANIM_PROPERTY_BLUR_BOTTOM_SHADOW,
                mZDepthParam.mBlurBottomShadow,
                zDepth.mBlurBottomShadow);
        mZDepthParam = zDepth;
        ValueAnimator anim = ValueAnimator
                .ofPropertyValuesHolder(
                        alphaTopShadowHolder,
                        alphaBottomShadowHolder,
                        offsetTopShadowHolder,
                        offsetBottomShadowHolder,
                        blurTopShadowHolder,
                        blurBottomShadowHolder);
        anim.setDuration(mZDepthAnimDuration);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int alphaTopShadow = (Integer) animation.getAnimatedValue(ANIM_PROPERTY_ALPHA_TOP_SHADOW);
                int alphaBottomShadow = (Integer) animation.getAnimatedValue(ANIM_PROPERTY_ALPHA_BOTTOM_SHADOW);
                float offsetTopShadow = (Float) animation.getAnimatedValue(ANIM_PROPERTY_OFFSET_TOP_SHADOW);
                float offsetBottomShadow = (Float) animation.getAnimatedValue(ANIM_PROPERTY_OFFSET_BOTTOM_SHADOW);
                float blurTopShadow = (Float) animation.getAnimatedValue(ANIM_PROPERTY_BLUR_TOP_SHADOW);
                float blurBottomShadow = (Float) animation.getAnimatedValue(ANIM_PROPERTY_BLUR_BOTTOM_SHADOW);


                int colorTop = ColorUtil.adjustAlpha(alphaTopShadow,mShadowColor);
                int colorBottom = ColorUtil.adjustAlpha(alphaBottomShadow,mShadowColor);

                mShadow.setParameter(colorTop, colorBottom, offsetTopShadow, offsetBottomShadow, blurTopShadow, blurBottomShadow, mShadowDrawingRect);

                mParent.invalidate();
            }
        });
        anim.start();
    }
}
