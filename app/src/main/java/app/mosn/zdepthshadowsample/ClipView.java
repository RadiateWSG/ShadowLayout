package app.mosn.zdepthshadowsample;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/3/18 0018.
 */

public class ClipView extends View {

    public ClipView(Context context) {
        this(context,null);
    }

    public ClipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mPath = new Path();

    }
    int mCircleRadus = 100;
    int mBlurRadus =50;
    Path mPath;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPath.reset();
//        mPath.addRect(0,0,getWidth(),getHeight(), Path.Direction.CCW);
//        canvas.clipPath(mPath);
        mPath.reset();
        int x = 300;
        int y = 140;
        canvas.drawText( Region.Op.INTERSECT.toString(),300,50,mTextPaint);
        canvas.save();
        canvas.translate(x,y);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(-100,-100,200,300,mPaint);
        mPath.addCircle(0,0,80, Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.INTERSECT);
        mPaint.setColor(Color.RED);
        canvas.drawRect(-100,0,100,200,mPaint);
        canvas.restore();

        y+=460;
        canvas.drawText( Region.Op.DIFFERENCE.toString(),x,y,mTextPaint);


        canvas.save();
        canvas.translate(x,y);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(-100,-100,200,300,mPaint);
        canvas.clipPath(mPath, Region.Op.DIFFERENCE);
        mPaint.setColor(Color.RED);
        canvas.drawRect(-100,0,100,200,mPaint);
        canvas.restore();

        y+=460;
        canvas.drawText( Region.Op.REPLACE.toString(),x,y-80,mTextPaint);
        canvas.save();
        canvas.translate(x,y);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(-100,-100,200,300,mPaint);
        canvas.clipPath(mPath, Region.Op.REPLACE);
        mPaint.setColor(Color.RED);
        canvas.drawRect(-100,0,100,200,mPaint);

        canvas.restore();


        canvas.save();
        y+=460;
        canvas.drawText( Region.Op.REVERSE_DIFFERENCE.toString(),x,y-80,mTextPaint);
        canvas.translate(x,y);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(-100,-100,200,300,mPaint);
        canvas.clipPath(mPath, Region.Op.REVERSE_DIFFERENCE);
        mPaint.setColor(Color.RED);
        canvas.drawRect(-100,0,100,200,mPaint);
        canvas.restore();

        y+=460;
        canvas.drawText( Region.Op.UNION.toString(),x,y-80,mTextPaint);
        canvas.save();
        canvas.translate(x,y);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(-100,-100,200,300,mPaint);
        canvas.clipPath(mPath, Region.Op.UNION);
        mPaint.setColor(Color.RED);
        canvas.drawRect(-100,0,100,200,mPaint);

        canvas.restore();
        y+=460;
        canvas.drawText( Region.Op.XOR.toString(),x,y-80,mTextPaint);
        canvas.save();
        canvas.translate(x,y);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(-100,-100,200,300,mPaint);
        canvas.clipPath(mPath, Region.Op.XOR);
        mPaint.setColor(Color.RED);
        canvas.drawRect(-100,0,100,200,mPaint);

        canvas.restore();
        mPath.reset();
    }
    int x;
    int y;
    private void drawRect(Canvas canvas,Region.Op op){
        canvas.save();
        canvas.translate(x,y);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(-100,100,200,300,mPaint);
        canvas.clipPath(mPath, op);
        mPaint.setColor(Color.RED);
        canvas.drawRect(-100,0,100,200,mPaint);

        canvas.restore();
    }
}
