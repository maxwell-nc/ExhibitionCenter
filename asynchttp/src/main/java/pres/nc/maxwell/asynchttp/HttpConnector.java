package pres.nc.maxwell.asynchttp;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import pres.nc.maxwell.asynchttp.cache.CacheManager;
import pres.nc.maxwell.asynchttp.callback.ICallback;
import pres.nc.maxwell.asynchttp.conn.ConnectTask;
import pres.nc.maxwell.asynchttp.log.LogUtils;
import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.thread.ThreadPoolController;

/**
 * HTTP连接器
 */
public class HttpConnector {

    private ConnectBuilder mConnectBuilder;
    private static ConfigBuilder mConfigBuilder;

    private HttpConnector(ConnectBuilder connectBuilder) {
        mConnectBuilder = connectBuilder;
    }

    /**
     * 执行请求操作
     */
    public void execute() {
        if (mConnectBuilder.request.getURL() == null) {
            return;
        }

        //配置全局配置,不覆写
        if (mConfigBuilder != null) {

            if (mConnectBuilder.context == null) {
                mConnectBuilder.context = mConfigBuilder.context;
            }

            if (mConnectBuilder.connectTimeout <= 0) {
                mConnectBuilder.connectTimeout = mConfigBuilder.connectTimeout;
            }

            if (mConnectBuilder.readTimeout <= 0) {
                mConnectBuilder.readTimeout = mConfigBuilder.readTimeout;
            }

            if (mConnectBuilder.readTimeout <= 0) {
                mConnectBuilder.readTimeout = mConfigBuilder.readTimeout;
            }

            if (mConnectBuilder.cacheTime <= 0 && mConfigBuilder.cacheTime > 0) {
                mConnectBuilder.cacheTime = mConfigBuilder.cacheTime;
            }

        }

        if (mConnectBuilder.connectTimeout > 0) {
            mConnectBuilder.request.setConnectTimeout(mConnectBuilder.connectTimeout);
        }

        if (mConnectBuilder.readTimeout > 0) {
            mConnectBuilder.request.setReadTimeout(mConnectBuilder.readTimeout);
        }

        ConnectTask connectTask = new ConnectTask(mConnectBuilder.resultCallback, mConnectBuilder.request,
                mConnectBuilder.logTag, mConnectBuilder.isCache, mConnectBuilder.context, mConnectBuilder.cacheTime, mConfigBuilder == null || mConfigBuilder.isEnableNetwork);
        connectTask.executeOnExecutor(ThreadPoolController.getThreadPool());
    }

    /**
     * 发起GET请求
     *
     * @param url 请求地址
     * @return 构建器
     */
    public static ConnectBuilder get(String url) {
        return new ConnectBuilder(Request.createGetRequest(url));
    }

    /**
     * 发起POST请求
     *
     * @param url 请求地址
     * @return 构建器
     */
    public static ConnectBuilder post(String url) {
        return new ConnectBuilder(Request.createPostRequest(url));
    }

    /**
     * 全局配置
     */
    public static ConfigBuilder globalConfig(Context context) {
        return new ConfigBuilder(context);
    }

    /**
     * 清除某个地址的缓存
     *
     * @return 是否成功
     */
    public static boolean clearCache(Context context, String url) {
        boolean result = new CacheManager(context, url, -1).clearCache();
        LogUtils.log().priority(Log.INFO).tag("http cache")
                .addMsg("clear cache :" + url)
                .addMsg("clear cache :" + result).build().execute();
        return result;
    }

    /**
     * 清理所有缓存
     */
    public static boolean clearCache(Context context) {
        boolean result = new CacheManager(context, "", -1).clearAllCache();
        LogUtils.log().priority(Log.INFO).tag("http cache")
                .addMsg("clear all cache")
                .addMsg("clear all cache :" + result).build().execute();
        return result;
    }

    /**
     * 配置构建器
     */
    public static class ConfigBuilder {

        /**
         * 连接超时时间
         */
        int connectTimeout = -1;

        /**
         * 读取超时时间
         */
        int readTimeout = -1;

        /**
         * 缓存时间，默认不缓存
         */
        long cacheTime = -1;

        /**
         * 是否开启网络
         */
        private boolean isEnableNetwork = true;

        Context context;


        /**
         * 设置连接超时时间
         *
         * @param connectTimeout 连接超时时间,毫秒
         */
        public ConfigBuilder setDefaultConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * 设置读取超时时间
         *
         * @param readTimeout 读取超时时间,毫秒
         */
        public ConfigBuilder setDefaultReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * 设置默认缓存时间
         *
         * @param cacheTime 缓存时间
         */
        public ConfigBuilder setDefaultCacheTime(long cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        /**
         * 只使用缓存，不请求网络
         */
        public ConfigBuilder onlyCache() {
            this.isEnableNetwork = false;
            return this;
        }

        /**
         * 配置
         */
        public void config() {
            setConfig(this);
        }

        ConfigBuilder(Context context) {
            this.context = context;
        }

    }

    /**
     * 设置全局属性
     */
    private static void setConfig(ConfigBuilder configBuilder) {
        mConfigBuilder = configBuilder;
    }

    /**
     * 连接构建器
     */
    public static class ConnectBuilder {

        /**
         * 日志标记
         */
        String logTag;

        /**
         * 结果回调
         */
        ICallback resultCallback;

        /**
         * 请求信息
         */
        Request request;

        /**
         * 构建生成的
         */
        HttpConnector httpConnector;

        /**
         * 是否使用缓存
         */
        boolean isCache = false;

        /**
         * 缓存时间，毫秒
         */
        long cacheTime = -1;

        /**
         * 连接超时时间
         */
        int connectTimeout = -1;

        /**
         * 读取超时时间
         */
        int readTimeout = -1;

        /**
         * 上下文，用于获取缓存目录
         */
        Context context;

        ConnectBuilder(Request request) {
            this.request = request;
        }

        /**
         * 设置参数Map
         */
        public ConnectBuilder setParams(HashMap<String, Object> params) {
            if (params == null)
                return this;

            HashMap<String, Object> requestParams = request.getParams();
            requestParams.putAll(params);
            request.setParams(requestParams);//设置回去
            return this;
        }

        /**
         * 添加参数
         *
         * @param key   参数名
         * @param value 参数值
         * @return 构建器
         */
        public ConnectBuilder addParams(String key, Object value) {
            HashMap<String, Object> params = request.getParams();
            params.put(key, value);
            request.setParams(params);//记得设置回去
            return this;
        }

        /**
         * 设置日志信息标记，不设置则不打印日志
         *
         * @param tag 日志标记
         * @return 构建器
         */
        public ConnectBuilder log(String tag) {
            this.logTag = tag;
            return this;
        }

        /**
         * 设置回调
         *
         * @param resultCallback 结果回调
         * @return 构建器
         */
        public ConnectBuilder callback(ICallback resultCallback) {
            this.resultCallback = resultCallback;
            return this;
        }

        /**
         * 使用缓存
         *
         * @param context   上下文
         * @param cacheTime 缓存时间，毫秒
         * @return 构建器
         */
        public ConnectBuilder cache(Context context, long cacheTime) {
            this.context = context;
            this.cacheTime = cacheTime;
            this.isCache = true;
            return this;
        }

        /**
         * 使用缓存
         * 使用这个方法必须配置过全局属性，否则无法获取上下文报错
         *
         * @param cacheTime 缓存时间，毫秒
         * @return 构建器
         */
        public ConnectBuilder cache(long cacheTime) {
            this.cacheTime = cacheTime;
            this.isCache = true;
            return this;
        }

        /**
         * 使用缓存
         * 使用这个方法必须配置过全局属性，否则无法获取上下文报错
         *
         * @return 构建器
         */
        public ConnectBuilder cache() {
            this.isCache = true;
            return this;
        }

        /**
         * 设置连接超时时间
         *
         * @param connectTimeout 连接超时时间,毫秒
         */
        public ConnectBuilder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * 设置读取超时时间
         *
         * @param readTimeout 读取超时时间,毫秒
         */
        public ConnectBuilder setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * 构建连接器
         *
         * @return 连接器
         */
        public HttpConnector build() {
            httpConnector = new HttpConnector(this);
            return httpConnector;
        }

        /**
         * 加载
         */
        public void load() {
            if (httpConnector != null) {
                httpConnector.execute();
            } else {
                new HttpConnector(this).execute();
            }
        }

    }

}
