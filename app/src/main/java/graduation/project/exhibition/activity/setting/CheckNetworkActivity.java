package graduation.project.exhibition.activity.setting;

import android.view.View;
import android.widget.TextView;

import com.fenjuly.library.ArrowDownloadButton;

import java.util.Timer;
import java.util.TimerTask;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BackActivity;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.NetworkStateUtils;
import graduation.project.exhibition.utils.SimpleTimerTask;
import graduation.project.exhibition.utils.ThreadQueue;

/**
 * 诊断网络状态Activity
 */
public class CheckNetworkActivity extends BackActivity implements View.OnClickListener {

    int count = 0;//已经完成进度
    int progress = 0;//显示的进度
    public Boolean pingResult;

    private ArrowDownloadButton adbCheck;
    private TextView btnCheck;

    private TextView currentText;
    private TextView wifiText;
    private TextView mobileText;
    private TextView stateText;
    private TextView testText;

    @Override
    protected int setViewId() {
        return R.layout.activity_check_network;
    }

    @Override
    protected String initBackBar() {
        return "诊断网络状态";
    }


    @Override
    protected void initView() {
        adbCheck = (ArrowDownloadButton) findViewById(R.id.adb_check);
        btnCheck = (TextView) findViewById(R.id.tv_check);

        currentText = (TextView) findViewById(R.id.tv_current);
        wifiText = (TextView) findViewById(R.id.tv_wifi);
        mobileText = (TextView) findViewById(R.id.tv_mobile);
        stateText = (TextView) findViewById(R.id.tv_state);
        testText = (TextView) findViewById(R.id.tv_test);
    }

    @Override
    protected void initData() {
        adbCheck.setOnClickListener(this);
        btnCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.adb_check:
            case R.id.tv_check:
                checkNetwork(v);
                break;
        }
    }

    /**
     * 初始化状态
     */
    private void initState() {
        pingResult = null;
        adbCheck.reset();
        progress = 0;
        count = 0;
    }

    /**
     * 设置网络
     */
    private void checkNetwork(View v) {

        final int deepGreen = MethodUtils.getColor(v, R.color.deep_green);
        final int deepRed = MethodUtils.getColor(v, R.color.deep_red);

        initState();

        adbCheck.startAnimating();

        SimpleTimerTask.newUITask(this, 800, 20, new SimpleTimerTask.Task() {
            @Override
            public void onSchedule(final Timer timer) {
                if (count > 0) {
                    progress = progress + 1;
                    count--;
                }
                adbCheck.setProgress(progress);
                if (adbCheck.getProgress() >= 100) {
                    timer.cancel();

                    //写在这里，看起来进度很精确
                    if (pingResult) {
                        testText.setText(MethodUtils.getColorText("通过",
                                deepGreen).insert(0, "连接测试："));
                    } else {
                        testText.setText(MethodUtils.getColorText("失败",
                                deepRed).insert(0, "连接测试："));
                    }
                }
            }
        });


        //判断网络状态
        count += 20;
        currentText.setText("当前网络：测试中...");
        int networkType = NetworkStateUtils.getNetworkType(this);
        switch (networkType) {
            case NetworkStateUtils.NET_TYPE_WIFI:
                currentText.setText(MethodUtils.getColorText("WIFI",
                        deepGreen).insert(0, "当前网络："));
                break;
            case NetworkStateUtils.NET_TYPE_WAP:
                currentText.setText(MethodUtils.getColorText("CMWAP",
                        deepGreen).insert(0, "当前网络："));
                break;
            case NetworkStateUtils.NET_TYPE_NET:
                currentText.setText(MethodUtils.getColorText("CMNET",
                        deepGreen).insert(0, "当前网络："));
                break;
            default:
                currentText.setText(MethodUtils.getColorText("无信号",
                        deepRed).insert(0, "当前网络："));
                break;
        }

        //判断wifi是否连接
        count += 20;
        wifiText.setText("WIFI网络：测试中...");
        if (NetworkStateUtils.isWifiConnected(this)) {
            wifiText.setText(MethodUtils.getColorText("已连接",
                    deepGreen).insert(0, "WIFI网络："));
        } else {
            wifiText.setText(MethodUtils.getColorText("未连接",
                    deepRed).insert(0, "WIFI网络："));
        }

        //判断移动网路是否连接
        count += 20;
        mobileText.setText("移动网络：测试中...");
        if (NetworkStateUtils.isMobileConnected(this)) {
            mobileText.setText(MethodUtils.getColorText("可用",
                    deepGreen).insert(0, "移动网络："));
        } else {
            mobileText.setText(MethodUtils.getColorText("不可用",
                    deepRed).insert(0, "移动网络："));
        }


        //检测网络是否可用
        count += 30;
        stateText.setText("网络连接状态：测试中...");
        if (NetworkStateUtils.isNetworkConnected(this)) {
            stateText.setText(MethodUtils.getColorText("已连接",
                    deepGreen).insert(0, "网络连接状态："));
        } else {
            stateText.setText(MethodUtils.getColorText("未连接",
                    deepRed).insert(0, "网络连接状态："));
        }

        //ping测试
        testText.setText("连接测试：测试中...");
        ThreadQueue.newQueue(this).onChildThread(new ThreadQueue.Task() {
            @Override
            public void run() {
                pingResult = NetworkStateUtils.pingIp("github.com");
            }
        }).onUiThread(new ThreadQueue.Task() {
            @Override
            public void run() {
                count += 10;
            }
        }).exec();


    }
}
