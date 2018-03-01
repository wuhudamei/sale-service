package com.damei.shiro;

import com.damei.shiro.cache.CacheData;
import com.google.common.collect.Maps;

import java.util.Map;

public class MemoryManager {
    /**
     * 将所有缓存到这个Map中
     */
    private final Map<String, CacheData> cacheMap = Maps.newConcurrentMap();


    /**
     * 获取缓存中的对象，如果不存在返回null
     *
     * @param key 查找缓存的key
     * @param <T> 返回缓存对象的类型
     * @return 返回缓存对象 不存在返回null
     */
    public <T> T getValueByKey(final String key) {

        CacheData cached = cacheMap.get(key);
        if (cached == null || (cached.getExpiresTime() > 0 && cached.getExpiresTime() + cached.getCreateTime() < System.currentTimeMillis())) {
            cacheMap.remove(key);
            return null;
        }
        return (T) cached.getData();
    }

    /**
     * 删除指定key的缓存
     *
     * @param key 删除缓存的key
     */
    public void deleteByKey(final String key) {
        cacheMap.remove(key);
    }

    /**
     * 保存缓存指定失效时间
     *
     * @param key        缓存key
     * @param data       缓存数据
     * @param expireTime 过期时间
     */
    public void saveValueByKey(final String key, Object data, long expireTime) {
        CacheData cacheData = new CacheData.Builder().data(data, expireTime).builder();
        cacheMap.put(key, cacheData);
    }
}
