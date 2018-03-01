package com.damei.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.FactoryBean;

public class CustomShiroCredentialsMatherFactoryBean implements FactoryBean<HashedCredentialsMatcher> {


    @Override
    public HashedCredentialsMatcher getObject() throws Exception {
        //设定Password校验的Hash算法与迭代次数.
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(PasswordUtil.HASH_ALGORITHM);
        matcher.setHashIterations(PasswordUtil.HASH_ITERATIONS);
        return matcher;
    }

    @Override
    public Class<?> getObjectType() {
        return HashedCredentialsMatcher.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
