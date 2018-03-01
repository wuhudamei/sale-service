package com.damei.service.sale.workorder;

import com.alibaba.fastjson.JSON;
import com.damei.Constants;
import com.damei.common.PropertyHolder;
import com.damei.common.service.CrudService;
import com.damei.dto.StatusDto;
import com.damei.dto.WorkOrderDto;
import com.damei.dto.WorkOrderReturnDto;
import com.damei.entity.sale.account.User;
import com.damei.entity.sale.dict.MdniDictionary;
import com.damei.entity.sale.mdniorder.MdniOrder;
import com.damei.entity.sale.organization.MdniOrganization;
import com.damei.entity.sale.report.ReportNissin;
import com.damei.entity.sale.workorder.*;
import com.damei.enumeration.OrderExecuteStatus;
import com.damei.enumeration.OrderStatus;
import com.damei.enumeration.PushType;
import com.damei.enumeration.oa.DEPTYPE;
import com.damei.repository.sale.report.ReportNissinDao;
import com.damei.repository.sale.workorder.WorkOrderDao;
import com.damei.repository.sale.workorder.WorkOrderRemarkDao;
import com.damei.repository.sale.workorder.WorkorderPushFailDao;
import com.damei.service.sale.account.UserService;
import com.damei.service.sale.dict.BrandService;
import com.damei.service.sale.dict.MdniDictionaryService;
import com.damei.service.sale.mdniorder.MdniOrderService;
import com.damei.service.sale.organization.MdniOrganizationService;
import com.damei.shiro.ShiroUser;
import com.damei.utils.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@SuppressWarnings("all")
@Service
public class WorkOrderService extends CrudService<WorkOrderDao, WorkOrder> {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final int MINUTES = 0;
    public static final int MINUTES1 = 30;
    private static Logger logger = LoggerFactory.getLogger(WorkOrderService.class);//日志
    @Autowired
    private WorkOrderDao workOrderDao;
    
    @Autowired
    private WorkOrderRemarkDao workOrderRemarkDao;

    @Autowired
    private MdniOrderService mdniOrderService;
    @Autowired
    private ReportNissinDao reportNissinDao;

    @Autowired
    private MdniDictionaryService mdniDictionaryService;
    @Autowired
    private UserService userService;
    @Autowired
    private MdniOrganizationService organizationService;
    @Autowired
    private WorkOrderRemarkService workOrderRemarkService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private WorkorderPushFailDao workorderPushFailDao;

    /**
     * 创建工单
     *
     * @param workOrder
     */
    @Transactional
    public void insertWorkOrder(WorkOrder workOrder, Integer flag, Long userId) throws Exception {
        workOrder.setWorkOrderCode(generatorWorkOrderCode(workOrder.getSrcCompany().getOrgCode(), workOrder.getSrcDepartment().getOrgCode()));
        workOrder.setReceptionTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        workOrder.setStatuFlag(1);
        if (flag == 1) {
            workOrder.setOrderStatus(OrderStatus.CREATE.name());
        } else {
            workOrder.setOrderStatus(OrderStatus.CONSULTOVER.name());
        }
        workOrder.setCreateUser(userId);
        workOrder.setCreateDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        workOrder.setFenpaiDate(new Date());
        workOrder.setFeedbackTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        workOrderDao.insert(workOrder);

        if (workOrder.getCopyWorkId() != null) {
            workOrderDao.updateWorkCopyFlag(workOrder.getCopyWorkId());
        }

        WorkOrderRemark workOrderRemark = new WorkOrderRemark();
        workOrderRemark.setWorkOrderId(workOrder.getId());
        workOrderRemark.setOperationUser(new User());
        workOrderRemark.getOperationUser().setId(userId);
        workOrderRemark.setOperationDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        workOrderRemark.setOperationType(OrderStatus.CREATE.name());
        workOrderRemark.setRemark("新建工单");
        workOrderRemarkService.insertWorkOrderRemark(workOrderRemark, userId);//插入工单备注
        //插入 的话
        if(flag == 1){
            Long companyId = workOrder.getLiableCompany().getId();
            Long departmentId = workOrder.getLiableDepartment().getId();
            pushWorkOrderAndRemark(companyId,departmentId,workOrder.getId(),"40");
        }
    }
    //推送工单 或者 修改工单  40推  50改 并把推送失败的进行 标记修改
    private void pushWorkOrderAndRemark(Long companyId,Long departmentId,Long workOrderId,String status) throws Exception {
                //判断是否推送 手动推送不需要判断是否推送则 companyId==null&&departmentId==null
                if((companyId==null&& departmentId==null)||isPush(companyId,departmentId)){
                    WorkOrderServiceDto workOrderServiceDto = entityDao.pushWordOrder(workOrderId);
                    //工单编号
                    String workOrderCode = workOrderServiceDto.getWorkOrderCode();
                    String post = pushWorkOrder(workOrderServiceDto,status);
                    Date date = new Date();
                    List<WorkorderPushFail> byWorkorderId = workorderPushFailDao.getByWorkorderId(workOrderId);
                    if(post!=null){
                        Map<String, String> result = JsonUtils.fromJsonAsMap(post, String.class, String.class);
                        if(result.get("code")!=null && result.get("code").equals("200")){
                            //推送成功
                            if(status.equals("40")) {
                                WorkorderPushFail pushFail = pushType(byWorkorderId, PushType.PUSH);
                                if (pushFail!=null){
                                    pushFail.setPushResult("1");
                                    pushFail.setRemarks("成功推送");
                                    pushFail.setPushDate(date);
                                    workorderPushFailDao.update(pushFail);
                                }
                            }else if(status.equals("50")){
                                WorkorderPushFail pushFail = pushType(byWorkorderId, PushType.TURNDOWN);
                                if (pushFail!=null){
                                    pushFail.setPushResult("1");
                                    pushFail.setRemarks("成功在驳回");
                                    pushFail.setPushDate(date);
                                    workorderPushFailDao.update(pushFail);
                                }
                            }
                        }else{
                            //推送失败
                            //推送
                            if(status.equals("40")) {
                                WorkorderPushFail pushFail = pushType(byWorkorderId, PushType.PUSH);
                                if(byWorkorderId==null||null== pushFail){
                                    WorkorderPushFail workorderPushFail = new WorkorderPushFail();
                                    workorderPushFail.setWorkOrderId(workOrderId);
                                    workorderPushFail.setWorkOrderCode(workOrderCode);
                                    workorderPushFail.setPushDate(date);
                                    workorderPushFail.setPushNumber(1L);
                                    workorderPushFail.setPushResult("0");
                                    workorderPushFail.setSynDate(date);
                                    workorderPushFail.setRemarks("第1次推送无响应");
                                    workorderPushFail.setPushType(PushType.PUSH);
                                    workorderPushFailDao.insert(workorderPushFail);
                                }else{
                                    long number = pushFail.getPushNumber() + 1L;
                                    pushFail.setPushDate(date);
                                    pushFail.setPushNumber(number);
                                    pushFail.setRemarks("第"+number+"次推送无响应");
                                    workorderPushFailDao.update(pushFail);
                                }
                                //驳回
                            }else if (status.equals("50")){
                                WorkorderPushFail pushFail = pushType(byWorkorderId, PushType.TURNDOWN);
                                if(byWorkorderId==null||null== pushFail){
                                    WorkorderPushFail workorderPushFail = new WorkorderPushFail();
                                    workorderPushFail.setWorkOrderId(workOrderId);
                                    workorderPushFail.setWorkOrderCode(workOrderCode);
                                    workorderPushFail.setPushDate(date);
                                    workorderPushFail.setPushNumber(1L);
                                    workorderPushFail.setPushResult("0");
                                    workorderPushFail.setSynDate(date);
                                    workorderPushFail.setRemarks("第1次再驳回无响应");
                                    workorderPushFail.setPushType(PushType.TURNDOWN);
                                    workorderPushFailDao.insert(workorderPushFail);
                                }else{
                                    long number = pushFail.getPushNumber() + 1L;
                                    pushFail.setPushDate(date);
                                    pushFail.setPushNumber(number);
                                    pushFail.setRemarks("第"+number+"次再驳回无响应");
                                    workorderPushFailDao.update(pushFail);
                                }
                            }
                        }
                        //推送无响应
                    }else {
                        //推送
                        if(status.equals("40")) {
                            WorkorderPushFail pushFail = pushType(byWorkorderId, PushType.PUSH);
                            if(byWorkorderId==null||null== pushFail){
                                WorkorderPushFail workorderPushFail = new WorkorderPushFail();
                                workorderPushFail.setWorkOrderId(workOrderId);
                                workorderPushFail.setWorkOrderCode(workOrderCode);
                                workorderPushFail.setPushDate(date);
                                workorderPushFail.setPushNumber(1L);
                                workorderPushFail.setPushResult("0");
                                workorderPushFail.setSynDate(date);
                                workorderPushFail.setRemarks("第1次推送失败");
                                workorderPushFail.setPushType(PushType.PUSH);
                                workorderPushFailDao.insert(workorderPushFail);
                            }else{
                                long number = pushFail.getPushNumber() + 1L;
                                pushFail.setPushDate(date);
                                pushFail.setPushNumber(number);
                                pushFail.setRemarks("第"+number+"次推送失败");
                                workorderPushFailDao.update(pushFail);
                            }
                            //驳回
                        }else if (status.equals("50")){
                            WorkorderPushFail pushFail = pushType(byWorkorderId, PushType.TURNDOWN);
                            if(byWorkorderId==null||null== pushFail){
                                WorkorderPushFail workorderPushFail = new WorkorderPushFail();
                                workorderPushFail.setWorkOrderId(workOrderId);
                                workorderPushFail.setWorkOrderCode(workOrderCode);
                                workorderPushFail.setPushDate(date);
                                workorderPushFail.setPushNumber(1L);
                                workorderPushFail.setPushResult("0");
                                workorderPushFail.setSynDate(date);
                                workorderPushFail.setRemarks("第1次再驳回失败");
                                workorderPushFail.setPushType(PushType.TURNDOWN);
                                workorderPushFailDao.insert(workorderPushFail);
                            }else{
                                long number = pushFail.getPushNumber() + 1L;
                                pushFail.setPushDate(date);
                                pushFail.setPushNumber(number);
                                pushFail.setRemarks("第"+number+"次再驳回失败");
                                workorderPushFailDao.update(pushFail);
                            }
                        }


                    }
                }

    }
    //判断list 里面的推送类型
    private WorkorderPushFail pushType(List<WorkorderPushFail> byWorkorderId,PushType pushType){
        for (WorkorderPushFail workorderPushFail:byWorkorderId) {
            if (workorderPushFail.getPushType().equals(pushType)){
                return workorderPushFail;
            }
        }
        return null;
    }
    //判断 是否需要推送
    private Boolean isPush(Long companyId,Long departmentId){
        List<String> companyIds = Arrays.asList(PropertyHolder.getOrderserviceCompanyIds().split(","));
        List<String> departmentIds = Arrays.asList(PropertyHolder.getOrderserviceDepartmentIds().split(","));
        return (companyIds.contains(companyId.toString())&&departmentIds.contains(departmentId.toString()));
    }

    //推送工单 修改工单 status ->10  status->50
    private String pushWorkOrder(WorkOrderServiceDto workOrderDto,String status){
        String businessOnlyMarkInt = workOrderDto.getWorkOrderCode();
        String businessType="601";
        String businessData=JsonUtils.toJson(workOrderDto);
        String[] parameterArr = new String[]{"businessOnlyMarkInt="+ businessOnlyMarkInt,"businessType="+ businessType, "businessData="+ businessData};
        //生成key
        String key = SignUtil.getKey(parameterArr, PropertyHolder.getOrderServiceMd5());
        HashMap<String, Object> paramJson = Maps.newHashMap();
        paramJson.put("businessOnlyMarkInt",businessOnlyMarkInt);
        paramJson.put("businessType",businessType);
        paramJson.put("key",key);
        paramJson.put("businessData",workOrderDto);
        paramJson.put("status",status);
        // 序列化参数
        String s = JsonUtils.toJson(paramJson);

        HashMap<String, String> params= Maps.newHashMap();
        params.put("reciveJson",s);
        try {
            String s1 = HttpUtils.post(PropertyHolder.getOrderserviceUrl()+ Constants.ORDER_SERVICE_PUSH,params);
            return s1;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }


    /**
     * 返回对外接口数据
     *
     * @return
     */
//    public List<WorkOrder> orderService(String startDate,String endDate,String liableDepartments,String liableType1s){
//        String[] liableDeptArr = liableDepartments.split(",");
//        String[] liableType1Arr= liableType1s.split(",");
//
//
//
//    }
    public List<WorkOrderServiceDto> orderService(Map<String, Object> params) {
        return this.entityDao.orderService(params);
    }

    /**
     * 工单处理、回复、回访 更新工单,并且插入工单备注
     *
     * @param workOrderJson 工单信息json
     * @param userId
     */
    @Transactional(rollbackFor=Exception.class)
    public void operationWorkOrder(String workOrderJson, Long userId) {

        //微信消息模板head
        String wechatHead = null;
        String wechatRemark = null;

        Map<String, String> jsonMap = anlyWorkOrderJson(workOrderJson);
        Long workOrderId = Long.parseLong(jsonMap.get("id").toString());

        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(workOrderId);

        //图片
        String photo = jsonMap.get("photo");
        if(StringUtils.isNotBlank(photo)){
            workOrder.setPhoto(photo);
        }

        WorkOrderRemark workOrderRemark = new WorkOrderRemark();
        workOrderRemark.setWorkOrderId(workOrderId);
        workOrderRemark.setOperationUser(new User());
        workOrderRemark.getOperationUser().setId(userId);
        workOrderRemark.setOperationDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String opType = String.valueOf(jsonMap.get("operationType"));
        String oStatus = String.valueOf(jsonMap.get("orderStatus"));
        if (opType.equals("VISIT") && oStatus.equals("CALLBACK")) {
            workOrderRemark.setOperationType("CALLBACK");
        } else {
            workOrderRemark.setOperationType(opType);
        }
        if (OrderExecuteStatus.RECEIVE.name().equals(jsonMap.get("operationType"))) {//处理
            workOrder.setOrderStatus(OrderStatus.RECEIVED.name());
            workOrder.setLiablePerson(new User(userId));
            workOrder.setFenpaiDate(new Date());
            workOrderRemark.setRemark("接收工单");
            workOrder.setReceptionTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

            wechatHead = "接收";
            wechatRemark = "您好，您发起的工单已经被接收。";
        } else if (OrderExecuteStatus.FOLLOWUP.name().equals(jsonMap.get("operationType"))) {
            workOrder.setTreamentPlan(jsonMap.get("treamentPlan"));
            workOrder.setBrand(Long.parseLong(jsonMap.get("brand")));
            workOrderRemark.setRemark(String.valueOf(jsonMap.get("treamentPlan")));
        } else if (OrderExecuteStatus.REJECT.name().equals(jsonMap.get("operationType"))) {//申诉
            workOrder.setOrderStatus(OrderStatus.ASSIGN.name());
            workOrder.setLiableDepartment(null);
            workOrder.setBrand(Long.parseLong(jsonMap.get("brand").toString()));
            workOrder.setRefuseTime(new Date());
            workOrder.setRemark(String.valueOf(jsonMap.get("treamentPlan")));
            workOrderRemark.setRemark(String.valueOf(jsonMap.get("treamentPlan")));
            workOrder.setRefuseTime(new Date());
        } else if (OrderExecuteStatus.REJECTAGAIN.name().equals(jsonMap.get("operationType"))) {
        	//申诉无效
            workOrder.setOrderStatus(OrderStatus.REFUSEDAGAIN.name());
            workOrder.setRefusedagainTime(new Date());
            workOrder.setRefusedagainTime(new Date());

            //设置分配意见
            workOrder.setSuggestion(jsonMap.get("suggestion"));

            //更新问题
            String problem = String.valueOf(jsonMap.get("problem"));
            workOrder.setProblem(problem);
            //更新工单类型
            workOrder.setWorkType(String.valueOf(jsonMap.get("workType")));
            workOrderRemark.setRemark("客管部再次申诉工单,问题:" + problem);
        } else if (OrderExecuteStatus.SELECTREMAINDER.name().equals(jsonMap.get("operationType"))) {//处理
            workOrder.setRead(Boolean.FALSE);
        } else if (OrderExecuteStatus.OPERATIONAGAIN.name().equals(jsonMap.get("operationType"))) {
        	//再次处理工单--重新分派责任部门
            workOrder.setOrderStatus(OrderStatus.CREATE.name());
            workOrder.setFenpaiDate(new Date());
            //设置分配意见
            workOrder.setSuggestion(jsonMap.get("suggestion"));
//            workOrder.setBrand(Integer.parseInt(jsonMap.get("brand").toString()));
            MdniOrganization liableDep = new MdniOrganization();
            liableDep.setId(Long.parseLong(String.valueOf(jsonMap.get("liableDepartment"))));
            workOrder.setLiableDepartment(organizationService.getById(Long.parseLong(String.valueOf(jsonMap.get("liableDepartment")))));
            workOrder.setLiablePerson(new User(userId));
            workOrder.setFenpaiDate(new Date());
            //每次重新分配责任部门都清空供应商id
            MdniOrganization liableSupplier= new MdniOrganization();
            workOrder.setLiableSupplier(liableSupplier);
            MdniDictionary dic = new MdniDictionary();
            dic.setId(Long.parseLong(String.valueOf(jsonMap.get("questionType1"))));
            MdniDictionary dic2 = new MdniDictionary();
            dic2.setId(Long.parseLong(String.valueOf(jsonMap.get("questionType2"))));

            workOrder.setQuestionType1(dic);
            workOrder.setQuestionType2(dic2);
            workOrder.setRemark("");
            workOrder.setTreamentPlan("");
            //更新问题
            String problem = String.valueOf(jsonMap.get("problem"));
            workOrder.setProblem(problem);
            //更新工单类型
            workOrder.setWorkType(String.valueOf(jsonMap.get("workType")));
            workOrderRemark.setRemark("重新分派责任部门,问题:" + problem);
        } else if (OrderExecuteStatus.FINISHORDER.name().equals(jsonMap.get("operationType"))) {//完成工单
            workOrder.setOrderStatus(OrderStatus.COMPLETED.name());
            workOrder.setRemark(String.valueOf(jsonMap.get("treamentPlan")));
            workOrderRemark.setRemark(String.valueOf(jsonMap.get("treamentPlan")));

            wechatHead = "完成工单";
            wechatRemark = "您好，您发起的工单已经被处理完成。";
        } else if (OrderExecuteStatus.OPERATION.name().equals(jsonMap.get("operationType"))) {//处理
            String orderStatus = this.entityDao.getById(workOrderId).getOrderStatus();
            String treamentPlan = String.valueOf(jsonMap.get("treamentPlan"));
            String treamentTime = String.valueOf(jsonMap.get("treamentTime"));
            String feedbackRmk = String.valueOf(jsonMap.get("feedbackRmk"));

            workOrder.setOrderStatus(OrderStatus.PROCESSING.name());
            workOrder.setBrand(Long.parseLong(jsonMap.get("brand").toString()));
            workOrder.setLiablePerson(new User(userId));
            workOrder.setTreamentPlan(treamentPlan);
            workOrder.setTreamentTime(treamentTime);

            String remark = treamentPlan.equals("") || treamentPlan == null ? feedbackRmk : treamentPlan;
            workOrderRemark.setRemark(remark);

            wechatHead = "处理";
            wechatRemark = "您好，您发起的工单已经被处理。\r\n处理方案： " + treamentPlan
                + "，预计完成时间： " + treamentTime;
        } else if (OrderExecuteStatus.VISIT.name().equals(jsonMap.get("operationType"))) {//回访
            //回访结果，未执行，直接复制一条新工单
            if (OrderExecuteStatus.UNEXECUTED.name().equals(jsonMap.get("orderStatus"))) {
                WorkOrder workOrder1 = new WorkOrder();

                Long id = Long.parseLong(String.valueOf(jsonMap.get("id")));
                WorkOrder workOrder2 = this.entityDao.getById(id);
                workOrder1.setId(id);
                workOrder1.setProblem(String.valueOf(jsonMap.get("visitResult")));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String strDate = formatter.format(date);
                workOrder1.setReceptionTime(strDate);
                workOrder1.setCreateDate(strDate);
                MdniDictionary importantDegree1 = mdniDictionaryService.getByName("到期回访未执行1");
                MdniDictionary importantDegree2 = mdniDictionaryService.getByName("到期回访未执行2");

                this.entityDao.insertCopy(workOrder1);
                WorkOrderRemark workOrderRemark2 = new WorkOrderRemark();
                workOrderRemark2.setWorkOrderId(workOrder1.getId());
                workOrderRemark2.setOperationUser(new User());
                workOrderRemark2.getOperationUser().setId(userId);
                workOrderRemark2.setOperationDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                workOrderRemark2.setOperationType(OrderStatus.CREATE.name());
                workOrderRemark2.setRemark("新建工单");
                workOrderRemarkService.insertWorkOrderRemark(workOrderRemark2, userId);//插入工单备注
            }

            workOrder.setOrderStatus(String.valueOf(jsonMap.get("orderStatus")));
            workOrder.setVisitResult(String.valueOf(jsonMap.get("visitResult")));
            workOrderRemark.setRemark(String.valueOf(jsonMap.get("visitResult")));
        } else if (OrderExecuteStatus.TURNSEND.name().equals(jsonMap.get("operationType"))) {//处理
            //材料部的 转派: 1.给工单添加 责任供应商(此时用户的责任部门就是责任供应商); 2.插入一条新轨迹
            MdniOrganization org = new MdniOrganization();
            org.setId(Long.parseLong(jsonMap.get("liableDepartment")));
            workOrder.setLiableSupplier(org);
            //插入新记录
            workOrderRemark.setRemark("材料部转派给: " + jsonMap.get("departmentName"));
        }else if(OrderExecuteStatus.INVALID.name().equals(jsonMap.get("operationType"))){
            //无效工单
            workOrder.setOrderStatus(jsonMap.get("orderStatus"));
            workOrderRemark.setOperationType(jsonMap.get("operationType"));
            workOrderRemark.setRemark("工单置为无效");
        }

        updateWorkInfo(workOrder);//更新工单信息
        workOrderRemarkService.insertWorkOrderRemark(workOrderRemark, userId);//插入工单备注


        //通过工单id查询旧工单详情
        WorkOrder oldWorkOrder = workOrderDao.getById(workOrder.getId());

        //接受，处理，完成  操作时,发送模板消息
        //String head, String jobNo, String url, String workOrderCode,
        //String remark, String orderStatus, String operationUser
        if(OrderExecuteStatus.RECEIVE.name().equals(jsonMap.get("operationType")) ||
                OrderExecuteStatus.OPERATION.name().equals(jsonMap.get("operationType")) ||
                OrderExecuteStatus.FINISHORDER.name().equals(jsonMap.get("operationType"))){

            ShiroUser shiroUser = WebUtils.getLoggedUser();


            //根据工单创建人id 查询创建者,如果存在取jobNo; 如果不存在,就说明不需要发送模板消息
            //url: "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa3b36bda18546de8&redirect_uri=http%3A%2F%2Fsso.rocoinfo.cn%2Foauth%2Fmenu%2Fcode%3Fappid%3D285a2fbf8f0b0969ad%26redirect_url%3Dhttp%3A%2F%2Fmdnss.rocozxb.cn%2FoauthCallBack&response_type=code&scope=snsapi_base&state=/workorder/workOrderInfo?workOrderId=" + workOrder.getId()+ "#wechat_redirect"
            User createUser = userService.getByIdWithRemind(oldWorkOrder.getCreateUser());
            if(createUser != null){
                TemplateUtils.sendWorkOrderStageTemplate("工单" + wechatHead + "通知",
                        createUser.getAccount(), TemplateUtils.getOauthUrl("/workorder/workOrderInfo?workOrderId=" + workOrder.getId()),
                        oldWorkOrder.getWorkOrderCode(),
                        wechatRemark, OrderStatus.valueOf(workOrder.getOrderStatus()).getLable(),
                        shiroUser.getName());
            }
        }

        //判断  重新分配--> 推送工单   在申诉-->修改状态
        //判断 是否需要推送
        Long companyId = oldWorkOrder.getLiableCompany().getId();
        Long departmentId = oldWorkOrder.getLiableDepartment().getId();
        try {
            String operationType = workOrderRemark.getOperationType();
            if(operationType.equals(OrderExecuteStatus.OPERATIONAGAIN.toString())){ //推送工单
                pushWorkOrderAndRemark(companyId,departmentId,workOrder.getId(),"40");
            }else if(operationType.equals(OrderExecuteStatus.REJECTAGAIN.toString())){//推送驳回
                pushWorkOrderAndRemark(companyId,departmentId,workOrder.getId(),"50");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 解析传入的工单信息
     *
     * @param workOrderJson
     * @return
     */
    private Map<String, String> anlyWorkOrderJson(String workOrderJson) {
        Map<String, String> orderJsonMap = JSON.parseObject(workOrderJson, HashMap.class);
        return orderJsonMap;
    }

    public Long searchByDate(String start, String end) {
        return this.entityDao.searchByDate(start + " 00:00:00", end + " 23:59:59");
    }

    /**
     * 更新工单信息
     *
     * @param workOrder
     */
    @Transactional
    public void updateWorkInfo(WorkOrder workOrder) {
        workOrderDao.update(workOrder);//更新工单信息
    }

    @Override
    public WorkOrder getById(Long id) {
        WorkOrder workOrder = this.entityDao.getById(id);
        String start = workOrder.getContractStartTime();
        String end = workOrder.getContractCompleteTime();
        if (StringUtils.isNotBlank(start)) {
        	if(start.length() >= 10){
        		start = start.substring(0,10);
        		if(start.indexOf("/") != -1){
        			start = start.replaceAll("/", "-");
        		}
        	}
        	if("NULL".equals(start)){
        		start = "-";
        	}
        }else{
        	start = "-";
        }
        
        if (StringUtils.isNotBlank(end)) {
        	if(end.length() >= 10){
        		end = end.substring(0,10);
        		if(end.indexOf("/") != -1){
        			end = end.replaceAll("/", "-");
        		}
        	}
        	if("NULL".equals(end)){
        		end = "-";
        	}
        }else{
        	end = "-";
        }
        workOrder.setContractStartTime((StringUtils.isBlank(start) ? "-" : start) + " / " + (StringUtils.isBlank(end) ? "-" : end));
        String shejishi = workOrder.getStyListName();
        String jingli = workOrder.getContractorName();
        String jianli = workOrder.getSupervisorName();

    	if(StringUtils.isBlank(shejishi) || "NULL".equals(shejishi)){
    		shejishi = "-";
        }
    	if(StringUtils.isBlank(jingli) || "NULL".equals(jingli)){
    		jingli = "-";
        }
    	if(StringUtils.isBlank(jianli) || "NULL".equals(jianli)){
    		jianli = "-";
        }
        workOrder.setStyListName(shejishi + " / " + jingli + " / " + jianli);
        return workOrder;
    }

    /**
     * 根据工单id查询工单操作记录
     */
    public List<WorkOrderRemark> getRemarkByOrderId(Long orderId) {
        return workOrderDao.getRemarkByOrderId(orderId);
    }

    /**
     * 查询工单信息
     */
    public List<WorkOrder> getWorkOrderInfo() {
        List<WorkOrder> workOrderList = workOrderDao.getWorkOrderInfo();
        return workOrderList;
    }

    /**
     * 查询工单信息
     */
    public List<WorkOrder> findAllInfo(String start, String end) {
        List<WorkOrder> workOrderList = workOrderDao.findAllInfo(start, end);
        return workOrderList;
    }


    /**
     * 遍历出全部信息
     *
     * @return
     */
    public List<WorkOrder> getAllInfo(Map<String, Object> params) {
        List<WorkOrder> workOrderList = workOrderDao.search(params);
        for (WorkOrder workOrder : workOrderList) {
            switch (workOrder.getOrderStatus()) {
                case "PENDING":
                    workOrder.setOrderStatus("待处理");
                    break;
                case "ASSIGN":
                    workOrder.setOrderStatus("待分配");
                    break;
                case "NREPLY":
                    workOrder.setOrderStatus("待回复");
                    break;
                case "NVISIT":
                    workOrder.setOrderStatus("待回访");
                    break;
                case "UNEXECUTED":
                    workOrder.setOrderStatus("回访未执行");
                    break;
                case "COMPLETED":
                    workOrder.setOrderStatus("已解决");
                    break;
            }
            switch (workOrder.getWorkType() == null ? "" : workOrder.getWorkType()) {
                case "PRESALE":
                    workOrder.setWorkType("售前");
                    break;
                case "SELLING":
                    workOrder.setWorkType("售中");
                    break;
                case "AFTERSALE":
                    workOrder.setWorkType("售后");
                    break;
                default:
                    workOrder.setWorkType("");
                    break;
            }
            if (workOrder.getStatuFlag() != null) {
                switch (workOrder.getStatuFlag()) {
                    case 0:
                        workOrder.setStatusFlagName("无效");
                        break;
                    case 1:
                        workOrder.setStatusFlagName("正常");
                        break;
                }
            }
        }
        return workOrderList;
    }

    /**
     * 获取当天工单序号
     *
     * @return
     */
    public synchronized String getCurrentOrderNum() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("currentDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
        return workOrderDao.getCurrentOrderNum(paramMap);
    }

    /**
     * 生成工单编号
     *
     * @param srcCompanyCode
     * @param srcDepartmentCode
     * @return
     */
    private String generatorWorkOrderCode(String srcCompanyCode, String srcDepartmentCode) {
        return srcCompanyCode + DateUtils.format(new Date(), "yyMMdd") + getCurrentOrderNum();
    }

    /**
     * 获取用户待办事物数量--首页
     *
     * @param
     */
    public Map<String, Long> getUserWorksNum(Long comId, Long depId) {
        Map<String, Long> works = Maps.newHashMap();

        ShiroUser user = WebUtils.getLoggedUser();

        Long createNum = null;
        String userDep=user.getDepartment();
        MdniOrganization dep=null;
        if(userDep!=null &&userDep!=""){
            dep=organizationService.getById(Long.parseLong(userDep));
            if(dep.getDepType()!=null) {
                if (dep.getDepType().name().equals("LIABLEDEPARTMENT")) {
                    createNum = workOrderDao.searchTotal(ImmutableMap.of("status", "CREATE", "liableDepartment", depId == null ? "" : depId));

                } else {
                    createNum = workOrderDao.searchTotal(ImmutableMap.of("status", "CREATE", "liableCompany", comId == null ? "" : comId));
                }
            }
        } else {
            createNum = workOrderDao.searchTotal(ImmutableMap.of("status", "CREATE", "liableCompany", comId == null ? "" : comId));
        }
        //查询未派单工单数量
        works.put("createNum", createNum);

        //查询已接收工单数量
        Long receiveNum = workOrderDao.searchTotal(ImmutableMap.of("status", "RECEIVED", "liableCompany", comId == null ? "" : comId, "liableDepartment", depId == null ? "" : depId));
        works.put("receiveNum", receiveNum);


        //查询处理中工单数量
        Long processingNum = workOrderDao.searchTotal(ImmutableMap.of("status", "PROCESSING", "liableCompany", comId == null ? "" : comId, "liableDepartment", depId == null ? "" : depId));
        works.put("processingNum", processingNum);

        //查询转派工单数量
        Long refusedagainNum = workOrderDao.searchTotal(ImmutableMap.of("status", "REFUSEDAGAIN", "liableCompany", comId == null ? "" : comId, "liableDepartment", depId == null ? "" : depId));
        works.put("refusedagainNum", refusedagainNum);

        //查询待分配工单数量
        Long assignNum = workOrderDao.searchTotal(ImmutableMap.of("status", "ASSIGN", "liableCompany", comId == null ? "" : comId));
        works.put("assignNum", assignNum);

        //查询已完成工单数量
        Long completedNum = workOrderDao.searchTotal(ImmutableMap.of("status", "COMPLETED"));
        works.put("completedNum", completedNum);

        //查询暂无评价工单数量
        Long invalidNum = workOrderDao.searchTotal(ImmutableMap.of("status", "INVALIDVISIT"));
        works.put("invalidNum", invalidNum);

        //查询回访不成功工单数量
        Long failvisitNum = workOrderDao.searchTotal(ImmutableMap.of("status", "FAILUREVISIT"));
        works.put("failvisitNum", failvisitNum);


        return works;
    }

    /**
     * 获取工单报表
     *
     * @return 返回工单列表
     */
    public Object getExportOrder(Long liable1, String startDate, String endDate) {

        //查询满足条件的投诉列表
        List<WorkOrder> workOrders = workOrderDao.findByLiableAndDate(liable1, startDate, endDate);

        List<WorkOrderDto> workOrderDtos = Lists.newArrayList();

        if (!workOrders.isEmpty() && workOrders.size() > 0) {

            builderOrderDto(workOrders, workOrderDtos);
        }

        MdniDictionary liableType1 = mdniDictionaryService.getById(liable1);

        Map<String, Integer> liableResultMap = Maps.newHashMap();
        List<MdniDictionary> liableType2s = mdniDictionaryService.getByType(Integer.parseInt(liable1.toString()), 4);//获取责任职级2的列表

        Map<Integer, List<WorkOrderDto>> dtoType2Map = workOrderDtos.stream().collect(groupingBy(WorkOrderDto::getLiableType2));

        //循环责任部门2统计责任部门2的投诉数量
        if (liableType2s != null && !liableType2s.isEmpty()) {
            for (MdniDictionary liableType2 : liableType2s) {
                String liableType2Name = liableType2.getName();
                List<WorkOrderDto> type2OrderDtos = dtoType2Map.get(liableType2.getId().intValue());
                Integer orderNum = type2OrderDtos == null ? 0 : type2OrderDtos.size();
                liableResultMap.put(liableType2Name, orderNum);
            }
        }

        //根据投诉人分组
        Map<String, Integer> customerMap = Maps.newHashMap();
        for (WorkOrderDto workOrderDto : workOrderDtos) {
            String customerKey = workOrderDto.getPhone() + "&" + workOrderDto.getAddress();
            Integer orderNum = customerMap.get(customerKey) == null ? 1 : customerMap.get(customerKey) + 1;
            customerMap.put(customerKey, orderNum);
        }
        Map<String, Integer> customerNumResult = Maps.newHashMapWithExpectedSize(customerMap.size());
        for (Map.Entry<String, Integer> customerEntry : customerMap.entrySet()) {
            String key = customerEntry.getKey().substring(customerEntry.getKey().indexOf("&") + 1);
            customerNumResult.put(key, customerEntry.getValue());
        }

        //返回数据
        Map<String, Object> orderNumMap = new HashedMap(5);
        orderNumMap.put("customs", customerNumResult);
        orderNumMap.put("liable2s", liableResultMap);
        orderNumMap.put("orderTotal", workOrderDtos.size());
        orderNumMap.put("customerNum", customerNumResult.size());
        orderNumMap.put("liableTypeName", liableType1.getName());

        return orderNumMap;
    }

    /**
     * 构建workOrderDto
     *
     * @param workOrders    售后系统工单列表
     * @param workOrderDtos 返回数据dtos
     */
    private void builderOrderDto(final List<WorkOrder> workOrders, final List<WorkOrderDto> workOrderDtos) {

        if (workOrders != null && !workOrders.isEmpty()) {
            workOrders.stream().forEach(workOrder -> {

                WorkOrderDto.Builder orderBuilder = new WorkOrderDto.Builder();
                workOrderDtos.add(
                        orderBuilder.setId(workOrder.getId())
                                .setOrderCode(workOrder.getWorkOrderCode())
                                .setLiableType1(workOrder.getLiableType1().getId().intValue())
                                .setLiableType2(workOrder.getLiableType2().getId().intValue())
                                .setPhone(workOrder.getCustomerMobile())
                                .setAddress(workOrder.getCustomerAddress())
                                .build());
            });
        }
    }

    public Page<WorkOrder> findStoreWorkOrder(Map<String, Object> params, PageRequest pageable) {
        List<WorkOrder> workOrders = Collections.emptyList();
        //查询该条件下的结果总数
        long count = workOrderDao.countStoreWorkOrder(params);
        if (count > 0) {
            workOrders = workOrderDao.findStoreWorkOrder(params);
            //遍历工单集合,分别查询操作时间
            for (WorkOrder workOrder : workOrders) {
            	//设置完成时间
            	workOrder.setOperationDateFromRmk(workOrderRemarkDao.getByOrderIdAndType(workOrder.getId()));
			}
        }
        return new PageImpl<WorkOrder>(workOrders, pageable, count);
    }

    /**
     * 通过条件查询工单库: 查询门店库/门店库
     *      不带分页,且查询字段详细,用于导出文件
     * @return
     *
     * @date 2017年7月25日
     */
    public List<WorkOrderForExport> findAllStoreWorkOrder(Map<String, Object> params) {
        return workOrderDao.findAllStoreWorkOrder(params);
    }

    public List<WorkOrderReturnDto> findStoreWorkOrderRetureReport(Map<String, Object> params) {
        return workOrderDao.findAllList(params);
    }

    /**
     * @param params 统计 一段时间范围的 公司/部门下的工单个数
     * @return
     *
     * @date 2017-7-3
     */
    public Long getWorkCount(Map<String, Object> params) {
        return entityDao.getWorkCount(params);
    }

    public Long getCountByOperationType(Map<String, Object> params) {
        return entityDao.getCountByOperationType(params);
    }


    public Long getExcessiveFeedback(Map<String, Object> params) {
        return entityDao.getExcessiveFeedback(params);
    }

    public Long getOnSchedule(Map<String, Object> params) {
        return entityDao.getOnSchedule(params);
    }

    public Long getExpires(Map<String, Object> params) {
        return entityDao.getExpires(params);
    }

    public Long getGoodReceive(Map<String, Object> params) {
        return entityDao.getGoodReceive(params);
    }

    @Transactional
    public void addNewOrderByOldIdWithOrderRMK(Long workOrderId, WorkOrder workOrder) {
        ShiroUser loggedUser = WebUtils.getLoggedUser();

        //通过旧工单id查找
        WorkOrder oldWorkOrder = workOrderDao.getById(workOrderId);
        //给旧工单修改相应的新内容
        //1.投诉原因
        oldWorkOrder.setComplaintType(workOrder.getComplaintType());
        //2.工单状态:未派单
        oldWorkOrder.setOrderStatus(OrderStatus.CREATE.toString());
        //3.问题
        oldWorkOrder.setProblem(workOrder.getProblem());
        //4.客户要求回电时间
        oldWorkOrder.setCustomerFeedbackTime(workOrder.getCustomerFeedbackTime());
        //5.重要程度
        oldWorkOrder.setImportantDegree1(workOrder.getImportantDegree1());
        //6.清空原回访结果
        oldWorkOrder.setVisitResult(null);
        //7.清空处理方案,处理时间
        oldWorkOrder.setTreamentPlan(null);
        oldWorkOrder.setTreamentTime(null);
        //8.工单来源: 回访
        oldWorkOrder.setSource(mdniDictionaryService.getByName("回访"));
        
        //9.设置来源公司
        MdniOrganization org = new MdniOrganization();
        if(StringUtils.isNotBlank(loggedUser.getCompany())){
        	org.setId(Long.parseLong(loggedUser.getCompany()));
        }else{
        	org.setId(null);
        }
        org.setOrgCode(loggedUser.getCompanyCode());
        oldWorkOrder.setSrcCompany(org);
        //10.设置来源部门
        org = new MdniOrganization();
        if(StringUtils.isNotBlank(loggedUser.getDepartment())){
        	org.setId(Long.parseLong(loggedUser.getDepartment()));
        }else{
        	org.setId(null);
        }
        org.setOrgCode(loggedUser.getDepartmentCode());
        oldWorkOrder.setSrcDepartment(org);

        oldWorkOrder.setWorkOrderCode(generatorWorkOrderCode(oldWorkOrder.getSrcCompany().getOrgCode(), oldWorkOrder.getSrcDepartment().getOrgCode()));
        oldWorkOrder.setReceptionTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        oldWorkOrder.setStatuFlag(1);
        oldWorkOrder.setCreateUser(WebUtils.getLoggedUserId());
        oldWorkOrder.setCreateDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        oldWorkOrder.setFeedbackTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //设置品牌默认值0
        oldWorkOrder.setBrand(0L);
        //清空id
        oldWorkOrder.setId(null);
        //将copyFlag 改为N
        oldWorkOrder.setCopyFlag("N");
        oldWorkOrder.setUrgeTimes(0);
        oldWorkOrder.setRead(false);
        //清空拒绝时间/再次拒绝时间
        oldWorkOrder.setRefuseTime(null);
        oldWorkOrder.setRefusedagainTime(null);
        

        //一.新增工单
        workOrderDao.insert(oldWorkOrder);

        //2.插入工单轨迹表 
        WorkOrderRemark workOrderRemark = new WorkOrderRemark();
        //工单id--新生成工单的id
        workOrderRemark.setWorkOrderId(oldWorkOrder.getId());
        //备注
        if(StringUtils.isNotBlank(workOrder.getTreamentPlan())){
        	workOrderRemark.setRemark(workOrder.getTreamentPlan());
        }else{
        	String orderStatus = workOrder.getOrderStatus();
        	if(StringUtils.isNoneBlank(orderStatus)){
    			orderStatus = OrderStatus.valueOf(orderStatus).getLable();
        	}
        	workOrderRemark.setRemark("新建工单(" + orderStatus + ")");
        }
        //操作类型
        workOrderRemark.setOperationType(OrderExecuteStatus.CREATE.toString());
        //投诉原因
        workOrderRemark.setComplaintType(workOrder.getComplaintType());

        //二.新增工单轨迹
        workOrderRemarkService.insertWorkOrderRemark(workOrderRemark, WebUtils.getLoggedUserId());
    }

    public Long getFirstTimeCount(Map<String, Object> params) {
        return entityDao.getFirstTimeCount(params);
    }

    public Long getReminderCount(Map<String, Object> params) {
        return entityDao.getReminderCount(params);
    }

    public Long getResultCount(Map<String, Object> params) {
        return entityDao.getResultCount(params);
    }

    public List<WorkOrder> findWorkOrdersByCustomerId(String customerId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("customerId", customerId);
        params.put("sort", new Sort(Sort.Direction.valueOf("DESC"), "id"));
        return workOrderDao.findStoreWorkOrder(params);
    }

	public List<WorkOrder> batchOrderStatus(List<WorkOrder> list) {
		if(CollectionUtils.isEmpty(list)){
			return list;
		}
		for (WorkOrder workOrder : list) {
			if(StringUtils.isNotBlank(workOrder.getOrderStatus())){
				switch (workOrder.getOrderStatus()) {
					case "CREATE":
						workOrder.setOrderStatus("未派单");
						break;
					case "RECEIVED":
						workOrder.setOrderStatus("已接收");
						break;
					case "PROCESSING":
						workOrder.setOrderStatus("处理中");
						break;
					case "REFUSED":
						workOrder.setOrderStatus("申诉");
						break;
					case "REFUSEDAGAIN":
						workOrder.setOrderStatus("申诉无效");
						break;
					case "PENDING":
						workOrder.setOrderStatus("待处理");
						break;
					case "SATISFIED":
						workOrder.setOrderStatus("回访满意");
						break;
					case "COMMONLY":
						workOrder.setOrderStatus("一般满意");
						break;
					case "UNSATISFIED":
						workOrder.setOrderStatus("回访不满意");
						break;
					case "INVALIDVISIT":
						workOrder.setOrderStatus("暂无评价");
						break;
					case "FAILUREVISIT":
						workOrder.setOrderStatus("不再回访");
						break;
					case "ASSIGN":
						workOrder.setOrderStatus("待分配");
						break;
					case "URGE":
						workOrder.setOrderStatus("催单");
						break;
					case "NREPLY":
						workOrder.setOrderStatus("待回复");
						break;
					case "NVISIT":
						workOrder.setOrderStatus("待回访");
						break;
					case "CONSULTOVER":
						workOrder.setOrderStatus("咨询完毕");
						break;
					case "UNEXECUTED":
						workOrder.setOrderStatus("回访未执行");
						break;
					case "COMPLETED":
						workOrder.setOrderStatus("已完成");
						break;
				}
			}
			//工单分类
			if(StringUtils.isNotBlank(workOrder.getWorkType())){
				switch (workOrder.getWorkType()) {
	                case "PRESALE":
	                    workOrder.setWorkType("售前");
	                    break;
	                case "SELLING":
	                    workOrder.setWorkType("售中");
	                    break;
	                case "AFTERSALE":
	                    workOrder.setWorkType("售后");
	                    break;
	                default:
	                    workOrder.setWorkType("");
	                    break;
				}
			}
			//状态
            if (workOrder.getStatuFlag() != null) {
                switch (workOrder.getStatuFlag()) {
                    case 0:
                        workOrder.setStatusFlagName("无效");
                        break;
                    case 1:
                        workOrder.setStatusFlagName("正常");
                        break;
                }
            }
		}
		
		return list;
	}

	/**
	 * 通过条件查询  工单条数
	 * @param params
	 * @return 
	 */
	public Long countStoreWorkOrder(Map<String, Object> params) {
		return workOrderDao.countStoreWorkOrder(params);
	}
	/**
	 * 远程调用用于修改工单状态  插入轨迹
	 * @param params
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public StatusDto remoteWorkeOrder(Map<String, Object> params) {
       try {
            String status =(String) params.get("status");
           String workorderId =(String)params.get("workorderId");
            Long operationUser = Long.valueOf((String) params.get("operationUser"));
            String operationTime = (String) params.get("operationTime");
            WorkOrder workOrder = new WorkOrder();
            workOrder.setWorkOrderCode(workorderId);
            workOrder.setOrderStatus(status);


           WorkOrderRemark workOrderRemark = new WorkOrderRemark();
           Long byCode = entityDao.getByCode(workorderId);
           workOrderRemark.setWorkOrderId(byCode);
           workOrderRemark.setOperationDate(operationTime);
           User user = new User();
           user.setId(operationUser);
           workOrderRemark.setOperationUser(user);
            //接收
            if(status.equals(OrderStatus.RECEIVED.toString())){
                //添加轨迹
                workOrderRemark.setOperationType(OrderExecuteStatus.RECEIVE.toString());
            }else if(status.equals(OrderStatus.PROCESSING.toString())){
                String remarks = (String) params.get("remarks");
                String treamentTime = (String) params.get("treamentTime");
                workOrder.setTreamentTime(treamentTime);
                workOrder.setTreamentPlan(remarks);
                //添加轨迹
                workOrderRemark.setOperationType(OrderExecuteStatus.OPERATION.toString());
             //驳回 申诉
            }else if(status.equals(OrderStatus.ASSIGN.toString())){
                workOrder.setRefuseTime(DateUtil.parse(operationTime,"yyyy-MM-dd HH:mm:ss"));
                String remarks = (String) params.get("remarks");
                //添加轨迹
                workOrderRemark.setOperationType(OrderExecuteStatus.REJECT.toString());
                workOrderRemark.setRemark(remarks);
             //已完成
            }else if(status.equals(OrderStatus.COMPLETED.toString())){
                workOrderRemark.setOperationType(OrderExecuteStatus.FINISHORDER.toString());
            }
           //修改工单
           entityDao.serviceUpdate(workOrder);
           logger.debug("远程调用修改工单，工单号为"+workOrder.getWorkOrderCode());
           //添加轨迹
           workOrderRemarkService.insert(workOrderRemark);
           logger.debug("远程调用插入工单轨迹");
       }catch (Exception e){
           e.printStackTrace();
           return StatusDto.buildStatusDtoWithCode("500","操作失败");
       }
        return StatusDto.buildSuccessStatusDto("修改成功");
    }

    /**
     * 获取推送失败的工单列表
     * @param params
     * @return
     */
    public List<MdniOrder> getOrderInfoFailList(Map<String, Object> params) {
        return entityDao.getOrderInfoFailList(params);
    }

    /**
     * 获取推送失败的工单列表条数
     * @param params
     * @return
     */
    public Long getOrderInfoFailListTotal(Map<String, Object> params){
        return entityDao.getOrderInfoFailListTotal(params);
    }

    /**
     * 工单同步
     * @param workorderId
     * @return
     */

    @Transactional(rollbackFor = Exception.class)
    public Object synWorkOrder(Long workorderId,String status) {
        try {
            pushWorkOrderAndRemark(null,null,workorderId,status);
            return StatusDto.buildSuccessStatusDto("推送完成");
        }catch (Exception e){
            return StatusDto.buildFailureStatusDto("推送失败");
        }
    }


    /**
     *
     * @date 2017-10-9 16:53:14
     * 查询 当月/当天 公司/部门下的工单详情   然后 组装数据 返回查询的列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void findAllListForCall(Boolean isGroup){
        if(isGroup==null){
            //默认集团
            isGroup=true;
        }
        //查询参数
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        //组装成当天的日期
        Date date = new Date();
        //设置时间
        date.setHours(Constants.WORK_ORDER_TIME);
        date.setMinutes(MINUTES);
        date.setSeconds(MINUTES);
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        Date parse2 = calendar.getTime();   //得到前一天的时间
        String format = DateUtils.format(parse2, YYYY_MM_DD_HH_MM_SS);
        Date date3=new Date();
        date3.setHours(Constants.WORK_ORDER_OPERATE_TIME_HOUR);
        date3.setMinutes(Constants.WORK_ORDER_OPERATE_TIME_MINUTE);
        date3.setSeconds(MINUTES);
        params.put("date1",format); //前一天 4点
        params.put("date2",date); //今天 4点
        params.put("date3",date); //今天 4点
        params.put("date4",date3); //今天 5点 30
        //当天的数据
        List<WorkOrder> dayCollect = entityDao.findAllListForCall(params);
        //组装当月的数据
        //获取前一个月最后一天
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        calendar2.set(Calendar.DAY_OF_MONTH, 0);
        String lastDay = DateUtils.format(calendar2.getTime(), YYYY_MM_DD_HH_MM_SS);
        params.put("date1",lastDay); //前一个月最后一天
        //当月的数据
        List<WorkOrder> monthCollect = entityDao.findAllListForCall(params);
        //组装数据
        ArrayList<ReportNissin> reportNissins = new ArrayList<ReportNissin>();
        //取出组织结构去重 从月份中取出
        Map<Long, String> orgMap=null;
        //分店的排序用的
        Map<Long, Set<Long>> collect=null;
        if(isGroup) {
             orgMap = monthCollect.stream().collect(Collectors.toMap(a -> {
                return a.getLiableCompany().getId();
            }, b -> {
                return b.getLiableCompany().getOrgName();
            }, (c, d) -> c));
        }else{
            orgMap = monthCollect.stream().collect(Collectors.toMap(a -> {
                return a.getLiableDepartment().getId();
            }, b -> {
                return b.getLiableDepartment().getOrgName();
            }, (c, d) -> c));
            collect= monthCollect.stream().collect(groupingBy(a -> a.getLiableCompany().getId(), mapping(b -> b.getLiableDepartment().getId(), toSet())));
        }

        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(2);
        //空指针处理  一般不会出现
        if(monthCollect!=null&&dayCollect!=null){
        //遍历结构 进行统计
        for (Map.Entry<Long, String>  entry:orgMap.entrySet()) {
            String orgName = entry.getValue();
            Long key = entry.getKey();
            ReportNissin reportNissin = new ReportNissin();
            reportNissin.setOrgId(entry.getKey());
            reportNissin.setOrgName(orgName);
            //从当天/当月的数据中取出遍历组织的数据
            List<WorkOrder> orgMonth=null;
            List<WorkOrder> orgDay=null;
            if(isGroup) {
                orgMonth = monthCollect.stream().filter(a -> key.equals(a.getLiableCompany().getId())).collect(Collectors.toList());
                orgDay = dayCollect.stream().filter(a -> key.equals(a.getLiableCompany().getId())).collect(Collectors.toList());
            }else{
                orgMonth = monthCollect.stream().filter(a -> key.equals(a.getLiableDepartment().getId())).collect(Collectors.toList());
                orgDay = dayCollect.stream().filter(a -> key.equals(a.getLiableDepartment().getId())).collect(Collectors.toList());
            }
            //空指针处理  一般不会出现
            if(orgMonth!=null&&orgDay!=null) {
                //当天的
                    //工单数
                    int daySize = orgDay.size();
                    reportNissin.setDaySize(Long.valueOf(daySize));
                    //客管部待分配
                    long count = orgDay.stream().filter(a -> OrderStatus.ASSIGN.toString().equals(a.getOrderStatus())).count();
                    reportNissin.setDayAssign(Long.valueOf(count));
                    //责任人部门
                    List<WorkOrder> libDayCollect = orgDay.stream().filter(a -> DEPTYPE.LIABLEDEPARTMENT.equals(a.getLiableDepartment().getDepType())).collect(Collectors.toList());
                    long score1=0;
                    if(libDayCollect!=null) {
                        //申诉无效
                        long refusedAgain = libDayCollect.stream().filter(a -> OrderStatus.REFUSEDAGAIN.toString().equals(a.getOrderStatus())).count();
                        reportNissin.setDayRefusedAgain(Long.valueOf(refusedAgain));
                        //未派单
                        long create = libDayCollect.stream().filter(a -> OrderStatus.CREATE.toString().equals(a.getOrderStatus())).count();
                        reportNissin.setDayCreate(Long.valueOf(create));
                        //已接收
                        long received = libDayCollect.stream().filter(a -> OrderStatus.RECEIVED.toString().equals(a.getOrderStatus())).count();
                        reportNissin.setDayReceive(Long.valueOf(received));
                        //得分
                        score1 = (refusedAgain + create + received) * -5;
                    }
                        reportNissin.setScore1(Double.valueOf(score1));
                //当月客诉的处理
                    //已到期未完成
                    List<WorkOrder> maturityCollect = orgMonth.stream().filter(a -> a!=null&&a.getTreamentTime()!=null&& parse2.after(DateUtils.parse(a.getTreamentTime(), YYYY_MM_DD_HH_MM_SS,YYYY_MM_DD_HH_MM,YYYY_MM_DD))).collect(toList());
                    int size=0;
                    double score2 = 0D;
                    long noProcessing=0;
                    double solveRate=0d;
                    if(maturityCollect!=null) {
                        //已到期
                        size = maturityCollect.size();
                        //未解决
                       noProcessing = maturityCollect.stream().filter(a -> OrderStatus.PROCESSING.toString().equals(a.getOrderStatus())).count();
                        //解决率
                        if(size!=0) {
                            double sizeDouble = Double.longBitsToDouble(size);
                            solveRate =getDoubleForTwoSize((sizeDouble - Double.longBitsToDouble(noProcessing)) / sizeDouble*100);
                            //得分
                            score2 = getDoubleForTwoSize(solveRate * 30/100);
                        }
                    }
                    reportNissin.setMonthProcessingRate(Double.valueOf(solveRate));
                    reportNissin.setMonthMaturity(Long.valueOf(size));
                    reportNissin.setMonthNoProcessing(Long.valueOf(noProcessing));
                    reportNissin.setScore2(Double.valueOf(score2));
                    //当月总解决率
                    //当月量
                    int monthSize = orgMonth.size();
                    reportNissin.setMonthSize(Long.valueOf(monthSize));
                    //解决量
                    long mothSolve = orgMonth.stream().filter(a -> OrderStatus.SATISFIED.toString().equals(a.getOrderStatus())
                            || OrderStatus.COMMONLY.toString().equals(a.getOrderStatus())
                            || OrderStatus.UNSATISFIED.toString().equals(a.getOrderStatus())
                            || OrderStatus.INVALIDVISIT.toString().equals(a.getOrderStatus())
                            || OrderStatus.FAILUREVISIT.toString().equals(a.getOrderStatus())
                            || OrderStatus.NREPLY.toString().equals(a.getOrderStatus())
                            || OrderStatus.NVISIT.toString().equals(a.getOrderStatus())
                            || OrderStatus.CONSULTOVER.toString().equals(a.getOrderStatus())
                            || OrderStatus.UNEXECUTED.toString().equals(a.getOrderStatus())
                            || OrderStatus.COMPLETED.toString().equals(a.getOrderStatus())
                    ).count();
                    reportNissin.setMonthSolve(Long.valueOf(mothSolve));
                    //未解决
                    long mothNoSolve = orgMonth.stream().filter(a ->
                            OrderStatus.ASSIGN.toString().equals(a.getOrderStatus())
                                    || OrderStatus.REFUSEDAGAIN.toString().equals(a.getOrderStatus())
                                    || OrderStatus.CREATE.toString().equals(a.getOrderStatus())
                                    || OrderStatus.RECEIVED.toString().equals(a.getOrderStatus())
                                    || OrderStatus.PROCESSING.toString().equals(a.getOrderStatus())
                    ).count();
                    reportNissin.setMonthNoSolve(Long.valueOf(mothNoSolve));
                    //总解决率
                    double score3=0D;
                    double allSolveRate=0d;
                    if(monthSize!=0) {
                         allSolveRate = getDoubleForTwoSize(Double.longBitsToDouble(mothSolve) / Double.longBitsToDouble(monthSize)*100);
                        //得分
                         score3 = getDoubleForTwoSize(allSolveRate * 20/100);
                    }
                    reportNissin.setMonthSolveRate(Double.valueOf(allSolveRate));
                    reportNissin.setScore3(Double.valueOf(score3));
                //客管部回访
                    //成功回访满意率
                    double score4 = 30d;
                    if(maturityCollect!=null) {
                        //满意
                        long satisfied = maturityCollect.stream().filter(a -> OrderStatus.SATISFIED.toString().equals(a.getOrderStatus())).count();
                        reportNissin.setVisitSatisfied(Long.valueOf(satisfied));
                        //一般满意
                        long commonly = maturityCollect.stream().filter(a -> OrderStatus.COMMONLY.toString().equals(a.getOrderStatus())).count();
                        reportNissin.setVisitCommonly(Long.valueOf(commonly));
                        //不满意
                        long unsatisfied = maturityCollect.stream().filter(a -> OrderStatus.UNSATISFIED.toString().equals(a.getOrderStatus())).count();
                        reportNissin.setVisitUnsatisfied(Long.valueOf(unsatisfied));
                        //得分
                        long returnVisit = satisfied + commonly + unsatisfied;
                        if (returnVisit != 0L) {
                            score4 = getDoubleForTwoSize(Double.longBitsToDouble(satisfied) / Double.longBitsToDouble(returnVisit) * 30);
                        }
                        //回访失败
                        //暂无评价
                        long invalidvisit = maturityCollect.stream().filter(a -> OrderStatus.INVALIDVISIT.toString().equals(a.getOrderStatus())).count();
                        reportNissin.setVisitInvalid(Long.valueOf(invalidvisit));
                        //拒绝回访
                        long failurevisit = maturityCollect.stream().filter(a -> OrderStatus.FAILUREVISIT.toString().equals(a.getOrderStatus())).count();
                        reportNissin.setVisitFail(Long.valueOf(failurevisit));
                        //未回访
                        long completed = maturityCollect.stream().filter(a -> OrderStatus.COMPLETED.toString().equals(a.getOrderStatus())).count();
                        reportNissin.setVisitCompleted(Long.valueOf(completed));
                    }
                reportNissin.setScore4(Double.valueOf(score4));
                //回访未执行
                    double score5=20d;
                    double unsatisfiedAndComplainTypeRate=0d;
                    if(maturityCollect!=null) {
                        //数量
                        long unsatisfiedAndComplainType = maturityCollect.stream().filter(a -> OrderStatus.UNSATISFIED.toString().equals(a.getOrderStatus()) && a.getComplaintType()!=null&&"回访未执行" .equals( a.getComplaintType().getName())).count();
                        reportNissin.setVisitUnsatisfiedComplain(Long.valueOf(unsatisfiedAndComplainType));
                        //未执行率
                        if (size - noProcessing != 0) {
                            unsatisfiedAndComplainTypeRate = getDoubleForTwoSize(Double.longBitsToDouble(unsatisfiedAndComplainType) / (Double.longBitsToDouble(size) - Double.longBitsToDouble(noProcessing))*100);
                            //得分
                            score5 = getDoubleForTwoSize((1 - unsatisfiedAndComplainTypeRate/100) * 20);
                        }
                    }
                reportNissin.setMonthUnsatisfiedComplainRate(Double.valueOf(unsatisfiedAndComplainTypeRate));
                reportNissin.setScore5(Double.valueOf(score5));
                //计算合计 一行
                double score=getDoubleForTwoSize(score1+score2+score3+score4+score5);
                reportNissin.setScore(Double.valueOf(score));
                reportNissins.add(reportNissin);
            }
        }
        }
        //得分排序
        if(reportNissins!=null&&reportNissins.size()>0){
            //集团的
            if(isGroup) {
                reportNissins.sort((o1, o2) -> o2.getScore().compareTo(o1.getScore()));
                reportNissins.stream().forEach(a -> {
                    a.setSort(Long.valueOf(reportNissins.indexOf(a) + 1));
                    a.setCreateDate(new Date());
                });
                reportNissinDao.insertBatch(reportNissins);
                //分店的
            }else{
                for (Map.Entry<Long,Set<Long>> entry:collect.entrySet()) {
                    Long key = entry.getKey();
                    Set<Long> value = entry.getValue();
                    List<ReportNissin> collect1 = reportNissins.stream().filter(a -> value.contains(a.getOrgId())).collect(toList());
                    collect1.sort((o1, o2) -> o2.getScore().compareTo(o1.getScore()));
                    collect1.stream().forEach(a -> {
                        a.setSort(Long.valueOf(collect1.indexOf(a) + 1));
                        a.setCreateDate(new Date());
                    });
                    reportNissinDao.insertBatch(collect1);
                }
            }

        }

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
}
