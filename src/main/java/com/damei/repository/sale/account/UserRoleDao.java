package com.damei.repository.sale.account;


import com.damei.entity.sale.account.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleDao {

    void insert(UserRole userRole);

    List<Long> getRoleIdsByUserId(Long userId);


    void deleteByUserId(Long userId);

    void deleteByRoleId(Long roleId);
}
