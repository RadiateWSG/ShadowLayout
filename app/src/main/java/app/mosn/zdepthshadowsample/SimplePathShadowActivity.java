package app.mosn.zdepthshadowsample;

import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ytjojo.shadowlayout.ShadowLayout;
import com.ytjojo.shadowlayout.demo.R;
import com.ytjojo.shadowlayout.shadowdelegate.PathModel;

/**
 * Created by Administrator on 2017/11/4 0004.
 */

public class SimplePathShadowActivity extends AppCompatActivity {
    Path mCustomPath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simplepath);
//        ShadowLayout shadowLayout = (ShadowLayout) findViewById(R.id.ShadowLayout);
//        PathModel pathModel= (PathModel) shadowLayout.getShadowDeltegate();
//        pathModel.setPath(mCustomPath);


    }
}
