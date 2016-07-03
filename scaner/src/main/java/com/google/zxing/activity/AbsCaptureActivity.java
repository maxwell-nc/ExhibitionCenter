package com.google.zxing.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * 摄像界面接口
 */
public abstract class AbsCaptureActivity extends Activity{

    /**
     * 创建成功之后
     */
    public abstract void afterCreate(Bundle savedInstanceState);

    /**
     * 打开摄像头失败
     */
    public abstract void onFailedToOpenCamera();

    /**
     * 设置界面id
     */
    public abstract int setContentId();

    /**
     * 设置ScanLayout Id
     */
    public abstract int setScanLayoutId();

    /**
     * 设置结果监听器，执行在resultListener之后，可不设置
     */
    protected abstract void onGetResult(String content);
}
