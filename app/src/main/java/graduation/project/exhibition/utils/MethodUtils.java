package graduation.project.exhibition.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import graduation.project.exhibition.AppApplication;
import graduation.project.exhibition.R;
import graduation.project.exhibition.ui.ThemeConfirmDialog;

/**
 * 常用方法工具类
 */
public class MethodUtils {

    public final static Random random;

    static {
        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }

    /**
     * 弹出提示退出对话框
     */
    public static void exit(Context context) {

        ThemeConfirmDialog.newDialog(context)
                .title("确认退出").desc("是否确认退出？")
                .leftText("确认", new ThemeConfirmDialog.ButtonClickListener() {
                    @Override
                    public void onClick() {
                        System.exit(0);
                    }
                })
                .rightText("取消", null)
                .show();

    }

    /**
     * 显示快餐条
     */
    public static void snackBar(View view, String msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        //设置文字颜色
        ((TextView) snackbar.getView()
                .findViewById(R.id.snackbar_text))
                .setTextColor(getColor(view, R.color.white));
        //设置北京颜色
        snackbar.getView().setBackgroundColor(getColor(view, R.color.main_theme));
        snackbar.show();
    }

    /**
     * 获取颜色
     */
    public static int getColor(View view, @ColorRes int color) {
        return ContextCompat.getColor(view.getContext(), color);
    }

    /**
     * 获取TextView彩色文本
     */
    public static SpannableStringBuilder getColorText(String str, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);

        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);

        builder.setSpan(colorSpan, 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * 返回16进制的hashcode文本
     */
    public static String hashCodeString(Object object) {
        return Integer.toHexString(object.hashCode());
    }

    /**
     * Bitmap转Uri
     */
    public static Uri bitmap2Uri(Context context, @NonNull Bitmap bitmap) {
        return Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null));
    }

    /**
     * 取随机数
     */
    public static int randomInt(int maxInt) {
        return random.nextInt(maxInt);
    }

    /**
     * 复制文本到粘贴板
     */
    public static void copyTextToClipBoard(Context context, String text) {
        ClipboardManager clipManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipManager.setPrimaryClip(ClipData.newPlainText(text, text));
        ToastUtils.toast("复制成功");
    }

    /**
     * 替换数据库中的换行文本,并且缩进
     */
    public static String replaceLineAndAddSpace(String text) {
        return "\u3000\u3000" + text.replaceAll("\\\\n", "\n\u3000\u3000");
    }

    /**
     * 替换数据库中的换行文本
     */
    public static String replaceLine(String text) {
        return text.replaceAll("\\\\n", "\n");
    }

    /**
     * 获取设备码的hash值
     */
    public static String getDrvHashCode() {
        return hashCodeString(getDrvCode());
    }

    /**
     * 获取设备码
     */
    public static String getDrvCode() {
        return PhoneInfoUtils.getIMEI(AppApplication.getAppContext());
    }

    /**
     * 获取用户名
     */
    public static String getUserName() {
        String username = SharePreferencesUtils.newGetTask().get("username", ChineseNameUtils.generate());
        SharePreferencesUtils.newPutTask().put("username", username).commit();
        return username;
    }
}
