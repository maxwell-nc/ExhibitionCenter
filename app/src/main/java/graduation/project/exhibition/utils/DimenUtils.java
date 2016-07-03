package graduation.project.exhibition.utils;

import android.content.Context;

/**
 * 屏幕密度工具类，提供sp、dp、px互转
 */
public class DimenUtils {

    /**
     * dp转px
     *
     * @param context
     *            上下文，用于获取屏幕密度
     * @param dpValue
     *            dp值
     * @return px值
     * @see #px2dp(Context, float)
     * @see #sp2px(Context, float)
     * @see #px2sp(Context, float)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dpValue * scale);
    }

    /**
     * px转dp
     *
     * @param context
     *            上下文，用于获取屏幕密度
     * @param pxValue
     *            px值
     * @return dp值
     * @see #dp2px(Context, float)
     * @see #sp2px(Context, float)
     * @see #px2sp(Context, float)
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(pxValue / scale);
    }

    /**
     * sp转px
     *
     * @param context
     *            上下文，用于获取屏幕密度
     * @param spValue
     *            sp值
     * @return px值
     * @see #dp2px(Context, float)
     * @see #px2dp(Context, float)
     * @see #px2sp(Context, float)
     */
    public static int sp2px(Context context, float spValue) {
        return dp2px(context, spValue);
    }

    /**
     * px转sp
     *
     * @param context
     *            上下文，用于获取屏幕密度
     * @param pxValue
     *            px值
     * @return sp值
     * @see #dp2px(Context, float)
     * @see #px2dp(Context, float)
     * @see #sp2px(Context, float)
     */
    public static int px2sp(Context context, float pxValue) {
        return px2dp(context, pxValue);
    }

}
