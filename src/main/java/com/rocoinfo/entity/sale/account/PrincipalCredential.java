package com.rocoinfo.entity.sale.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rocoinfo.entity.IdEntity;
import com.rocoinfo.enumeration.Status;
import org.springframework.data.annotation.Transient;

import java.util.Date;

/**
 * <dl>
 * <dd>Description: 用户和密码信息</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/8 20:24</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
public abstract class PrincipalCredential extends IdEntity {

    private static final long serialVersionUID = -3921953761059952963L;

    /**
     * 用户名(或邮箱)
     */
    private String username;

    /**
     * 原始密码(未加密)
     */
    @JsonIgnore
    private String plainPassword;

    /**
     * 加密后密码
     */
    @JsonIgnore
    private String password;

    /**
     * 盐值
     */
    @JsonIgnore
    private String salt;

    /**
     * 姓名
     */
    private String name;

    /**
     * 状态
     */
    private Status status;

    /**
     * 最近登录时间
     */
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date latestLoginTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getLatestLoginTime() {
        return latestLoginTime;
    }

    public void setLatestLoginTime(Date latestLoginTime) {
        this.latestLoginTime = latestLoginTime;
    }
}
