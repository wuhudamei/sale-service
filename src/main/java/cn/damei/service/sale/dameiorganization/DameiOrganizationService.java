package cn.damei.service.sale.dameiorganization;

import cn.damei.common.service.CrudService;
import cn.damei.dto.StatusDto;
import cn.damei.entity.sale.account.User;
import cn.damei.entity.sale.dameiorganization.DameiOrganization;
import cn.damei.entity.sale.dameiorganization.zTreeOrg;
import cn.damei.repository.sale.dameiorganization.DameiOrganizationDao;
import cn.damei.utils.DateUtils;
import cn.damei.utils.WebUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class DameiOrganizationService extends CrudService<DameiOrganizationDao, DameiOrganization> {

    public DameiOrganization getByCode(@Param("orgCode") String orgCode) {
        return this.entityDao.getByCode(orgCode);
    }

    /**
     * 检查code是否重复
     *
     * @param dameiOrganization
     * @return
     */
    public boolean validateCodeAvailable(DameiOrganization dameiOrganization) {
        boolean flag = true;
        String orgCode = dameiOrganization.getOrgCode();
        DameiOrganization dameiOrganization1 = this.entityDao.getByCode(orgCode);
        if (dameiOrganization1 == null) {
            flag = false;
        } else {
            //只有查到的和自己传入的一样才能返回false
            if (dameiOrganization.getId() != null) {
                String selfCode = this.entityDao.getById(dameiOrganization.getId()).getOrgCode();
                if (selfCode.equals(orgCode)) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * 添加或编辑组织架构
     *
     * @param dameiOrganization
     * @return
     */
    public Object saveOrUpdate(DameiOrganization dameiOrganization) {
        String familyCode = "";
        DameiOrganization orgFamily = null;
        if (dameiOrganization.getId() == null) {
            User user = new User(WebUtils.getLoggedUser().getId());
            dameiOrganization.setCreateUser(user.getId());
            dameiOrganization.setCreateDate(DateUtils.format(new Date(), "yyyy-MM-dd"));
            this.entityDao.insert(dameiOrganization);
            orgFamily = this.entityDao.getByCode(dameiOrganization.getOrgCode());
        } else {
            orgFamily = this.entityDao.getById(dameiOrganization.getId());
            orgFamily.setOrgCode(dameiOrganization.getOrgCode());
            orgFamily.setOrgName(dameiOrganization.getOrgName());
            orgFamily.setParentId(dameiOrganization.getParentId());
            orgFamily.setType(dameiOrganization.getType());
            orgFamily.setDepType(dameiOrganization.getDepType());
        }
        if (dameiOrganization.getParentId() != 0) {
            familyCode = this.entityDao.getById(dameiOrganization.getParentId()).getFamilyCode() + "-" + orgFamily.getId();
            orgFamily.setFamilyCode(familyCode);
        }else {
            orgFamily.setFamilyCode(dameiOrganization.getFamilyCode());
        }

        this.entityDao.update(orgFamily);
        return StatusDto.buildDataSuccessStatusDto();
    }

    /**
     * 禁用组织架构
     *
     * @param id
     */
    public void disableOrg(Long id) {
        this.entityDao.disableOrg(id);
    }

    /**
     * 查询全部公司
     *
     * @return'
     *
     */
    public List<DameiOrganization> findByType(String type) {
        List<DameiOrganization> dameiOrganizationList = this.entityDao.findByType(type);
        return dameiOrganizationList;
    }
    /**
     * 查询全部数据，返回ZTREE格式
     *
     * @return
     */
    public List<zTreeOrg> fetchTree() {
        List<DameiOrganization> dameiOrganizationList = this.entityDao.findAll();
        List<zTreeOrg> zTreeOrgList = new ArrayList<>();
        for (DameiOrganization dameiOrganization : dameiOrganizationList) {
            zTreeOrg z=new zTreeOrg();
            z.setId(dameiOrganization.getId());
            z.setpId(dameiOrganization.getParentId());
            z.setName(dameiOrganization.getOrgName());
            zTreeOrgList.add(z);
        }
        return zTreeOrgList;
    }
    /**
     * 查询全部部门，返回ZTREE格式
     *
     * @return
     */
    public List<zTreeOrg> fetchDept(Long id) {
        List<DameiOrganization> dameiOrganizationList = this.entityDao.findDept(id);
        List<zTreeOrg> zTreeOrgList = new ArrayList<>();
        zTreeOrg z1=new zTreeOrg();
        z1.setId(0L);
        z1.setpId(0L);
        z1.setName("全部");
        zTreeOrgList.add(z1);
        for (DameiOrganization dameiOrganization : dameiOrganizationList) {
            zTreeOrg z=new zTreeOrg();
            z.setId(dameiOrganization.getId());
            z.setpId(dameiOrganization.getParentId());
            z.setName(dameiOrganization.getOrgName());
            zTreeOrgList.add(z);
        }
        return zTreeOrgList;
    }

    /**
     * 查询全部部门
     *
     * @return
     */
    public List<DameiOrganization> findDepartment(String familyCode) {
        List<DameiOrganization> organizationList=ListUtils.EMPTY_LIST;
        if(!familyCode.equals("98")) {
            //普通门店
            organizationList=this.entityDao.findDepartment(familyCode + "-");
        }else {
            organizationList=this.entityDao.findGroupDepartment();
        }
        return organizationList;
    }

    /**
     * 通过最后一个节点返回该节点最近的部门和公司
     * @param code
     * @return
     */
    public Object getOrgByLastCode(String code) {
        Map<String, Long> paramsMap = new HashMap<>();
        DameiOrganization dameiOrganization = this.entityDao.getOrgByLastCode(code);
        String familyArr[] = dameiOrganization.getFamilyCode().split("-");
        for (int i = familyArr.length-1; i >0 ; i--) {
            DameiOrganization dameiOrganization1 = this.entityDao.getById(Long.parseLong(familyArr[i]));
            if (dameiOrganization1.getType().equals("DEPARTMENT")) {
                if(paramsMap.get("DEPARTMENT")==null) {
                    paramsMap.put("DEPARTMENT", dameiOrganization1.getId());
                }
            } else if (dameiOrganization1.getType().equals("COMPANY")) {
                paramsMap.put("COMPANY", dameiOrganization1.getId());
                break;
            }
            continue;
        }
        return StatusDto.buildDataSuccessStatusDto(paramsMap);
    }

    /**
     * 通过familyCode 查询全部供应商
     * @param familyCode
     * @return
     */
    public List<DameiOrganization> findSuppliers(String familyCode) {
        return this.entityDao.findSuppliers(familyCode + "-");
    }
    public List<DameiOrganization> findCompany(Integer parentId) {
        return this.entityDao.findCompany(parentId);
    }
}