package pres.nc.maxwell.asynchttp.request;

import java.io.Serializable;
import java.util.HashMap;

import pres.nc.maxwell.asynchttp.conn.URLParser;
import pres.nc.maxwell.asynchttp.config.Constant;

/**
 * 请求
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 网址
     */
    private String URL;

    /**
     * 参数
     */
    private HashMap<String, Object> params = new HashMap<>();

    /**
     * 请求方法，默认为GET
     *
     * @see pres.nc.maxwell.asynchttp.config.Constant#METHOD_GET
     * @see pres.nc.maxwell.asynchttp.config.Constant#METHOD_POST
     */
    private String requestMethod = Constant.METHOD_GET;

    /**
     * 连接超时时间,默认为10,000 ms
     */
    private int connectTimeout = 10000;

    /**
     * 读取超时时间,默认为15,000ms
     */
    private int readTimeout = 15000;

    /**
     * 用户代理
     *
     * @see pres.nc.maxwell.asynchttp.config.Constant#USERAGENT_DEFALUT
     */
    private String userAgent = Constant.USERAGENT_DEFALUT;

    /**
     * 不要直接创建Request
     *
     * @see #createGetRequest
     * @see #createPostRequest
     */
    private Request() {
    }

    /**
     * 创建GET请求
     *
     * @param url 请求地址
     * @return GET请求
     */
    public static Request createGetRequest(String url) {
        Request request = new Request();
        request.setURL(url);
        request.setRequestMethod(Constant.METHOD_GET);
        return request;
    }

    /**
     * 创建POST请求
     *
     * @param url 请求地址
     * @return POST请求
     */
    public static Request createPostRequest(String url) {
        Request request = new Request();
        request.setURL(url);
        request.setRequestMethod(Constant.METHOD_POST);
        return request;
    }

    /**
     * @return 返回拼接参数的请求地址
     */
    @Override
    public String toString() {
        return URLParser.mergeParams(this);
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
