package com.rocoinfo.repository.sale.workorder;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.workorder.WorkOrder;
import com.rocoinfo.entity.sale.workorder.WorkOrderRemark;

import java.util.Date;
import java.util.List;

/**
 * <dl>
 * <dd>Description: 工单轨迹 Dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017年6月30日 下午3:02:53</dd>
 * <dd>@author：Paul</dd>
 * </dl>
 */
@Repository
public interface WorkOrderRemarkDao extends CrudDao<WorkOrderRemark> {

	/**
     * 创建工单备注信息
     *
     * @param workOrderRemark
     */
    void insertOrderRemark(WorkOrderRemark workOrderRemark);

    List<WorkOrderRemark> findRemainderById(@Param("orderId") Long orderId);

    /**
     * 通过工单id和操作类型  获取工单轨迹操作时间
     * @param id
     * @return
     */
	Date getByOrderIdAndType(Long id);
}
