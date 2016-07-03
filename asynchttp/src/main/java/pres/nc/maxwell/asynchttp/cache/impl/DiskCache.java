package pres.nc.maxwell.asynchttp.cache.impl;

import android.os.Environment;

import java.io.File;

import pres.nc.maxwell.asynchttp.cache.CacheManager;
import pres.nc.maxwell.asynchttp.cache.ICache;
import pres.nc.maxwell.asynchttp.io.IOUtils;

/**
 * 本地缓存
 */
public class DiskCache implements ICache {

    private final CacheManager cacheManager;

    public DiskCache(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public String getCache(String key) {
        File file = new File(getLocalCachePath(), key);

        if (!file.exists()) {
            return null;
        }

        long modiTime = file.lastModified();

        //缓存经历时间
        long cacheSpend = System.currentTimeMillis() - modiTime;

        if (cacheSpend > cacheManager.getCacheTime()) {//失效
            clearCache(key);
            return null;
        }

        return IOUtils.loadFileToString(file);
    }

    @Override
    public boolean clearCache(String key) {
        File file = new File(getLocalCachePath(), key);
        return file.exists() && file.delete();
    }

    @Override
    public void setCache(String key, String cache) {
        IOUtils.writeStrToFile(getLocalCachePath() + "/" + key, cache);
    }

    /**
     * 获取本地缓存位置
     */
    public String getLocalCachePath() {

        // sdcard位置
        String cachePath = null;

        // SD存在则使用SD缓存
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {

            File externalCacheDir = cacheManager.getContext().getExternalCacheDir();
            if (externalCacheDir != null) {
                cachePath = externalCacheDir.getPath();
            }
        }

        if (cachePath == null) {
            cachePath = cacheManager.getContext().getCacheDir().getPath();
        }

        cachePath += "/httpcache";

        // 如果文件夹不存在, 创建文件夹
        File dirFile = new File(cachePath);

        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        return cachePath;
    }

    @Override
    public boolean clearCache() {
        return IOUtils.removeDir(new File(getLocalCachePath()));
    }
}
