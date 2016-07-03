package pres.nc.maxwell.asynchttp.callback.impl;

import java.io.InputStream;

import pres.nc.maxwell.asynchttp.io.IOUtils;

/**
 * 字节数组回调
 */
public abstract class ByteArrayCallback extends AbsCallback<byte[]> {

    public byte[] parseResponseStream(InputStream is, int contentLength) throws Exception {
        return IOUtils.inputStream2bytes(is);
    }

    @Override
    public String toCache(byte[] data) {
        return new String(data);
    }
}
