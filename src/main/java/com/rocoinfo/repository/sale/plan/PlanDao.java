package com.rocoinfo.repository.sale.plan;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.plan.UserPlan;

/**
 * <dl>
 * <dd>Description: 计划管理的dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/15 14:41</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
@Repository
public interface PlanDao extends CrudDao<UserPlan> {

    List<UserPlan> getPlans(@Param("userId") Long userId, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
