package com.rocoinfo.utils;

import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;

/**
 * <dl>
 * <dd>Description: </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2016/10/27 19:49</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
public class WebUtils {

    /**
     * 加上threadSession解决shiro一次请求多次从redis读取session的问题 解决完之后:每次非静态资源请求读取一次session,静态资源请求不会读取session
     */
    public static final ThreadLocal threadSession = new ThreadLocal();

    private WebUtils() {
    }

    /**
     * 返回站点访问Base路径
     *
     * @return http://localhost:8080/ctxPath
     */
    public static String getBaseSiteUrl(HttpServletRequest request) {
        final StringBuilder basePath = new StringBuilder();
        basePath.append(request.getScheme()).append("://").append(request.getServerName());
        if (request.getServerPort() != 80) {
            basePath.append(":").append(request.getServerPort());
        }
        basePath.append(request.getContextPath());
        return basePath.toString();
    }

    /**
     * 判断当前是否有用户登录
     *
     * @param req request
     * @return
     */
    public static boolean isLogin(HttpServletRequest req) {
        if (getLoggedUser() != null)
            return true;
        return false;
    }


    /**
     * 获取登录用户信息
     *
     * @return 登录用户信息
     */
    public static ShiroUser getLoggedUser() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Object principal = subject.getPrincipal();
            return (ShiroUser) principal;
        } catch (UnavailableSecurityManagerException ex) {
            return null;
        }
    }

    /**
     * 获取当前登录用户id
     *
     * @return
     */
    public static Long getLoggedUserId() {
        ShiroUser user = getLoggedUser();
        return user == null ? null : user.getId();
    }

    /**
     * 获取由shiro管理的session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取文件上传的访问路径
     *
     * @param path
     * @return
     */
    public static String getUploadFilePath(String path) {
        return PropertyHolder.getUploadBaseUrl() + path;
    }

    /**
     * 获取文件上传的绝对访问路径
     *
     * @param imagePath
     * @return
     */
    public static String getFullUploadFilePath(String imagePath) {
        return PropertyHolder.getBaseurl() + PropertyHolder.getUploadBaseUrl() + imagePath;
    }

}
