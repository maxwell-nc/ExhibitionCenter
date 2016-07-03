package pres.mc.maxwell.library.config;

import com.google.zxing.activity.AbsCaptureActivity;

import pres.mc.maxwell.library.QRCodeScaner;

/**
 * 存放扫描的配置
 */
public class ScanConfig {

    public static Class<? extends AbsCaptureActivity> captureClazz;
    public static QRCodeScaner.onGetResultContentListener listener;

    //实际扫描的区域，如果不设置幕宽度的居中正方形
    public static int scanLeft;
    public static int scanTop;
    public static int scanWidth;
    public static int scanHeight;

    //自动对焦毫秒
    public static long autoFocusIntervalMs = 1000L;

}
