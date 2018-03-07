package cn.damei.rest.sale.dameiorganization;

import cn.damei.dto.OrganizationTreeDto;
import cn.damei.service.sale.dameiorganization.DameiOrganizationService;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusBootTableDto;
import cn.damei.dto.StatusDto;
import cn.damei.entity.sale.account.User;
import cn.damei.entity.sale.dameiorganization.DameiOrganization;
import cn.damei.entity.sale.dameiorganization.zTreeOrg;
import cn.damei.enumeration.oa.DEPTYPE;
import cn.damei.service.sale.account.UserService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/org")
public class DameiOrganizationController extends BaseController {
    @Autowired
    DameiOrganizationService dameiOrganizationService;
    @Autowired
    private UserService userService;
    @RequestMapping("/findAll")
    public Object findAll(@RequestParam(required = false,defaultValue = "COMPANY") String type) {
        List<DameiOrganization> dameiOrganizationList = dameiOrganizationService.findByType(type);
        return StatusDto.buildDataSuccessStatusDto(dameiOrganizationList);
    }

    @RequestMapping("/findByLogginUser/{companyId}")
    public Object findByLogginUser(@PathVariable Long companyId) {

        DameiOrganization comp = dameiOrganizationService.getById(companyId);
        return StatusDto.buildDataSuccessStatusDto(comp);
    }


    @RequestMapping("/fetchTree")
    public Object fetchTree(){
        List<zTreeOrg> zTreeOrgList= dameiOrganizationService.fetchTree();

        return StatusDto.buildDataSuccessStatusDto(zTreeOrgList);
    }
    @RequestMapping("/fetchDept")
    public Object fetchDeptByUser(){

        ShiroUser user= WebUtils.getLoggedUser();
        List<zTreeOrg> zTreeOrgList= dameiOrganizationService.fetchDept(Long.parseLong(user.getCompany()));

        return StatusDto.buildDataSuccessStatusDto(zTreeOrgList);
    }
    @RequestMapping("/fetchDeptById/{id}")
    public Object fetchDeptById(@PathVariable Long id){
        List<zTreeOrg> zTreeOrgList=new ArrayList<>();
        if(id!=null){
            zTreeOrgList= dameiOrganizationService.fetchDept(id);
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
        List<DameiOrganization> dameiOrganizationList = dameiOrganizationService.findDepartment(familyCode);
        return StatusDto.buildDataSuccessStatusDto(dameiOrganizationList);
    }

    /**
     * 通过familyCode 查询全部供应商
     * @param familyCode
     * @return
     */
    @RequestMapping("/findSuppliers/{familyCode}")
    public Object findSuppliers(@PathVariable String familyCode) {
        return StatusDto.buildDataSuccessStatusDto(dameiOrganizationService.findSuppliers(familyCode));
    }

    /**
     * 加载组织机构jstree(使用中)
     * @return
     */
    @RequestMapping("/tree")
    public Object tree(){
        StatusDto result=StatusDto.buildDataSuccessStatusDto();
        List<DameiOrganization> list= dameiOrganizationService.findAll();
        OrganizationTreeDto tree = DameiOrganization.buildOrganizationTree(new OrganizationTreeDto(0L), list);
        result.setData(tree.getChildren());
        return result;

    }

    @RequestMapping("/jobTree/{userId}")
    public Object jobTree(@PathVariable Long userId){
        List<DameiOrganization> list = dameiOrganizationService.findAll();
        User user = userService.getById(userId);
        return StatusDto.buildDataSuccessStatusDto(DameiOrganization.buildOrgTreeWithJob(
                new OrganizationTreeDto(0L), list, user).getChildren());

    }

    @RequestMapping("/delete/{id}")
    public Object deleteById(@PathVariable Long id) {
        if (null == id) {
            return StatusDto.buildFailureStatusDto("ID不能为空");
        }
        DameiOrganization dameiOrganization = dameiOrganizationService.getById(id);
        if(dameiOrganization.getParentId()==0){
            return StatusDto.buildDataFailureStatusDto("初始化数据不允许删除");
        }

        dameiOrganizationService.disableOrg(id);
        return StatusDto.buildDataSuccessStatusDto("删除成功");
    }
    /**
     * 添加或编辑组织架构
     * @param dameiOrganization
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object add(@RequestBody DameiOrganization dameiOrganization) {

        if(dameiOrganization == null){
            return StatusDto.buildFailureStatusDto("请求参数不能为空!");
        }

        if (dameiOrganizationService.validateCodeAvailable(dameiOrganization)) {
            return StatusDto.buildDataFailureStatusDto("编码重复！");
        }

        //如果是 type=SUPPLIER,就给 depType赋值SUPPLIER
        if("SUPPLIER".equals(dameiOrganization.getType())){
            dameiOrganization.setDepType(DEPTYPE.SUPPLIER);
        }

        dameiOrganizationService.saveOrUpdate(dameiOrganization);
        OrganizationTreeDto dto = new OrganizationTreeDto(dameiOrganization.getId(), dameiOrganization.getOrgName());
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

        return StatusBootTableDto.buildDataSuccessStatusDto(dameiOrganizationService.searchScrollPage(params, page));
    }

    /**
     * 根据id获取用户信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StatusDto getById(@PathVariable(value = "id") Long id) {

        DameiOrganization org = dameiOrganizationService.getById(id);
        if (org == null) {
            return StatusDto.buildFailureStatusDto("获取组织架构失败！");
        }
        return StatusDto.buildDataSuccessStatusDto(org);

    }

}