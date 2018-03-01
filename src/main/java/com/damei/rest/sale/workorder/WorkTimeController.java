package com.damei.rest.sale.workorder;

import com.damei.common.BaseController;
import com.damei.dto.StatusBootTableDto;
import com.damei.dto.StatusDto;
import com.damei.entity.sale.account.User;
import com.damei.entity.sale.workorder.TreamentApproval;
import com.damei.entity.sale.workorder.TreamentResult;
import com.damei.service.sale.account.UserService;
import com.damei.service.sale.workorder.TreamentApprovalService;
import com.damei.service.sale.workorder.TreamentResultService;
import com.damei.service.sale.workorder.WorkOrderService;
import com.damei.shiro.ShiroUser;
import com.damei.utils.MapUtils;
import com.damei.utils.WebUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/mdni/worktime")
public class WorkTimeController extends BaseController {


    @Autowired
    private WorkOrderService workOrderService;
    @Autowired
    private TreamentApprovalService treamentApprovalService;
    @Autowired
    private TreamentResultService treamentResultService;
    @Autowired
    private UserService userService;

    /**
     *  申请
     * @param treamentApproval
     * @return
     */
    @RequestMapping(value = "/approval")
    public Object approval(@RequestBody TreamentApproval treamentApproval) {
            try {
                treamentApproval.setCreateDate(new Date());
                treamentApproval.setCreateUser(WebUtils.getLoggedUser().getId());
                //0 申请
              return   treamentApprovalService.insertApproval(treamentApproval,"0");
            }catch (Exception e){
                e.printStackTrace();
                return StatusDto.buildDataFailureStatusDto("申请失败");
            }
        }
    /**
     *  审批列表
     *      一个领导管理多个部门,可看到多个部门的审批数据
     *     去查询当前用户的partTimeJob;其就是兼职部门的id
     * @return
     */
    @RequestMapping(value = "/approvalList")
    public Object approvalList(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "offset", defaultValue = "0") int offset,
                               @RequestParam(value = "pageSize", defaultValue = "20") int limit,
                               @RequestParam(value = "sortName", defaultValue = "create_date") String orderColumn,
                               @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {
              //查看当前人是不是责任人 0 不是 1是
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        String departmentHead = loggedUser.getDepartmentHead();
        //根据登录人  获取其详细信息
        User user = userService.getById(loggedUser.getId());

        if(StringUtils.isEmpty(departmentHead) || departmentHead.equals("0")){
            return StatusDto.buildDataFailureStatusDto("当前人不是部门负责人");
        }else {
            
            Map<String, Object> params = Maps.newHashMap();
            MapUtils.putNotNull(params, "keyword", keyword);
            MapUtils.putNotNull(params, "status", "0");
            MapUtils.putNotNull(params, "company", loggedUser.getCompany());
            //MapUtils.putNotNull(params, "department", loggedUser.getDepartment());


            List<Long> partTimeJobList = new ArrayList<Long>();
            if(StringUtils.isNotBlank(loggedUser.getDepartment())){
                //将该用户本来所在部门添加到 部门集合中
                partTimeJobList.add(Long.parseLong(loggedUser.getDepartment()));
            }

            //从 partTimeJob 中获取兼职部门id
            if(StringUtils.isNotBlank(user.getPartTimeJob())){
                String[] ids = user.getPartTimeJob().split("&");
                for (int i = 0; i < ids.length; i++) {
                    partTimeJobList.add(Long.parseLong(ids[i]));
                }
            }
            //有数据 加入到 参数集合中
            if(partTimeJobList.size() > 0){
                params.put("partTimeJobList", partTimeJobList);
            }

            PageRequest pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.valueOf(orderSort.toUpperCase()), orderColumn));
            Page<TreamentApproval> approvals = treamentApprovalService.approvalList(params, pageable);
            return StatusBootTableDto.buildDataSuccessStatusDto(approvals);
        }
    }
    /**
     *  通过id 查找 一条申请
     * @return
     */
    @RequestMapping(value = "/approvalById")
    public Object approvalById(@RequestParam(value = "id") Long id) {
            return StatusDto.buildDataSuccessStatusDto(treamentApprovalService.getById(id));
    }

    /**
     *  审批
     * @param result
     * @return
     */
    @RequestMapping(value = "/result")
    public Object result(@RequestBody TreamentResult result) {
            result.setCreateDate(new Date());
            result.setCreateUser(WebUtils.getLoggedUser().getId());
            return StatusDto.buildDataSuccessStatusDto(treamentApprovalService.result(result));
    }
    /**
     *  驳回记录
     * @return
     */
    @RequestMapping(value = "/back")
    public Object back(@RequestParam(value = "workorderId") Long workorderId) {
            return StatusDto.buildDataSuccessStatusDto(treamentApprovalService.getBack(workorderId));
    }

}
