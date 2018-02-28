package com.rocoinfo.repository.sale.account;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.rocoinfo.entity.sale.account.UserRole;

/**
 * <dl>
 * <dd>Description: 用户角色管理Dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/8 20:25</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
@Repository
public interface UserRoleDao {

    void insert(UserRole userRole);

    List<Long> getRoleIdsByUserId(Long userId);


    void deleteByUserId(Long userId);

    void deleteByRoleId(Long roleId);
}
