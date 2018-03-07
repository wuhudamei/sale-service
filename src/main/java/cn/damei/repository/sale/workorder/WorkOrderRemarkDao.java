package cn.damei.repository.sale.workorder;

import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sale.workorder.WorkOrderRemark;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WorkOrderRemarkDao extends CrudDao<WorkOrderRemark> {

    void insertOrderRemark(WorkOrderRemark workOrderRemark);

    List<WorkOrderRemark> findRemainderById(@Param("orderId") Long orderId);
	Date getByOrderIdAndType(Long id);
}
