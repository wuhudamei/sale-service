package cn.damei.rest.sale.workorder;

import cn.damei.entity.sale.customer.Customer;
import cn.damei.entity.sale.workorder.WorkOrderRemark;
import cn.damei.service.sale.workorder.WorkOrderRemarkService;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusBootTableDto;
import cn.damei.entity.sale.account.User;
import cn.damei.entity.sale.dameiorder.DameiOrder;
import cn.damei.enumeration.OrderStatus;
import cn.damei.service.sale.customer.CustomerService;
import cn.damei.service.sale.workorder.WorkOrderService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.DateUtils;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import cn.damei.utils.excel.ExcelUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.damei.Constants;
import cn.damei.dto.StatusDto;
import cn.damei.entity.sale.workorder.WorkOrder;
import cn.damei.entity.sale.workorder.WorkOrderForExport;
import cn.damei.enumeration.PushType;
import cn.damei.service.sale.dameiorder.DameiOrderService;
import cn.damei.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/damei/workorder")
public class WorkOrderController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(WorkOrderController.class);//日志

    @Autowired
    private WorkOrderService workOrderService;
    @Autowired
    private DameiOrderService dameiOrderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WorkOrderRemarkService workOrderRemarkService;

    /**
     * 创建工单
     *
     * @param workOrder 工单POJO
     * @return
     */
    @RequestMapping(value = "/save/{flag}")
    public Object save(@RequestBody WorkOrder workOrder, @PathVariable Integer flag) {
        try {
            ShiroUser shiroUser = WebUtils.getLoggedUser();

            String mobile = workOrder.getCustomerMobile();
            Long companyId = workOrder.getLiableCompany().getId();
            String name = workOrder.getCustomerName();
            List<Customer> customerList = customerService.findCustomerByNameAndMobile(name, mobile, companyId);
            if (customerList.size() == 0) {
                Customer customer = new Customer();
                customer.setCustomerName(name);
                customer.setCustomerAddress(workOrder.getCustomerAddress());
                customer.setCustomerMobile(mobile);
                customer.setCompany(workOrder.getLiableCompany());
                customerService.insert(customer);
                workOrder.setCustomerId(customer.getId() + "");
            } else {
                workOrder.setCustomerId(customerList.get(0).getId() + "");
                workOrder.setCustomerName(name);
                workOrder.setCustomerMobile(mobile);
                workOrder.setCustomerAddress(workOrder.getCustomerAddress());
            }
            workOrder.setComplaintType(workOrder.getLiableType1());
            workOrderService.insertWorkOrder(workOrder, flag, shiroUser.getId());
            return StatusDto.buildDataSuccessStatusDto();
        } catch (Exception e) {
            e.printStackTrace();
            return StatusDto.buildDataFailureStatusDto("创建工单失败,[" + e.getMessage() + "]");
        }
    }

    /**
     * 更新工单
     *
     * @param workOrder 工单POJO
     * @param request
     * @return
     */
    @RequestMapping(value = "/update/{id}")
    public Object update(@RequestBody WorkOrder workOrder, @PathVariable Long id, HttpServletRequest request) {
        try {
            ShiroUser shiroUser = WebUtils.getLoggedUser();
            if (workOrder.getCustomerId() == null) {
                String mobile = workOrder.getCustomerMobile();
                String name = workOrder.getCustomerName();
                Long companyId = workOrder.getLiableCompany().getId();
                List<Customer> customerList = customerService.findCustomerByNameAndMobile(name, mobile, companyId);
                if (customerList.size() == 0) {
                    Customer customer = new Customer();
                    customer.setCustomerName(name);
                    customer.setCustomerAddress(workOrder.getCustomerAddress());
                    customer.setCustomerMobile(mobile);
                    customerService.insert(customer);
                    workOrder.setCustomerId(customer.getId() + "");
                } else {
                    workOrder.setCustomerId(customerList.get(0).getId() + "");
                    workOrder.setCustomerName(name);
                    workOrder.setCustomerMobile(mobile);
                    workOrder.setCustomerAddress(workOrder.getCustomerAddress());
                }
            }

            String status = workOrderService.getById(id).getOrderStatus();
            if (status.equals(OrderStatus.ASSIGN.name())) {
                WorkOrderRemark workOrderRemark = new WorkOrderRemark();
                workOrderRemark.setWorkOrderId(id);
                workOrderRemark.setOperationUser(new User());
                workOrderRemark.getOperationUser().setId(shiroUser.getId());
                workOrderRemark.setOperationDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                workOrderRemark.setOperationType("ASSIGN");
                workOrderRemark.setRemark(workOrder.getTreamentPlan());
                workOrderRemarkService.insertWorkOrderRemark(workOrderRemark, shiroUser.getId());
            }
            workOrder.setId(id);
            workOrder.setOrderStatus(OrderStatus.PENDING.name());
            workOrderService.update(workOrder);
            return StatusDto.buildDataSuccessStatusDto();
        } catch (Exception e) {
            e.printStackTrace();
            return StatusDto.buildDataFailureStatusDto("更新工单失败,[" + e.getMessage() + "]");
        }
    }


    /**
     * 工单处理、回复、回访
     *
     * @param workOrderJson 信息json
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateWorkOrder")
    public Object updateWorkOrder(@RequestBody String workOrderJson, HttpServletRequest request) {
        try {
            ShiroUser shiroUser = WebUtils.getLoggedUser();
            if (shiroUser == null) {
                return StatusDto.buildDataFailureStatusDto("请重新登录！");
            }
            workOrderService.operationWorkOrder(workOrderJson, shiroUser.getId());
            return StatusDto.buildDataSuccessStatusDto();
        } catch (Exception e) {
            e.printStackTrace();
            return StatusDto.buildDataFailureStatusDto("操作失败,[" + e.getMessage() + "]");
        }
    }

    /**
     * 创建工单备注信息
     *
     * @param workOrderRemark 备注信息POJO
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveRemark")
    public Object saveRemark(@RequestBody WorkOrderRemark workOrderRemark, HttpServletRequest request) {
        try {
            ShiroUser shiroUser = WebUtils.getLoggedUser();
            workOrderRemarkService.insertWorkOrderRemark(workOrderRemark, shiroUser.getId());
            return StatusDto.buildDataSuccessStatusDto();
        } catch (Exception e) {
            e.printStackTrace();
            return StatusDto.buildDataFailureStatusDto("创建工单备注失败,[" + e.getMessage() + "]");
        }
    }

    @RequestMapping("/checkUsed/{contractCompany}")
    public Object checkUsed(@PathVariable Long contractCompany){

        if(!dameiOrderService.checkUsed(contractCompany)){
            return StatusDto.buildFailureStatusDto("该功能暂未启用");
        }
        return StatusDto.buildDataSuccessStatusDto();
    }
    @RequestMapping(value = "/mdnOrderList")
    public Object mdnOrderList(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "contractCompany", required = false) Integer contractCompany,
                               @RequestParam(defaultValue = "0") int offset,
                               @RequestParam(defaultValue = "20") int limit, HttpServletRequest request) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        if (StringUtils.isNotBlank(keyword)) {
            params.put("mobile", keyword);
            params.put("contractCompany", contractCompany);
            params.put("offset", offset);
            params.put("limit", limit);

			try {
				List<DameiOrder> mdnOrderList = dameiOrderService.getOrderInfoByCondition(params);
				return StatusBootTableDto.buildDataSuccessStatusDto(mdnOrderList, Long.parseLong(mdnOrderList.size() + ""));
			} catch (Exception e) {
				e.printStackTrace();
				return StatusBootTableDto.buildDataSuccessStatusDto(Collections.EMPTY_LIST, 0L);
			}
        }
        return StatusBootTableDto.buildDataSuccessStatusDto(Collections.EMPTY_LIST, 0L);
    }

    @RequestMapping(value = "/checkNull")
    public Object checkNull(@RequestParam String startDate, @RequestParam String endDate) {
        Long i = workOrderService.searchByDate(startDate, endDate);
        if (i == 0)
            return StatusDto.buildFailureStatusDto("暂无数据");
        return StatusDto.buildSuccessStatusDto();
    }

    @RequestMapping(value = "/export")
    public void export(HttpServletResponse resp,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "queryLiableType1", required = false) String queryLiableType1,
                       @RequestParam(value = "status", required = true) String status,
                       @RequestParam(value = "srcId", required = false) Long srcId,
                       @RequestParam(value = "copyFlag", required = false) String copyFlag,
                       @RequestParam(value = "receptionPerson", required = false) String receptionPerson,
                       @RequestParam(value = "personName", required = false) String personName,
                       @RequestParam(value = "manage", required = false) Boolean manage,
                       @RequestParam(value = "receptionStartTime", required = false) String receptionStartTime,
                       @RequestParam(value = "receptionEndTime", required = false) String receptionEndTime
    ) {
        ShiroUser shiroUser = WebUtils.getLoggedUser();
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "queryLiableType1", queryLiableType1);
        MapUtils.putNotNull(params, "status", status);
        MapUtils.putNotNull(params, "srcId", srcId);
        MapUtils.putNotNull(params, "copyFlag", copyFlag);
        MapUtils.putNotNull(params, "receptionPerson", receptionPerson);
        MapUtils.putNotNull(params, "personName", personName);
        MapUtils.putNotNull(params, "receptionStartTime", receptionStartTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (receptionEndTime != null && !receptionEndTime.equals("")) {
                Date endDate = sdf.parse(receptionEndTime);
                MapUtils.putNotNull(params, "receptionEndTime", DateUtil.addDays(endDate, 1));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ServletOutputStream out = null;
        Workbook workbook = null;
        try {

            resp.setContentType("application/x-msdownload");
            resp.addHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode("工单.xlsx", "UTF-8") + "\"");
            out = resp.getOutputStream();
            List<WorkOrder> list = workOrderService.getAllInfo(params);

            if (CollectionUtils.isNotEmpty(list)) {
                workbook = ExcelUtil.getInstance().exportObj2ExcelWithTitleAndFields(list, WorkOrder.class, true, titles, fields);
            }

            if (workbook != null) {
                workbook.write(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 工单列表--工单处理
     */
    @RequestMapping(value = "/workOrderList")
    public Object workOrderList(@RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "questionType1", required = false) Integer questionType1,
                                @RequestParam(value = "importantDegree1", required = false) Integer importantDegree1,
                                @RequestParam(value = "complaintType", required = false) Integer complaintType,
                                @RequestParam(value = "status", required = false) String status,
                                @RequestParam(value = "statusFlag", required = false) String statusFlag,
                                @RequestParam(value = "fenpaiDate", required = false) String fenpaiDate,
                                @RequestParam(value = "refuseDate", required = false) String refuseDate,
                                @RequestParam(value = "refusedagainDate", required = false) String refusedagainDate,
                                @RequestParam(value = "expectDate", required = false) String expectDate,
                                @RequestParam(value = "faqiDate", required = false) String faqiDate,
                                @RequestParam(value = "startDate", required = false) String startDate,
                                @RequestParam(value = "endDate", required = false) String endDateForm,
                                @RequestParam(value = "liableCompany", required = false) Long liableCompany,
                                @RequestParam(value = "receptionStartTime", required = false) String receptionStartTime,
                                @RequestParam(value = "customerFeedbackTime", required = false) String customerFeedbackTime,
                                @RequestParam(value = "receptionEndTime", required = false) String receptionEndTime,
                                @RequestParam(defaultValue = "0") int offset,
                                @RequestParam(defaultValue = "20") int limit, HttpServletRequest request) {
        ShiroUser shiroUser = WebUtils.getLoggedUser();
        if(StringUtils.isBlank(shiroUser.getCompany()) || StringUtils.isBlank(shiroUser.getDepartment())
        		|| shiroUser.getDepType() == null ){
        	//登录用户公司,部门,部门类型 都不能为空
            return StatusBootTableDto.buildDataSuccessStatusDto(Collections.EMPTY_LIST, 0L);
        }
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "questionType1", questionType1);
        MapUtils.putNotNull(params, "importantDegree1", importantDegree1);
        MapUtils.putNotNull(params, "complaintType", complaintType);
        MapUtils.putNotNull(params, "status", status);
        MapUtils.putNotNull(params, "statusFlag", statusFlag);
        MapUtils.putNotNull(params, "fenpaiDate", fenpaiDate);
        MapUtils.putNotNull(params, "refuseDate", refuseDate);
        MapUtils.putNotNull(params, "refusedagainDate", refusedagainDate);
        MapUtils.putNotNull(params, "expectDate", expectDate);
        MapUtils.putNotNull(params, "faqiDate", faqiDate);
        MapUtils.putNotNull(params, "startDate", startDate);

        if(StringUtils.isNotBlank(endDateForm)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date endDate = sdf.parse(endDateForm);
                Calendar date = Calendar.getInstance();
                date.setTime(endDate);
                Date addEndDate=DateUtil.addDays(endDate, 1);
                MapUtils.putNotNull(params, "endDate",sdf.format(addEndDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        MapUtils.putNotNull(params, "receptionStartTime", receptionStartTime);
        MapUtils.putNotNull(params, "customerFeedbackTime", customerFeedbackTime);

        //liableId???
        if ((status.equals("PENDING") || status.equals("COMPLETED"))) {
            MapUtils.putNotNull(params, "liableId", shiroUser.getDepartment());
        }
        //除了待分配和分公司客管部，都限定责任部门
        if (!status.equals("ASSIGN")) {
        	
        	//如果是分公司客官部的未派单，全部部门可见
            if (status.equals("CREATE") && shiroUser.getDepType().name().equals("FILIALECUSTOMERSERVICE")) {
                MapUtils.putNotNull(params, "liableCompany", shiroUser.getCompany());
            }
            
            if (!shiroUser.getDepType().name().equals("FILIALECUSTOMERSERVICE")) {
                MapUtils.putNotNull(params, "liableDepartment", shiroUser.getDepartment());
            }
        }else{
        	//是待分配,部门类型是 分公司客官部, 就查当前部门的
            if (shiroUser.getDepType().name().equals("FILIALECUSTOMERSERVICE")) {
            	MapUtils.putNotNull(params, "liableCompany", shiroUser.getCompany());
            }else{
            	//部门类型是 责任公司的  就查当前公司的
            	MapUtils.putNotNull(params, "liableDepartment", shiroUser.getDepartment());
            }
        }

        //如果登录用户是供应商,其部门id就是供应商id,就加供应商id查询条件,并且将部门查询条件删除掉
        if(shiroUser.getDepType().name().equals("SUPPLIER")){
            MapUtils.putNotNull(params, "liableSupplier", shiroUser.getDepartment());
            params.remove("liableDepartment");
        }

        MapUtils.putNotNull(params, "offset", offset);
        MapUtils.putNotNull(params, "limit", limit);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (StringUtils.isNotBlank(receptionEndTime)) {
                Date endDate = sdf.parse(receptionEndTime);
                MapUtils.putNotNull(params, "receptionEndTime", DateUtil.addDays(endDate, 1));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        PageRequest pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));
        Page<WorkOrder> pageWorkOrder = workOrderService.searchScrollPage(params, pageable);
        return StatusBootTableDto.buildDataSuccessStatusDto(pageWorkOrder);
    }

    /**
     * 获取工单详情
     *
     * @param orderId 工单id
     */
    @RequestMapping(value = "/{orderId}/get", method = RequestMethod.GET)
    public Object getOrderDetails(@PathVariable("orderId") Long orderId) {
        WorkOrder workOrder = workOrderService.getById(orderId);
        List<WorkOrder> workOrders = Lists.newArrayList(workOrder);
        return StatusDto.buildDataSuccessStatusDto(workOrders.get(0));
    }

    /**
     * 根据工单id获取工单操作记录
     */
    @RequestMapping("/{orderId}/getRemarks")
    public Object getRemarkByOrderId(@PathVariable(value = "orderId") Long orderId) {
        List<WorkOrderRemark> remarks = workOrderService.getRemarkByOrderId(orderId);
        return StatusDto.buildDataSuccessStatusDto(remarks);
    }

    /**
     * 获取用户待处理事物数量
     */
    @RequestMapping("/getCurrentUserWorksNum")
    public Object getCurrentUserWorksNum() {
        String company = WebUtils.getLoggedUser().getCompany();
        Long companyId = null;
        if (StringUtils.isNotBlank(company)) {
            companyId = Long.parseLong(company);
        }
        String dept = WebUtils.getLoggedUser().getDepartment();
        Long deptId = null;
        if (StringUtils.isNotBlank(dept)) {
            deptId = Long.parseLong(dept);
        }
        Map<String, Long> worksNum = workOrderService.getUserWorksNum(companyId, deptId);
        return StatusDto.buildDataSuccessStatusDto(worksNum);
    }


    @RequestMapping("/statisticsOrderNum")
    public StatusDto statisticsOrderNum(Long liable1, String startDate, String endDate) {

        Object result = workOrderService.getExportOrder(liable1, startDate, endDate);

        return StatusDto.buildDataSuccessStatusDto(result);
    }
    @RequestMapping("/storeWorkOrderList")
    public Object storeWorkOrderList(String keyword, String createDate, String customerFeedbackTime, Boolean blackFlag,
                                     String orderStatus, String treamentTime, String startDate, String endDate, Long departmentId,
                                     Long companyId, Long questionType1, Long complaintType, Long importantDegree1,String createUserName,
                                     @RequestParam(required = false, defaultValue = "0") Integer offset,
                                     @RequestParam(required = false, defaultValue = "20") Integer limit, Integer visitedTimes,
                                     @RequestParam(defaultValue = "id") String orderColumn,
                                     @RequestParam(defaultValue = "DESC") String orderSort) {

        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        //发起时间
        createDate = "true".equals(createDate) ? createDate : null;
        //客户要求回电时间
        customerFeedbackTime = "true".equals(customerFeedbackTime) ? customerFeedbackTime : null;
        //完成时间
        treamentTime = "true".equals(treamentTime) ? treamentTime : null;

        MapUtils.putNotNull(params, "createDate", createDate);
        MapUtils.putNotNull(params, "customerFeedbackTime", customerFeedbackTime);
        MapUtils.putNotNull(params, "treamentTime", treamentTime);
        MapUtils.putNotNull(params, "startDate", startDate);
        MapUtils.putNotNull(params, "endDate", endDate);
        MapUtils.putNotNull(params, "departmentId", departmentId);
        MapUtils.putNotNull(params, "questionType1", questionType1);
        MapUtils.putNotNull(params, "complaintType", complaintType);
        MapUtils.putNotNull(params, "importantDegree1", importantDegree1);
        MapUtils.putNotNull(params, "createUserName", createUserName);
        MapUtils.putNotNull(params, "blackFlag", blackFlag);
        MapUtils.putNotNull(params, "visitedTimes", visitedTimes);


        MapUtils.putNotNull(params, "offset", offset);
        MapUtils.putNotNull(params, "limit", limit);

        //companyId为空,表示该查询来自门店库,给liableCompany赋值
        if (companyId == null) {
            //当前用户所在公司
            MapUtils.putNotNull(params, "companyId", WebUtils.getLoggedUser().getCompany());
        }else if (!Constants.MDI_GROUP_ID.equals(companyId)) {
        	//如果公司id是98,就表示查所有
            MapUtils.putNotNull(params, "companyId", companyId);
        }
        //添加orderStatus搜索条件
        MapUtils.putNotNull(params, "orderStatus", orderStatus);
        //创建分页对象
        PageRequest pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));
        params.put(Constants.PAGE_OFFSET, pageable.getOffset() / pageable.getPageSize());
        params.put(Constants.PAGE_SIZE, pageable.getPageSize());
        params.put(Constants.PAGE_SORT, pageable.getSort());
        //排序
        params.put("sort", new Sort(Sort.Direction.valueOf(orderSort), orderColumn));

        //查询工单列表
        Page<WorkOrder> storeOrderList = workOrderService.findStoreWorkOrder(params, pageable);
        
        return StatusBootTableDto.buildDataSuccessStatusDto(storeOrderList);
    }

    /**
     * 通过id更新工单对象(单表操作)
     *
     * @param workOrder 工单对象
     * @return 操作结果:成功与否
     *
     * @date 2017年7月3日 下午3:08:14
     * @version 2.0
     */
    @RequestMapping(value = "/updateWorkOrderById")
    public Object updateWorkOrderById(WorkOrder workOrder) {
        try {
            workOrderService.update(workOrder);
            return StatusDto.buildDataSuccessStatusDto("操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return StatusDto.buildDataFailureStatusDto("操作失败,[" + e.getMessage() + "]");
        }
    }

    @RequestMapping(value = "/addNewOrderByOldIdWithOrderRMK")
    public Object addNewOrderByOldIdWithOrderRMK(Long workOrderId, WorkOrder workOrder) {
        try {
            workOrderService.addNewOrderByOldIdWithOrderRMK(workOrderId, workOrder);
            return StatusDto.buildDataSuccessStatusDto("操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return StatusDto.buildDataFailureStatusDto("操作失败,[" + e.getMessage() + "]");
        }
    }

    /**
     * 通过客户id查找当前用户下的  工单列表  (不带分页)
     *
     * @param customerId 客户id
     * @return
     *
     * @date 2017年7月10日 上午11:05:11
     */
    @RequestMapping(value = "/findWorkOrdersByCustomerId")
    public Object findWorkOrdersByCustomerId(String customerId) {
        if (StringUtils.isBlank(customerId)) {
            return StatusDto.buildFailureStatusDto("客户id不能为空!");
        }
        try {
            List<WorkOrder> workOrders = workOrderService.findWorkOrdersByCustomerId(customerId);
            return StatusDto.buildDataSuccessStatusDto(workOrders);
        } catch (Exception e) {
            e.printStackTrace();
            return StatusDto.buildDataFailureStatusDto("操作失败,[" + e.getMessage() + "]");
        }
    }

    
    /**
     * 通过条件查询工单条数: 用于导出
     * @param keyword               关键字
     * @param createDate            发起时间
     * @param customerFeedbackTime  结束时间
     * @param orderStatus           工单状态
     * @param treamentTime          完成时间
     * @param startDate             开始时间
     * @param endDate               结束时间
     * @param departmentId          部门id
     * @param companyId             公司id
     * @param questionType1         事项分类
     * @param complaintType         投诉原因
     * @param importantDegree1      重要程度
     * @return
     */
    @RequestMapping(value = "/countWorkOrderByQuery")
    public Object countWorkOrderByQuery(String keyword, String createDate, String customerFeedbackTime,
            String orderStatus, String treamentTime, String startDate, String endDate, Long departmentId,
            Long companyId, Long questionType1, Long complaintType, Long importantDegree1) {

    	Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        //发起时间
        createDate = "true".equals(createDate) ? createDate : null;
        //客户要求回电时间
        customerFeedbackTime = "true".equals(customerFeedbackTime) ? customerFeedbackTime : null;
        //完成时间
        treamentTime = "true".equals(treamentTime) ? treamentTime : null;

        MapUtils.putNotNull(params, "createDate", createDate);
        MapUtils.putNotNull(params, "customerFeedbackTime", customerFeedbackTime);
        MapUtils.putNotNull(params, "treamentTime", treamentTime);
        MapUtils.putNotNull(params, "startDate", startDate);
        MapUtils.putNotNull(params, "endDate", endDate);
        MapUtils.putNotNull(params, "departmentId", departmentId);
        MapUtils.putNotNull(params, "questionType1", questionType1);
        MapUtils.putNotNull(params, "complaintType", complaintType);
        MapUtils.putNotNull(params, "importantDegree1", importantDegree1);

       if (companyId != null && companyId != 98) {
            //如果公司id是98,就表示查所有
            MapUtils.putNotNull(params, "companyId", companyId);
        }
        //添加orderStatus搜索条件
        MapUtils.putNotNull(params, "orderStatus", orderStatus);
    	
    	Long count = workOrderService.countStoreWorkOrder(params);
    	
    	return StatusDto.buildDataSuccessStatusDto(count);
    }
    /**
     * 工单库--集团库  导出:
     *      按照筛选条件
     * @param keyword               关键字
     * @param createDate            发起时间
     * @param customerFeedbackTime  结束时间
     * @param orderStatus           工单状态
     * @param treamentTime          完成时间
     * @param startDate             开始时间
     * @param endDate               结束时间
     * @param departmentId          部门id
     * @param companyId             公司id
     * @param questionType1         事项分类
     * @param complaintType         投诉原因
     * @param importantDegree1      重要程度
     * @param //orderColumn           排序列
     * @param //orderSort             排序方式
     */
    @RequestMapping(value = "/exportWorkorders")
    public void exportWorkorders(String keyword, String createDate, String customerFeedbackTime,
                        String orderStatus, String treamentTime, String startDate, String endDate, Long departmentId,
                        Long companyId, Long questionType1, Long complaintType, Long importantDegree1,
                        HttpServletResponse resp) {

        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        //发起时间
        createDate = "true".equals(createDate) ? createDate : null;
        //客户要求回电时间
        customerFeedbackTime = "true".equals(customerFeedbackTime) ? customerFeedbackTime : null;
        //完成时间
        treamentTime = "true".equals(treamentTime) ? treamentTime : null;

        MapUtils.putNotNull(params, "createDate", createDate);
        MapUtils.putNotNull(params, "customerFeedbackTime", customerFeedbackTime);
        MapUtils.putNotNull(params, "treamentTime", treamentTime);
        MapUtils.putNotNull(params, "startDate", startDate);
        MapUtils.putNotNull(params, "endDate", endDate);
        MapUtils.putNotNull(params, "departmentId", departmentId);
        MapUtils.putNotNull(params, "questionType1", questionType1);
        MapUtils.putNotNull(params, "complaintType", complaintType);
        MapUtils.putNotNull(params, "importantDegree1", importantDegree1);
        //添加orderStatus搜索条件
        MapUtils.putNotNull(params, "orderStatus", orderStatus);
        //文件导出标志
        MapUtils.putNotNull(params, "export", true);
        

       if (companyId != null && companyId != 98) {
            //如果公司id是98,就表示查所有
            MapUtils.putNotNull(params, "companyId", companyId);
        }

        ServletOutputStream out = null;
        Workbook workbook = null;
        
        
        try {

            resp.setContentType("application/x-msdownload");
            resp.addHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode("客户信息处理统计.xlsx", "UTF-8") + "\"");
            out = resp.getOutputStream();
            
            List<WorkOrderForExport> list = workOrderService.findAllStoreWorkOrder(params);
            
            if (CollectionUtils.isNotEmpty(list)) {
                workbook = ExcelUtil.getInstance().exportObj2ExcelWithTitleAndFields(list, WorkOrderForExport.class, true, workOrderTitles, workOrderFields);
            }

            if (workbook != null) {
                workbook.write(out);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    //初识化导出的字段
    private static List<String> titles = null;
    private static List<String> fields = null;
    private static List<String> workOrderTitles = null;
    private static List<String> workOrderFields = null;

    static {


        titles = Lists.newArrayListWithExpectedSize(27);
        titles.add("事项编号");
        titles.add("接收部门");
        titles.add("接待人");
        titles.add("工单创建时间");
        titles.add("客户");
        titles.add("电话");
        titles.add("地址");
        titles.add("设计师");
        titles.add("电话");
        titles.add("项目经理");
        titles.add("电话");
        titles.add("监理");
        titles.add("电话");
        titles.add("工单分类");
        titles.add("问题");
        titles.add("客户要求回电时间");
        titles.add("重要程度1");
        titles.add("重要程度2");
        titles.add("责任部门");
        titles.add("责任类别一");
        titles.add("责任类别二");
        titles.add("品牌");
        titles.add("反馈人");
        titles.add("反馈时间");
        titles.add("处理方案");
        titles.add("完成时间");
        titles.add("回访结果");
        titles.add("事项状态");


        fields = Lists.newArrayListWithExpectedSize(28);

        fields.add("workOrderCode");
        fields.add("srcDepartment.orgName");
        fields.add("receptionPerson.name");
        fields.add("createDate");
        fields.add("customerName");
        fields.add("customerMobile");
        fields.add("customerAddress");
        fields.add("styListName");
        fields.add("styListMobile");
        fields.add("contractorName");
        fields.add("contractorMobile");
        fields.add("supervisorName");
        fields.add("supervisorMobile");
        fields.add("workType");
        fields.add("problem");
        fields.add("customerFeedbackTime");
        fields.add("importantDegree1.name");
        fields.add("importantDegree2.name");
        fields.add("liableDepartment.orgName");
        fields.add("liableType1.name");
        fields.add("liableType2.name");
        fields.add("brand");
        fields.add("liablePerson.name");
        fields.add("feedbackTime");
        fields.add("treamentPlan");
        fields.add("treamentTime");
        fields.add("visitResult");
        fields.add("orderStatus");

        //工单库中 导出 标题
        workOrderTitles = new ArrayList<String>(28);
        workOrderTitles.add("事项编号");
        workOrderTitles.add("发起部门");
        workOrderTitles.add("发起人");
        workOrderTitles.add("工单创建时间");
        workOrderTitles.add("客户");
        workOrderTitles.add("电话");
        workOrderTitles.add("地址");
        workOrderTitles.add("设计师");
        workOrderTitles.add("电话");
        workOrderTitles.add("项目经理");
        workOrderTitles.add("电话");
        workOrderTitles.add("监理");
        workOrderTitles.add("电话");
        workOrderTitles.add("工单分类");
        workOrderTitles.add("问题");
        workOrderTitles.add("客户要求回电时间");
        
        workOrderTitles.add("门店");
        workOrderTitles.add("责任部门");
        workOrderTitles.add("事项分类");
        workOrderTitles.add("投诉原因");
        workOrderTitles.add("重要程度");
        workOrderTitles.add("品牌");
        workOrderTitles.add("处理人");
        workOrderTitles.add("处理时间");
        workOrderTitles.add("处理方案");
        workOrderTitles.add("预计完成时间");
        workOrderTitles.add("实际完成时间");
        workOrderTitles.add("工单状态");
        workOrderTitles.add("不满意原因");
        workOrderTitles.add("工单来源");
        workOrderTitles.add("延期次数");
        workOrderTitles.add("问题类型");
        workOrderTitles.add("催单次数");

        workOrderFields = new ArrayList<String>(28);

        workOrderFields.add("workOrderCode");
        workOrderFields.add("srcDepartment");
        workOrderFields.add("receptionPerson");
        workOrderFields.add("createDate");
        workOrderFields.add("customerName");
        workOrderFields.add("customerMobile");
        workOrderFields.add("customerAddress");
        workOrderFields.add("styListName");
        workOrderFields.add("styListMobile");
        workOrderFields.add("contractorName");
        workOrderFields.add("contractorMobile");
        workOrderFields.add("supervisorName");
        workOrderFields.add("supervisorMobile");
        workOrderFields.add("workType");
        workOrderFields.add("problem");
        workOrderFields.add("customerFeedbackTime");
        
        workOrderFields.add("liableCompany");
        workOrderFields.add("liableDepartment");
        workOrderFields.add("questionType1");
        workOrderFields.add("complaintType");
        workOrderFields.add("importantDegree1");
        //品牌名称
        workOrderFields.add("brandName");
        //处理人
        workOrderFields.add("operationUserFromRmk");
        workOrderFields.add("operationDate");
        workOrderFields.add("treamentPlan");
        workOrderFields.add("treamentTime");
        workOrderFields.add("operationDateFromRmk");
        workOrderFields.add("orderStatus");
        workOrderFields.add("unSatisfiedFromRmk");
        workOrderFields.add("source");
        workOrderFields.add("treamentResultCount");
        workOrderFields.add("questionType2");
        workOrderFields.add("urgeTimes");
    }


    /**
     * 工单同步失败列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/workOrderFailList")
    public Object mdnOrderFailList(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "companyId", required = false) Long companyId,
                               @RequestParam(value = "departmentId", required = false) Long departmentId,
                               @RequestParam(value = "startDate", required = false) String startDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               @RequestParam(defaultValue = "0") int offset,
                               @RequestParam(defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
            params.put("keyword", keyword);
            params.put("liableCompany", companyId);
            params.put("liableDepartment", departmentId);
            params.put("startDate", startDate);
            params.put("endDate", endDate);
            params.put("offset", offset);
            params.put("limit", limit);
            try {
                Long orderInfoFailListTotal = workOrderService.getOrderInfoFailListTotal(params);
                if (orderInfoFailListTotal!=null && orderInfoFailListTotal!=0l) {
                    List<DameiOrder> mdnOrderList = workOrderService.getOrderInfoFailList(params);
                    return StatusBootTableDto.buildDataSuccessStatusDto(mdnOrderList, orderInfoFailListTotal);
                }else{
                    return StatusBootTableDto.buildDataSuccessStatusDto(Collections.EMPTY_LIST, 0L);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return StatusBootTableDto.buildDataSuccessStatusDto(Collections.EMPTY_LIST, 0L);
            }
    }

    /**
     * 工单同步
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/workOrderSyn")
    public Object workOrderSyn(@RequestParam(value = "id", required = true) Long id,
                               @RequestParam(value = "pushType", required = true) PushType pushType) {
        try{
            String status="40";
            if(pushType.equals(PushType.TURNDOWN)){
                status="50";
            }
            return workOrderService.synWorkOrder(id,status);
        }catch (Exception e){
            e.printStackTrace();
            return StatusDto.buildFailureStatusDto("同步异常");
        }

    }

}
