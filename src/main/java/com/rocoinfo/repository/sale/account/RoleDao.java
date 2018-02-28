package com.rocoinfo.repository.sale.account;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.account.Role;

/**
 * <dl>
 * <dd>Description: 角色管理Dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/8 20:24</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
@Repository
public interface RoleDao extends CrudDao<Role> {

    void insert(Role role);

    void update(Role role);

    Role checkRoleExistByName(@Param("id") Long id, @Param("name") String name);

    List<Role> getRolesByUserId(Long id);

    void deleteUserRolesByUserId(Long id);

    void deleteUserRolesByRoleId(Long id);

    void insertUserRoles(@Param("userId") Long userId, @Param("roleId") Long roleid);

    List<Role> search(Map<String, Object> parameters);
}
