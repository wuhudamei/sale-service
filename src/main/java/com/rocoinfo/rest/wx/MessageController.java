package com.rocoinfo.rest.wx;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <dl>
 * <dd>Description: </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/20 上午11:24</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/wx")
public class MessageController {

    /**
     * 微信消息处理
     *
     * @param request  request
     * @param response response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Object handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream is = request.getInputStream();
        List<String> list = IOUtils.readLines(is);
        System.out.println(list);
        return null;
    }
}
