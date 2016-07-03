package graduation.project.exhibition.business;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import graduation.project.exhibition.utils.MethodUtils;
import pres.nc.maxwell.asynchttp.callback.impl.StringCallback;

/**
 * 用户信息业务
 */
public class UserInfoBusiness {

    public static void getUsername(StringCallback callback) {

        HttpRequest.jsonRequestWithoutCache("/getusername?code=" + MethodUtils.getDrvCode(), callback);

    }

    public static void setUsername(final String name, StringCallback callback) {

        String encode = null;
        try {
            encode = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpRequest.jsonRequestWithoutCache("/setusername?code=" + MethodUtils.getDrvCode() + "&name=" + encode, callback);

    }

}
