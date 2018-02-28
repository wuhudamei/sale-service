package com.rocoinfo.repository.sale.workorder;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.workorder.WorkorderPushFail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <dl>
 * <dd>Description: 美得你 推送工单失败dao</dd>
 * <dd>@date：2017/9/11  15:05</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Repository
public interface WorkorderPushFailDao extends CrudDao<WorkorderPushFail> {
    /**
     * 根据工单 id/失败类型 获取实体
     * @param workorderId
     * @return
     */
    List <WorkorderPushFail> getByWorkorderId(Long workorderId);

}
