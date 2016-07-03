package pres.nc.maxwell.asynchttp.conn;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;

import pres.nc.maxwell.asynchttp.cache.CacheManager;
import pres.nc.maxwell.asynchttp.callback.ICallback;
import pres.nc.maxwell.asynchttp.log.LogUtils;
import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 连接任务
 */
public class ConnectTask extends AsyncTask<Void, Void, Boolean> {

    /**
     * 缓存管理，为null则不缓存
     */
    private CacheManager mCacheManager;

    /**
     * 日志标记，为null则不打印日志
     */
    private String logTag;

    /**
     * 请求信息
     */
    private final Request request;

    /**
     * 结果处理器
     */
    private ICallback resultCallback;

    /**
     * 响应信息
     */
    private Response response = new Response();

    /**
     * 是否启用网络
     */
    private final boolean isEnableNetwork;

    /**
     * 创建连接任务
     */
    public ConnectTask(ICallback resultCallback, Request request, String logTag,
                       boolean isCache, Context context, long cacheTime, boolean isEnableNetwork) {
        this.resultCallback = resultCallback;
        this.request = request;
        this.logTag = logTag;
        this.isEnableNetwork = isEnableNetwork;

        if (isCache) {
            mCacheManager = new CacheManager(context, request.getURL(), cacheTime);
        }
    }

    @Override
    protected void onPreExecute() {//主线程

        //TODO:可以取消任务

        if (logTag != null) {
            LogUtils.log()
                    .tag(logTag)
                    .priority(Log.INFO)
                    .addMsg("Request: " + request.toString())
                    .addMsg("RequestMethod: " + request.getRequestMethod())
                    .build()
                    .execute();
        }

        resultCallback.onPrepared(request);
    }

    @Override
    protected Boolean doInBackground(Void... params) {// 子线程

        if (mCacheManager != null) {
            String cache = mCacheManager.getCache();
            if (cache != null) {
                response.setResponseCode(-200);//区分是否冲缓存读取的
                response.setResponseMsg("读取缓存成功！");
                try {
                    //这里的contentLength没有意义，因为文件不做缓存
                    response.setResponseData(resultCallback.parseResponseStream(new ByteArrayInputStream(cache.getBytes()), 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        if (!isEnableNetwork) {
            response.setResponseCode(-404);//区分普通错误
            response.setResponseMsg("读取缓存失败并且没有启用网络请求！");
            return false;
        }

        HttpURLConnection urlConnection = null;

        try {

            urlConnection = URLConnection.getURLConnection(request);

            if (urlConnection == null) {
                return false;
            }

            urlConnection.connect();

            response.setResponseCode(urlConnection.getResponseCode());
            if (response.getResponseCode() == 200) {//成功接收

                InputStream inputStream = urlConnection.getInputStream();
                if ("gzip".equals(urlConnection.getContentEncoding())) {
                    inputStream = new GZIPInputStream(inputStream);
                }

                //内容大小
                int contentLength = urlConnection.getContentLength();

                // 调用监听器
                if (resultCallback != null) {
                    response.setResponseMsg(urlConnection.getResponseMessage());
                    response.setResponseData(resultCallback.parseResponseStream(inputStream, contentLength));
                }
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setResponseCode(-999);
            response.setResponseMsg("连接服务器失败！");
            return false;
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();// 断开
            }

        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {// 主线程

        if (resultCallback != null) {

            if (result) {// 成功接收

                if (logTag != null) {
                    LogUtils.log()
                            .tag(logTag)
                            .priority(Log.INFO)
                            .addMsg("Request Success")
                            .addMsg("Response Message: " + response.getResponseMsg())
                            .build()
                            .execute();
                }

                resultCallback.onSuccess(response);

                //写缓存
                if (mCacheManager != null) {
                    if (response.getResponseCode() != -200) {//非缓存数据
                        new Thread() {
                            @Override
                            public void run() {
                                mCacheManager.setCache(resultCallback.toCache(response.getResponseData()));
                            }
                        }.start();
                    }
                }

            } else {// 接收失败

                if (logTag != null) {
                    LogUtils.log()
                            .tag(logTag)
                            .priority(Log.WARN)
                            .addMsg("Request Failed")
                            .addMsg("Error code: " + response.getResponseCode())
                            .addMsg("Error Msg: " + response.getResponseMsg())
                            .build()
                            .execute();
                }
                resultCallback.onFailure(response);
            }


            resultCallback.onAfter(response);
        }

    }

}
