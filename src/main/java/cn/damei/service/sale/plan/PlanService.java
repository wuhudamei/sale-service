package cn.damei.service.sale.plan;

import cn.damei.common.service.CrudService;
import cn.damei.entity.sale.plan.UserPlan;
import cn.damei.repository.sale.plan.PlanDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService extends CrudService<PlanDao,UserPlan> {

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
