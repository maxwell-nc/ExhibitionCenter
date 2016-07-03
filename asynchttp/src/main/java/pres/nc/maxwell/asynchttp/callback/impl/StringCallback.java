package pres.nc.maxwell.asynchttp.callback.impl;

import java.io.InputStream;

import pres.nc.maxwell.asynchttp.io.IOUtils;

/**
 * 字符串回调
 */
public abstract class StringCallback extends AbsCallback<String> {

    @Override
    public String parseResponseStream(InputStream is, int contentLength) throws Exception {
        return new String(IOUtils.inputStream2bytes(is));
    }

    @Override
    public String toCache(String data) {
        return data;
    }
}
