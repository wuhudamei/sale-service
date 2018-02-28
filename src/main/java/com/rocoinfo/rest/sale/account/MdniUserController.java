package com.rocoinfo.rest.sale.account;

import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusBootTableDto;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.sale.account.User;
import com.rocoinfo.enumeration.Status;
import com.rocoinfo.service.sale.account.RoleService;
import com.rocoinfo.service.sale.account.UserService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.MapUtils;
import com.rocoinfo.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * /*
 * /*@author asher
 * /*@time 2017-03-08 14:14:18
 **/
@RestController
@RequestMapping(value = "/api/users")
public class MdniUserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 分页查询用户列表
     *
     * @param keyword 工号或姓名
     * @param status  用户状态
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object search(@RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "status", required = false) Status status,
                         @RequestParam(value = "companyId", required = false) Long companyId,
                         @RequestParam(value = "departmentId", required = false) Long departmentId,
                         @RequestParam(value = "supplierId", required = false) Long supplierId,
                         @RequestParam(value = "offset", defaultValue = "0") int offset,
                         @RequestParam(value = "pageSize", defaultValue = "10") int limit,
                         @RequestParam(value = "sortName", defaultValue = "id") String orderColumn,
                         @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {

        Map<String, Object> params = Maps.newHashMap();

        MapUtils.putNotNull(params, "status", status);
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "companyId", companyId);
        MapUtils.putNotNull(params, "departmentId", departmentId);
        MapUtils.putNotNull(params, "supplierId", supplierId);

        PageRequest pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.valueOf(orderSort.toUpperCase()), orderColumn));
        Page<User> pageList = userService.searchScrollPage(params, pageable);

        return StatusBootTableDto.buildDataSuccessStatusDto(pageList);
    }

    /**
     * 根据id获取用户信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StatusDto getById(@PathVariable(value = "id") Long id) {

        User user = userService.getById(id);
        if (user == null) {
            return StatusDto.buildFailureStatusDto("获取用户信息失败！");
        }
        return StatusDto.buildDataSuccessStatusDto(user);

    }

    /**
     * 新建/更新用户接口
     *
     * @param user 用户信息
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public StatusDto create(@RequestBody() User user) {
        // TODO check params

        if (user.getId() == null) {
            ShiroUser loginUser = WebUtils.getLoggedUser();
            user.setCreateUser(loginUser.getId());
        }
        return userService.saveOrUpdateUser(user);
    }

    /**
     * 重置密码
     */
    @RequestMapping("/{id}/resetPassWord")
    public StatusDto<String> resetPassWord(@PathVariable("id") Long id) {

        return userService.resetPassword(id);

    }

    /**
     * 查找用户角色
     */
    @RequestMapping(value = "/role/{id}")
    public Object getUserRoles(@PathVariable Long id) {

        return StatusDto.buildDataSuccessStatusDto(this.roleService.getUserRoles(id));
    }

    /**
     * 修改用户密码
     *
     * @param plainPwd   原密码
     * @param loginPwd   新密码
     * @param confirmPwd 确认密码
     * @return
     */
    @RequestMapping(value = "/updatePwd")
    public Object updatePwd(@RequestParam("plainPwd") String plainPwd, @RequestParam("loginPwd") String loginPwd, @RequestParam("confirmPwd") String confirmPwd) {
        if (StringUtils.isBlank(plainPwd)) {
            return StatusDto.buildFailureStatusDto("原密码为空！");
        }
        if (StringUtils.isBlank(loginPwd)) {
            return StatusDto.buildFailureStatusDto("新密码为空！");
        }
        if (StringUtils.isBlank(confirmPwd)) {
            return StatusDto.buildFailureStatusDto("确认密码为空！");
        }
        if (!loginPwd.equals(confirmPwd)) {
            return StatusDto.buildFailureStatusDto("两次密码输入不一致！");
        }
        ShiroUser user = WebUtils.getLoggedUser();
        return this.userService.updateLoginPassword(user.getId(), plainPwd, loginPwd);
    }

    /**
     * 设置用户角色
     *
     * @param userId 用户id
     * @param roles  角色id
     * @return
     */
    @RequestMapping(value = "/userrole")
    public Object setUserRole(@RequestParam("userId") Long userId, @RequestParam("roles[]") List<Long> roles) {
        if (userId == null)
            return StatusDto.buildDataFailureStatusDto("用户id为空！");
        if (roles == null || roles.size() == 0)
            return StatusDto.buildDataFailureStatusDto("角色为空！");
        return roleService.insertUserRoles(userId, roles);
    }

    /**
     * 获取用户具有的权限信息
     */
    @RequestMapping(value = "/getLogger/Permission")
    public StatusDto getUserPermissions() {

        ShiroUser shiroUser = WebUtils.getLoggedUser();
        return StatusDto.buildDataSuccessStatusDto(shiroUser);

    }
    /**
     * 修改用户 用于修改负责人
     */
    @RequestMapping(value = "/updateHead",method = RequestMethod.POST)
    public StatusDto updateHead(@RequestBody User user) {
        return StatusDto.buildDataSuccessStatusDto(userService.updateHead(user));
    }

    /**
     * 查询部门负责人
     * 1.查询当前人所在部门的领导
     * 2.查询当前人所在部门 是否有兼职领导
     */
    @RequestMapping(value = "/findHeadList",method = RequestMethod.GET)
    public StatusDto findHeadList() {
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        String company = loggedUser.getCompany();
        String department = loggedUser.getDepartment();

        if(StringUtils.isEmpty(company)||StringUtils.isEmpty(department)){
            return StatusDto.buildDataFailureStatusDto("请联系管理员，先设置部门");
        }
        //1.查询当前人所在部门的领导
        List<User> departmentHead = userService.findDepartmentHead(Long.valueOf(company), Long.valueOf(department));

        //2.查询当前人所在部门 是否有兼职领导
        List<User> partTimeJobHead = userService.findPartTimeJobHead(Long.valueOf(department));
        if((departmentHead == null || departmentHead.size() == 0) && (partTimeJobHead == null
                || partTimeJobHead.size() == 0)){
            return StatusDto.buildDataFailureStatusDto("请联系管理员，先设置部门负责人");
        }
        //合并
        departmentHead.addAll(partTimeJobHead);
        return StatusDto.buildDataSuccessStatusDto(departmentHead);
    }

    /**
     * 从单点登录中 同步用户信息
     * @author Paul
     * @date 2017-09-05
     */
    @RequestMapping(value = "/synUser")
    public Object synchroUser(){
        if( userService.synchroUser() ){
            return StatusDto.buildSuccessStatusDto("同步用户账号成功！");
        }else{
            return StatusDto.buildFailureStatusDto("同步用户账号失败！");
        }
    }
}