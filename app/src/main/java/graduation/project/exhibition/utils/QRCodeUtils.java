package graduation.project.exhibition.utils;

import android.app.Activity;

import graduation.project.exhibition.activity.home.ScanActivity;
import pres.mc.maxwell.library.QRCodeScaner;

/**
 * 二维码工具类
 */
public class QRCodeUtils {

    public static Activity activityContext;

    /**
     * 启动扫描二维码页面
     */
    public static void scan(Activity activityContext) {

        QRCodeUtils.activityContext = activityContext;

        QRCodeScaner.configBuilder()
                .setCaptureClass(ScanActivity.class)
                .buildScanAfterConfig(activityContext)
                .scan();
    }

}
