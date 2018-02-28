package com.rocoinfo.common;

import com.rocoinfo.Constants;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <dl>
 * <dd>描述:</dd>
 * <dd>公司: 大城若谷信息技术有限公司</dd>
 * <dd>创建时间：15/8/11 下午3:47</dd>
 * <dd>创建人： asher</dd>
 * </dl>
 */
@SuppressWarnings("all")
public abstract class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({Exception.class})
    public Object exception(Exception ex) {
        logger.error("异常:", ex);
        return StatusDto.buildDataFailureStatusDto("操作失败");
    }

    /**
     * 构建返回值(与微信端交互用)
     *
     * @param response
     * @param right    true, code值为"1" ,false code值为"0"
     * @param msg      消息
     */
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
