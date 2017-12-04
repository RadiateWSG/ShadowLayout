package app.mosn.zdepthshadowsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ytjojo.shadowlayout.ShadowLayout;
import com.ytjojo.shadowlayout.ZDepth;
import com.ytjojo.shadowlayout.shadowdelegate.ExactlyModel;
import com.ytjojo.shadowlayout.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeZDepthActivity extends AppCompatActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.zDepthShadowLayout_toolBar)
    ShadowLayout mZDepthShadowLayoutToolbar;

    @BindView(R.id.zDepthShadowLayout_rect)
    ShadowLayout mZDepthShadowLayoutRect;

    @BindView(R.id.zDepthShadowLayout_oval)
    ShadowLayout mZDepthShadowLayoutOval;
    ExactlyModel mExactlyModelToobar;
    ExactlyModel mExactlyModelLayoutRect;
    ExactlyModel mExactlyModelLayoutOval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_zdepth);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mExactlyModelLayoutOval = (ExactlyModel) mZDepthShadowLayoutOval.getShadowDeltegate();
        mExactlyModelLayoutRect = (ExactlyModel) mZDepthShadowLayoutToolbar.getShadowDeltegate();
        mExactlyModelToobar = (ExactlyModel) mZDepthShadowLayoutRect.getShadowDeltegate();
    }

    @OnClick(R.id.button_zDepth_0)
    protected void onClickZDepth0() {
        mExactlyModelLayoutRect.changeZDepth(ZDepth.Depth0);
        mExactlyModelLayoutOval.changeZDepth(ZDepth.Depth0);
        mExactlyModelToobar.changeZDepth(ZDepth.Depth0);
    }

    @OnClick(R.id.button_zDepth_1)
    protected void onClickZDepth1() {
        mExactlyModelLayoutRect.changeZDepth(ZDepth.Depth1);
        mExactlyModelLayoutOval.changeZDepth(ZDepth.Depth1);
        mExactlyModelToobar.changeZDepth(ZDepth.Depth1);
    }

    @OnClick(R.id.button_zDepth_2)
    protected void onClickZDepth2() {
        mExactlyModelLayoutRect.changeZDepth(ZDepth.Depth2);
        mExactlyModelLayoutOval.changeZDepth(ZDepth.Depth2);
        mExactlyModelToobar.changeZDepth(ZDepth.Depth2);
    }

    @OnClick(R.id.button_zDepth_3)
    protected void onClickZDepth3() {
        mExactlyModelLayoutRect.changeZDepth(ZDepth.Depth3);
        mExactlyModelLayoutOval.changeZDepth(ZDepth.Depth3);
        mExactlyModelToobar.changeZDepth(ZDepth.Depth3);
    }

    @OnClick(R.id.button_zDepth_4)
    protected void onClickZDepth4() {
        mExactlyModelLayoutRect.changeZDepth(ZDepth.Depth4);
        mExactlyModelLayoutOval.changeZDepth(ZDepth.Depth4);
        mExactlyModelToobar.changeZDepth(ZDepth.Depth4);
    }

    @OnClick(R.id.button_zDepth_5)
    protected void onClickZDepth5() {
        mExactlyModelLayoutRect.changeZDepth(ZDepth.Depth5);
        mExactlyModelLayoutOval.changeZDepth(ZDepth.Depth5);
        mExactlyModelToobar.changeZDepth(ZDepth.Depth5);
    }

}
