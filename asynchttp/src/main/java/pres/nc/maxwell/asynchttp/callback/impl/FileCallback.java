package pres.nc.maxwell.asynchttp.callback.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件回调
 * TODO:多线程
 */
public abstract class FileCallback extends AbsCallback<File> {

    private final String fullPath;

    public FileCallback(String fullPath) {
        this.fullPath = fullPath;
    }

    @Override
    public File parseResponseStream(InputStream is, int contentLength) throws Exception {

        File file = new File(fullPath);
        FileOutputStream outputStream = new FileOutputStream(file);

        byte[] buffer = new byte[1024];
        int len;

        float sum = 0;
        while ((len = is.read(buffer)) != -1) {
            sum += len;
            onDownProgress(sum * 1.0f / ((float) contentLength), contentLength);
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();

        return file;
    }

    @Override
    public String toCache(File data) {
        return null;//不缓存文件
    }

    /**
     * 下载中的调用
     *
     * @param progress 进度百分比
     */
    public abstract void onDownProgress(float progress, int fileSize);

}
