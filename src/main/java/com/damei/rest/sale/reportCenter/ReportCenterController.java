package com.damei.rest.sale.reportCenter;

import com.damei.common.BaseController;
import com.damei.dto.StatusBootTableDto;
import com.damei.dto.StatusDto;
import com.damei.dto.WorkOrderReturnDto;
import com.damei.enumeration.OrderExecuteStatus;
import com.damei.enumeration.OrderStatus;
import com.damei.service.sale.workorder.WorkOrderService;
import com.damei.shiro.ShiroUser;
import com.damei.utils.DateUtils;
import com.damei.utils.WebUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@RestController
@SuppressWarnings("all")
@RequestMapping("/api/reportCenter")
public class ReportCenterController extends BaseController {
    //公司id
    private final String COMPANYID = "companyId";
    //部门id
    private final String DEPARTMENTID = "departmentId";
    //部门名字
    private final String ORGNAME = "orgName";
    //公司名字
    private final String COMPANYNAME = "companyName";
    //责任部门id -->传参用
    private  final String LIABLEDEPARTMENT = "liableDepartment";
    //部门的 id   -->传参用
    private final String LIABLECOMPANY = "liableCompany";
    //开始时间 -->传参数
    private final String BEGINDATE = "beginDate";
    //结束时间 -->传参数
    private final String ENDDATE = "endDate";
    //操作类型-->传参数
    private final String OPERATIONTYPE = "operationType";
    //工单现在状态
    private final String ORDERSTATUS = "orderStatus";
    //不满意 接不接单
    private final String COPYFLAG = "copyFlag";
    //查询的 标记
    private final   String FLAG = "flag";
    //标记的值
    private final   String Y = "Y";
    private final   String N = "N";
    //员工id -->传参用
    private final   String ID = "id";
    //首次派单
    private final String FIRSTTIMECOUNT = "firstTimeCount";
    //多次来电
    private final String REMINDERCOUNT = "reminderCount";
    //咨询完毕
    private final String OVERTALK = "overTalk";
    //回访满意
    private final String SATISFACTIONVISIT = "satisfactionVisit";
    //回访不满意
    private final String NOTSATISFACTIONVISIT = "notSatisfactionVisit";
    //回访失败
    private final String FAILVISIT = "failVisit";
    //暂无评价
    private final String INVALIDVISIT = "invalidvisit";
    //个人业绩 操作类型
    private final String OPTIONTYPE = "optionType";
    @Autowired
    WorkOrderService workOrderService;

    /**
     * 工单回访统计
     *
     * @param departmentId 部门
     * @param companyId    分公司
     * @param isGroup      是不是集团
     * @return
     */
    @RequestMapping(value = "/workReturn", method = RequestMethod.GET)
    public Object workOrderReturn(@RequestParam(value = DEPARTMENTID, required = false) Long departmentId,
                                  @RequestParam(value = COMPANYID, required = false) Long companyId,
                                  @RequestParam(value = "isGroup", required = false) Boolean isGroup,
                                  @RequestParam(value = BEGINDATE, required = false) String beginDate,
                                  @RequestParam(value = ENDDATE, required = false) String endDate) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        PageRequest pageable = new PageRequest(1, 1, null);
        //isGroup->true  集团的话->传参数   isGroup->false非团的话->取当前的用户的公司
        if (isGroup != null && isGroup) {
            params.put(COMPANYID, companyId);
        } else {
            ShiroUser loggedUser = WebUtils.getLoggedUser();
            params.put(COMPANYID, loggedUser.getCompany());
        }
        params.put(DEPARTMENTID, departmentId);
        List<WorkOrderReturnDto> storeWorkOrderRetureReport = workOrderService.findStoreWorkOrderRetureReport(params);
        //根据部门分组
        Map<String, List<WorkOrderReturnDto>> collect = storeWorkOrderRetureReport.stream().collect(groupingBy(WorkOrderReturnDto::getOrgName));
        //统计相应的数值
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(2);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (Map.Entry<String, List<WorkOrderReturnDto>> entry : collect.entrySet()) {
            HashMap<String, Object> map = new HashMap<>();
            String orgName = entry.getKey();
            //部门名字
            map.put(ORGNAME, orgName);
            List<WorkOrderReturnDto> value = entry.getValue();
            //公司的名字
            Map<String, Object> paramCount = Maps.newHashMapWithExpectedSize(1);
            String liableCompany = null;
            String liableDepartment = null;
            if (value.size() > 0) {
                WorkOrderReturnDto workOrderReturnDto = value.get(0);
                map.put(COMPANYNAME, workOrderReturnDto.getLiableCompanyName());
                liableCompany = workOrderReturnDto.getLiableCompany();
                liableDepartment = workOrderReturnDto.getLiableDepartment();
                paramCount.put(LIABLEDEPARTMENT, Integer.valueOf(liableDepartment));
                paramCount.put(LIABLECOMPANY, Integer.valueOf(liableCompany));
                paramCount.put(BEGINDATE, beginDate);
                paramCount.put(ENDDATE, endDate);
                //应回访数：工单状态为已完成
                paramCount.put(OPERATIONTYPE, OrderExecuteStatus.FINISHORDER.toString());
                long shouldReturnVisitCount = workOrderService.getCountByOperationType(paramCount);
                map.put("shouldReturnVisitCount", shouldReturnVisitCount);
                //满意
                paramCount.put(OPERATIONTYPE, OrderExecuteStatus.VISIT.toString());
                paramCount.put(ORDERSTATUS, OrderStatus.SATISFIED.toString());
                long satisfactionCount = workOrderService.getCountByOperationType(paramCount);
                map.put("satisfactionCount", satisfactionCount);
                //不满意派单的
                paramCount.put(ORDERSTATUS, OrderStatus.UNSATISFIED.toString());
                paramCount.put(COPYFLAG, Y);
                long notSatisfiedCountY = workOrderService.getCountByOperationType(paramCount);
                map.put("notSatisfiedCountY", notSatisfiedCountY);
                //不满意不派单的
                paramCount.put(COPYFLAG, N);
                long notSatisfiedCountN = workOrderService.getCountByOperationType(paramCount);
                map.put("notSatisfiedCountN", notSatisfiedCountN);
                //不满意
                long notSatisfiedCount = notSatisfiedCountY + notSatisfiedCountN;
                //无效
                paramCount.put(ORDERSTATUS, OrderStatus.INVALIDVISIT.toString());
                paramCount.put(COPYFLAG, null);
                long invalidCount = workOrderService.getCountByOperationType(paramCount);
                map.put("invalidCount", invalidCount);
                //回访不成功
                paramCount.put(ORDERSTATUS, OrderStatus.FAILUREVISIT.toString());
                long unsuccessCount = workOrderService.getCountByOperationType(paramCount);
                map.put("unsuccessCount", unsuccessCount);
                //已回访量：工单状态为回访满意  回访不满意 暂无评价 回访不成功的和
                long haveReturned = satisfactionCount + notSatisfiedCount + invalidCount + unsuccessCount;
                map.put("haveReturned", haveReturned);
                //有效回访量 :回访满意  回访不满意的和
                long effectiveReturnVisit = satisfactionCount + notSatisfiedCount;
                map.put("effectiveReturnVisit", effectiveReturnVisit);
                Double returnRate = 0.0;
                Double returnVisitCompletionRate = 0.0;
                if (shouldReturnVisitCount != 0) {
                    //回访率：已经回访/应回访
                    returnRate = Double.longBitsToDouble(haveReturned) / Double.longBitsToDouble(shouldReturnVisitCount);
                    //回访完成率:（有效回访量+ 回访不成功的和）/应回访
                    returnVisitCompletionRate = Double.longBitsToDouble((effectiveReturnVisit + unsuccessCount)) / Double.longBitsToDouble(shouldReturnVisitCount);
                }
                map.put("returnRate", fmt.format(returnRate));
                map.put("returnVisitCompletionRate", fmt.format(returnVisitCompletionRate));

                long satisfactionRate = 0L;
                if (effectiveReturnVisit != 0) {
                    //满意率：回访满意/有效回访量
                    satisfactionRate = satisfactionCount / effectiveReturnVisit;
                }
                map.put("satisfactionRate", fmt.format(satisfactionRate));
            }
            int j=0;
            for (Map.Entry entry1:map.entrySet()) {
                Object value1 = entry1.getValue();
                if(value1 instanceof String){
                    String value11 = (String) value1;
                    if("0".equals(value11)||"0%".equals(value11)){
                        j++;
                    }
                }else if(value1 instanceof Long){
                    Long value11 = (Long) value1;
                    if(value11==0l){
                        j++;
                    }
                }
            }
            if(map.size()-2==j){

            }else{
                maps.add(map);
            }

        }
        PageImpl page = new PageImpl(maps, pageable, maps.size());
        return StatusBootTableDto.buildDataSuccessStatusDto(page);
    }

    /**
     * 工单处理
     *
     * @param departmentId 部门id
     * @param companyId    公司id
     * @param isGroup      是不是集团业务
     * @return
     */
    @RequestMapping(value = "/workDeal", method = RequestMethod.GET)
    public Object workOrderDeal(@RequestParam(value = DEPARTMENTID, required = false) Long departmentId,
                                @RequestParam(value = COMPANYID, required = false) Long companyId,
                                @RequestParam(value = "isGroup", required = false) Boolean isGroup,
                                @RequestParam(value = BEGINDATE, required = false) String beginDate,
                                @RequestParam(value = ENDDATE, required = false) String endDate) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        PageRequest pageable = new PageRequest(1, 1, null);
        //isGroup->true  集团的话->传参数   isGroup->false非团的话->取当前的用户的公司
        if (isGroup != null && isGroup) {
            params.put(COMPANYID, companyId);
        } else {
            ShiroUser loggedUser = WebUtils.getLoggedUser();
            params.put(COMPANYID, loggedUser.getCompany());
        }
        params.put(DEPARTMENTID, departmentId);
        List<WorkOrderReturnDto> storeWorkOrderRetureReport = workOrderService.findStoreWorkOrderRetureReport(params);
        //根据部门分组
        Map<String, List<WorkOrderReturnDto>> collect = storeWorkOrderRetureReport.stream().collect(groupingBy(WorkOrderReturnDto::getOrgName));
        //统计相应的数值
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(2);
        Date date = new Date();
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (Map.Entry<String, List<WorkOrderReturnDto>> entry : collect.entrySet()) {
            //参数 部门/公司  时间
            HashMap<String, Object> map = new HashMap<>();
            String orgName = entry.getKey();
            //部门
            map.put(ORGNAME, orgName);
            List<WorkOrderReturnDto> value = entry.getValue();
            String liableCompany = null;
            String liableDepartment = null;
            if (value.size() > 0) {
                WorkOrderReturnDto workOrderReturnDto = value.get(0);
                map.put(COMPANYNAME, workOrderReturnDto.getLiableCompanyName());
                liableCompany = workOrderReturnDto.getLiableCompany();
                liableDepartment = workOrderReturnDto.getLiableDepartment();
                Map<String, Object> paramCount = Maps.newHashMapWithExpectedSize(1);
                paramCount.put(LIABLEDEPARTMENT, Integer.valueOf(liableDepartment));
                paramCount.put(LIABLECOMPANY, Integer.valueOf(liableCompany));
                paramCount.put(BEGINDATE, beginDate);
                paramCount.put(ENDDATE, endDate);
                //工单数量
                Long workCount = workOrderService.getWorkCount(paramCount);
                map.put("workCount", workCount);
                //接收数量
                paramCount.put(OPERATIONTYPE, OrderExecuteStatus.RECEIVE.toString());
                Long receiveCount = workOrderService.getCountByOperationType(paramCount);
                map.put("receiveCount", receiveCount);
                //申诉数量
                paramCount.put(OPERATIONTYPE, OrderExecuteStatus.REJECT.toString());
                Long rejectCount = workOrderService.getCountByOperationType(paramCount);
                map.put("rejectCount", rejectCount);
                //转派数量
                paramCount.put(OPERATIONTYPE, OrderExecuteStatus.REJECTAGAIN.toString());
                Long rejectagain = workOrderService.getCountByOperationType(paramCount);
                map.put("rejectagain", rejectagain);
                //方案未反馈数量
                paramCount.put(OPERATIONTYPE, OrderExecuteStatus.OPERATION);
                long noFeedback = workCount - workOrderService.getCountByOperationType(paramCount);
                map.put("noFeedback", noFeedback);
                //超期未反馈量
                Long excessiveFeedback = workOrderService.getExcessiveFeedback(paramCount);
                map.put("excessiveFeedback", excessiveFeedback);
                //已完成数量
                paramCount.put(OPERATIONTYPE, OrderExecuteStatus.FINISHORDER.toString());
                long finiShorder = workOrderService.getCountByOperationType(paramCount);
                map.put("finiShorder", finiShorder);
                //超期未完成数量
                paramCount.put(FLAG, N);
                Long notOnSchedule = workOrderService.getOnSchedule(paramCount);
                map.put("notOnSchedule", notOnSchedule);
                //按期解决数量
                paramCount.put(FLAG, Y);
                Long onSchedule = workOrderService.getOnSchedule(paramCount);
                map.put("onSchedule", onSchedule);
                //到期应完成的数量
                Long expires = workOrderService.getExpires(paramCount);
                Double resolutionRate = 0.0;
                Double scheduledRate = 0.0;
                Double responsibilityDepartmentDismissed = 0.0;
                Double feedbackRate = 0.0;
                Double receiptRate = 0.0;
                if (expires != null && expires != 0) {
                    //解决率
                    resolutionRate = Double.longBitsToDouble(finiShorder) / Double.longBitsToDouble(expires);
                    //按期解决率
                    scheduledRate = Double.longBitsToDouble(onSchedule) / Double.longBitsToDouble(expires);
                }
                map.put("resolutionRate", fmt.format(resolutionRate));
                map.put("scheduledRate", fmt.format(scheduledRate));
                //责任部门转派率
                if (rejectCount != null && rejectCount != 0) {
                    responsibilityDepartmentDismissed = Double.longBitsToDouble(rejectagain) / Double.longBitsToDouble(rejectCount);
                }
                map.put("responsibilityDepartmentDismissed", fmt.format(responsibilityDepartmentDismissed));
                //接收率
                if (workCount != 0) {
                    receiptRate = Double.longBitsToDouble(workOrderService.getGoodReceive(paramCount)) / Double.longBitsToDouble(workCount);
                }
                map.put("receiptRate", fmt.format(receiptRate));

                long l = receiveCount - rejectCount;
                if (l != 0) {
                    //反馈及时率
                    feedbackRate = 1 - (Double.longBitsToDouble(excessiveFeedback) / Double.longBitsToDouble(l));
                }
                map.put("feedbackRate", fmt.format(feedbackRate));
            }

            int j=0;
            for (Map.Entry entry1:map.entrySet()) {
                Object value1 = entry1.getValue();
                if(value1 instanceof String){
                    String value11 = (String) value1;
                    if("0".equals(value11)||"0%".equals(value11)){
                        j++;
                    }
                }else if(value1 instanceof Long){
                    Long value11 = (Long) value1;
                    if(value11==0l){
                        j++;
                    }
                }
            }
            if(map.size()-2==j){

            }else{
                maps.add(map);
            }
        }
        PageImpl page = new PageImpl(maps, pageable, maps.size());
        return StatusBootTableDto.buildDataSuccessStatusDto(page);
    }

    /**
     * 个人业绩 报表
     * @return
     */

    @RequestMapping(value = "/personlReport", method = RequestMethod.GET)
    public Object personalReport() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        Long id = loggedUser.getId();
        Map<String, Map<String, Object>> maps = new HashMap<String, Map<String, Object>>();
        //查询今日
        HashMap<String, Object> dayMap = new HashMap<>();
        Date date = new Date();
        String now = format.format(date);
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put(ID, id);
        params.put(BEGINDATE, now);
        params.put(ENDDATE, now);
        //首次派单
        params.put(FLAG, Y);
        dayMap.put(FIRSTTIMECOUNT, workOrderService.getFirstTimeCount(params));
        //多次来电
        dayMap.put(REMINDERCOUNT, workOrderService.getReminderCount(params));
        //咨询完毕
        params.put(FLAG, N);
        dayMap.put(OVERTALK, workOrderService.getFirstTimeCount(params));
        //回访满意
        params.put(FLAG, null);
        params.put(OPTIONTYPE, OrderExecuteStatus.VISIT.toString());
        params.put(ORDERSTATUS, OrderStatus.SATISFIED.toString());
        dayMap.put(SATISFACTIONVISIT, workOrderService.getResultCount(params));
        //回访不满意
        params.put(ORDERSTATUS, OrderStatus.UNSATISFIED.toString());
        dayMap.put(NOTSATISFACTIONVISIT, workOrderService.getResultCount(params));
        //回访不成功
        params.put(ORDERSTATUS, OrderStatus.FAILUREVISIT.toString());
        dayMap.put(FAILVISIT, workOrderService.getResultCount(params));
        //暂无评价
        params.put(ORDERSTATUS, OrderStatus.INVALIDVISIT.toString());
        dayMap.put(INVALIDVISIT, workOrderService.getResultCount(params));
        maps.put("day", dayMap);
        //查询本周
        HashMap<String, Object> weekMap = new HashMap<>();
        Date beginDate = DateUtils.getMondayByDate(date);
        String beginDates = format.format(beginDate);
        String endDate = DateUtils.getDateBeforOrAfterDate(beginDate, 6);
        Map<String, Object> params2 = Maps.newHashMapWithExpectedSize(1);
        params2.put(ID, id);
        params2.put(BEGINDATE, beginDates);
        params2.put(ENDDATE, endDate);
        //首次派单
        params2.put(FLAG, Y);
        weekMap.put(FIRSTTIMECOUNT, workOrderService.getFirstTimeCount(params2));
        //多次来电
        weekMap.put(REMINDERCOUNT, workOrderService.getReminderCount(params2));
        //咨询完毕
        params2.put(FLAG, N);
        weekMap.put(OVERTALK, workOrderService.getFirstTimeCount(params2));
        //回访满意
        params2.put(FLAG, null);
        params2.put(OPTIONTYPE, OrderExecuteStatus.VISIT.toString());
        params2.put(ORDERSTATUS, OrderStatus.SATISFIED.toString());
        weekMap.put(SATISFACTIONVISIT, workOrderService.getResultCount(params2));
        //回访不满意
        params2.put(ORDERSTATUS, OrderStatus.UNSATISFIED.toString());
        weekMap.put(NOTSATISFACTIONVISIT, workOrderService.getResultCount(params2));
        //回访不成功
        params2.put(ORDERSTATUS, OrderStatus.FAILUREVISIT.toString());
        weekMap.put(FAILVISIT, workOrderService.getResultCount(params2));
        //暂无评价
        params2.put(ORDERSTATUS, OrderStatus.INVALIDVISIT.toString());
        weekMap.put(INVALIDVISIT, workOrderService.getResultCount(params2));
        maps.put("week", weekMap);
        //查询本月
        HashMap<String, Object> monthMap = new HashMap<>();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        String first = format.format(c.getTime());
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        Map<String, Object> params3 = Maps.newHashMapWithExpectedSize(1);
        params3.put(ID, id);
        params3.put(BEGINDATE, first);
        params3.put(ENDDATE, last);
        //首次派单
        params3.put(FLAG, Y);
        monthMap.put(FIRSTTIMECOUNT, workOrderService.getFirstTimeCount(params3));
        //多次来电
        monthMap.put(REMINDERCOUNT, workOrderService.getReminderCount(params3));
        //咨询完毕
        params3.put(FLAG, N);
        monthMap.put(OVERTALK, workOrderService.getFirstTimeCount(params3));
        //回访满意
        params3.put(FLAG,null);
        params3.put(OPTIONTYPE, OrderExecuteStatus.VISIT.toString());
        params3.put(ORDERSTATUS, OrderStatus.SATISFIED.toString());
        monthMap.put(SATISFACTIONVISIT, workOrderService.getResultCount(params3));
        //回访不满意
        params3.put(ORDERSTATUS, OrderStatus.UNSATISFIED.toString());
        monthMap.put(NOTSATISFACTIONVISIT, workOrderService.getResultCount(params3));
        //回访不成功
        params3.put(ORDERSTATUS, OrderStatus.FAILUREVISIT.toString());
        monthMap.put(FAILVISIT, workOrderService.getResultCount(params3));
        //暂无评价
        params3.put(ORDERSTATUS, OrderStatus.INVALIDVISIT.toString());
        monthMap.put(INVALIDVISIT, workOrderService.getResultCount(params3));
        maps.put("month", monthMap);
        return StatusDto.buildDataSuccessStatusDto(maps);
    }
}
