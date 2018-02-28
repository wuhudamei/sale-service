package com.rocoinfo.entity.sale.account;

import com.rocoinfo.entity.IdEntity;

/**
 * <dl>
 * <dd>Description: 用户角色管理</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/8 20:24</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
public class UserRole extends IdEntity {
    private static final long serialVersionUID = -5493732869243219341L;

    private Long userId;
    private Long roleId;

    public UserRole() {
    }

    public UserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
