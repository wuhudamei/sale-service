package com.rocoinfo.service.sale.plan;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.sale.plan.UserPlan;
import com.rocoinfo.repository.sale.plan.PlanDao;

/**
 * <dl>
 * <dd>Description: 用户计划相关逻辑处理</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/15 10:55</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
@Service
public class PlanService extends CrudService<PlanDao,UserPlan>{

    public List<UserPlan> getPlans(Long userId, String startTime, String endTime) {

//        List<UserPlan> plans = Lists.newArrayList();
//        plans.add(new UserPlan(1001L, "这是一个工作计划", "2017-03-02 12:25:37", "2017-03-03 13:25:37"));
//        plans.add(new UserPlan(1001L, "这是一个工作计划", "2017-03-04 12:25:37", "2017-03-05 13:25:37"));
//        plans.add(new UserPlan(1001L, "这是一个工作计划", "2017-03-18 12:25:37", "2017-03-18 13:25:37"));

        List<UserPlan> plans = this.entityDao.getPlans(userId,startTime,endTime);

        return plans;
    }

    /**
     * 新建工作计划
     */
    public void insert(UserPlan plan) {

        super.insert(plan);
    }
}
