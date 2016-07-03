package pres.mc.maxwell.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;

import com.google.zxing.activity.AbsCaptureActivity;
import com.google.zxing.activity.DefaultCaptureActivity;

import pres.mc.maxwell.library.config.ScanConfig;

/**
 * Zxing扫描工具类
 */
public class QRCodeScaner {

    private ScanBuilder mScanBuilder;
    private ConfigBuilder mConfigBuilder;

    private QRCodeScaner(ScanBuilder scanBuilder) {
        this.mScanBuilder = scanBuilder;
    }

    public QRCodeScaner(ConfigBuilder configBuilder) {
        this.mConfigBuilder = configBuilder;
    }

    /**
     * 获取一个扫描构建器
     */
    public static ScanBuilder scanBuilder(Activity activity) {
        return new ScanBuilder(activity);
    }

    /**
     * 获取一个配置构建器
     */
    public static ConfigBuilder configBuilder() {
        return new ConfigBuilder();
    }

    /**
     * 执行扫描
     */
    public void executeScan() {
        if (mScanBuilder == null) {
            return;
        }

        //不判断null可以覆盖之前的设置
        ScanConfig.listener = mScanBuilder.listener;

        Intent intent;
        if (mScanBuilder.useExistConfig && ScanConfig.captureClazz != null) {//使用配置
            intent = new Intent(mScanBuilder.context, ScanConfig.captureClazz);
        } else {
            intent = new Intent(mScanBuilder.context, DefaultCaptureActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);//防止多次启动
        mScanBuilder.context.startActivity(intent);
    }

    /**
     * 配置
     */
    public void executeConfig() {
        if (mConfigBuilder == null) {
            return;
        }

        //对焦时间
        if (mConfigBuilder.focusInterval > 0) {
            ScanConfig.autoFocusIntervalMs = mConfigBuilder.focusInterval;
        }

        //自定义摄像界面，不判断null可以覆盖之前的设置
        ScanConfig.captureClazz = mConfigBuilder.captureClazz;

        //实际扫描区域设置
        if (mConfigBuilder.rect != null) {
            ScanConfig.scanLeft = mConfigBuilder.rect.left;
            ScanConfig.scanTop = mConfigBuilder.rect.top;
            ScanConfig.scanWidth = mConfigBuilder.rect.width();
            ScanConfig.scanHeight = mConfigBuilder.rect.height();
        }

    }

    /**
     * 配置构建器
     */
    public static class ConfigBuilder {

        long focusInterval;
        Rect rect;
        Class<? extends AbsCaptureActivity> captureClazz;

        /**
         * 设置摄像头对焦间隔毫秒
         */
        public ConfigBuilder autoFocusInterval(long interval) {
            this.focusInterval = interval;
            return this;
        }

        public ConfigBuilder setCaptureClass(Class<? extends AbsCaptureActivity> captureClazz) {
            this.captureClazz = captureClazz;
            return this;
        }

        public ConfigBuilder scanArea(Rect rect) {
            this.rect = rect;
            return this;
        }

        public void config() {
            new QRCodeScaner(this).executeConfig();
        }

        public ScanBuilder buildScanAfterConfig(Activity activity) {
            config();
            return new ScanBuilder(activity).useExistConfig(true);
        }
    }

    /**
     * 扫描构建器
     */
    public static class ScanBuilder {

        Context context;
        onGetResultContentListener listener;
        private boolean useExistConfig = false;//默认采用false

        /**
         * 设置上下文
         */
        public ScanBuilder(Context context) {
            this.context = context;
        }

        /**
         * 结果监听器
         */
        public ScanBuilder resultListener(onGetResultContentListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 使用配置
         */
        public ScanBuilder useExistConfig(boolean useExistConfig) {
            this.useExistConfig = useExistConfig;
            return this;
        }

        /**
         * 开始扫描二维码
         */
        public void scan() {
            new QRCodeScaner(this).executeScan();
        }
    }

    /**
     * 获取结果监听器
     */
    public interface onGetResultContentListener {
        /**
         * 获取结果时的操作
         *
         * @param captureActivity 扫描的activity
         * @param result          二维码文本数据
         */
        void onGetResultContent(AbsCaptureActivity captureActivity, String result);
    }

    /**
     * 错误回调监听器
     */
    public interface onErrorListener {
        /**
         * 打开摄像头失败的时候
         */
        void onOpenCameraError();
    }
}
