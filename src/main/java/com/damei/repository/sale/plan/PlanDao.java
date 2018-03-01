package com.damei.repository.sale.plan;

import com.damei.entity.sale.plan.UserPlan;
import com.damei.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanDao extends CrudDao<UserPlan> {

    List<UserPlan> getPlans(@Param("userId") Long userId, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
