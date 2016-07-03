package graduation.project.exhibition.activity.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import graduation.project.exhibition.R;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.PhoneInfoUtils;

/**
 * 基本的Activity
 */
public abstract class BaseActivity extends Activity {

    protected Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;
        beforeSetView();

        setView();

        initView();
        initData();
    }

    /**
     * 设置显示的内容
     */
    protected void setView() {
        setContentView(setViewId());
    }

    /**
     * 设置全屏显示
     */
    protected void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * 设置透明状态栏
     */
    @SuppressWarnings("deprecation")
    @TargetApi(19)
    protected void setTranslucentBar(int color) {
        setTranslucentBar(color, null);
    }

    /**
     * 设置透明状态栏,必须在setContentView后执行
     */
    @SuppressWarnings("deprecation")
    @TargetApi(19)
    protected void setTranslucentBar(int color, int resId) {

        if (resId > 0) {
            setTranslucentBar(color, findViewById(resId));
        }

    }

    /**
     * 设置透明状态栏
     */
    @SuppressWarnings("deprecation")
    @TargetApi(19)
    protected void setTranslucentBar(int color, View paddingView) {

        if (paddingView != null) {
            paddingView.setPadding(0, PhoneInfoUtils.getStatusBarHeight(this), 0, 0);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(color));
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    /**
     * 设置透明状态栏
     */
    @SuppressWarnings("deprecation")
    @TargetApi(19)
    protected void setTranslucentBar(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintDrawable(drawable);
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    /**
     * setContentView之前执行的操作
     */
    protected void beforeSetView() {
    }

    /**
     * 设置布局id
     */
    protected abstract int setViewId();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentUtils.setActivityAnimation(this);
    }
}

