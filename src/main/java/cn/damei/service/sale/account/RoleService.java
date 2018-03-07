package cn.damei.service.sale.account;

import cn.damei.repository.sale.account.RoleDao;
import cn.damei.common.service.CrudService;
import cn.damei.dto.StatusDto;
import cn.damei.entity.sale.account.Permission;
import cn.damei.entity.sale.account.Role;
import cn.damei.entity.sale.account.User;
import cn.damei.repository.sale.account.RolePermissionDao;
import cn.damei.repository.sale.account.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService extends CrudService<RoleDao,Role>{

    @Autowired
    private UserDao adminUserDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    /**
     * 查找所有角色()
     *
     * @param userId
     */
    public List<Role> getUserRoles(final Long userId) {
        return entityDao.getRolesByUserId(userId);
    }

    /**
     * 为用户设置角色
     *
     * @param userId
     * @param roles
     * @return 2016年3月24日 下午2:01:15
     */
    public StatusDto insertUserRoles(final Long userId, final List<Long> roles) {
        User user = adminUserDao.getById(userId);
        if (user == null)
            return StatusDto.buildDataFailureStatusDto("用户信息有误！");

        entityDao.deleteUserRolesByUserId(userId);
        for (Long roleid : roles) {
            entityDao.insertUserRoles(userId, roleid);
        }

        return StatusDto.buildDataSuccessStatusDto("角色设置成功！");
    }

    /**
     * 为角色设置权限
     *
     * @param roleId
     * @param permissions
     * @return 2016年3月25日 下午2:20:15
     */
    public StatusDto insertRolePermission(final Long roleId, final List<Long> permissions) {
        Role role = entityDao.getById(roleId);
        if (role == null)
            return StatusDto.buildDataFailureStatusDto("无此角色！");

        rolePermissionDao.deleteByRoleId(roleId);
        for (Long ids : permissions) {
            rolePermissionDao.insert(roleId, ids);
        }
        return StatusDto.buildDataSuccessStatusDto("权限设置成功！");
    }

    /**
     * 创建或更新角色
     *
     * @param role 2016年3月25日 上午11:03:58
     */
    public StatusDto saveOrUpdate(Role role) {
        Role tempRole = entityDao.checkRoleExistByName(role.getId(), role.getName());
        if (tempRole != null) {
            return StatusDto.buildDataFailureStatusDto("角色名称已存在！");
        }
        if (role.getId() != null) {
            entityDao.update(role);
        } else {
            entityDao.insert(role);
        }
        return StatusDto.buildDataSuccessStatusDto("保存角色成功！");
    }

    /**
     * 删除角色
     *
     * @param id
     * @return 2016年3月25日 上午11:16:24
     */
    public StatusDto deleteRole(Long id) {
        entityDao.deleteById(id);
        entityDao.deleteUserRolesByRoleId(id);
        rolePermissionDao.deleteByRoleId(id);
        return StatusDto.buildDataSuccessStatusDto("删除角色成功！");
    }

    /**
     * 查找角色绑定的权限及未绑定权限
     *
     * @param roleId 角色id
     * @return 2016年3月25日 下午1:39:29
     */
    public List<Permission> findRolePermission(Long roleId) {
        return rolePermissionDao.findRolePermission(roleId);
    }

}
