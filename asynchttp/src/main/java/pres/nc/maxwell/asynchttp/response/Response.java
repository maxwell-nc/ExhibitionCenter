package pres.nc.maxwell.asynchttp.response;

import java.io.Serializable;

/**
 * 响应
 * @param <T> 响应数据类型
 */
public class Response<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 响应码
     */
    private int responseCode;

    /**
     * 响应信息
     */
    private String responseMsg;

    /**
     * 响应数据
     */
    private T responseData;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }

}
