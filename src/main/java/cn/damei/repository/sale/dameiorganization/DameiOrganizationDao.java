package cn.damei.repository.sale.dameiorganization;

import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sale.dameiorganization.DameiOrganization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DameiOrganizationDao extends CrudDao<DameiOrganization> {

    DameiOrganization getByCode(String orgCode);

    void disableOrg(@Param("id") Long id);

    List<DameiOrganization> findDepartment(@Param("familyCode") String familyCode);

    List<DameiOrganization> findGroupDepartment();

    List<DameiOrganization> findDept(@Param("parentId") Long id);

    List<DameiOrganization> findByType(@Param("type") String type);

    DameiOrganization getOrgByLastCode(@Param("code") String code);

    List<DameiOrganization> findSuppliers(@Param("familyCode") String familyCode);
    List<DameiOrganization> findCompany(Integer parentId);
}