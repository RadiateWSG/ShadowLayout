package com.gigamole.shadowlayout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.gigamole.library.ShadowLayout;
import com.gigamole.library.shadowdelegate.AutoModel;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.gigamole.shadowlayout.DisplayUtil.dip2px;

public class MainActivity extends Activity {

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
        mCircleSL = (ShadowLayout) findViewById(R.id.sl_circle);
        mRoundSL = (ShadowLayout) findViewById(R.id.sl_round);
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
                    float zoomDy = dip2px(progress,MainActivity.this);
                    zoomDy -= dip2px(seekBar.getMax()/2,MainActivity.this);
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
                    float offsetDx = dip2px(progress,MainActivity.this);
                    offsetDx -= dip2px(seekBar.getMax()/2,MainActivity.this);
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
                    float offsetDy = dip2px(progress,MainActivity.this);
                    offsetDy -= dip2px(seekBar.getMax()/2,MainActivity.this);
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
                    float radius = dip2px(progress,MainActivity.this);
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
}
