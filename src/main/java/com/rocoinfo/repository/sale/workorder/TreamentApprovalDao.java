package com.rocoinfo.repository.sale.workorder;


import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.workorder.TreamentApproval;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dd>Description: 审批预计完成时间 申请记录 Dao</dd>
 * <dd>Company: mdni</dd>
 * <dd>@date：2017-8-24 15:38:44</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Repository
public interface TreamentApprovalDao extends CrudDao<TreamentApproval> {
    /**
     * 查询申请列表
     * @param params
     * @return
     */
     List<TreamentApproval> approvalList(Map<String, Object> params);
}