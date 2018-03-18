package app.mosn.zdepthshadowsample;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/3/18 0018.
 */

public class BlurMaskFilterView extends View {
    public BlurMaskFilterView(Context context) {
        this(context,null);
    }

    public BlurMaskFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BlurMaskFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();


    }
    Paint mPaint ;
    TextPaint mTextPaint;
    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.YELLOW);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(40);

    }
    int mCircleRadus = 100;
    int mBlurRadus =50;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        int x = getWidth()/2;
        int y = mBlurRadus*2 + mCircleRadus + 80;
        canvas.translate(x,y);
        drawCicle(canvas, BlurMaskFilter.Blur.INNER);
        canvas.translate(0, mBlurRadus*2+ 2 * mCircleRadus +80);
        drawCicle(canvas, BlurMaskFilter.Blur.NORMAL);
        canvas.translate(0, mBlurRadus*2+2 *  mCircleRadus +80);
        drawCicle(canvas, BlurMaskFilter.Blur.SOLID);
        canvas.translate(0, mBlurRadus*2+ 2 *mCircleRadus + 80);
        drawCicle(canvas, BlurMaskFilter.Blur.OUTER);
        mPaint.setMaskFilter(new BlurMaskFilter(mBlurRadus, BlurMaskFilter.Blur.SOLID));
        canvas.translate(0, mBlurRadus + mCircleRadus );
        canvas.drawRect(-100,50,100,250,mPaint);
        canvas.restore();

    }
    private void drawCicle(Canvas canvas, BlurMaskFilter.Blur style){
        canvas.drawText(style.toString(),-60,-mCircleRadus - mBlurRadus-60,mTextPaint);
        mPaint.setMaskFilter(new BlurMaskFilter(mBlurRadus,style));
        canvas.drawCircle(0,0,mCircleRadus,mPaint);
    }
}
