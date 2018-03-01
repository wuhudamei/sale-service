package com.damei.service.sale.workorder;

import com.damei.common.service.CrudService;
import com.damei.entity.sale.account.User;
import com.damei.entity.sale.workorder.TreamentApproval;
import com.damei.entity.sale.workorder.WorkOrderRemark;
import com.damei.enumeration.OrderExecuteStatus;
import com.damei.repository.sale.account.UserDao;
import com.damei.repository.sale.workorder.WorkOrderDao;
import com.damei.repository.sale.workorder.WorkOrderRemarkDao;
import com.damei.shiro.ShiroUser;
import com.damei.utils.DateUtils;
import com.damei.Constants;
import com.damei.dto.StatusDto;
import com.damei.entity.sale.workorder.TreamentResult;
import com.damei.entity.sale.workorder.WorkOrder;
import com.damei.enumeration.OrderStatus;
import com.damei.repository.sale.workorder.TreamentApprovalDao;
import com.damei.repository.sale.workorder.TreamentResultDao;
import com.damei.service.sale.account.UserService;
import com.damei.utils.TemplateUtils;
import com.damei.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TreamentApprovalService extends CrudService<TreamentApprovalDao, TreamentApproval> {
    @Autowired
    private WorkOrderDao workOrderDao;
    @Autowired
    private TreamentResultDao TreamentResultDao;
    @Autowired
    private WorkOrderRemarkDao workOrderRemarkDao;
    @Autowired
    private TreamentResultDao treamentResultDao;
    @Autowired
    private UserDao userdao;
    @Autowired
    private UserService userService;


    /**
     * 添加 记录修改
     * @param treamentApproval
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public StatusDto insertApproval(TreamentApproval treamentApproval, String status) {
        //添加轨迹
            entityDao.insert(treamentApproval);
        //工单修改状态
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(treamentApproval.getWorkorderId());
        //申请
        workOrder.setTreamentTimeUpdate(status);
        workOrderDao.update(workOrder);
        //通知 XX 审批
        //验证部门领导
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        String company = loggedUser.getCompany();
        String department = loggedUser.getDepartment();
        if(StringUtils.isEmpty(company)||StringUtils.isEmpty(department)){
            return StatusDto.buildDataFailureStatusDto("请联系管理员，先设置部门");
        }

        //1.查询当前人所在部门的领导
        List<User> departmentHead = userService.findDepartmentHead(Long.valueOf(company), Long.valueOf(department));

        //2.查询当前人所在部门 是否有兼职领导
        List<User> partTimeJobHead = userService.findPartTimeJobHead(Long.valueOf(department));
        if((departmentHead == null || departmentHead.size() == 0) && (partTimeJobHead == null
                || partTimeJobHead.size() == 0)){
            return StatusDto.buildDataFailureStatusDto("请联系管理员，先设置部门负责人");
        }
        //合并
        departmentHead.addAll(partTimeJobHead);

        for (User user : departmentHead) {
            //过滤 可以发模板消息的
            if(user.getRemindFlag() == 1){
                //发送审批模板消息--给当前人部门领导
                //url: PropertyHolder.getBaseurl()+"/workorder/approval?id=" +treamentApproval.getWorkorderId()+"&ids="+treamentApproval.getId()

                TemplateUtils.sendApprovalTemplate("工单延期待审批",user.getAccount(),
                        TemplateUtils.getOauthUrl("/workorder/approval?id=" +treamentApproval.getWorkorderId()+";ids="+treamentApproval.getId()),
                        loggedUser.getName(),
                        loggedUser.getDepartmentName(),"工单延期审批",
                        DateUtils.format(new Date(),"yyyy-MM-dd HH:mm"));  // 申请后的时间:treamentApproval.getNewTime()
                logger.debug("延期审批模板通知");
            }
        }
        return StatusDto.buildDataSuccessStatusDto("申请成功");
    }

    /**
     * 申请列表
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Page<TreamentApproval> approvalList(Map<String, Object> params, PageRequest pageable) {
        List<TreamentApproval> pageData = Collections.emptyList();
        Long count =0l;
        params.put(Constants.PAGE_OFFSET, pageable.getOffset()/pageable.getPageSize());
        params.put(Constants.PAGE_SIZE, pageable.getPageSize());
        params.put(Constants.PAGE_SORT, pageable.getSort());
        pageData = entityDao.approvalList(params);
        if(pageData!=null&&pageData.size()>0){
            count=Long.valueOf(pageData.size());
        }
        return new PageImpl<TreamentApproval>(pageData, pageable, count);
    }
    /**
     * 审批
     */
    @Transactional(rollbackFor = Exception.class)
    public StatusDto result(TreamentResult treamentApproval) {
        //修改状态
        WorkOrder workOrder = new WorkOrder();
        Long workorderId = treamentApproval.getWorkorderId();
        workOrder.setId(workorderId);
        workOrder.setTreamentTimeUpdate(treamentApproval.getApprovalResult());
        //修改后的时间
        String newTime = DateUtils.format(entityDao.getById(treamentApproval.getApprovalId()).getNewTime(), "yyyy-MM-dd HH:mm:ss");
        workOrder.setTreamentTime(newTime);
        workOrderDao.update(workOrder);
        //添加结果
        TreamentResultDao.insert(treamentApproval);
        if("1".equals(treamentApproval.getApprovalResult())){
            //添加轨迹
            WorkOrderRemark workOrderRemark = new WorkOrderRemark();
            workOrderRemark.setOperationDate(DateUtils.format(treamentApproval.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
            workOrderRemark.setWorkOrderId(workorderId);
            // 设置 备注
            workOrderRemark.setRemark("修改预期时间");
            workOrderRemark.setOperationType(OrderExecuteStatus.MODIFYEXPECTEDTIME.toString());
            User user = new User();
            user.setId(WebUtils.getLoggedUser().getId());
            workOrderRemark.setOperationUser(user);
            workOrderRemarkDao.insertOrderRemark(workOrderRemark);

            //发送修改预计完成时间 通知
            //String head, String jobNo, String url, String workOrderCode,
            //String remark, String orderStatus, String operationUser
            ShiroUser shiroUser = WebUtils.getLoggedUser();

            //通过工单id查询旧工单详情
            WorkOrder oldWorkOrder = workOrderDao.getById(workOrder.getId());

            //根据工单创建人id 查询创建者jobNo
            User createUser = userService.getById(oldWorkOrder.getCreateUser());
            if(createUser != null){
                TemplateUtils.sendWorkOrderStageTemplate("工单修改预计完成时间通知",
                        createUser.getAccount(), TemplateUtils.getOauthUrl("/workorder/workOrderInfo?workOrderId=" + workOrder.getId()),
                        oldWorkOrder.getWorkOrderCode(), "您好，您发起的工单预期完成时间已经被修改。修改为:" + newTime,
                        OrderStatus.valueOf(oldWorkOrder.getOrderStatus()).getLable(),
                        shiroUser.getName());
            }
        }
        return StatusDto.buildDataSuccessStatusDto("提交成功");
    }


    /**
     * 驳回记录
     * @return
     */
    public TreamentResult getBack(Long workorderId) {
       return treamentResultDao.getBack(workorderId);
    }

}
