package com.rocoinfo.repository.sale.account;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.rocoinfo.entity.sale.account.Permission;

/**
 * <dl>
 * <dd>Description: 角色权限管理Dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/8 20:24</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
@Repository
public interface RolePermissionDao{

    void insert(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    void deleteByRoleId(@Param("roleId") Long roleId);

    List<Permission> findRolePermission(@Param("roleId") Long roleId);
}
