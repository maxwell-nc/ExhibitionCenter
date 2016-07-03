package graduation.project.exhibition.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

import java.util.UUID;

/**
 * 手机信息获取工具类
 */
public class PhoneInfoUtils {

    /**
     * 获得状态栏的高度，失败返回-1
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = -1;
        int resId = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(
                    resId);
        }
        return statusBarHeight;
    }

    /**
     * 获取屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * 获取屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 获取IMEI
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

}
