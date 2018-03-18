package app.mosn.zdepthshadowsample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/3/18 0018.
 */

public class BlurMaskFilterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ClipView(this));
    }
}
