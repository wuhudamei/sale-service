package com.rocoinfo.rest.sale.account;

import com.rocoinfo.Constants;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.service.sale.account.LoginService;
import com.rocoinfo.service.sale.account.LogoutService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.HttpUtils;
import com.rocoinfo.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <dl>
 * <dd>Description: 登录,登出接口Controller</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-3-7 17:17:56</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/mdni")
public class LoginController extends BaseController {
    private static final String JOB_NUM = "jobNum";
    private static final String REDIRECT_URL = "redirectUrl";
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);//日志

    @Autowired
    private LoginService loginService;

    @Autowired
    private LogoutService logoutService;

    /**
     * 用户登录接口
     *
     * @param userAccount 用户账号
     * @param passWord    用户密码
     * @param request     请求对象
     * @param response    相应对象
     * @return 成功返回用户信息 失败返回错误提示
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestParam(value = "userAccount") String userAccount,
                        @RequestParam(value = "passWord") String passWord,
                        HttpServletRequest request, HttpServletResponse response) {


        if (StringUtils.isBlank(userAccount) || StringUtils.isBlank(passWord)) {
            return StatusDto.buildFailureStatusDto("用户名和密码不能为空");
        }

        return loginService.login(userAccount, passWord, false, request, response);
    }

    /**
     * 登出
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/logout")
    public Object logout(HttpServletRequest req) {
    	ShiroUser loggedUser = WebUtils.getLoggedUser();
		if(loggedUser != null){
			try {
				Map<String,String> paramMap = new HashMap<String,String>();
				paramMap.put("appid", PropertyHolder.getOauthCenterAppid());
				paramMap.put("secret", PropertyHolder.getOauthCenterSecret());
				paramMap.put("username", loggedUser.getOrgCode());
				String logoutStr = HttpUtils.post(PropertyHolder.getOauthCenterUrl() + Constants.OAUTH_LOGOUT_URL,paramMap);
				logger.error("Logout Message : " + logoutStr);
				logoutService.logout();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:/logout");
    }

    /**
     * @Ryze
     * @date 2017-7-31
     * 修改密码  调用认证中心的接口
     */
    @RequestMapping("/updPwd")
    public Object updPwd(HttpServletRequest request) {
        String hostName = getHostName(request);
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        if (loggedUser != null) {
            String callBack = hostName + "/mdni/logout";
            String updateUrl =  PropertyHolder.getOauthCenterUrl() + Constants.OAUTH_PASSWORD_URL + "?" + JOB_NUM + "=" + loggedUser.getOrgCode() + "&" +
                    REDIRECT_URL + "=" + callBack;
            return new ModelAndView("redirect:"+updateUrl );
        }else{
            return StatusDto.buildDataFailureStatusDto("用户未登录");
        }


    }
    /**@Ryze
     * @date 2017-7-31
     * //获取 域名
     * @param request
     * @return
     */
    private String getHostName(HttpServletRequest request){
        //获取 域名
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).toString();
        return tempContextUrl;
    }
}
