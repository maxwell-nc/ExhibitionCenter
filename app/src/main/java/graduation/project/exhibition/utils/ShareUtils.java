package graduation.project.exhibition.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

/**
 * 分享工具类
 */
public class ShareUtils {

    private Context context;
    private View view;
    private String shareText;

    public ShareUtils(View view, String shareText) {
        this.context = view.getContext();
        this.view = view;
        this.shareText = shareText;
    }

    public void wechatCircle(Uri image) {
        ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        shareWithImg(componentName, "朋友圈", image);
    }

    public void wechat(Uri image) {
        ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        shareWithImg(componentName, "微信", image);
    }

    public void sina(Uri image) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent.putExtra(Intent.EXTRA_SUBJECT, shareText);
        intent.putExtra(Intent.EXTRA_STREAM, image);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setComponent(new ComponentName("com.sina.weibo", "com.sina.weibo.composerinde.ComposerDispatchActivity"));
        context.startActivity(intent);
    }

    public void all() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            MethodUtils.snackBar(view, "分享失败！");
        }
    }

    private void shareWithImg(ComponentName componentName, String type, Uri image) {
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent.putExtra(Intent.EXTRA_STREAM, image);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            MethodUtils.snackBar(view, "分享" + type + "失败！");
        }
    }

    public void sms() {
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        sendIntent.putExtra("sms_body", shareText);//短信内容
        sendIntent.setType("vnd.android-dir/mms-sms");
        context.startActivity(sendIntent);
    }

    public void qq(Uri image) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent.putExtra(Intent.EXTRA_SUBJECT, shareText);
        intent.putExtra(Intent.EXTRA_STREAM, image);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //intent.setComponent(new ComponentName("com.tencent.mobileqq", "android/com.android.internal.app.ResolverActivity"));
        intent.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
        context.startActivity(intent);


    }
}
