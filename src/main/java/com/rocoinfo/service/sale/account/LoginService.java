package com.rocoinfo.service.sale.account;

import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.shiro.token.CustomUsernamePasswordToken;
import com.rocoinfo.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <dl>
 * <dd>Description: </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2016/10/27 13:32</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
@Service
public class LoginService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LogoutService logoutService;

    /**
     * @return 登录成功返回User对象，登录失败返回状态码为400的ResponseEntity对象
     */
    public Object login(String loginName,
                        String password,
                        boolean ssoLogin,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        try {
            login(new CustomUsernamePasswordToken(loginName, password, ssoLogin));

            //将当前登录用户信息返回
            ShiroUser loginUser = WebUtils.getLoggedUser();

            return StatusDto.buildDataSuccessStatusDto("登录成功", loginUser);

        } catch (AuthenticationException e) {

            return StatusDto.buildFailureStatusDto(getErrorResponseEntity(e));
        }
    }

    public void login(AuthenticationToken token) throws AuthenticationException {
        Subject subject = SecurityUtils.getSubject();
        //如果已登录，先退出
        if (subject.getPrincipal() != null) {
            logoutService.logout();
        }
        //登录
        subject.login(token);
    }

    /**
     * 获取登录失败原因
     *
     * @param e 异常类型
     * @return 返回失败描述
     */
    private String getErrorResponseEntity(AuthenticationException e) {
        final String loginFail = "[账户/密码]不正确或账户被锁定";

        if (e instanceof IncorrectCredentialsException) {
            return "账户或密码不正确";
        }
        if (e instanceof ExpiredCredentialsException) {
            return "密码已过期";
        }
        if (e instanceof CredentialsException) {
            return "密码验证失败";
        }
        if (e instanceof UnknownAccountException) {
            return "账户或密码不正确";
        }
        if (e instanceof LockedAccountException) {
            return "账户已被锁定";
        }
        if (e instanceof DisabledAccountException) {
            return "账户已被禁用";
        }
        if (e instanceof ExcessiveAttemptsException) {
            return "尝试次数太多";
        }
        if (e instanceof AccountException) {
            String error = e.getMessage();
            if (StringUtils.isBlank(error)) {
                error = loginFail;
            }
            return error;
        }
        logger.debug(loginFail, e);
        return loginFail;
    }
}
