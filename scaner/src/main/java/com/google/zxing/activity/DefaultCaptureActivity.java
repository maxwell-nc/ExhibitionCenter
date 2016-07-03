package com.google.zxing.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.android.CaptureActivityHandler;
import com.google.zxing.camera.CameraManager;

import java.util.Collection;
import java.util.Map;

import pres.mc.maxwell.library.R;
import pres.mc.maxwell.library.config.ScanConfig;
import pres.mc.maxwell.library.ui.ScanLayout;

/**
 * 默认的摄像界面
 */
public class DefaultCaptureActivity extends AbsCaptureActivity implements SurfaceHolder.Callback {

    // 相机控制
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    private SurfaceView surfaceView;

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(setContentId());

        ScanLayout scanLayout = (ScanLayout) findViewById(setScanLayoutId());
        surfaceView = scanLayout.getSurfaceView();

        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        hasSurface = false;

        afterCreate(savedInstanceState);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager必须在这里初始化，而不是在onCreate()中。
        // 这是必须的，因为当我们第一次进入时需要显示帮助页，我们并不想打开Camera,测量屏幕大小
        // 当扫描框的尺寸不正确时会出现bug
        cameraManager = new CameraManager(getApplication());

        handler = null;

        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；
            // surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

        decodeFormats = null;
        characterSet = null;
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    /**
     * 扫描成功，处理反馈信息
     */
    public void handleDecode(Result rawResult) {

        if (ScanConfig.listener != null) {
            ScanConfig.listener.onGetResultContent(this, rawResult.getText());
        }

        onGetResult(rawResult.getText());
    }

    /**
     * 再次扫描
     * 扫描成功后会停止扫描，请调用此方法再次扫描
     */
    public void scanAgain() {
        if (handler != null) {
            Message message = Message.obtain(handler, R.id.restart_preview);
            message.sendToTarget();
        }
    }

    /**
     * 初始化Camera
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats,
                        decodeHints, characterSet, cameraManager);
            }
        } catch (Exception e) {
            onFailedToOpenCamera();
        }
    }

    @Override
    public void onFailedToOpenCamera() {
        //显示底层错误信息并退出应用
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("摄像头设备异常，请允许拍摄权限或重启设备！");
        builder.setPositiveButton("确定", new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    @Override
    public int setContentId() {
        return R.layout.activity_capture;
    }

    @Override
    public int setScanLayoutId() {
        return R.id.sv_scan;
    }

    @Override
    protected void onGetResult(String content) {

    }

    /**
     * 用于在少数情况下退出App的监听
     *
     * @author Sean Owen
     */
    private static final class FinishListener implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {

        private final Activity activityToFinish;

        public FinishListener(Activity activityToFinish) {
            this.activityToFinish = activityToFinish;
        }

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            run();
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            run();
        }

        private void run() {
            activityToFinish.finish();
        }

    }

}

