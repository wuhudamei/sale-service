package cn.damei.service.sale.account;

import cn.damei.shiro.PasswordUtil;
import cn.damei.common.service.CrudService;
import cn.damei.entity.sale.account.User;
import cn.damei.enumeration.Status;
import cn.damei.repository.sale.account.UserDao;
import cn.damei.utils.DateUtils;
import cn.damei.utils.HttpUtils;
import cn.damei.utils.WebUtils;
import com.google.common.collect.Maps;
import cn.damei.common.PropertyHolder;
import cn.damei.dto.StatusDto;
import cn.damei.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends CrudService<UserDao, User> {

    @Autowired
    private UserDao userDao;

    /**
     * 根据账户
     * @param userAccount 用户账号
     * @return
     */
    public User getByUserAccount(String userAccount) {
        if (StringUtils.isNotBlank(userAccount))
            return this.entityDao.getByUserAccount(userAccount);
        return null;
    }

    /**
     * 根据id 查询用户所有信息
     *
     * @param id 用户id
     * @return
     */
    public User getAllUserInfoById(Long id) {
        return this.entityDao.getAllInfo(id);
    }

    public User getAllUserInfoByAccount(String account){
        return this.entityDao.getAllInfoByAccount(account);
    }

    /**
     * 新增或保存
     *
     * @param user
     * @return
     */
    public StatusDto saveOrUpdateUser(User user) {
        User checkUser = this.entityDao.getByUserAccount(user.getAccount());
        Long userId = user.getId();
        if (null != userId) {
            if (checkUser != null && !checkUser.getId().equals(userId)) {
                return StatusDto.buildDataFailureStatusDto("登录名已存在，如有问题请联系管理员！");
            }
            this.entityDao.update(user);
        } else {
            //新增用户时,初识密码和用户名一样
            if (checkUser != null) {
                return StatusDto.buildDataFailureStatusDto("登录名已存在，如有问题请联系管理员！");
            }
            user.setPlainPassword(user.getAccount());
            PasswordUtil.entryptUserPassword(user);
            user.setStatus(Status.NORMAL);
            this.entityDao.insert(user);
        }
        return StatusDto.buildSuccessStatusDto("操作成功！");
    }

    /**
     * 更新登录密码
     *
     * @param userId      用户名
     * @param plainPwd    原密码
     * @param newPlainPwd 新密码
     * @return 2016年3月23日 下午3:22:07
     */
    public Object updateLoginPassword(final long userId, final String plainPwd, final String newPlainPwd) {
        User user = this.entityDao.getById(userId);
        try {
            if (user.getPassWord().equals(PasswordUtil.hashPassword(plainPwd, user.getSalt()))) {
                user.setPlainPassword(newPlainPwd);
                PasswordUtil.entryptUserPassword(user);
                this.entityDao.update(user);
                return StatusDto.buildSuccessStatusDto("修改密码成功！");
            } else {
                return StatusDto.buildFailureStatusDto("原密码输入错误！");
            }
        } catch (Exception e) {
            return StatusDto.buildFailureStatusDto("修改密码失败！");
        }
    }
    /**
     * 重置密码
     *
     * @param userId 用户id
     * @return
     */
    public StatusDto<String> resetPassword(final Long userId) {
        if (userId == null) {
            return StatusDto.buildFailureStatusDto("重置用户为非法用户！");
        }
        User user = this.entityDao.getById(userId);

        try {
            User u = new User();
            u.setId(userId);
            u.setPlainPassword(user.getAccount());
            PasswordUtil.entryptUserPassword(u);
            this.entityDao.update(u);
        } catch (Exception e) {
            return StatusDto.buildFailureStatusDto("重置密码失败！");
        }
        return StatusDto.buildSuccessStatusDto("重置密码成功！");
    }

    /**
     * 根据 公司和部门 查找 负责人
     */
    public  List<User> findDepartmentHead(Long company, Long department){
       return    entityDao.findDepartmentHead(company,department);
    }
    public  List<User> findPartTimeJobHead(Long department){
       return userDao.findPartTimeJobHead(department);
    }
    /**
     * 修改 用于负责人的修改
     */
    @Transactional(rollbackFor = Exception.class)
    public  StatusDto<String> updateHead(User user){
        try {
            entityDao.update(user);
        }catch (Exception e){
            return StatusDto.buildFailureStatusDto("修改失败！");
        }
        return StatusDto.buildSuccessStatusDto("修改成功！");
    }

    /**
     * 通过id查询可以发送微信通知的用户
     * @param id 用户id
     * @return
     */
    public User getByIdWithRemind(Long id) {
        return userDao.getByIdWithRemind(id);
    }

    /**
     * 从单点登录中 同步用户信息:
     *  1.首先请求认证中心获取accessToken，然后以accessToken为参数请求对应的接口，
     *    获取本系统下所有账户
     *  2.根据账户名称查询acct_user表中，是否存在，不存在的话插入用户信息
     */
    public boolean synchroUser(){
        boolean resultFlag = false;
        try {
            //首先调用AppToken接口，获取AppToken
            NameValuePair appid = new BasicNameValuePair("appid", PropertyHolder.getOauthCenterAppid());
            NameValuePair secret = new BasicNameValuePair("secret", PropertyHolder.getOauthCenterSecret());

            String appTokenRespResult = HttpUtils.post(PropertyHolder.getoAuthAppTokenUrl(), appid,secret);
            logger.info("调用认证中心appToken接口，返回结果：" + appTokenRespResult);
            Map<String, Object> appTokenResultMap = JsonUtils.fromJsonAsMap(appTokenRespResult,String.class,Object.class);
            Map<String,String> appTokenData = (Map<String,String>)appTokenResultMap.get("data");
            NameValuePair accessToken = new BasicNameValuePair("accessToken", appTokenData.get("access_token"));
            //请求用户账号接口
            String appUserRespResult = HttpUtils.post(PropertyHolder.getoAuthAppUserUrl(), appid,accessToken);
            logger.info("调用认证中心appUser接口，返回结果：" + appUserRespResult);

            Map<String, Object> appUserResultMap = JsonUtils.fromJsonAsMap(appUserRespResult,String.class,Object.class);
            Map<String,Object> appUserData = (Map<String,Object>)appUserResultMap.get("data");
            //获取到的所有用户
            List<Map<String,String>> accountList = (List<Map<String,String>>)appUserData.get("users");
            if( accountList != null && accountList.size() >0 ){

                //查询本系统中所有用户
                List<User> userExcludeAdminList = userDao.findAllWithDelete();
                Map<String,User> userExcludeAdminMap = Maps.newHashMap();
                if( userExcludeAdminList != null && userExcludeAdminList.size() > 0 ){
                    for(User user : userExcludeAdminList){
                        //使用jobNum 作为主键 去分辨用户
                        userExcludeAdminMap.put(user.getAccount(), user);
                    }
                }
                User user = new User();
                for(Map<String,String> map: accountList){
                    if (userExcludeAdminMap.get( map.get("jobNum") ) == null) {
                        //当前系统中 没有该用户
                        user.setName(map.get("name"));
                        user.setOrgCode(map.get("orgCode"));
                        user.setPhone(map.get("mobile"));
                        user.setEmail(map.get("email"));
                        user.setAccount(map.get("jobNum"));
                        user.setStatus(Status.NORMAL);
                        user.setRemindFlag(1);
                        user.setCreateDate(DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
                        user.setCreateUser(WebUtils.getLoggedUserId());
                        userDao.insert(user);
                    }else{
                        //当前系统中 有该用户,就将单点中的用户 name和orgCode更新到本地系统
                        user.setId(((User)userExcludeAdminMap.get( map.get("jobNum"))).getId());
                        user.setName(map.get("name"));
                        user.setOrgCode(map.get("orgCode"));
                        user.setEmail(map.get("email"));
                        user.setPhone(map.get("mobile"));
                        //修改时 都将状态变为 正常, 保证本地系统中用户唯一
                        user.setStatus(Status.NORMAL);
                        userDao.update(user);
                        userExcludeAdminMap.remove(map.get("jobNum") );
                    }
                }

                //遍历删除剩余的原系统人员--将本系统中存在,但单点中没有的用户删除掉
                for(User deluser : userExcludeAdminMap.values()){
                    this.userDao.deleteById(deluser.getId());
                }
            }
            resultFlag = true;
        } catch (Exception e) {
            logger.error("调用认证中心接口出现异常，异常信息" + e.getMessage());
        }
        return resultFlag;
    }
}
