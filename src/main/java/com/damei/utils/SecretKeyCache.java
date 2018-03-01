package com.damei.utils;

import com.google.common.collect.Maps;

import java.util.Map;

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
