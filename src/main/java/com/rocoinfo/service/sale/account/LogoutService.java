package com.rocoinfo.service.sale.account;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * <dl>
 * <dd>Description: 登出service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/8 21:12</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
@Service
public class LogoutService {

    public void logout() {

        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() != null) {
            subject.logout();
        }
    }
}
