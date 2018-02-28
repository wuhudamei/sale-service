package com.rocoinfo.rest.sale.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.sale.plan.UserPlan;
import com.rocoinfo.service.sale.plan.PlanService;
import com.rocoinfo.utils.WebUtils;

/**
 * <dl>
 * <dd>Description: 用户计划相关接口r</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/15 10:51</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
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
