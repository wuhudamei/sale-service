package cn.damei.common;

import cn.damei.dto.StatusDto;
import cn.damei.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
@SuppressWarnings("all")
public abstract class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({Exception.class})
    public Object exception(Exception ex) {
        logger.error("异常:", ex);
        return StatusDto.buildDataFailureStatusDto("操作失败");
    }

    protected Object buildResp(HttpServletResponse response, boolean right, String msg) {
        response.setCharacterEncoding("UTF-8");
        Map<String, String> res = new HashMap<>();
        if (right)
            res.put("code", "1");
        else
            res.put("code", "0");

        res.put("msg", msg);
        try (PrintWriter write = response.getWriter()) {
            write.write(JsonUtils.toJson(res));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
