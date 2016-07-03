package graduation.project.exhibition.business;

import android.content.Context;
import android.widget.ImageView;

import graduation.project.exhibition.R;
import graduation.project.exhibition.config.ConfigConstant;
import graduation.project.exhibition.utils.SharePreferencesUtils;
import io.github.maxwell_nc.imageloader.ImageLoader;
import io.github.maxwell_nc.imageloader.conn.impl.web.WebImageRequest;
import io.github.maxwell_nc.imageloader.entity.ImageModel;

/**
 * 图片工具类
 */
public class ImageUtil {

    /**
     * 加载网络图片
     */
    public static void loadWebImage(ImageView imageView, String url) {

        String imgMode = SharePreferencesUtils.newGetTask().get("no_img", "off");

        if (imgMode.equals("on")) {
            imageView.setImageResource(ConfigConstant.noImgRes);
            return;
        }

        ImageLoader.createTask().loadingRes(ConfigConstant.loadRes).failedRes(ConfigConstant.failedRes)
                .load(url)
                .using(new WebImageRequest() {
                    @Override
                    public void setModel(ImageModel model) {
                        super.setModel(model);
                        model.setPath(ConfigConstant.baseUrl + model.getPath());
                    }
                })
                .into(imageView).start();
    }

    /**
     * 清空缓存
     */
    public static void clearCache(Context context) {
        ImageLoader.clearMemCache();//clear all
        ImageLoader.clearDiskCache(context);//clear all
    }

}
