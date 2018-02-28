package com.rocoinfo.repository.sale.workorder;


import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.workorder.TreamentResult;
import org.springframework.stereotype.Repository;

/**
 * <dl>
 * <dd>Description: 审批预计完成时间 结果记录 dao</dd>
 * <dd>Company: mdni</dd>
 * <dd>@date：2017-8-24 15:38:44</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Repository
public interface TreamentResultDao extends CrudDao<TreamentResult> {
    TreamentResult getBack(Long workorderId);
}