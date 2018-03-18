package app.mosn.zdepthshadowsample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.ytjojo.shadowlayout.ShadowLayout;
import com.ytjojo.shadowlayout.demo.R;
import com.ytjojo.shadowlayout.shadowdelegate.AutoModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
public class MainActivity extends Activity  {

    ShadowLayout mRoundSL;
    ShadowLayout mCircleSL;
    ImageView mImgRound;
    SeekBar progressBar;
    TextView mTvOffsetDx;
    TextView mTvZoomDy;
    TextView mTvOffsetDy;
    TextView mTvRadius;
    AutoModel mRoundAm;
    AutoModel mCirclAm;
    @BindView(R.id.ovalShapeSl)
    ShadowLayout mOvalShapeSl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(new LogcatLogStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCircleSL = (ShadowLayout) findViewById(R.id.sl_circle);
        mRoundSL = (ShadowLayout) findViewById(R.id.sl_round);
        int value= (int) DisplayUtil.dip2px(30,this);
        mRoundSL.setPadding(value,value,value,value);
        mCircleSL.setPadding(value,value,value,value);
        mCirclAm= (AutoModel) mCircleSL.getShadowDeltegate();
        mRoundAm= (AutoModel) mRoundSL.getShadowDeltegate();
        mImgRound = (ImageView) findViewById(R.id.img_round);
        mTvRadius = (TextView) findViewById(R.id.tv_radius);
        mTvOffsetDx = (TextView) findViewById(R.id.tv_offsetDx);
        mTvOffsetDy = (TextView) findViewById(R.id.tv_offsetDy);
        mTvZoomDy = (TextView) findViewById(R.id.tv_zoomdy);
        mImgRound.postDelayed(new Runnable() {
            @Override
            public void run() {
                mImgRound.requestLayout();
            }
        },2000);
        MultiTransformation multi = new MultiTransformation(
                new CenterCrop(),
                new RoundedCornersTransformation(25, 0, RoundedCornersTransformation.CornerType.ALL));
        Glide.with(this).load(R.mipmap.ngr_patient_back_followup_statistics_1).apply( bitmapTransform(multi)).into(mImgRound);
        progressBar = (SeekBar) findViewById(R.id.seekbar_zoomdy);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    float zoomDy = DisplayUtil.dip2px(progress,MainActivity.this);
                    zoomDy -= DisplayUtil.dip2px(seekBar.getMax()/2,MainActivity.this);
                    mCirclAm.setZoomDy(zoomDy);
                    mRoundAm.setZoomDy(zoomDy);
                    mTvZoomDy.setText("zoomdy value ="+zoomDy);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        progressBar = (SeekBar) findViewById(R.id.seekbar_dx);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    float offsetDx = DisplayUtil.dip2px(progress,MainActivity.this);
                    offsetDx -= DisplayUtil.dip2px(seekBar.getMax()/2,MainActivity.this);
                    mCirclAm.setOffsetDx(offsetDx);
                    mRoundAm.setOffsetDx(offsetDx);
                    mTvOffsetDx.setText("offsetDx value ="+offsetDx);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        progressBar = (SeekBar) findViewById(R.id.seekbar_dy);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    float offsetDy = DisplayUtil.dip2px(progress,MainActivity.this);
                    offsetDy -= DisplayUtil.dip2px(seekBar.getMax()/2,MainActivity.this);
                    mCirclAm.setOffsetDy(offsetDy);
                    mRoundAm.setOffsetDy(offsetDy);
                    mTvOffsetDy.setText("offsetDy value ="+offsetDy);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        progressBar = (SeekBar) findViewById(R.id.seekbar_radius);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    float radius = DisplayUtil.dip2px(progress,MainActivity.this);
                    mCirclAm.setRadius(radius);
                    mRoundAm.setRadius(radius);
                    mTvRadius.setText("radius value ="+radius);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Switch sw = (Switch) findViewById(R.id.switch_drawCenter);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCirclAm.setDrawCenter(isChecked);
                mRoundAm.setDrawCenter(isChecked);
            }
        });
    }
    @OnClick(R.id.tv_Simplezdepth)
    public void goSimpleZdepth(){
        Intent intent = new Intent(this, SimpleZDepthActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.tv_Changezdepth)
    public void goChangeZdepth(){
        Intent intent = new Intent(this, ChangeZDepthActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.sl_goSimpleAuto)
    public void goSimpleAuto(){
        Intent intent = new Intent(this, SimpleAutoActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.sl_goAngleActivity)
    public void goAngleAuto(){
        Intent intent = new Intent(this, SimpleAngleAutoActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.sl_round)
    public void roundSelectColor(){
        ColorPickerDialog dialog =ColorPickerDialog.newBuilder()
                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                .setAllowPresets(false)
                .setDialogId(1)
                .setColor(Color.BLACK)
                .setShowAlphaSlider(true)
                .create();
        dialog.show(getFragmentManager(),"color-picker-dialog");


            dialog.setColorPickerDialogListener(new ColorPickerDialogListener() {
                @Override
                public void onColorSelected(int dialogId, @ColorInt int color) {
                    if(dialogId ==1){
                        mRoundSL.setShadowColor(color);
                    }
                }

                @Override
                public void onDialogDismissed(int dialogId) {

                }
            });

    }
    @OnClick(R.id.sl_circle)
    public void circleSelectColor(){
        ColorPickerDialog dialog =ColorPickerDialog.newBuilder()
                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                .setAllowPresets(false)
                .setDialogId(0)
                .setColor(Color.BLACK)
                .setShowAlphaSlider(true)
                .create();
        dialog.show(getFragmentManager(),"color-picker-dialog");


        dialog.setColorPickerDialogListener(new ColorPickerDialogListener() {
            @Override
            public void onColorSelected(int dialogId, @ColorInt int color) {
                if(dialogId ==0){
                    mCircleSL.setShadowColor(color);
                }
            }

            @Override
            public void onDialogDismissed(int dialogId) {

            }
        });
    }
    @OnClick(R.id.ovalShapeSl)
    public void ovalShapeSlSelectColor(){
        ColorPickerDialog dialog =ColorPickerDialog.newBuilder()
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setAllowPresets(false)
                .setDialogId(2)
                .setColor(Color.BLACK)
                .setShowAlphaSlider(true)
                .create();
        dialog.show(getFragmentManager(),"color-picker-dialog");


        dialog.setColorPickerDialogListener(new ColorPickerDialogListener() {
            @Override
            public void onColorSelected(int dialogId, @ColorInt int color) {
                if(dialogId ==2){
                    mOvalShapeSl.setShadowColor(color);
                }
            }

            @Override
            public void onDialogDismissed(int dialogId) {

            }
        });
    }

    @OnClick(R.id.tv_pathModel)
    protected void goSimplePathShadowActivity() {
        Intent intent = new Intent(this,SimplePathShadowActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.tv_blurmaskfilterView)
    protected void goBlurMaskFilterActivity() {
        Intent intent = new Intent(this,BlurMaskFilterActivity.class);
        startActivity(intent);
    }
}
