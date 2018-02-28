package com.rocoinfo.repository.sale.organization;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.organization.MdniOrganization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
/*
/*@author asher
/*@time 2017-03-08 14:14:18
**/
@Repository
public interface MdniOrganizationDao extends CrudDao<MdniOrganization> {

    /**
     * 通过组织机构代码获取组织机构
     * @return
     */
    MdniOrganization getByCode(String orgCode);

    /**
     * 禁用组织架构
     * @param id
     */
    void disableOrg(@Param("id") Long id);

    /**
     * 根据家庭码查询部门--type是部门
     * @param familyCode
     * @return
     */
    List<MdniOrganization> findDepartment(@Param("familyCode") String familyCode);

    /**
     * 查询集团的部门
     * @return
     */
    List<MdniOrganization> findGroupDepartment();

    /**
     * 查询部门
     * @param
     * @return
     */
    List<MdniOrganization> findDept(@Param("parentId") Long id);

    /**
     * 查询公司
     * @return
     */
    List<MdniOrganization> findByType(@Param("type") String type);

    /**
     * 根据传入的末尾code查找相应组织
     * @param code
     * @return
     */
    MdniOrganization getOrgByLastCode(@Param("code") String code);

    /**
     * 通过familyCode 查询全部供应商
     * @param familyCode
     * @return
     */
    List<MdniOrganization> findSuppliers(@Param("familyCode") String familyCode);
    /**
     * @author Ryze
     * @date 2017/10/17 15:18
     * @description 查询分店/公司
     * @param   parentId 父id 默认98
     * @return
     */
    List<MdniOrganization> findCompany(Integer parentId);
}