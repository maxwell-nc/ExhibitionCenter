package graduation.project.exhibition.business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;

import graduation.project.exhibition.AppApplication;
import graduation.project.exhibition.activity.setting.UpdateActivity;
import graduation.project.exhibition.domain.UpdateInfo;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.ui.ThemeConfirmDialog;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.ToastUtils;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 更新业务
 */
public class UpdateBusiness {

    /**
     * 安装Apk
     */
    public static void toInstall(String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppApplication.getAppContext().startActivity(intent);
    }

    public static void toUpdate(final Activity activity) {

        HttpRequest.jsonRequest("/checkUpdate", new JsonCallback<UpdateInfo>() {

            @Override
            public void onSuccess(Response<UpdateInfo> response) {
                super.onSuccess(response);

                final UpdateInfo responseData = response.getResponseData();

                if (responseData == null) {
                    ToastUtils.toast("检查更新失败！");
                    return;
                }

                int currentVer = getAppVersion();
                int updateVer = Integer.parseInt(responseData.getInfo().getVerCode());
                if (currentVer >= updateVer) {
                    ToastUtils.toast("当前版本已经是最新版本，无需更新！");
                } else {

                    String desc = MethodUtils.replaceLine(responseData.getInfo().getDesc());

                    ThemeConfirmDialog.newDialog(activity)
                            .title("检查到新版本").desc(desc)
                            .leftText("确认", new ThemeConfirmDialog.ButtonClickListener() {
                                @Override
                                public void onClick() {
                                    IntentUtils.newIntent(activity)
                                            .setActivityClass(UpdateActivity.class)
                                            .putExtra("updateInfo", responseData)
                                            .startActivity();
                                }
                            })
                            .rightText("取消", null)
                            .show();

                }
            }

            @Override
            public void onFailure(Response<UpdateInfo> response) {
                super.onFailure(response);
                ToastUtils.toast("检查更新失败！");
            }

        });

    }

    /**
     * 返回当前程序版本
     */
    public static int getAppVersion() {
        Context context = AppApplication.getAppContext();
        int versioncode = -1;

        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versioncode;
    }
}
