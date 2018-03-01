package com.damei.rest.wx;

import com.damei.common.BaseController;
import com.damei.entity.sale.account.User;
import com.damei.entity.sale.account.UserBind;
import com.damei.service.sale.account.LoginService;
import com.damei.service.sale.account.UserBindService;
import com.damei.dto.StatusDto;
import com.damei.service.sale.account.UserService;
import com.damei.utils.WebUtils;
import com.rocoinfo.weixin.api.OAuthApi;
import com.rocoinfo.weixin.util.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/api")
public class WeChatDispatcherController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserBindService userBindService;
    @Autowired
    private LoginService loginService;

    /**
     * 重定向到登录页面
     */
    private static final String LOGIN_PAGE = "redirect:/wx/login";

    /**
     * 重定向到首页
     */
    private static final String INDEX_PAGE = "redirect:/index";

    @RequestMapping(value = "/wx/dispatcher", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {
        // 如果已经登录 直接跳转到首页
        if (WebUtils.isLogin(request)) {
            return INDEX_PAGE;
        }

        // 获取微信端传的code
        String code = request.getParameter("code");
        // 如果code为null，跳转到登录页面
        if (StringUtils.isBlank(code)) {
            return LOGIN_PAGE;
        }

        // 根据code获取openid
        String openid = OAuthApi.getOpenid(code);
        // 如果openid为空，跳转到登录页面
        if (StringUtils.isBlank(openid)) {
            return LOGIN_PAGE;
        }

        // 根据openid查询绑定,无绑定关系跳转到首页
        UserBind userBind = this.userBindService.getByOidAndPlatform(openid, UserBind.Platfrom.WECHAT);
        if (userBind == null) {
            Session session = WebUtils.getSession();
            session.setAttribute("openid", openid);
            return LOGIN_PAGE;
        }

        // 已绑定，查询用户信息，执行登录逻辑
        User user = this.userService.getById(userBind.getUser().getId());
        if (user != null) {
            StatusDto res = (StatusDto) this.loginService.login(user.getAccount(), null, true, request, response);
            if ("1".equals(res.getCode())) {
                return INDEX_PAGE;
            }
        }

        return LOGIN_PAGE;
    }
}
