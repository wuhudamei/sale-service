package com.damei.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.List;

public class CustomUsernamePasswordToken extends UsernamePasswordToken {

	private String username;

	private String name;

	private List<String> roles;

	private List<String> permission;
	
    /**
     * 是否是单点登录（单点登录不需要密码）
     */
    private boolean ssoLogin;

    public CustomUsernamePasswordToken(String username, String password, boolean ssoLogin) {
        super(username, password);
        this.ssoLogin = ssoLogin;
    }
    
	public CustomUsernamePasswordToken(String username, String name, List<String> roles, List<String> permission,
			boolean ssoLogin) {
		this.username = username;
		this.name = name;
		this.roles = roles;
		this.permission = permission;
		this.ssoLogin = ssoLogin;
	}

	
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getPermission() {
		return permission;
	}

	public void setPermission(List<String> permission) {
		this.permission = permission;
	}

	public boolean isSsoLogin() {
        return ssoLogin;
    }

    public void setSsoLogin(boolean ssoLogin) {
        this.ssoLogin = ssoLogin;
    }
}
