package cn.damei.rest.wx;

import cn.damei.common.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
