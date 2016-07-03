package graduation.project.exhibition.business;

import graduation.project.exhibition.AppApplication;
import graduation.project.exhibition.config.ConfigConstant;
import pres.nc.maxwell.asynchttp.HttpConnector;
import graduation.project.exhibition.http.JsonCallback;
import pres.nc.maxwell.asynchttp.callback.ICallback;

/**
 * 网络请求业务
 */
public class HttpRequest {

    /**
     * 获取完整地址
     */
    public static String getFullUrl(String urlAdditionString) {
        return ConfigConstant.baseUrl + "/" + urlAdditionString;
    }

    /**
     * JSON请求
     */
    public static void jsonRequest(String urlAdditionString, ICallback callback) {
        HttpConnector.post(getFullUrl(urlAdditionString))
                .callback(callback).log(urlAdditionString).cache().load();
    }

    /**
     * JSON请求,无缓存
     */
    public static void jsonRequestWithoutCache(String urlAdditionString, ICallback callback) {
        HttpConnector.post(getFullUrl(urlAdditionString))
                .callback(callback).log(urlAdditionString).load();
    }

    /**
     * 清理缓存
     */
    public static void clearCache(String urlAdditionString) {
        HttpConnector.clearCache(AppApplication.getAppContext(), getFullUrl(urlAdditionString));
    }

}
