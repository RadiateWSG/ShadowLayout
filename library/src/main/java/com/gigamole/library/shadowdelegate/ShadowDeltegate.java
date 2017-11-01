package com.gigamole.library.shadowdelegate;

import android.graphics.Canvas;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public interface ShadowDeltegate {

    void onLayout(boolean changed, int left, int top, int right, int bottom);
    void onAttachToWindow();
    void onDetachedFromWindow();
    void onDraw(Canvas canvas) ;
    void onClipCanvas(Canvas canvas);

}
