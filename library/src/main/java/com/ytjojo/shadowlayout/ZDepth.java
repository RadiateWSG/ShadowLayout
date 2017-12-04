package com.ytjojo.shadowlayout;

import android.content.Context;
import android.graphics.Color;

import com.ytjojo.shadowlayout.utils.DisplayUtils;


public enum ZDepth {

    Depth0( // TODO
            0,
            0,
            0,
            0,
            0,
            0
    ),

    Depth1(
            30, // alpha to black
            61, // alpha to black
            1.0f, // dp
            1.0f, // dp
            1.5f, // dp
            1.0f  // dp
    ),
    Depth2(
            40,
            58,
            3.0f,
            3.0f,
            3.0f,
            3.0f
    ),
    Depth3(
            48,
            58,
            10.0f,
            6.0f,
            10.0f,
            3.0f
    ),
    Depth4(
            64,
            56,
            14.0f,
            10.0f,
            14.0f,
            5.0f
    ),
    Depth5(
            76,
            56,
            19.0f,
            15.0f,
            19.0f,
            6.0f
    );

    public int mAlphaTopShadow; // alpha to black
    public int mAlphaBottomShadow; // alpha to black

    public final float mOffsetYTopShadow; // dp
    public final float mOffsetYBottomShadow; // dp

    public final float mBlurTopShadow; // dp
    public final float mBlurBottomShadow; // dp

    private ZDepth(int alphaTopShadow, int alphaBottomShadow, float offsetYTopShadow, float offsetYBottomShadow, float blurTopShadow, float blurBottomShadow) {
        mAlphaTopShadow = alphaTopShadow;
        mAlphaBottomShadow = alphaBottomShadow;
        mOffsetYTopShadow = offsetYTopShadow;
        mOffsetYBottomShadow = offsetYBottomShadow;
        mBlurTopShadow = blurTopShadow;
        mBlurBottomShadow = blurBottomShadow;
    }

    public int getAlphaTopShadow() {
        return mAlphaTopShadow;
    }

    public int getAlphaBottomShadow() {
        return mAlphaBottomShadow;
    }

    public float getOffsetYTopShadowPx(Context context) {
        return DisplayUtils.convertDpToPx(context, mOffsetYTopShadow);
    }

    public float getOffsetYBottomShadowPx(Context context) {
        return DisplayUtils.convertDpToPx(context, mOffsetYBottomShadow);
    }

    public float getBlurTopShadowPx(Context context) {
        return DisplayUtils.convertDpToPx(context, mBlurTopShadow);
    }

    public float getBlurBottomShadowPx(Context context) {
        return DisplayUtils.convertDpToPx(context, mBlurBottomShadow);
    }



    public float mOffsetYTopShadowPx; // px
    public float mOffsetYBottomShadowPx; // px

    public float mBlurTopShadowPx; // px
    public float mBlurBottomShadowPx; // px

    public void initZDepth(Context context) {
        mOffsetYTopShadowPx = getOffsetYTopShadowPx(context);
        mOffsetYBottomShadowPx = getOffsetYBottomShadowPx(context);
        mBlurTopShadowPx =getBlurTopShadowPx(context);
        mBlurBottomShadowPx = getBlurBottomShadowPx(context);
    }

    public int getColorTopShadow() {
        return Color.argb(mAlphaTopShadow, 0, 0, 0);
    }

    public int getColorBottomShadow() {
        return Color.argb(mAlphaBottomShadow, 0, 0, 0);
    }
}
