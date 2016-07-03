package pres.nc.maxwell.asynchttp.cache.impl;

import android.util.LruCache;

import pres.nc.maxwell.asynchttp.cache.ICache;

/**
 * 内存缓存
 */
public class MemoryCache implements ICache {

    public static final MemoryCache cacheInstance = new MemoryCache();

    private MemoryCache() {
        long maxCacheMemory = Runtime.getRuntime().maxMemory() / 16;// 设置最大Cache占用应用总内存1/16
        mMemoryCache = new LruCache<String, String>((int) maxCacheMemory) {
            @Override
            protected int sizeOf(String key, String value) {
                return value.getBytes().length;
            }
        };
    }

    private static LruCache<String, String> mMemoryCache;

    /**
     * 获取单例
     */
    public static MemoryCache getInstance() {
        return cacheInstance;
    }

    @Override
    public String getCache(String key) {
        return mMemoryCache.get(key);
    }

    @Override
    public void setCache(String key, String cache) {
        if (cache != null) {
            mMemoryCache.put(key, cache);
        }
    }

    @Override
    public boolean clearCache(String key) {
        String remove = mMemoryCache.remove(key);
        if (remove != null) {
            remove = null;//回收
            return true;
        }
        return false;
    }

    @Override
    public boolean clearCache() {
        mMemoryCache.evictAll();
        return true;
    }
}
