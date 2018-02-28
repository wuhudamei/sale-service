package com.rocoinfo.utils;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * <dl>
 * <dd>Description:  用来保存OA给微信平台发送秘钥的缓存类</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2016/10/28 12:41</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
public class SecretKeyCache {

    private static Map<String, String> cache = Maps.newConcurrentMap();

    private SecretKeyCache() {
        super();
    }

    /**
     * get and remove
     *
     * @param key key
     * @return
     */
    public static synchronized String getAndRemove(String key) {
        return cache.remove(key);
    }

    /**
     * put
     *
     * @param key   key
     * @param value value
     */
    public static void put(String key, String value) {
        cache.put(key, value);
    }

    /**
     * 是否存在指定key
     *
     * @param key key
     * @return
     */
    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }
}
