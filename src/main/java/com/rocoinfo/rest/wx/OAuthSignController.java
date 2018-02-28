package com.rocoinfo.rest.wx;

import com.rocoinfo.common.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <dl>
 * <dd>Description: 微信授权域名回调验证</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/20 下午3:41</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
@RestController
@RequestMapping("/MP_verify_KznZROUB9KVvgvAK.txt")
public class OAuthSignController extends BaseController {

    /**
     * 微信授权回调需要配置一个校验文件，只有验证通过才能配置回调域名
     *
     * @param response 直接返回文件内容
     * @return
     */
    @RequestMapping
    public Object sign(HttpServletResponse response) {
        try (PrintWriter writer = response.getWriter()) {
            writer.write("KznZROUB9KVvgvAK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
