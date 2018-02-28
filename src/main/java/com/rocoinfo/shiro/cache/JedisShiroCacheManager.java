package com.rocoinfo.shiro.cache;

import com.rocoinfo.shiro.MemoryManager;
import org.apache.shiro.cache.Cache;

/**
 * 这里的name是指自定义relm中的授权/认证的类名加上授权/认证英文名字
 *
 * @author michael
 */
public class JedisShiroCacheManager implements ShiroCacheManager {

    private MemoryManager memoryManager;

    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        return new JedisShiroCache<K, V>(name, getMemoryManager());
    }

    @Override
    public void destroy() {
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    public void setMemoryManager(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }
}
