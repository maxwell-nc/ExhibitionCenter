package graduation.project.exhibition.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

/**
 * 网络状态工具类
 */
public class NetworkStateUtils {

    private static final String TAG = "NetworkStateUtils";

    public static final int NET_TYPE_WIFI = 1;
    public static final int NET_TYPE_WAP = 2;
    public static final int NET_TYPE_NET = 3;

    /**
     * 判断是否有网络连接
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断WIFI网络是否可用
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (extraInfo != null && !extraInfo.isEmpty()) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NET_TYPE_NET;
                } else {
                    netType = NET_TYPE_WAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NET_TYPE_WIFI;
        }
        return netType;
    }

    /**
     * ping操作，5秒超时
     */
    public static final boolean pingIp(String ipStr) {
        try {
            String ipAddress = ipStr;
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 5 " + ipAddress);
            int status = p.waitFor();
            if (status == 0) {
                return true;
            } else {
                LogUtils.i(TAG, "Ping Failed: IP地址不可达！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
