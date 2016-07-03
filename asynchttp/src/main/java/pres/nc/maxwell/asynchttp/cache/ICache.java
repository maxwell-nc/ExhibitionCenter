package pres.nc.maxwell.asynchttp.cache;

/**
 * 缓存实现接口
 */
public interface ICache {

    /**
     * 获取缓存
     */
    String getCache(String key);

    /**
     * 保存缓存
     */
    void setCache(String key, String cache);

    /**
     * 清除指定缓存
     */
    boolean clearCache(String key);

    /**
     * 清除所有缓存
     */
    boolean clearCache();
}
