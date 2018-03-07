package cn.damei.rest.wx;

import cn.damei.common.BaseController;
import cn.damei.service.sale.account.LoginService;
import cn.damei.service.sale.account.UserBindService;
import cn.damei.utils.WebUtils;
import cn.damei.dto.StatusDto;
import cn.damei.entity.sale.account.User;
import cn.damei.entity.sale.account.UserBind;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController(value = "WeChatLoginController")
@RequestMapping(value = "/api")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserBindService userBindService;


    @RequestMapping(value = "/wx/login", method = RequestMethod.POST)
    public Object login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletRequest request,
                        HttpServletResponse response) {

        if (StringUtils.isAnyBlank(username, password))
            return StatusDto.buildFailureStatusDto("用户名和密码不能为空");

        StatusDto res = (StatusDto) loginService.login(username, password, false, request, response);
        // 登录成功建立绑定关系
        if ("1".equals(res.getCode())) {
            // 如果登录成功，则将用户id和微信openid绑定
            Long userId = WebUtils.getLoggedUser().getId();
            String openid = String.valueOf(WebUtils.getSession().getAttribute("openid"));
            UserBind bind = new UserBind(new User(userId), openid, UserBind.Platfrom.WECHAT, new Date());
            this.userBindService.insert(bind);
        }

        return res;
    }
}
