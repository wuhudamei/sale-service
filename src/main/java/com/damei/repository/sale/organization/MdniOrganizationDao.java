package com.damei.repository.sale.organization;

import com.damei.entity.sale.organization.MdniOrganization;
import com.damei.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MdniOrganizationDao extends CrudDao<MdniOrganization> {

    MdniOrganization getByCode(String orgCode);

    void disableOrg(@Param("id") Long id);

    List<MdniOrganization> findDepartment(@Param("familyCode") String familyCode);

    List<MdniOrganization> findGroupDepartment();

    List<MdniOrganization> findDept(@Param("parentId") Long id);

    List<MdniOrganization> findByType(@Param("type") String type);

    MdniOrganization getOrgByLastCode(@Param("code") String code);

    List<MdniOrganization> findSuppliers(@Param("familyCode") String familyCode);
    List<MdniOrganization> findCompany(Integer parentId);
}