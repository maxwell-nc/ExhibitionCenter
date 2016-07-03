package pres.mc.maxwell.library.ui;

import android.os.Bundle;

import com.google.zxing.activity.DefaultCaptureActivity;

/**
 * 提供外部使用的自定义摄像界面
 */
public abstract class AbsCustomCaptureActivity extends DefaultCaptureActivity {

    @Override
    public abstract void afterCreate(Bundle savedInstanceState);

    @Override
    public abstract int setScanLayoutId();

    @Override
    public abstract int setContentId();

}
