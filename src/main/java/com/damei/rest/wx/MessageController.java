package com.damei.rest.wx;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
