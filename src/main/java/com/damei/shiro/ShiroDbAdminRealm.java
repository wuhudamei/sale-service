/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.damei.shiro;

import com.damei.entity.sale.account.User;
import com.damei.enumeration.Status;
import com.damei.shiro.token.CustomUsernamePasswordToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;

public class ShiroDbAdminRealm extends ShiroAbstractRealm {

	/**
	 * 设置校验规则
	 *
	 * @param credentialsMatcher
	 *            自定义校验规则
	 */

	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		super.setCredentialsMatcher((token, info) -> {
			if (token instanceof CustomUsernamePasswordToken) {
				CustomUsernamePasswordToken customToken = (CustomUsernamePasswordToken) token;
				if (StringUtils.isNotBlank(customToken.getUsername()) && customToken.isSsoLogin() && info != null) {
					return true;
				}
			}
			return false;
		});
	}
	
    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
    	CustomUsernamePasswordToken token = (CustomUsernamePasswordToken) authcToken;
        User adminUser = this.userService.getAllUserInfoByAccount(token.getUsername());
        // 根据前后台用户类型 走不同的查询方法
        if (adminUser == null) {
            throw new AccountException("不存在的管理员用户：" + token.getUsername());
        }

        if (Status.INVALID.equals(adminUser.getStatus())) {
            throw new LockedAccountException("管理员账户被锁定：" + token.getUsername());
        }
        if (Status.DELETE.equals(adminUser.getStatus())) {
            throw new LockedAccountException("不存在的管理员用户：" + token.getUsername());
        }
        
        ShiroUser shiroUser = new ShiroUser(adminUser.getId(),adminUser.getAccount(), adminUser.getDepCode(), adminUser.getOrgCode(),
                adminUser.getName(), adminUser.getPhone(),
                (adminUser.getCompany() != null && adminUser.getCompany().getId() != null) ? adminUser.getCompany().getId().toString() : "",
                (adminUser.getCompany() != null && adminUser.getCompany().getOrgCode() != null) ? adminUser.getCompany().getOrgCode() : "",
                (adminUser.getDepartment() != null && adminUser.getDepartment().getId() != null) ? adminUser.getDepartment().getId().toString() : "",
                (adminUser.getDepartment() != null && adminUser.getDepartment().getOrgCode() != null) ? adminUser.getDepartment().getOrgCode() : "",
                (adminUser.getDepartment() != null && adminUser.getDepartment().getOrgName() != null) ? adminUser.getDepartment().getOrgName() : "",
                (adminUser.getDepartment() != null && adminUser.getDepartment().getDepType() != null) ? adminUser.getDepartment().getDepType():null,
                adminUser.getDepartmentHead(),token.getRoles(), token.getPermission());

        return new SimpleAuthenticationInfo(shiroUser, null, getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof CustomUsernamePasswordToken) {
            // 如果不是单点登录，走这个realm校验登录信息
            return !((CustomUsernamePasswordToken) token).isSsoLogin();
        }
        return true;
    }
}
