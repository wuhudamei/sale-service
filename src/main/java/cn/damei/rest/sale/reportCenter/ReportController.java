package cn.damei.rest.sale.reportCenter;

import cn.damei.common.BaseController;
import cn.damei.entity.sale.dameiorganization.DameiOrganization;
import cn.damei.service.sale.dameiorganization.DameiOrganizationService;
import cn.damei.service.sale.report.ReportNissinService;
import cn.damei.service.sale.workorder.WorkOrderService;
import cn.damei.utils.DateUtils;
import cn.damei.utils.SignUtil;
import com.alibaba.fastjson.JSONObject;
import cn.damei.dto.StatusDto;
import cn.damei.entity.sale.report.ReportNissin;
import com.google.common.collect.Maps;
import cn.damei.common.PropertyHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@SuppressWarnings("all")
@RequestMapping("/reportForCall")
public class ReportController extends BaseController {

    public static final String COMPANY_ID = "companyId";
    public static final String DATE = "date";
    public static final String IS_GROUP = "isGroup";
    @Autowired
    WorkOrderService workOrderService;
    @Autowired
    ReportNissinService reportNissinService;
    @Autowired
    DameiOrganizationService dameiOrganizationService;
    /**
     * 工单报表 接口调用
     *
     * @param departmentId 部门
     * @param companyId    集团->分公司  分公司->部门
     * @param isGroup      是不是集团
     * @return
     */
    @RequestMapping(value = "/workReturn", method = RequestMethod.POST)
    public Object workOrderReturn(@RequestParam(value = "key") String key,
                                  @RequestParam(value = "date") String date,
                                  @RequestParam(value = "companyId") String companyId) {

        // 非空验证
        if (StringUtils.isBlank(date)
                ||StringUtils.isBlank(key)
                ||StringUtils.isBlank(companyId)) {
            logger.debug("错误[401]必填参数为空");
            return StatusDto.buildStatusDtoWithCode("401", "必填参数为空");
        }

        JSONObject reqObj = new JSONObject();
        reqObj.put("key", key);
        reqObj.put("date", date);
        reqObj.put("companyId", companyId);

        String[] parameterArr = new String[]{"date=" + date, "companyId=" + companyId};
        // 验签
        if (!SignUtil.checkSignMD5AndKey(reqObj, parameterArr, key, PropertyHolder.getOaMd5())) {
            logger.debug("错误[406]签名认证失败");
            return StatusDto.buildStatusDtoWithCode("406", "签名认证失败");
        }
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        PageRequest pageable = new PageRequest(1, 1, null);
        params.put(DATE, DateUtils.parse(date,"yyyy-MM-dd") );
        params.put(COMPANY_ID, Integer.valueOf(companyId).intValue());
        List<ReportNissin> reportNissinList = reportNissinService.findReportNissinList(params);
        //统计
        if(reportNissinList!=null && reportNissinList.size()>0){
            ReportNissin reportNissin = new ReportNissin();
            reportNissin.setOrgName("总计");
             Long daySize=0L;
             Long dayAssign=0L;
             Long dayRefusedAgain=0L;
             Long dayCreate=0L;
             Long dayReceive=0L;
             Long monthMaturity=0L;
             Long monthNoProcessing=0L;
             Double monthProcessingRate=0d;
             Long monthSize=0L;
             Long monthSolve=0L;
             Long monthNoSolve=0L;
             Double monthSolveRate=0d;
             Long visitSatisfied=0L;
             Long visitCommonly=0L;
             Long visitUnsatisfied=0L;
             Long visitInvalid=0L;
             Long visitFail=0L;
             Long visitCompleted=0L;
             Long visitUnsatisfiedComplain=0L;
             Double monthUnsatisfiedComplainRate=0d;
            for (ReportNissin re:reportNissinList) {
                daySize+=re.getDaySize();
                dayAssign+=re.getDayAssign();
                dayRefusedAgain+=re.getDayRefusedAgain();
                dayCreate+=re.getDayCreate();
                dayReceive+=re.getDayReceive();
                monthMaturity+=+re.getMonthMaturity();
                monthNoProcessing+=re.getMonthNoProcessing();
                monthSize+=re.getMonthSize();
                monthSolve+=re.getMonthSolve();
                monthNoSolve+=re.getMonthNoSolve();
                visitSatisfied+=re.getVisitSatisfied();
                visitCommonly+=re.getVisitCommonly();
                visitUnsatisfied+=re.getVisitUnsatisfied();
                visitInvalid+=re.getVisitInvalid();
                visitFail+=re.getVisitFail();
                visitCompleted+=re.getVisitCompleted();
                visitUnsatisfiedComplain+=re.getVisitUnsatisfiedComplain();
            }
            reportNissin.setDaySize(daySize);
            reportNissin.setDayAssign(dayAssign);
            reportNissin.setDayRefusedAgain(dayRefusedAgain);
            reportNissin.setDayCreate(dayCreate);
            reportNissin.setDayReceive(dayReceive);
            reportNissin.setMonthMaturity(monthMaturity);
            reportNissin.setMonthNoProcessing(monthNoProcessing);
            //
            monthProcessingRate=0d;
            long l = monthMaturity - monthNoProcessing;
            if(monthMaturity!=0){
            monthProcessingRate=getDoubleForTwoSize(Double.longBitsToDouble(l)/Double.longBitsToDouble(monthMaturity)*100);
            }
            reportNissin.setMonthProcessingRate(monthProcessingRate);
            reportNissin.setMonthSize(monthSize);
            reportNissin.setMonthSolve(monthSolve);
            reportNissin.setMonthNoSolve(monthNoSolve);
            //
            monthSolveRate=0d;
            if(monthSize!=0){
                monthSolveRate=getDoubleForTwoSize(Double.longBitsToDouble(monthSolve)/Double.longBitsToDouble(monthSize)*100);
            }
            reportNissin.setMonthSolveRate(monthSolveRate);
            reportNissin.setVisitSatisfied(visitSatisfied);
            reportNissin.setVisitCommonly(visitCommonly);
            reportNissin.setVisitUnsatisfied(visitUnsatisfied);
            reportNissin.setVisitInvalid(visitInvalid);
            reportNissin.setVisitFail(visitFail);
            reportNissin.setVisitCompleted(visitCompleted);
            reportNissin.setVisitUnsatisfiedComplain(visitUnsatisfiedComplain);
            //
            monthUnsatisfiedComplainRate=0d;
            //未执行率
            if (l!= 0) {
                monthUnsatisfiedComplainRate = getDoubleForTwoSize(Double.longBitsToDouble(visitUnsatisfiedComplain) / Double.longBitsToDouble(l)*100);
            }
            reportNissin.setMonthUnsatisfiedComplainRate(monthUnsatisfiedComplainRate);
            reportNissinList.add(reportNissin);
        }
        return StatusDto.buildDataSuccessStatusDto(reportNissinList);
    }

    /**
     * 保留两位小数
     * @param a
     * @return
     */
    private double getDoubleForTwoSize(double a){
        BigDecimal b=new BigDecimal(a);
        return b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    @RequestMapping(value = "/depList", method = RequestMethod.POST)
    public Object depReport(@RequestParam(value = "companyId") String companyId,
                            @RequestParam(value = "key") String key) {
        // 非空验证
        if (StringUtils.isBlank(companyId)) {
            logger.debug("错误[401]必填参数为空");
            return StatusDto.buildStatusDtoWithCode("401", "必填参数为空");
        }
        JSONObject reqObj = new JSONObject();
        reqObj.put("key", key);
        reqObj.put("companyId", companyId);

        String[] parameterArr = new String[]{"companyId=" + companyId};
        // 验签
        if (!SignUtil.checkSignMD5AndKey(reqObj, parameterArr, key, PropertyHolder.getOaMd5())) {
            logger.debug("错误[406]签名认证失败");
            return StatusDto.buildStatusDtoWithCode("406", "签名认证失败");
        }
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put(COMPANY_ID, Integer.valueOf(companyId).intValue());
        List<DameiOrganization> company = dameiOrganizationService.findCompany(Integer.valueOf(companyId));
        return StatusDto.buildDataSuccessStatusDto(company);
    }


}
