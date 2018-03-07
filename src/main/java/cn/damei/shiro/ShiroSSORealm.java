package cn.damei.shiro;


import cn.damei.service.sale.account.UserService;
import cn.damei.shiro.token.CustomUsernamePasswordToken;
import cn.damei.entity.sale.account.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;

public class ShiroSSORealm extends ShiroAbstractRealm {

    private UserService userService;

    /**
     * 设置校验规则
     *
     * @param credentialsMatcher 自定义校验规则
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher((token, info) -> {
            if (token instanceof CustomUsernamePasswordToken) {
                CustomUsernamePasswordToken customToken = (CustomUsernamePasswordToken) token;
                if (StringUtils.isNotBlank(customToken.getUsername()) && customToken.isSsoLogin() && info != null) {
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CustomUsernamePasswordToken token = (CustomUsernamePasswordToken) authenticationToken;
        User user = this.userService.getAllUserInfoByAccount(token.getUsername());
        if (user != null) {
            ShiroUser shiroUser = new ShiroUser(user.getId(),user.getAccount(), user.getDepCode(), user.getOrgCode(),
                    user.getName(), user.getPhone(),
                    (user.getCompany() != null && user.getCompany().getId() != null) ? user.getCompany().getId().toString() : "",
                    (user.getCompany() != null && user.getCompany().getOrgCode() != null) ? user.getCompany().getOrgCode() : "",
                    (user.getDepartment() != null && user.getDepartment().getId() != null) ? user.getDepartment().getId().toString() : "",
                    (user.getDepartment() != null && user.getDepartment().getOrgCode() != null) ? user.getDepartment().getOrgCode() : "",
                    (user.getDepartment() != null && user.getDepartment().getOrgName() != null) ? user.getDepartment().getOrgName() : "",
                    (user.getDepartment() != null && user.getDepartment().getDepType() != null) ? user.getDepartment().getDepType(): null,
                    user.getDepartmentHead(),token.getRoles(), token.getPermission());
            return new SimpleAuthenticationInfo(shiroUser, null, getName());
        }
        return null;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof CustomUsernamePasswordToken) {
            return ((CustomUsernamePasswordToken) token).isSsoLogin();
        }
        return false;
    }

    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
