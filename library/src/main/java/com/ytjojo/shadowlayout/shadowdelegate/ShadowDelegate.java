package com.ytjojo.shadowlayout.shadowdelegate;

import android.graphics.Canvas;
import android.support.annotation.ColorInt;
import android.view.View;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public interface ShadowDelegate {

    void onLayout(boolean changed, int left, int top, int right, int bottom);
    void onAttachToWindow();
    void onDetachedFromWindow();
    void onDraw(Canvas canvas) ;
    void onDrawOver(Canvas canvas) ;
    boolean onClipCanvas(Canvas canvas,View child);
    void invalidateShadow();
    void setShadowColor(@ColorInt int color);

}
