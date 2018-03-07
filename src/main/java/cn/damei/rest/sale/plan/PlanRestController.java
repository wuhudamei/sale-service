package cn.damei.rest.sale.plan;

import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.sale.plan.UserPlan;
import cn.damei.service.sale.plan.PlanService;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plans")
public class PlanRestController extends BaseController {

    @Autowired
    private PlanService planService;

    @RequestMapping("/getPlans")
    public StatusDto getPlans(@RequestParam(value = "startTime") String startTime,
                              @RequestParam(value = "endTime") String endTime) {

        Long userId = WebUtils.getLoggedUser().getId();

        return StatusDto.buildDataSuccessStatusDto(planService.getPlans(userId, startTime, endTime));
    }

    /**
     * 新建工作计划
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public StatusDto createPlan(UserPlan plan) {

        Long loginUserId = WebUtils.getLoggedUser().getId();

        plan.setUserId(loginUserId);

        planService.insert(plan);

        return StatusDto.buildDataSuccessStatusDto("创建工作计划成功！");
    }
}
