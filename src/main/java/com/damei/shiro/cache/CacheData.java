package com.damei.shiro.cache;

import java.io.Serializable;

public class CacheData<T> implements Serializable {

    /**
     * 过期时间(单位:毫秒),值小于0时视为永不过期
     */
    private long expiresTime;

    /**
     * 创建时间（单位：毫秒）默认当前创建时间
     */
    private long createTime;

    /**
     * 缓存对象
     */
    private T data;

    /**
     * 获取失效时间 （单位毫秒）值小于0时永不过期
     *
     * @return 缓存对象失效时间
     */
    public long getExpiresTime() {
        return expiresTime;
    }

    public void setExpireTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }

    /**
     * 获取创建时间（单位毫秒）
     *
     * @return 缓存对象创建时间
     */
    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取缓存数据
     *
     * @return 缓存数据对象
     */
    public T getData() {
        return data;
    }

    /**
     * 设置缓存对象内容
     *
     * @param data 缓存对象内容
     */
    public void setData(T data) {
        this.data = data;
    }

    public CacheData(Builder<T> builder) {
        this.expiresTime = builder.expireTime;
        this.createTime = builder.createTime;
        this.data = builder.data;
    }


    /**
     * 缓存对象生成器
     *
     * @param <T> 缓存数据类型
     */
    public static class Builder<T> {
        private long expireTime;

        private long createTime;

        private T data;

        public Builder expireTime(long expireTime) {
            this.expireTime = expireTime;
            return this;
        }

        public Builder ereateTime(long createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder<T> data(T data) {
            this.createTime = -1;
            this.createTime = System.currentTimeMillis();
            this.data = data;
            return this;
        }

        public Builder<T> data(T data, long expireTime) {
            this.expireTime = expireTime;
            this.createTime = System.currentTimeMillis();
            this.data = data;
            return this;
        }

        /**
         * 构建缓存对象
         *
         * @return 返回缓存对象
         */
        public CacheData builder() {
            return new CacheData<T>(this);
        }


    }

}
