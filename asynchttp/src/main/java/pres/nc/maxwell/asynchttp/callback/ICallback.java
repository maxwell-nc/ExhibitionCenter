package pres.nc.maxwell.asynchttp.callback;

import java.io.InputStream;

import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * Response接口模型
 */
public interface ICallback<T> {

    /**
     * 请求准备操作，主线程
     *
     * @param request 请求信息
     */
    void onPrepared(Request request);

    /**
     * 成功请求时操作，主线程
     *
     * @param response 响应信息
     */
    void onSuccess(Response<T> response);

    /**
     * 请求失败时操作，主线程
     *
     * @param response 响应信息
     */
    void onFailure(Response<T> response);


    /**
     * 请求结束后的操作，主线程，执行再onSuccess和onFailure之后
     *
     * @param response 响应信息
     */
    void onAfter(Response<T> response);

    /**
     * 解析响应输入流，子线程
     *
     * @param is 输入流
     * @param contentLength
     * @return 特定类型数据
     */
    T parseResponseStream(InputStream is, int contentLength) throws Exception;

    /**
     * 转换成缓存
     *
     * @param data 数据
     * @return 缓存
     */
    String toCache(T data);

}
