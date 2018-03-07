package cn.damei.repository.sale.account;

import cn.damei.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.damei.entity.sale.account.User;

import java.util.List;

@Repository
public interface UserDao extends CrudDao<User> {

    User getByUserAccount(String userAccount);

    User getByUserName2(String username);

    User getAllInfo(@Param(value = "id") Long id);

    User getAllInfoByAccount(String account);

    List<User> findDepartmentHead(@Param(value = "company") Long company,@Param(value = "department") Long department);
    List<User> findPartTimeJobHead(@Param(value = "department") Long department);

    User getByIdWithRemind(Long id);

    List<User> findAllWithDelete();
}
