package com.damei.repository.sale.account;

import com.damei.entity.sale.account.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionDao{

    void insert(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    void deleteByRoleId(@Param("roleId") Long roleId);

    List<Permission> findRolePermission(@Param("roleId") Long roleId);
}
