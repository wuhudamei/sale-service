package com.rocoinfo.service.sale.workorder;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.sale.account.User;
import com.rocoinfo.entity.sale.workorder.WorkOrder;
import com.rocoinfo.entity.sale.workorder.WorkOrderRemark;
import com.rocoinfo.repository.sale.workorder.WorkOrderDao;
import com.rocoinfo.repository.sale.workorder.WorkOrderRemarkDao;
import com.rocoinfo.utils.DateUtils;
import com.rocoinfo.utils.WebUtils;

/**
 * <dl>
 * <dd>Description: 工单轨迹 Service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017年6月30日 下午2:52:07</dd>
 * <dd>@author：Paul</dd>
 * </dl>
 */
@Service
public class WorkOrderRemarkService extends CrudService<WorkOrderRemarkDao, WorkOrderRemark> {
	
	@Autowired
	private WorkOrderRemarkDao workOrderRemarkDao;
	@Autowired
	private WorkOrderDao workOrderDao;
	
	 /**
     * 插入工单轨迹表
     * 创建工单备注信息
     * @param userId
     */
    @Transactional
    public void insertWorkOrderRemark(WorkOrderRemark workOrderRemark, Long userId) {
        workOrderRemark.setOperationUser(new User());
        workOrderRemark.getOperationUser().setId(userId);
        workOrderRemark.setOperationDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        workOrderRemarkDao.insertOrderRemark(workOrderRemark);
    }

    public List<WorkOrderRemark> findRemainderById(Long orderId){
        return this.entityDao.findRemainderById(orderId);
    }

    /**
     * 催单 :插入工单轨迹表  同时将工单表催单次数+1,且isRead为false
     * @author Paul
     * @date 2017年7月6日 下午2:37:35
     * @param workOrderRemark
     */
    @Transactional(rollbackFor=Exception.class)
	public void remainder(WorkOrderRemark workOrderRemark) {
    	//1.更新工单表
	 	WorkOrder workOrder = workOrderDao.getById(workOrderRemark.getWorkOrderId());
	 	workOrder.setUrgeTimes(workOrder.getUrgeTimes() + 1);
	 	workOrder.setRead(true);
	 	workOrderDao.update(workOrder);
	 	//2.插入工单轨迹表
        insertWorkOrderRemark(workOrderRemark, WebUtils.getLoggedUserId());
	}
    
    /**
     * 通过工单id和操作类型  获取工单轨迹操作时间
     * @param id
     * @return
     */
    public Date getByOrderIdAndType(Long id){
    	return workOrderRemarkDao.getByOrderIdAndType(id);
    }
}
