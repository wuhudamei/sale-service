package com.damei.repository.sale.workorder;

import com.damei.entity.sale.workorder.WorkOrderRemark;
import com.damei.common.persistence.CrudDao;
import com.damei.dto.WorkOrderReturnDto;
import com.damei.entity.sale.mdniorder.MdniOrder;
import com.damei.entity.sale.workorder.WorkOrder;
import com.damei.entity.sale.workorder.WorkOrderForExport;
import com.damei.entity.sale.workorder.WorkOrderServiceDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface WorkOrderDao extends CrudDao<WorkOrder> {

    List<WorkOrder> getWorkOrderInfo();

    List<WorkOrder> findAllInfo(@Param(value = "start") String start,@Param(value = "end") String end);

    String getCurrentOrderNum(Map<String, String> paramMap);

    Long insertCopy(WorkOrder workOrder);

    void updateWorkInfo(WorkOrder workOrder);

    void updateWorkCopyFlag(Long id);

    List<WorkOrderRemark> getRemarkByOrderId(Long orderId);

    Long searchByDate(@Param(value = "start") String start, @Param(value = "end") String end);


    List<WorkOrder> findByLiableAndDate(@Param(value = "liable1") Long liable1,
                                        @Param(value = "startDate") String startDate,
                                        @Param(value = "endDate") String endDate);

    List<WorkOrderServiceDto> orderService(Map<String, Object> params);

	List<WorkOrder> findStoreWorkOrder(Map<String, Object> params);

	Long countStoreWorkOrder(Map<String, Object> params);

	List<WorkOrderReturnDto>findAllList(Map<String, Object> params);

	Long getWorkCount(Map<String, Object> params);

	Long getCountByOperationType(Map<String, Object> params);
	Long getExcessiveFeedback(Map<String, Object> params);
	Long getOnSchedule(Map<String, Object> params);
	Long getExpires(Map<String, Object> params);
	Long getGoodReceive(Map<String, Object> params);
	Long getFirstTimeCount(Map<String, Object> params);
	Long getReminderCount(Map<String, Object> params);
    Long getResultCount(Map<String, Object> params);

	List<WorkOrderForExport> findAllStoreWorkOrder(Map<String, Object> params);
    void serviceUpdate(WorkOrder workOrder);
    WorkOrderServiceDto pushWordOrder(Long id);
    Long getByCode(String code);

    List<MdniOrder> getOrderInfoFailList(Map<String, Object> params);
    Long getOrderInfoFailListTotal(Map<String, Object> params);

    List<WorkOrder>findAllListForCall(Map<String, Object> params);
}
