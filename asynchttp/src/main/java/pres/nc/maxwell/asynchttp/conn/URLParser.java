package pres.nc.maxwell.asynchttp.conn;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.config.Constant;

public class URLParser {

    /**
     * 非法地址
     */
    public static final int TYPE_INVALID = 0;

    /**
     * HTTP类型地址
     */
    public static final int TYPE_HTTP = 1;

    /**
     * HTTPS类型地址
     */
    public static final int TYPE_HTTPS = 2;

    /**
     * 解析地址类型
     *
     * @param URL 地址
     * @return 地址类型
     */
    public static int parseType(String URL) {

        // 默认地址不合法
        int type = TYPE_INVALID;

        // HTTPS类型
        if (URL.startsWith("https://")) {
            type = TYPE_HTTPS;
        }

        // HTTP类型
        else if (URL.startsWith("http://")) {
            type = TYPE_HTTP;
        }

        // HTTP类型
        else if (URL.startsWith("www.")) {
            type = TYPE_HTTP;
        }

        return type;
    }

    /**
     * 获得参数字符串
     *
     * @param request 连接信息
     * @return 参数字符串
     */
    public static String getParamsString(Request request) {

        String paramsString = "";

        HashMap<String, Object> params = request.getParams();

        for (Entry<String, Object> entry : params.entrySet()) {

            try {
                paramsString += entry.getKey() + "="
                        + URLEncoder.encode(entry.getValue().toString(), "UTF-8") + "&";

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        if (paramsString.length() > 1) {
            // 去掉最后一个&
            paramsString = paramsString.substring(0, paramsString.length() - 1);
        }

        return paramsString;
    }

    /**
     * 合并地址和参数，GET请求时才有效
     *
     * @param request 连接信息
     * @return 合并后的地址
     */
    public static String mergeParams(Request request) {

        String URL = request.getURL();

        // 只有GET才拼写地址
        if (request.getRequestMethod().equals(Constant.METHOD_GET)) {
            String paramsString = getParamsString(request);
            if (paramsString.length() > 0) {
                URL = URL + "?" + paramsString;
            }
        }

        return URL;
    }

}
