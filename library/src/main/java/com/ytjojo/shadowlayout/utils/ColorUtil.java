package com.ytjojo.shadowlayout.utils;

import android.graphics.Color;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class ColorUtil {
    public final static int MAX_ALPHA = 255;
    public static int adjustAlpha(int shadowAlpha,int shadowColor) {
        return Color.argb(
                shadowAlpha,
                Color.red(shadowColor),
                Color.green(shadowColor),
                Color.blue(shadowColor)
        );
    }
}
