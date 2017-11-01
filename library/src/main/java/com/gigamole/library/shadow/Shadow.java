package com.gigamole.library.shadow;

import android.graphics.Canvas;

import com.gigamole.library.ZDepthParam;


public interface Shadow {
    public void setParameter(ZDepthParam parameter, int left, int top, int right, int bottom);
    public void onDraw(Canvas canvas);
}
