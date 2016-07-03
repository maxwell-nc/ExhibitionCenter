package pres.nc.maxwell.asynchttp.conn;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.config.Constant;

/**
 * URL连接管理
 */
public class URLConnection {

    /**
     * 自动判断HTTP还是HTTPS并获得HttpURLConnection,设置参数
     *
     * @param request 请求
     * @return 如果地址非法则返回null
     * @throws IOException
     */
    public static HttpURLConnection getURLConnection(Request request)
            throws IOException {

        String URL = request.getURL();

        int type = URLParser.parseType(URL);

        // 非法类型
        if (type == URLParser.TYPE_INVALID) {
            return null;
        }

        //补全http://
        if (type == URLParser.TYPE_HTTP) {
            URL = "http://" + URL;
        }

        if (request.getParams() != null) {
            // GET方法时合并地址和参数
            URL = URLParser.mergeParams(request);
        }

        HttpURLConnection connection = null;

        switch (type) {
            case URLParser.TYPE_HTTP:// 创建HttpURLConnection

                connection = (HttpURLConnection) new URL(URL).openConnection();

                break;
            case URLParser.TYPE_HTTPS:// 创建HttpsURLConnection

                // 信任所有HTTPS连接
                HttpsURLConnection
                        .setDefaultHostnameVerifier(new HostnameVerifier() {
                            public boolean verify(String string, SSLSession ssls) {
                                return true;
                            }
                        });

                connection = (HttpsURLConnection) new URL(URL).openConnection();
                break;
        }


        if (connection == null) {
            return null;
        }

        // 连接超时时间
        connection.setConnectTimeout(request.getConnectTimeout());

        // 读取超时时间
        connection.setReadTimeout(request.getReadTimeout());

        // User-Agent
        connection.setRequestProperty("User-Agent", request.getUserAgent());

        //支持gzip、deflate
        connection.setRequestProperty("Accept-Encoding", "gzip,deflate");

        // 请求方法
        connection.setRequestMethod(request.getRequestMethod());

        // 处理POST方法的参数
        if (request.getRequestMethod().equals(Constant.METHOD_POST) && request.getParams() != null) {
            String paramsString = URLParser.getParamsString(request);

            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",
                    String.valueOf(paramsString.length()));

            connection.setDoOutput(true);

            // 提交参数
            OutputStream os = connection.getOutputStream();
            os.write(paramsString.getBytes());
        }

        return connection;

    }

}
