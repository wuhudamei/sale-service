package com.rocoinfo.rest.wx;

import com.rocoinfo.common.BaseController;
import com.rocoinfo.weixin.util.SignUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <dl>
 * <dd>Description: 微信接入接口</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/20 上午11:03</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/wx")
public class SignController extends BaseController {

    /**
     * 微信接入校验：<br >
     * http://mp.weixin.qq.com/wiki/8/f9a0b8382e0b77d87b3bcc1ce6fbc104.html
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @param response  response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Object sign(@RequestParam String signature,
                       @RequestParam String timestamp,
                       @RequestParam String nonce,
                       @RequestParam String echostr,
                       HttpServletResponse response) {
        if (StringUtils.isNoneBlank(signature, timestamp, nonce, echostr) &&
                SignUtils.checkSignature(signature, timestamp, nonce)) {
            try (PrintWriter writer = response.getWriter()) {
                writer.write(echostr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
