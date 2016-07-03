package graduation.project.exhibition.activity.home;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import graduation.project.exhibition.R;
import graduation.project.exhibition.business.UpdateBusiness;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.PhoneInfoUtils;
import graduation.project.exhibition.utils.QRCodeUtils;
import graduation.project.exhibition.utils.ToastUtils;
import pres.mc.maxwell.library.ui.AbsCustomCaptureActivity;

/**
 * 扫描二维码界面
 */
public class ScanActivity extends AbsCustomCaptureActivity {

    public static final String IEC_COMPANY = "iec://company/";
    public static final String IEC_PRODUCT = "iec://product/";
    public static final String IEC_UPDATE = "iec://update/";

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        View toolbar = findViewById(R.id.ll_root);
        toolbar.setPadding(0, PhoneInfoUtils.getStatusBarHeight(this), 0, 0);//防止遮挡
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(MethodUtils.getColor(toolbar, R.color.main_theme));
            tintManager.setStatusBarTintEnabled(true);
        }

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public int setScanLayoutId() {
        return R.id.sv_scan;
    }

    @Override
    public int setContentId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void onGetResult(String content) {
        super.onGetResult(content);


        if (content.contains(IEC_COMPANY)) {//公司
            ToastUtils.toast("扫描到公司信息，马上跳转");
            String companyId = content.substring(IEC_COMPANY.length());
            IntentUtils.newIntent(this)
                    .setActivityClass(CompanyDetailActivity.class)
                    .putExtra("id", companyId)
                    .startActivity();
            finish();
        } else if (content.contains(IEC_PRODUCT)) {//展品
            ToastUtils.toast("扫描到展品信息，马上跳转");
            String productId = content.substring(IEC_PRODUCT.length());
            IntentUtils.newIntent(this)
                    .setActivityClass(ProductDetailActivity.class)
                    .putExtra("id", productId)
                    .startActivity();
            finish();
        } else if (content.contains(IEC_UPDATE)) {//更新
            ToastUtils.toast("更新应用，马上跳转");
            UpdateBusiness.toUpdate(QRCodeUtils.activityContext);
            QRCodeUtils.activityContext = null;
            finish();
        } else {
            ToastUtils.toast("不支持的二维码类型！");
            scanAgain();
        }


    }
}
