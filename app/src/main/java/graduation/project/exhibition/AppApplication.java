package graduation.project.exhibition;

import android.app.Application;
import android.content.Context;

import io.github.maxwell_nc.imageloader.ImageLoader;
import pres.nc.maxwell.asynchttp.HttpConnector;

/**
 * 用于全局初始化
 */
public class AppApplication extends Application {

    private static Context mContext;

    /**
     * 获取Application上下文
     */
    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initHttpConnector();
        initImageLoader();
    }

    /**
     * 初始化图片加载器
     */
    private void initImageLoader() {
        ImageLoader.log();
    }

    /**
     * 初始化网络
     */
    private void initHttpConnector() {
        HttpConnector.globalConfig(mContext)
                .setDefaultConnectTimeout(10000)//默认连接超时时间（毫秒），这里是10秒
                .setDefaultReadTimeout(15000)//默认读取超时时间（毫秒），这里是15秒
                .setDefaultCacheTime(15 * 24 * 3600000L)//默认缓存时间（毫秒）
                        // .onlyCache()//只读取缓存，不请求网络
                .config();
    }

}
