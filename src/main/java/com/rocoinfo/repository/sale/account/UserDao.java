package com.rocoinfo.repository.sale.account;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.account.User;

import java.util.List;

/**
 * <dl>
 * <dd>Description: 用户Dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/8 20:24</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
@Repository
public interface UserDao extends CrudDao<User> {

    /**
     * 根据账号
     *
     * @param userAccount 用户账号
     * @return
     */
    User getByUserAccount(String userAccount);

    /**
     * 根据中文名称模糊查询
     *
     * @param username
     * @return
     */
    User getByUserName2(String username);

    /**
     * 获取用户权限、角色灯信息
     *
     * @param id 用户id
     * @return
     */
    User getAllInfo(@Param(value = "id") Long id);

    /**
     * 获取用户权限、角色灯信息
     */
    User getAllInfoByAccount(String account);

    /**
     * 根据 公司和部门 查找 负责人
     */
    List<User> findDepartmentHead(@Param(value = "company") Long company,@Param(value = "department") Long department);
    /**
     * 根据 部门 查找 所有用户是否有兼职当前部门领导的
     */
    List<User> findPartTimeJobHead(@Param(value = "department") Long department);

    /**
     * 通过id查询可以发送微信通知的用户
     * @param id 用户id
     * @return
     */
    User getByIdWithRemind(Long id);

    /**
     * 查询所有用户, 包含删除掉的
     * @return
     */
    List<User> findAllWithDelete();
}
