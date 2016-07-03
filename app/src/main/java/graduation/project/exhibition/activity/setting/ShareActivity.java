package graduation.project.exhibition.activity.setting;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BackActivity;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.ShareUtils;
import io.github.maxwell_nc.imageloader.ImageLoader;
import io.github.maxwell_nc.imageloader.engine.PathParser;
import io.github.maxwell_nc.imageloader.utils.IOUtils;
import io.github.maxwell_nc.imageloader.utils.Uri2Path;

/**
 * 分享页面
 */
public class ShareActivity extends BackActivity implements View.OnClickListener {

    private Uri image;
    private Uri imageForTx;
    private String shareText;
    private ShareUtils shareUtils;
    private ImageView imageView;
    private String imagePath;

    @Override
    protected int setViewId() {
        return R.layout.activity_share;
    }

    @Override
    protected String initBackBar() {
        return "推荐分享";
    }

    @Override
    protected void initView() {
        imageView = (ImageView) findViewById(R.id.iv_code);
    }

    @Override
    protected void initData() {

        imagePath = getExternalCacheDir() + "share_code.png";

        try {
            IOUtils.writeStreamToFile(getAssets().open("share_code.png"), new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        image = Uri.parse(imagePath);

        shareText = "【琶洲会展中心导游应用】最全、最新、最详细的展览会信息，最便捷的展览导航，尽在琶洲会展中心导游应用！";

        findViewById(R.id.ll_wechat).setOnClickListener(this);
        findViewById(R.id.ll_wechat_circle).setOnClickListener(this);
        findViewById(R.id.ll_qq).setOnClickListener(this);
        findViewById(R.id.ll_sina).setOnClickListener(this);
        findViewById(R.id.ll_sms).setOnClickListener(this);
        findViewById(R.id.ll_more).setOnClickListener(this);

        ImageLoader.createTask().local(imagePath).into(imageView)
                .callback(new ImageLoader.OnResultListener() {
                    @Override
                    public void onFailed() {

                    }

                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        imageForTx = MethodUtils.bitmap2Uri(mActivity, bitmap);
                    }
                })
                .start();
    }

    @Override
    public void onClick(View v) {

        if (shareUtils == null) {
            shareUtils = new ShareUtils(v, shareText);
        }

        switch (v.getId()) {
            case R.id.ll_wechat:
                if (imageForTx != null) {
                    shareUtils.wechat(imageForTx);
                } else {
                    MethodUtils.snackBar(v, "出了点小错，请稍后再试试！");
                }
                break;
            case R.id.ll_wechat_circle:
                if (imageForTx != null) {
                    shareUtils.wechatCircle(imageForTx);
                } else {
                    MethodUtils.snackBar(v, "出了点小错，请稍后再试试！");
                }
                break;
            case R.id.ll_qq:
                shareUtils.qq(image);
                break;
            case R.id.ll_sina:
                shareUtils.sina(image);
                break;
            case R.id.ll_sms:
                shareUtils.sms();
                break;
            case R.id.ll_more:
                shareUtils.all();
                break;
        }
    }

    @Override
    protected void onDestroy() {

        //删除临时图片
        File file = new File(imagePath);
        if (file.exists()) {
            file.delete();
        }

        //删除微信分享需要使用临时文件
        String filepath = Uri2Path.getImageAbsolutePath(this, imageForTx);
        String params[] = new String[]{filepath};
        getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + " LIKE ?", params);

        super.onDestroy();
    }

}