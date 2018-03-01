package com.damei.rest.wx;

import com.damei.service.sale.report.ReportAuthorityService;
import com.damei.entity.sale.report.ReportAuthority;
import com.rocoinfo.weixin.api.OAuthApi;
import com.rocoinfo.weixin.api.UserApi;
import com.rocoinfo.weixin.model.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

@Controller
@RequestMapping("")
public class WechatCodeController {

    @Autowired
    private ReportAuthorityService reportAuthorityService;

    @RequestMapping("/pageAuthorizeHandler")
    public void pageAuthorizeHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String redirectUri = request.getParameter("redirect_uri");

        if (code == null) {
            //第一次跳转过来 将redirectUri缓存到Session中
            String appid = request.getParameter("appid");
            String scope = request.getParameter("scope");

            request.getSession().setAttribute("redirectUri", redirectUri);
            String URL = request.getRequestURL().toString();
            redirectUri = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
                    appid, URLEncode(URL), scope, state);

        } else {
            //第二次跳转过来,从Session中取出redirectUri,并删除redirectUri属性
            redirectUri = request.getSession().getAttribute("redirectUri") + "?code=" + code + "&state=" + state;
            request.getSession().removeAttribute("redirectUri");
        }
        response.sendRedirect(redirectUri);
    }

    /**
     * encode url
     */
    private String URLEncode(String s) {
        String res = null;
        try {
            res = URLEncoder.encode(s, "UTF-8");
            return res;
        } catch (UnsupportedEncodingException var3) {
            throw new RuntimeException("url encoding failed!");
        }
    }

    /**
     * 微信报表菜单绑定的页面
     *
     * @param request
     * @param response
     * @return 有权限：跳转到查看页面 没有权限：跳转到没有权限提醒页面
     */
    @RequestMapping("/report")
    public String report(HttpServletRequest request, HttpServletResponse response) {

        String code = request.getParameter("code");
        if (code == null) {
            return "admin/report/error";
        }
        String openId = OAuthApi.getOpenid(code);
        if (openId == null) {
            return "admin/report/error";
        }
        //根据openId找用户信息sale_report_authority
        ReportAuthority reportAuthority = reportAuthorityService.getByOpenId(openId);
        if (reportAuthority != null)
            return "admin/report/report";

        return "admin/report/error";
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Object getUserInfo(String openId) {
        ApiResult result = UserApi.getUseropenids(openId);
        return result.getRawJson();
    }

    @RequestMapping("/getUsersInfo")
    @ResponseBody
    public Object getUsersInfo(@RequestParam(value = "opendIds") String[] openIds) {

        ApiResult result = UserApi.batchGet(Arrays.asList(openIds));

        System.out.println(result.getRawJson());

        return result.getRawJson();

    }
}
