package com.rocoinfo.repository.sale.workorder;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.dto.WorkOrderReturnDto;
import com.rocoinfo.entity.sale.account.User;
import com.rocoinfo.entity.sale.mdniorder.MdniOrder;
import com.rocoinfo.entity.sale.workorder.WorkOrder;
import com.rocoinfo.entity.sale.workorder.WorkOrderForExport;
import com.rocoinfo.entity.sale.workorder.WorkOrderRemark;
import com.rocoinfo.entity.sale.workorder.WorkOrderServiceDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <dl>
 * <dd>Description: 工单信息Dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-03-10 16:47:26</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@Repository
public interface WorkOrderDao extends CrudDao<WorkOrder> {

    /**
     * 查询工单信息
     *
     * @return
     */
    List<WorkOrder> getWorkOrderInfo();

    /**
     * 查询工单及关联信息
     *
     * @return
     */
    List<WorkOrder> findAllInfo(@Param(value = "start") String start,@Param(value = "end") String end);

    /**
     * 获取当日工单序号
     *
     * @return
     */
    String getCurrentOrderNum(Map<String, String> paramMap);

    Long insertCopy(WorkOrder workOrder);

    void updateWorkInfo(WorkOrder workOrder);

    void updateWorkCopyFlag(Long id);

    /**
     * 根据orderId获取工单操作记录列表
     */
    List<WorkOrderRemark> getRemarkByOrderId(Long orderId);

    Long searchByDate(@Param(value = "start") String start, @Param(value = "end") String end);


    /**
     * 根据责任职级1和时间查询投诉列表
     *
     * @param liable1   责任职级1的id
     * @param startDate 查询开始时间
     * @param endDate   查询结束时间
     * @return 返回投诉列表
     */
    List<WorkOrder> findByLiableAndDate(@Param(value = "liable1") Long liable1,
                                        @Param(value = "startDate") String startDate,
                                        @Param(value = "endDate") String endDate);

    List<WorkOrderServiceDto> orderService(Map<String, Object> params);

    /**
     * 工单库: 查询门店库/集团库
     * @author Paul
     * @date 2017年6月28日 下午8:37:24
     * @return
     */
	List<WorkOrder> findStoreWorkOrder(Map<String, Object> params);

	/**
	 * 查询工单库 总条数
	 * @author Paul
	 * @date 2017年6月29日 下午7:51:56
	 * @param params
	 * @return
	 */
	Long countStoreWorkOrder(Map<String, Object> params);

    /**
     * @author Ryze
     * @date 2017-7-3
     * 查询 公司/部门下的工单
     * @param params
     * @return
     */
	List<WorkOrderReturnDto>findAllList(Map<String, Object> params);

    /**
     * @author Ryze
     * @date 2017-7-3
     * @param params
     * 统计 一段时间范围的 公司/部门下的工单个数
     * @return
     */
	Long getWorkCount(Map<String, Object> params);

    /**
     * @author Ryze
     * @date 2017-7-3
     * @param params
     * 统计 一段时间范围的 公司/部门下 指定工单操作类型的工单个数
     * @return
     */
	Long getCountByOperationType(Map<String, Object> params);
    /**
     * @author Ryze
     * @date 2017-7-3
     * @param params
     * 统计 超期未反馈
     * @return
     */
	Long getExcessiveFeedback(Map<String, Object> params);
    /**
     * @author Ryze
     * @date 2017-7-3
     * @param params
     * 统计 超期/按期 完成 flag='Y'/flag='N'
     * @return
     */
	Long getOnSchedule(Map<String, Object> params);
    /**
     * @author Ryze
     * @date 2017-7-3
     * @param params
     * @return 统计到期应该完成的工单数
     */
	Long getExpires(Map<String, Object> params);
    /**
     * @author Ryze
     * @date 2017-7-3
     * @param params
     * @return 工单发起 30分钟内 接收的工单数
     */
	Long getGoodReceive(Map<String, Object> params);
    /**
     * @author Ryze
     * @date 2017-7-4
     * @param params
     * @return 统计首次接单个数
     */
	Long getFirstTimeCount(Map<String, Object> params);
    /**
     * @author Ryze
     * @date 2017-7-4
     * @param params
     * @return 统计催单个数
     */
	Long getReminderCount(Map<String, Object> params);
    /**
     * @author Ryze
     * @date 2017-7-4
     * @param params
     * @return 统计不同操作类型工单个数
     */
    Long getResultCount(Map<String, Object> params);

    /**]
     * 通过条件查询工单库: 查询门店库/门店库
     *      不带分页,且查询字段详细,用于导出文件
     * @param params 条件
     * @author Paul
     * @return
     */
	List<WorkOrderForExport> findAllStoreWorkOrder(Map<String, Object> params);
    /**
     *修改  状态 预计完成时间和方案
     */
    void serviceUpdate(WorkOrder workOrder);

    /**
     * 查询 推出去 工单
     * @param id
     * @return
     */
    WorkOrderServiceDto pushWordOrder(Long id);
    /**
     * 通过code 查询  工单id
     * @param code
     * @return
     */
    Long getByCode(String code);

    /**
     * 获取推送失败的工单列表
     * @param params
     * @return
     */
    List<MdniOrder> getOrderInfoFailList(Map<String, Object> params);
    /**
     * 获取推送失败的工单列表条数
     * @param params
     * @return
     */
    Long getOrderInfoFailListTotal(Map<String, Object> params);



    /**
     * @author Ryze
     * @date 2017-10-9 16:53:14
     * 查询 当月 公司/部门下的工单详情
     * @param params
     * @return
     */
    List<WorkOrder>findAllListForCall(Map<String, Object> params);
}
