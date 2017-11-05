package com.ytjojo.shadowlayout.shadow;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;


public interface Shadow {
    public void setParameter(int colorTopShadow, int colorBottomShadow, float offsetTopShadow, float offsetBottomShadow,
                             float blurTopShadow,
                             float blurBottomShadow,
                             Rect rect);

    public void onDraw(Canvas canvas);
    public void onDrawOver(Canvas canvas);
    boolean onClipChildCanvas(Canvas canvas,View child);
    void onLayout(View parent,int left,int top,int right,int bottom);
}
