package com.rocoinfo.service.sale.organization;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.sale.account.User;
import com.rocoinfo.entity.sale.organization.MdniOrganization;
import com.rocoinfo.entity.sale.organization.zTreeOrg;
import com.rocoinfo.repository.sale.organization.MdniOrganizationDao;
import com.rocoinfo.utils.DateUtils;
import com.rocoinfo.utils.WebUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * /*
 * /*@author asher
 * /*@time 2017-03-08 14:14:18
 **/
@Service
public class MdniOrganizationService extends CrudService<MdniOrganizationDao, MdniOrganization> {

    public MdniOrganization getByCode(@Param("orgCode") String orgCode) {
        return this.entityDao.getByCode(orgCode);
    }

    /**
     * 检查code是否重复
     *
     * @param mdniOrganization
     * @return
     */
    public boolean validateCodeAvailable(MdniOrganization mdniOrganization) {
        boolean flag = true;
        String orgCode = mdniOrganization.getOrgCode();
        MdniOrganization mdniOrganization1 = this.entityDao.getByCode(orgCode);
        if (mdniOrganization1 == null) {
            flag = false;
        } else {
            //只有查到的和自己传入的一样才能返回false
            if (mdniOrganization.getId() != null) {
                String selfCode = this.entityDao.getById(mdniOrganization.getId()).getOrgCode();
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
     * @param mdniOrganization
     * @return
     */
    public Object saveOrUpdate(MdniOrganization mdniOrganization) {
        String familyCode = "";
        MdniOrganization orgFamily = null;
        if (mdniOrganization.getId() == null) {
            User user = new User(WebUtils.getLoggedUser().getId());
            mdniOrganization.setCreateUser(user.getId());
            mdniOrganization.setCreateDate(DateUtils.format(new Date(), "yyyy-MM-dd"));
            this.entityDao.insert(mdniOrganization);
            orgFamily = this.entityDao.getByCode(mdniOrganization.getOrgCode());
        } else {
            orgFamily = this.entityDao.getById(mdniOrganization.getId());
            orgFamily.setOrgCode(mdniOrganization.getOrgCode());
            orgFamily.setOrgName(mdniOrganization.getOrgName());
            orgFamily.setParentId(mdniOrganization.getParentId());
            orgFamily.setType(mdniOrganization.getType());
            orgFamily.setDepType(mdniOrganization.getDepType());
        }
        if (mdniOrganization.getParentId() != 0) {
            familyCode = this.entityDao.getById(mdniOrganization.getParentId()).getFamilyCode() + "-" + orgFamily.getId();
            orgFamily.setFamilyCode(familyCode);
        }else {
            orgFamily.setFamilyCode(mdniOrganization.getFamilyCode());
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
    public List<MdniOrganization> findByType(String type) {
        List<MdniOrganization> mdniOrganizationList = this.entityDao.findByType(type);
        return mdniOrganizationList;
    }
    /**
     * 查询全部数据，返回ZTREE格式
     *
     * @return
     */
    public List<zTreeOrg> fetchTree() {
        List<MdniOrganization> mdniOrganizationList = this.entityDao.findAll();
        List<zTreeOrg> zTreeOrgList = new ArrayList<>();
        for (MdniOrganization mdniOrganization : mdniOrganizationList) {
            zTreeOrg z=new zTreeOrg();
            z.setId(mdniOrganization.getId());
            z.setpId(mdniOrganization.getParentId());
            z.setName(mdniOrganization.getOrgName());
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
        List<MdniOrganization> mdniOrganizationList = this.entityDao.findDept(id);
        List<zTreeOrg> zTreeOrgList = new ArrayList<>();
        zTreeOrg z1=new zTreeOrg();
        z1.setId(0L);
        z1.setpId(0L);
        z1.setName("全部");
        zTreeOrgList.add(z1);
        for (MdniOrganization mdniOrganization : mdniOrganizationList) {
            zTreeOrg z=new zTreeOrg();
            z.setId(mdniOrganization.getId());
            z.setpId(mdniOrganization.getParentId());
            z.setName(mdniOrganization.getOrgName());
            zTreeOrgList.add(z);
        }
        return zTreeOrgList;
    }

    /**
     * 查询全部部门
     *
     * @return
     */
    public List<MdniOrganization> findDepartment(String familyCode) {
        List<MdniOrganization> organizationList=ListUtils.EMPTY_LIST;
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
        MdniOrganization mdniOrganization= this.entityDao.getOrgByLastCode(code);
        String familyArr[] = mdniOrganization.getFamilyCode().split("-");
        for (int i = familyArr.length-1; i >0 ; i--) {
            MdniOrganization mdniOrganization1 = this.entityDao.getById(Long.parseLong(familyArr[i]));
            if (mdniOrganization1.getType().equals("DEPARTMENT")) {
                if(paramsMap.get("DEPARTMENT")==null) {
                    paramsMap.put("DEPARTMENT", mdniOrganization1.getId());
                }
            } else if (mdniOrganization1.getType().equals("COMPANY")) {
                paramsMap.put("COMPANY",mdniOrganization1.getId());
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
    public List<MdniOrganization> findSuppliers(String familyCode) {
        return this.entityDao.findSuppliers(familyCode + "-");
    }
    /**
     * @author Ryze
     * @date 2017/10/17 15:16
     * @description 查询分店/公司
     * @param
     * @return
     */
    public List<MdniOrganization> findCompany(Integer parentId) {
        return this.entityDao.findCompany(parentId);
    }
}