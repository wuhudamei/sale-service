package cn.damei.rest.sale.workorder;

import cn.damei.dto.StatusDto;
import cn.damei.enumeration.OrderStatus;
import cn.damei.service.sale.workorder.WorkOrderService;
import cn.damei.utils.JsonUtils;
import cn.damei.utils.SignUtil;
import com.alibaba.fastjson.JSONObject;
import cn.damei.entity.sale.workorder.WorkOrderServiceDto;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/service")
public class WorkOrderServiceController {
    private static Logger logger = LoggerFactory.getLogger(WorkOrderServiceController.class);//日志
    @Autowired
    private WorkOrderService workOrderService;

    //接口常量--门店编码对应当前门店id
    private static HashMap<String, Long> codeAndId = new HashMap<String, Long>(5);
    ;

    static {
        //参数门店code对应当前系统门店id
        //北京丰台店
        codeAndId.put("100001", 99L);
        //杭州门店
        codeAndId.put("100002", 126L);
        //天津南开店
        codeAndId.put("100003", 121L);
        //深圳龙华店
        codeAndId.put("100004", 128L);
        //太原门店
        codeAndId.put("100005", 100L);
    }

    /**
     * 查询工单接口:
     *
     * @param key
     * @param startDate         开始日期
     * @param endDate           结束日期
     * @param liableCompanys    责任公司(多个之间使用逗号拼接)
     * @param liableDepartments 责任部门(多个之间使用逗号拼接)
     * @return
     */
    @RequestMapping(value = "/orderList")
    public Object orderService(@RequestParam String key, @RequestParam String startDate,
                               @RequestParam String endDate, @RequestParam String liableDepartments,
                               @RequestParam String liableCompanys) {
        // 非空验证
        if (StringUtils.isBlank(key)
                || StringUtils.isBlank(startDate)
                || StringUtils.isBlank(endDate)
                || StringUtils.isBlank(liableDepartments)
                || StringUtils.isBlank(liableCompanys)) {

            logger.debug("错误[401]必填参数为空");
            return StatusDto.buildStatusDtoWithCode("401", "必填参数为空");
        }
        JSONObject reqObj = new JSONObject();
        reqObj.put("key", key);
        reqObj.put("startDate", startDate);
        reqObj.put("endDate", endDate);
        reqObj.put("liableDepartment", liableDepartments);
        reqObj.put("liableCompany", liableCompanys);

        String[] parameterArr = new String[]{"startDate=" + startDate, "endDate=" + endDate
                , "liableDepartments=" + liableDepartments, "liableCompanys=" + liableCompanys};
        // 验签
        if (!SignUtil.checkSignMD5(reqObj, parameterArr, key)) {
            logger.debug("错误[406]签名认证失败");
            return StatusDto.buildStatusDtoWithCode("406", "签名认证失败");
        }


        String[] liableDeptArr = liableDepartments.split(",");
        String[] liableCompanyCodes = liableCompanys.split(",");
        Map<String, Object> params = new HashedMap();

        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("liableDeptArr", liableDeptArr);
        ArrayList<Long> liableCompanyArr = new ArrayList<Long>();
        //处理liableCompanyArr 
        for (String code : liableCompanyCodes) {
            //从映射中取出对应的id,并存入集合中
            liableCompanyArr.add(codeAndId.get(code));
        }
        params.put("liableCompanyArr", liableCompanyArr);
        //查询
        List<WorkOrderServiceDto> workOrderList = this.workOrderService.orderService(params);

        return StatusDto.buildDataSuccessStatusDto(workOrderList);
    }



        /**
         *  用于远程调用修改工单状态
         * @return
         */
    @RequestMapping(value = "/orderUpdate")
    public Object orderUpdate(String result) {
        if(result==null){
            logger.debug("错误[401]必填参数为空");
            return StatusDto.buildStatusDtoWithCode("401", "必填参数为空");
        }else {
            Map<String, String> stringStringMap = JsonUtils.fromJsonAsMap(result, String.class, String.class);
            String key = stringStringMap.get("key");
            String status = stringStringMap.get("status");
            String operationUser = stringStringMap.get("operationUser");
            String workorderId = stringStringMap.get("workorderId");
            String operationTime = stringStringMap.get("operationTime");
            String treamentTime = stringStringMap.get("treamentTime");
            String remarks = stringStringMap.get("remarks");
            // 非空验证
            if (StringUtils.isBlank(status)
                    || StringUtils.isBlank(operationUser)
                    || StringUtils.isBlank(workorderId)
                    || StringUtils.isBlank(operationTime)
                    || StringUtils.isBlank(key)) {
                logger.debug("错误[401]必填参数为空");
                return StatusDto.buildStatusDtoWithCode("401", "必填参数为空");
            }
            //驳回
            if (status.equals(OrderStatus.REFUSED.toString())) {
                if (StringUtils.isBlank(remarks)) {
                    logger.debug("错误[401]必填参数为空");
                    return StatusDto.buildStatusDtoWithCode("401", "必填参数为空");
                }
            }
            //处理
            else if (status.equals(OrderStatus.PROCESSING.toString())) {
                if (StringUtils.isBlank(treamentTime) || StringUtils.isBlank(remarks)) {
                    logger.debug("错误[401]必填参数为空");
                    return StatusDto.buildStatusDtoWithCode("401", "必填参数为空");
                }
            }
            JSONObject reqObj = new JSONObject();
            reqObj.put("key", key);
            reqObj.put("status", status);
            reqObj.put("operationUser", operationUser);
            reqObj.put("workorderId", workorderId);
            reqObj.put("operationTime", operationTime);
            String[] parameterArr = new String[]{"status=" + status, "operationUser=" + operationUser
                    , "workorderId=" + workorderId, "operationTime=" + operationTime};
            // 验签
            if (!SignUtil.checkSignMD5(reqObj, parameterArr, key)) {
                logger.debug("错误[406]签名认证失败");
                return StatusDto.buildStatusDtoWithCode("406", "签名认证失败");
            }

            Map<String, Object> params = new HashedMap();
            params.put("status", status);
            params.put("operationUser", operationUser);
            params.put("workorderId", workorderId);
            params.put("operationTime", operationTime);
            params.put("treamentTime", treamentTime);
            params.put("remarks", remarks);
            return workOrderService.remoteWorkeOrder(params);
        }
    }

}
