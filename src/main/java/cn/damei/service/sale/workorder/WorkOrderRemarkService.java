package cn.damei.service.sale.workorder;

import cn.damei.entity.sale.workorder.WorkOrderRemark;
import cn.damei.common.service.CrudService;
import cn.damei.entity.sale.account.User;
import cn.damei.repository.sale.workorder.WorkOrderDao;
import cn.damei.repository.sale.workorder.WorkOrderRemarkDao;
import cn.damei.utils.DateUtils;
import cn.damei.utils.WebUtils;
import cn.damei.entity.sale.workorder.WorkOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
