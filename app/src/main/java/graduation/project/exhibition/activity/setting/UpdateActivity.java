package graduation.project.exhibition.activity.setting;

import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.fenjuly.library.ArrowDownloadButton;

import java.io.File;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BackActivity;
import graduation.project.exhibition.business.UpdateBusiness;
import graduation.project.exhibition.config.ConfigConstant;
import graduation.project.exhibition.domain.UpdateInfo;
import graduation.project.exhibition.ui.ThemeConfirmDialog;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.ToastUtils;
import pres.nc.maxwell.asynchttp.HttpConnector;
import pres.nc.maxwell.asynchttp.callback.impl.FileCallback;
import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 更新应用界面
 */
public class UpdateActivity extends BackActivity {

    private ArrowDownloadButton mProgressBar;
    private UpdateInfo updateInfo;
    private View btnDown;
    private TextView updateText;
    private View.OnClickListener askInstallClickListener;
    private String apkPath;
    private View.OnClickListener updateClickListener;

    @Override
    protected String initBackBar() {
        return "更新应用";
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_update;
    }

    @Override
    protected void initView() {
        mProgressBar = (ArrowDownloadButton) findViewById(R.id.adb_check);
        btnDown = findViewById(R.id.tv_down);
        updateText = (TextView) findViewById(R.id.tv_update);
    }

    @Override
    protected void initData() {
        updateInfo = (UpdateInfo) getIntent().getSerializableExtra("updateInfo");
        updateText.setText(MethodUtils.replaceLine(updateInfo.getInfo().getDesc()));

        updateClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downApk();
            }
        };

        askInstallClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAskDown();
            }
        };

        mProgressBar.setOnClickListener(updateClickListener);
        btnDown.setOnClickListener(updateClickListener);
    }


    private void downApk() {

        apkPath = Environment.getExternalStorageDirectory() + "/exCenter_" + updateInfo.getInfo().getVerCode() + ".apk";

        HttpConnector.get(ConfigConstant.baseUrl + updateInfo.getInfo().getUrl()).log("update")
                .setConnectTimeout(10000).setReadTimeout(50000)
                .callback(new FileCallback(apkPath) {

                    public int fileSize = -1;

                    @Override
                    public void onPrepared(Request request) {
                        super.onPrepared(request);
                        mProgressBar.reset();
                        mProgressBar.startAnimating();

                        mProgressBar.setEnabled(false);
                        btnDown.setEnabled(false);
                    }

                    @Override
                    public void onAfter(Response<File> response) {
                        super.onAfter(response);

                        mProgressBar.setEnabled(true);
                        btnDown.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Response<File> response) {
                        super.onFailure(response);
                        mProgressBar.reset();
                    }

                    @Override
                    public void onDownProgress(final float progress, int fileSize) {

                        if (this.fileSize == -1 && fileSize > -1) {
                            this.fileSize = fileSize;
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(progress * 100f);
                            }
                        });
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        super.onSuccess(response);

                        long downSize = response.getResponseData().length();
                        if (downSize > -1 && downSize == fileSize) {
                            toAskDown();
                        } else {//下载错误
                            mProgressBar.reset();
                            ToastUtils.toast("下载错误，请重新尝试");
                        }

                    }
                }).load();

    }

    private void toAskDown() {
        ThemeConfirmDialog.newDialog(mActivity)
                .title("下载成功").desc("是否立即跳转安装Apk？")
                .leftText("确认", new ThemeConfirmDialog.ButtonClickListener() {
                    @Override
                    public void onClick() {
                        UpdateBusiness.toInstall(apkPath);
                    }
                })
                .rightText("取消", new ThemeConfirmDialog.ButtonClickListener() {
                    @Override
                    public void onClick() {
                        btnDown.setOnClickListener(askInstallClickListener);
                    }
                })
                .show();
    }
}
