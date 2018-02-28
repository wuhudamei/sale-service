package com.rocoinfo.rest.sale.organization;

import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.OrganizationTreeDto;
import com.rocoinfo.dto.StatusBootTableDto;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.sale.account.User;
import com.rocoinfo.entity.sale.organization.MdniOrganization;
import com.rocoinfo.entity.sale.organization.zTreeOrg;
import com.rocoinfo.enumeration.oa.DEPTYPE;
import com.rocoinfo.service.sale.account.UserService;
import com.rocoinfo.service.sale.organization.MdniOrganizationService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.MapUtils;
import com.rocoinfo.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * /*
 * /*@author asher
 * /*@time 2017-03-08 14:14:18
 **/
@RestController
@RequestMapping(value = "/api/org")
public class MdniOrganizationController extends BaseController {
    @Autowired
    MdniOrganizationService mdniOrganizationService;
    @Autowired
    private UserService userService;

    /**
     * 通过类型 查询组织机构
     * @author Paul 修改
     * @date 2017年7月4日 下午2:00:45
     * @param type 类型
     * @return
     */
    @RequestMapping("/findAll")
    public Object findAll(@RequestParam(required = false,defaultValue = "COMPANY") String type) {
        List<MdniOrganization> mdniOrganizationList = mdniOrganizationService.findByType(type);
        return StatusDto.buildDataSuccessStatusDto(mdniOrganizationList);
    }

    @RequestMapping("/findByLogginUser/{companyId}")
    public Object findByLogginUser(@PathVariable Long companyId) {

        MdniOrganization comp = mdniOrganizationService.getById(companyId);
        return StatusDto.buildDataSuccessStatusDto(comp);
    }


    @RequestMapping("/fetchTree")
    public Object fetchTree(){
        List<zTreeOrg> zTreeOrgList=mdniOrganizationService.fetchTree();

        return StatusDto.buildDataSuccessStatusDto(zTreeOrgList);
    }
    @RequestMapping("/fetchDept")
    public Object fetchDeptByUser(){

        ShiroUser user= WebUtils.getLoggedUser();
        List<zTreeOrg> zTreeOrgList=mdniOrganizationService.fetchDept(Long.parseLong(user.getCompany()));

        return StatusDto.buildDataSuccessStatusDto(zTreeOrgList);
    }
    @RequestMapping("/fetchDeptById/{id}")
    public Object fetchDeptById(@PathVariable Long id){
        List<zTreeOrg> zTreeOrgList=new ArrayList<>();
        if(id!=null){
            zTreeOrgList=mdniOrganizationService.fetchDept(id);
        }

        return StatusDto.buildDataSuccessStatusDto(zTreeOrgList);
    }

    /**
     * 通过familyCode 查询全部部门
     * @param familyCode
     * @return
     */
    @RequestMapping("/findDepartment/{familyCode}")
    public Object findDepartment(@PathVariable String familyCode) {
        List<MdniOrganization> mdniOrganizationList = mdniOrganizationService.findDepartment(familyCode);
        return StatusDto.buildDataSuccessStatusDto(mdniOrganizationList);
    }

    /**
     * 通过familyCode 查询全部供应商
     * @param familyCode
     * @return
     */
    @RequestMapping("/findSuppliers/{familyCode}")
    public Object findSuppliers(@PathVariable String familyCode) {
        return StatusDto.buildDataSuccessStatusDto(mdniOrganizationService.findSuppliers(familyCode));
    }

    /**
     * 加载组织机构jstree(使用中)
     * @return
     */
    @RequestMapping("/tree")
    public Object tree(){
        StatusDto result=StatusDto.buildDataSuccessStatusDto();
        List<MdniOrganization> list=mdniOrganizationService.findAll();
        OrganizationTreeDto tree = MdniOrganization.buildOrganizationTree(new OrganizationTreeDto(0L), list);
        result.setData(tree.getChildren());
        return result;

    }

    /**
     * 在用户列表中加载 jsTree 并选中和展开 已选的兼职部门
     * @author Paul
     * @param userId 用户id
     * @return
     */
    @RequestMapping("/jobTree/{userId}")
    public Object jobTree(@PathVariable Long userId){
        List<MdniOrganization> list = mdniOrganizationService.findAll();
        User user = userService.getById(userId);
        return StatusDto.buildDataSuccessStatusDto(MdniOrganization.buildOrgTreeWithJob(
                new OrganizationTreeDto(0L), list, user).getChildren());

    }

    @RequestMapping("/delete/{id}")
    public Object deleteById(@PathVariable Long id) {
        if (null == id) {
            return StatusDto.buildFailureStatusDto("ID不能为空");
        }
        MdniOrganization mdniOrganization = mdniOrganizationService.getById(id);
        if(mdniOrganization.getParentId()==0){
            return StatusDto.buildDataFailureStatusDto("初始化数据不允许删除");
        }

        mdniOrganizationService.disableOrg(id);
        return StatusDto.buildDataSuccessStatusDto("删除成功");
    }
    /**
     * 添加或编辑组织架构
     * @param mdniOrganization
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object add(@RequestBody MdniOrganization mdniOrganization) {

        if(mdniOrganization == null){
            return StatusDto.buildFailureStatusDto("请求参数不能为空!");
        }

        if (mdniOrganizationService.validateCodeAvailable(mdniOrganization)) {
            return StatusDto.buildDataFailureStatusDto("编码重复！");
        }

        //如果是 type=SUPPLIER,就给 depType赋值SUPPLIER
        if("SUPPLIER".equals(mdniOrganization.getType())){
            mdniOrganization.setDepType(DEPTYPE.SUPPLIER);
        }

        mdniOrganizationService.saveOrUpdate(mdniOrganization);
        OrganizationTreeDto dto = new OrganizationTreeDto(mdniOrganization.getId(), mdniOrganization.getOrgName());
        return StatusDto.buildDataSuccessStatusDto(dto);
    }

    @RequestMapping("/list")
    @Deprecated
    public Object list(@RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "status",defaultValue = "1", required = false) String status,
                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                       @RequestParam(value = "sortName", defaultValue = "id") String orderColumn,
                       @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "status", status);

        PageRequest page = new PageRequest(offset, pageSize, new Sort(Sort.Direction.valueOf(orderSort.toUpperCase()), orderColumn));

        return StatusBootTableDto.buildDataSuccessStatusDto(mdniOrganizationService.searchScrollPage(params, page));
    }

    /**
     * 根据id获取用户信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StatusDto getById(@PathVariable(value = "id") Long id) {

        MdniOrganization org = mdniOrganizationService.getById(id);
        if (org == null) {
            return StatusDto.buildFailureStatusDto("获取组织架构失败！");
        }
        return StatusDto.buildDataSuccessStatusDto(org);

    }

}