package com.damei.shiro;

import com.damei.service.sale.account.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ShiroAbstractRealm extends AuthorizingRealm {

    private static final String OR_OPERATOR = " or ";
    private static final String AND_OPERATOR = " and ";
    private static final String NOT_OPERATOR = "not ";

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 管理员service
     */
    protected UserService userService;

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        try {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
            info.addRoles(shiroUser.getRoles());
            info.addStringPermissions(shiroUser.getPermissions()); 
//            if (userService != null) {//不知道什么情况，回调一次后userService就为null了
//                // 如果是后台用户 需要查询用户对应的权限
//                User user = userService.getAllUserInfoById(shiroUser.getId());
//
//                if (user != null) {
//                    info.addRoles(user.getRoleNameList());
//                    info.addStringPermissions(user.getPermissions());
//                }
//            }
            return info;
        } catch (RuntimeException e) {
            logger.warn("授权时发生异常", e);
            throw e;
        }

    }

    /**
     * 支持or and not 关键词, 不支持and or混用 <br/>
     * 支持如下写法< shiro:hasPermission name="showcase:tree:create or showcase:tree:update or showcase:tree:delete">
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        if (permission.contains(OR_OPERATOR)) {
            String[] permissions = permission.split(OR_OPERATOR);
            for (String orPermission : permissions) {
                if (isPermittedWithNotOperator(principals, orPermission)) {
                    return true;
                }
            }
            return false;
        } else if (permission.contains(AND_OPERATOR)) {
            String[] permissions = permission.split(AND_OPERATOR);
            for (String orPermission : permissions) {
                if (!isPermittedWithNotOperator(principals, orPermission)) {
                    return false;
                }
            }
            return true;
        } else {
            return isPermittedWithNotOperator(principals, permission);
        }
    }

    private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
        if (permission.startsWith(NOT_OPERATOR)) {
            return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
        } else {
            return super.isPermitted(principals, permission);
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // TODO Auto-generated method stub
        return null;

    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
