package com.damei.repository.sale.account;

import com.damei.common.persistence.CrudDao;
import com.damei.entity.sale.account.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
