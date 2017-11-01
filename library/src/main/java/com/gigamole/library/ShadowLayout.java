package com.gigamole.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.gigamole.library.shadowdelegate.AutoModel;
import com.gigamole.library.shadowdelegate.ExactlyModel;
import com.gigamole.library.shadowdelegate.ShadowDeltegate;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public class ShadowLayout extends FrameLayout {
    public static final int SHADOW_MODEL_AUTO = 0;
    public static final int SHADOW_MODEL_SHAP = 1;
    public static final int SHADOW_MODEL_PATH = 2;

    ShadowDeltegate mShadowDeltegate;

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
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        int model = typedArray.getInt(R.styleable.ShadowLayout_sl_shadow_model, SHADOW_MODEL_AUTO);
        if (model == SHADOW_MODEL_AUTO) {
            mShadowDeltegate = new AutoModel(this, typedArray);
        } else if (model == SHADOW_MODEL_SHAP) {
            mShadowDeltegate = new ExactlyModel(this, typedArray);
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
        mShadowDeltegate.onClipCanvas(canvas);
        mShadowDeltegate.onDraw(canvas);
    }
    public void superdispatchDraw(Canvas canvas){
        super.dispatchDraw(canvas);
    }
    public ShadowDeltegate getShadowDeltegate(){
        return mShadowDeltegate;
    }
}
