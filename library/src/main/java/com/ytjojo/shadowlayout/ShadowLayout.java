package com.ytjojo.shadowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ytjojo.shadowlayout.shadowdelegate.AutoModel;
import com.ytjojo.shadowlayout.shadowdelegate.ExactlyModel;
import com.ytjojo.shadowlayout.shadowdelegate.PathModel;
import com.ytjojo.shadowlayout.shadowdelegate.ShadowDelegate;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public class ShadowLayout extends FrameLayout {
    public static final int SHADOW_MODEL_AUTO = 0;
    public static final int SHADOW_MODEL_SHAP = 1;
    public static final int SHADOW_MODEL_PATH = 2;

    ShadowDelegate mShadowDeltegate;

    public ShadowLayout(final Context context) {
        this(context, null);
    }

    public ShadowLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        // Retrieve attributes from xml
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, com.ytjojo.shadowlayout.R.styleable.ShadowLayout);
        int model = typedArray.getInt(com.ytjojo.shadowlayout.R.styleable.ShadowLayout_sl_shadow_model, SHADOW_MODEL_AUTO);
        if (model == SHADOW_MODEL_AUTO) {
            mShadowDeltegate = new AutoModel(this, typedArray);
        } else if (model == SHADOW_MODEL_SHAP) {
            mShadowDeltegate = new ExactlyModel(this, typedArray);
        }else if(model == SHADOW_MODEL_PATH){
            mShadowDeltegate = new PathModel(this,typedArray);
        }
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mShadowDeltegate.onLayout(changed, left, top, right, bottom);
        mShadowDeltegate.invalidateShadow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mShadowDeltegate.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mShadowDeltegate.onAttachToWindow();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mShadowDeltegate.onDraw(canvas);
        mShadowDeltegate.onDrawOver(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        try {
            canvas.save();
            return mShadowDeltegate.onClipCanvas(canvas,child)&super.drawChild(canvas, child, drawingTime);
        }finally {
            canvas.restore();
        }
    }
    public void setShadowColor(@ColorInt int color) {
        mShadowDeltegate.setShadowColor(color);
    }
    public void superdispatchDraw(Canvas canvas){
        super.dispatchDraw(canvas);
    }
    public ShadowDelegate getShadowDeltegate(){
        return mShadowDeltegate;
    }
}
